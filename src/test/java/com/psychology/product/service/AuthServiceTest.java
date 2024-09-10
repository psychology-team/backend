package com.psychology.product.service;

import com.psychology.product.controller.request.LoginRequest;
import com.psychology.product.controller.response.JwtResponse;
import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.repository.model.User;
import com.psychology.product.service.impl.AuthServiceImpl;
import com.psychology.product.util.Tokens;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private UserService userService;
    @Mock
    private TokenService tokenService;

    @Value("$jwt.secret.access")
    public static String jwtAccessToken;

    @Value("$jwt.secret.refresh")
    public static String jwtRefreshToken;

    String email;
    String password;
    String accessToken;
    String refreshToken;

    @BeforeEach
    void setUp() {
        email = "test@example.com";
        password = "password";
        accessToken = "accessToken";
        refreshToken = "refreshToken";
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail(email);
        user.setPassword(password);
        user.setEnabled(true);
        user.setRevoked(false);
    }

    @Test
    void loginUser_ShouldAuthenticateAndReturnJwtResponse() {
        LoginRequest loginRequest = new LoginRequest(email, password);
        Authentication authentication = mock(Authentication.class);
        Tokens generatedTokens = new Tokens(accessToken, refreshToken);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(tokenService.generateNewTokens(authentication))
                .thenReturn(generatedTokens);

        JwtResponse jwtResponse = authService.loginUser(loginRequest);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generateNewTokens(authentication);
        verifyNoMoreInteractions(authenticationManager, tokenService);

        assertEquals("accessToken", jwtResponse.jwtAccessToken());
        assertEquals("refreshToken", jwtResponse.jwtRefreshToken());
    }

    @Test
    void loginUser_ShouldThrowException_WhenAuthenticationFails() {
        LoginRequest loginRequest = new LoginRequest(email, "wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> authService.loginUser(loginRequest));

        assertEquals("Invalid credentials", exception.getMessage());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoMoreInteractions(authenticationManager, tokenService);
    }

    @Test
    void loginUser_ShouldSetSecurityContext_WhenAuthenticationIsSuccessful() {
        LoginRequest loginRequest = new LoginRequest(email, password);
        Authentication authentication = mock(Authentication.class);
        Tokens generatedTokens = new Tokens(accessToken, refreshToken);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(tokenService.generateNewTokens(authentication))
                .thenReturn(generatedTokens);

        JwtResponse jwtResponse = authService.loginUser(loginRequest);

        assertEquals(SecurityContextHolder.getContext().getAuthentication(), authentication);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generateNewTokens(authentication);
        verifyNoMoreInteractions(authenticationManager, tokenService);

        assertEquals("accessToken", jwtResponse.jwtAccessToken());
        assertEquals("refreshToken", jwtResponse.jwtRefreshToken());
    }

    @Test
    void getJwtAccessToken_ShouldReturnJwtResponse_WhenRefreshTokenIsValid() throws Exception {
        String refreshToken = "validRefreshToken";
        Authentication authentication = mock(Authentication.class);
        Tokens tokens = new Tokens(accessToken, refreshToken);
        Claims claims = mock(Claims.class);

        when(jwtUtils.validateJwtRefreshToken(refreshToken)).thenReturn(true);
        when(jwtUtils.getRefreshClaims(refreshToken)).thenReturn(claims);
        when(claims.getSubject()).thenReturn(email);
        when(userService.userAuthentication(any(User.class))).thenReturn(authentication);
        when(tokenService.generateAccessToken(authentication, refreshToken)).thenReturn(tokens);

        JwtResponse jwtResponse = authService.getJwtAccessToken(refreshToken);

        assertNotNull(jwtResponse);
        assertEquals(tokens.accessToken(), jwtResponse.jwtAccessToken());
        assertEquals(refreshToken, jwtResponse.jwtRefreshToken());

        verify(jwtUtils, times(2)).validateJwtRefreshToken(refreshToken);
        verify(tokenService).generateAccessToken(authentication, refreshToken);
    }

    @Test
    void getJwtAccessToken_ShouldThrowAuthException_WhenRefreshTokenIsInvalid() {
        String refreshToken = "invalidRefreshToken";

        when(jwtUtils.validateJwtRefreshToken(refreshToken)).thenReturn(false);

        AuthException exception = assertThrows(AuthException.class, () -> authService.getJwtAccessToken(refreshToken));

        assertEquals("Invalid token", exception.getMessage());

        verify(jwtUtils).validateJwtRefreshToken(refreshToken);
        verifyNoMoreInteractions(tokenService, userService);
    }

    @Test
    void getJwtRefreshToken_ShouldThrowAuthException_WhenRefreshTokenIsInvalid() {
        String refreshToken = "invalidRefreshToken";

        when(jwtUtils.validateJwtRefreshToken(refreshToken)).thenReturn(false);

        AuthException exception = assertThrows(AuthException.class, () -> authService.getJwtRefreshToken(refreshToken));

        assertEquals("Invalid token", exception.getMessage());

        verify(jwtUtils).validateJwtRefreshToken(refreshToken);
        verifyNoMoreInteractions(tokenService, userService);
    }

    @Test
    void logout_ShouldCallLogoutOnTokenServiceWithCurrentUser() {
        UserDTO userDTO = mock(UserDTO.class);
        when(userService.getCurrentUser()).thenReturn(userDTO);

        authService.logout();

        verify(userService).getCurrentUser();
        verify(tokenService).logout(userDTO);
        verifyNoMoreInteractions(userService, tokenService);
    }
}
