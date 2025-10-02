package com.artur.hasparian.psychology.platform.service.impl;

import com.artur.hasparian.psychology.platform.repository.UserRepository;
import com.artur.hasparian.psychology.platform.repository.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws BadCredentialsException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("User not found."));
        return new UserDetailsImpl(user);
    }
}
