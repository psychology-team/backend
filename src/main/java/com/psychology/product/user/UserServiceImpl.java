package com.psychology.product.user;

import com.psychology.product.auth.UserSignUp;
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
    public void createNewUser(UserSignUp userSignUp) {

        boolean userExist = userRepository.findByEmail(userSignUp.getEmail()).isPresent();
        if (userExist) throw RequestExceptionHandler.badRequestException("User already exist");

        UserDAO userDAO = UserDAO.builder()
                .firstName(userSignUp.getFirstName())
                .lastName(userSignUp.getLastName())
                .email(userSignUp.getEmail())
                .password(passwordEncoder.encode(userSignUp.getPassword()))
                .authorities(Set.of(UserAuthority.USER))
                .build();

        userRepository.save(userDAO);

    }

}
