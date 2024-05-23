package com.psychology.product.service.impl;

import com.psychology.product.repository.TokenRepository;
import com.psychology.product.repository.UserRepository;
import com.psychology.product.repository.dto.TokenDTO;
import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.repository.model.TokenDAO;
import com.psychology.product.service.JwtUtils;
import com.psychology.product.service.TokenService;
import com.psychology.product.service.UserService;
import com.psychology.product.service.mapper.TokenMapper;
import com.psychology.product.service.mapper.UserMapper;
import com.psychology.product.util.Tokens;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final TokenMapper tokenMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Override
    public Tokens recordTokens(Authentication authentication) {

        UserDTO user = userService.getUserFromEmail(authentication.getName());

        deactivatePreviousTokens(user);

        String jwtAccessToken = jwtUtils.generateJwtToken(authentication);
        String jwtRefreshToken = jwtUtils.generateRefreshToken(authentication);

        TokenDTO accessToken = new TokenDTO(jwtAccessToken, user);
        TokenDTO refreshToken = new TokenDTO(jwtRefreshToken, user);

        tokenRepository.save(tokenMapper.toDAO(accessToken));
        tokenRepository.save(tokenMapper.toDAO(refreshToken));

        return new Tokens(jwtAccessToken, jwtRefreshToken);
    }

    @Override
    public void logout(UserDTO user) {
        deactivatePreviousTokens(user);
    }

    @Override
    public void revokedToken(TokenDTO tokenDTO) {
    }

    @Override
    public boolean isTokenExpired(TokenDTO tokenDTO) {
        return false;
    }

    @Override
    public boolean isTokenRevoked(TokenDTO tokenDTO) {
        return false;
    }

    void deactivatePreviousTokens(UserDTO user) {
        List<TokenDAO> tokensList = tokenRepository.findAllByUser_Id(user.id());
        tokensList.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(tokensList);
    }

}
