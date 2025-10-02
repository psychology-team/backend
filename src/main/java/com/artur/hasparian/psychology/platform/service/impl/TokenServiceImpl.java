package com.artur.hasparian.psychology.platform.service.impl;

import com.artur.hasparian.psychology.platform.repository.TokenRepository;
import com.artur.hasparian.psychology.platform.repository.dto.TokenDTO;
import com.artur.hasparian.psychology.platform.repository.dto.UserDTO;
import com.artur.hasparian.psychology.platform.repository.model.Token;
import com.artur.hasparian.psychology.platform.service.JwtUtils;
import com.artur.hasparian.psychology.platform.service.TokenService;
import com.artur.hasparian.psychology.platform.service.UserService;
import com.artur.hasparian.psychology.platform.service.mapper.TokenMapper;
import com.artur.hasparian.psychology.platform.util.Tokens;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final TokenMapper tokenMapper;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Override
    public Tokens generateNewTokens(Authentication authentication) {
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
    public Tokens generateAccessToken(Authentication authentication, String refreshToken) {
        UserDTO user = userService.getUserFromEmail(authentication.getName());
        String jwtAccessToken = jwtUtils.generateJwtToken(authentication);
        TokenDTO accessToken = new TokenDTO(jwtAccessToken, user);
        deactivateAccessAndSaveRefresh(authentication, refreshToken);
        tokenRepository.save(tokenMapper.toDAO(accessToken));
        return new Tokens(jwtAccessToken, null);
    }

    @Override
    public void logout(UserDTO user) {
        deactivatePreviousTokens(user);
    }

    @Override
    public void deactivateToken(Token token) {
        token.setRevoked(true);
        token.setExpired(true);
        token.setUpdatedTimestamp(Instant.now());
        tokenRepository.save(token);
    }

    @Override
    public void deactivateAccessAndSaveRefresh(Authentication authentication, String refreshToken) {
        UserDTO user = userService.getUserFromEmail(authentication.getName());

        List<Token> tokensList = tokenRepository.findAllByUser_Id(user.id());
        tokensList.forEach(token -> {
            if (!token.getToken().equals(refreshToken)) {
                deactivateToken(token);
            }
        });
    }

    @Override
    public void deactivatePreviousTokens(UserDTO user) {
        List<Token> tokensList = tokenRepository.findAllByUser_Id(user.id());
        tokensList.forEach(this::deactivateToken);
        tokenRepository.saveAll(tokensList);
    }
}
