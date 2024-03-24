package com.psychology.product.service.impl;

import com.psychology.product.controller.request.SignUpRequest;
import com.psychology.product.repository.UserRepository;
import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.repository.model.UserAuthority;
import com.psychology.product.repository.model.UserDAO;
import com.psychology.product.service.JwtUtils;
import com.psychology.product.service.UserMapper;
import com.psychology.product.service.UserService;
import com.psychology.product.util.exception.ConflictException;
import com.psychology.product.util.exception.NotFoundException;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    @Override
    public UserDTO getCurrentUser() {
        String tokenFromRequest = getTokenFromRequest();
        String emailFromRequest = jwtUtils.getEmailFromJwtToken(tokenFromRequest);
        UserDAO userDAO = findUserByEmail(emailFromRequest);
        return userMapper.toDTO(userDAO);
    }

    @Override
    public void createNewUser(SignUpRequest signUpRequest) {

        String email = signUpRequest.email();
        boolean isEmailAlreadyUsed = userRepository.findByEmail(email).isPresent();
        if (isEmailAlreadyUsed) throw new ConflictException("The email is already in use");

        UserDAO user = new UserDAO();
        user.setFirstName(signUpRequest.firstName());
        user.setLastName((signUpRequest.lastName()));
        user.setEmail((signUpRequest.email()));
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setAuthorities((Set.of(UserAuthority.USER)));
        user.setCreatedTimestamp(Instant.now());
        user.setEnabled(true);
        user.setRevoked(false);

        userRepository.save(user);

    }

    @Override
    public boolean deleteUser() throws AuthException {
        String tokenFromRequest = getTokenFromRequest();
        String emailFromRequest = jwtUtils.getEmailFromJwtToken(tokenFromRequest);
        UserDAO user = findUserByEmail(emailFromRequest);
        if (user.getRevoked()) throw new AuthException("Unauthorized");
        boolean doesExistUser = userRepository.existsById(user.getId());
        if (!doesExistUser) return false;
        user.setRevoked(true);
        userRepository.save(user);
        return true;
    }


    @Override
    public UserDAO findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found."));
    }

    public Authentication userAuthentication(UserDAO user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private String getTokenFromRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .currentRequestAttributes())).getRequest();
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
