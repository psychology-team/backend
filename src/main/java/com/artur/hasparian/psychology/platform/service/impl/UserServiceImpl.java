package com.artur.hasparian.psychology.platform.service.impl;

import com.artur.hasparian.psychology.platform.controller.request.ForgotPasswordRequest;
import com.artur.hasparian.psychology.platform.controller.request.SignUpRequest;
import com.artur.hasparian.psychology.platform.repository.UserRepository;
import com.artur.hasparian.psychology.platform.repository.dto.UserDTO;
import com.artur.hasparian.psychology.platform.repository.model.User;
import com.artur.hasparian.psychology.platform.repository.model.UserAuthority;
import com.artur.hasparian.psychology.platform.service.JwtUtils;
import com.artur.hasparian.psychology.platform.service.MailService;
import com.artur.hasparian.psychology.platform.service.UserService;
import com.artur.hasparian.psychology.platform.service.mapper.UserMapper;
import com.artur.hasparian.psychology.platform.util.exception.ConflictException;
import com.artur.hasparian.psychology.platform.util.exception.NotFoundException;
import jakarta.mail.MessagingException;
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
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;
    private final MailService mailService;

    @Override
    public UserDTO getCurrentUser() {
        String tokenFromRequest = getTokenFromRequest();
        String emailFromRequest = jwtUtils.getEmailFromJwtToken(tokenFromRequest);
        User user = findUserByEmail(emailFromRequest);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO getUserFromEmail(String email) {
        User user = findUserByEmail(email);
        return userMapper.toDTO(user);
    }

    @Override
    public void createNewUser(SignUpRequest signUpRequest) throws MessagingException {

        String email = signUpRequest.email();
        boolean isEmailAlreadyUsed = userRepository.findByEmail(email).isPresent();
        if (isEmailAlreadyUsed) throw new ConflictException("The email is already in use");

        User user = new User();
        user.setFirstName(signUpRequest.firstName());
        user.setLastName((signUpRequest.lastName()));
        user.setEmail((signUpRequest.email()));
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setPhone(signUpRequest.phone());
        user.setAuthorities((Set.of(UserAuthority.USER)));
        user.setCreatedTimestamp(Instant.now());
        user.setEnabled(false);
        user.setUniqueCode(String.valueOf(new Random().nextInt(999999)));
        user.setRevoked(false);

        userRepository.save(user);
        mailService.sendRegistrationMail(user);

    }

    @Override
    public UserDTO updateUser(UserDTO updated) {
        String tokenFromRequest = getTokenFromRequest();
        String emailFromRequest = jwtUtils.getEmailFromJwtToken(tokenFromRequest);
        User current = findUserByEmail(emailFromRequest);
        Optional.ofNullable(updated.firstName()).ifPresent(current::setFirstName);
        Optional.ofNullable(updated.lastName()).ifPresent(current::setLastName);
        Optional.ofNullable(updated.phone()).ifPresent(current::setPhone);

        userRepository.save(current);

        return userMapper.toDTO(current);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public void disableUser() {
        String tokenFromRequest = getTokenFromRequest();
        String emailFromRequest = jwtUtils.getEmailFromJwtToken(tokenFromRequest);
        User user = findUserByEmail(emailFromRequest);
        if (user.getRevoked()) return;
        user.setRevoked(true);
        user.setRevokedTimestamp(Instant.now());
        userRepository.save(user);
    }


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found."));
    }

    public Authentication userAuthentication(User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public void activateUser(String uniqueCode) {
        User user = userRepository.findByUniqueCode(uniqueCode)
                .orElseThrow(() -> new NotFoundException("Invalid code"));
        user.setEnabled(true);
        user.setUniqueCode(null);
        userRepository.save(user);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setUniqueCode(String.valueOf(new Random().nextInt(999999)));
        userRepository.save(user);
        try {
            mailService.sendResetPasswordMail(user);
        } catch (MessagingException e) {
            throw new IllegalArgumentException("Could not send email");
        }
    }

    private String getTokenFromRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
