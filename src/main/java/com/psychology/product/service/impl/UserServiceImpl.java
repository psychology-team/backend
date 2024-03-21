package com.psychology.product.service.impl;

import com.psychology.product.controller.request.SignUpRequest;
import com.psychology.product.repository.UserRepository;
import com.psychology.product.repository.model.UserAuthority;
import com.psychology.product.repository.model.UserDAO;
import com.psychology.product.service.UserService;
import com.psychology.product.util.ResponseUtil;
import com.psychology.product.util.exception.ConflictException;
import com.psychology.product.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    public void createNewUser(SignUpRequest signUpRequest) {

        if (findUserByEmail(signUpRequest.email())) throw new ConflictException("User already exist");

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
    public ResponseEntity<?> deleteUser(String userId) {
        try {
            UUID userUuid = UUID.fromString(userId);
            UserDAO userDAO = findUserById(userUuid);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication.getAuthorities().contains(UserAuthority.ADMIN) ||
                    userDAO.getEmail().equals(authentication.getName())) {
                userDAO.setRevoked(true);
                userRepository.save(userDAO);

                SecurityContextHolder.clearContext();
                return ResponseUtil.generateResponse("Successfully Deleted", HttpStatus.OK);
            } else {
                return ResponseUtil.generateError("Haven't permission to delete user", HttpStatus.FORBIDDEN);
            }
        } catch (IllegalArgumentException e) {
            return ResponseUtil.generateError("Unsupported id format", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public boolean findUserByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public Authentication userAuthentication(UserDAO user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public UserDAO findUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

}
