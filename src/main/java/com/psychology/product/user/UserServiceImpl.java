package com.psychology.product.user;

import com.psychology.product.auth.SignUpRequest;
import com.psychology.product.util.exception.RequestExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createNewUser(SignUpRequest signUpRequest) {

        boolean userExist = userRepository.findByEmail(signUpRequest.getEmail()).isPresent();
        if (userExist) throw RequestExceptionHandler.badRequestException("User already exist");

        UserDAO userDAO = UserDAO.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .authorities(Set.of(UserAuthority.USER))
                .build();

        userRepository.save(userDAO);

    }

}
