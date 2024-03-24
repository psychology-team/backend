package com.psychology.product.service.impl;

import com.psychology.product.repository.UserRepository;
import com.psychology.product.repository.model.UserDAO;
import com.psychology.product.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserDAO user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found."));
        return new UserDetailsImpl(user);
    }
}
