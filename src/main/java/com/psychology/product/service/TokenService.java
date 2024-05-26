package com.psychology.product.service;

import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.repository.model.TokenDAO;
import com.psychology.product.util.Tokens;
import org.springframework.security.core.Authentication;

public interface TokenService {

    Tokens generateNewTokens(Authentication authentication);

    Tokens generateAccessToken(Authentication authentication, String refreshToken);

    void logout(UserDTO userDTO);

    void deactivateToken(TokenDAO tokenDAO);

    void deactivateAccessAndSaveRefresh(Authentication authentication, String refreshToken);

    void deactivatePreviousTokens(UserDTO userDTO);
}
