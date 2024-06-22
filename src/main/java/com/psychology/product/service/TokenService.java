package com.psychology.product.service;

import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.repository.model.Token;
import com.psychology.product.util.Tokens;
import org.springframework.security.core.Authentication;

public interface TokenService {

    /**
     * Generates new access and refresh tokens for the given authentication object.
     *
     * @param authentication the authentication object containing user details
     * @return a Tokens object containing the new access and refresh tokens
     */
    Tokens generateNewTokens(Authentication authentication);

    /**
     * Generates a new access token using the given authentication object and refresh token.
     *
     * @param authentication the authentication object containing user details
     * @param refreshToken the refresh token used to generate a new access token
     * @return a Tokens object containing the new access token
     */
    Tokens generateAccessToken(Authentication authentication, String refreshToken);

    /**
     * Logs out the user and invalidates their current tokens.
     *
     * @param userDTO the user details of the user to log out
     */
    void logout(UserDTO userDTO);

    /**
     * Deactivates the given token.
     *
     * @param token the token to be deactivated
     */
    void deactivateToken(Token token);

    /**
     * Deactivates the current access token and saves the provided refresh token.
     *
     * @param authentication the authentication object containing user details
     * @param refreshToken the refresh token to be saved
     */
    void deactivateAccessAndSaveRefresh(Authentication authentication, String refreshToken);

    /**
     * Deactivates all previous tokens for the given user.
     *
     * @param userDTO the user details of the user whose previous tokens will be deactivated
     */
    void deactivatePreviousTokens(UserDTO userDTO);
}
