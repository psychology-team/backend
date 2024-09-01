package com.psychology.product.service;

import com.psychology.product.controller.request.ForgotPasswordRequest;
import com.psychology.product.controller.request.SignUpRequest;
import com.psychology.product.repository.UserRepository;
import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.repository.model.User;
import com.psychology.product.repository.model.UserAuthority;
import com.psychology.product.service.impl.UserServiceImpl;
import com.psychology.product.service.mapper.UserMapper;
import com.psychology.product.util.exception.ConflictException;
import com.psychology.product.util.exception.NotFoundException;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailService mailService;

    private User user;

    private UserDTO userDTO;

    SignUpRequest signUpRequest;

    ForgotPasswordRequest forgotPasswordRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setEnabled(true);
        user.setRevoked(false);

        signUpRequest = new SignUpRequest("John", "Doe", "john.doe@example.com", "password123", "1234567890");

        forgotPasswordRequest = new ForgotPasswordRequest("test@test.com");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("Authorization", "Bearer validToken123");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
    }

    @Test
    void findAllUsers_ShouldReturnListOfUserDTOs() {
        // Arrange
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDTO(user1)).thenReturn(mock(UserDTO.class));
        when(userMapper.toDTO(user2)).thenReturn(mock(UserDTO.class));

        // Act
        List<UserDTO> userDTOs = userService.findAllUsers();

        // Assert
        Assertions.assertEquals(2, userDTOs.size());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(2)).toDTO(any(User.class));
    }

    @Test
    void findAllUsers_ShouldReturnEmptyList_WhenNoUsersExist() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of());

        // Act
        List<UserDTO> result = userService.findAllUsers();

        // Assert
        Assertions.assertEquals(0, result.size());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, never()).toDTO(any(User.class));
    }

    @Test
    void findUserByEmail_ShouldReturnUser_WhenUserExists() {
        // Arrange
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.findUserByEmail(user.getEmail());

        // Assert
        Assertions.assertEquals(user, foundUser);
    }

    @Test
    void findUserByEmail_ShouldThrowNotFoundException_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findByEmail("nonexistent@test.com")).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> userService.findUserByEmail("nonexistent@test.com"));
    }

    @Test
    void getCurrentUser_ShouldReturnUserDTO_WhenUserExists() {
        // Arrange
        when(jwtUtils.getEmailFromJwtToken("validToken123")).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.getCurrentUser();

        // Assert
        Assertions.assertEquals(userDTO, result);
        verify(jwtUtils, times(1)).getEmailFromJwtToken("validToken123");
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userMapper, times(1)).toDTO(user);
    }

    @Test
    void getCurrentUser_ShouldThrowNotFoundException_WhenUserDoesNotExist() {
        // Arrange
        when(jwtUtils.getEmailFromJwtToken("validToken123")).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> userService.getCurrentUser());
        verify(jwtUtils, times(1)).getEmailFromJwtToken("validToken123");
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userMapper, times(0)).toDTO(any(User.class));
    }

    @Test
    void createNewUser_ShouldCreateUser_WhenEmailIsNotInUse() throws MessagingException {
        // Arrange
        when(userRepository.findByEmail(signUpRequest.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(signUpRequest.password())).thenReturn("encodedPassword123");

        // Act
        userService.createNewUser(signUpRequest);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
        verify(mailService, times(1)).sendRegistrationMail(any(User.class));
    }

    @Test
    void createNewUser_ShouldThrowConflictException_WhenEmailIsInUse() throws MessagingException {
        // Arrange
        when(userRepository.findByEmail(signUpRequest.email())).thenReturn(Optional.of(new User()));

        // Act & Assert
        Assertions.assertThrows(ConflictException.class, () -> userService.createNewUser(signUpRequest));
        verify(userRepository, times(0)).save(any(User.class));
        verify(mailService, times(0)).sendRegistrationMail(any(User.class));
    }

    @Test
    void createNewUser_ShouldEncodePasswordCorrectly() throws MessagingException {
        // Arrange
        when(userRepository.findByEmail(signUpRequest.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(signUpRequest.password())).thenReturn("encodedPassword123");

        // Act
        userService.createNewUser(signUpRequest);

        // Assert
        verify(passwordEncoder, times(1)).encode(signUpRequest.password());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createNewUser_ShouldSetUserFieldsCorrectly() throws MessagingException {
        // Arrange
        when(userRepository.findByEmail(signUpRequest.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(signUpRequest.password())).thenReturn("encodedPassword123");

        // Capture the User object passed to save()
        doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            Assertions.assertEquals(signUpRequest.firstName(), user.getFirstName());
            Assertions.assertEquals(signUpRequest.lastName(), user.getLastName());
            Assertions.assertEquals(signUpRequest.email(), user.getEmail());
            Assertions.assertEquals("encodedPassword123", user.getPassword());
            Assertions.assertEquals(signUpRequest.phone(), user.getPhone());
            Assertions.assertEquals(Set.of(UserAuthority.USER), user.getAuthorities());
            Assertions.assertNotNull(user.getCreatedTimestamp());
            Assertions.assertFalse(user.getEnabled());
            Assertions.assertFalse(user.getRevoked());
            Assertions.assertNotNull(user.getUniqueCode());
            return null;
        }).when(userRepository).save(any(User.class));

        // Act
        userService.createNewUser(signUpRequest);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_ShouldUpdateUserDetails_WhenValidDataIsProvided() {
        // Arrange
        userDTO = new UserDTO(user.getId(), "NewFirstName", "NewLastName", user.getEmail(), "1234567890", user.getEnabled(), user.getRevoked());
        when(jwtUtils.getEmailFromJwtToken("validToken123")).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        // Act
        UserDTO result = userService.updateUser(userDTO);

        // Assert
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDTO(user);

        Assertions.assertEquals("NewFirstName", user.getFirstName());
        Assertions.assertEquals("NewLastName", user.getLastName());
        Assertions.assertEquals("1234567890", user.getPhone());

        Assertions.assertEquals(userDTO, result);
    }

    @Test
    void updateUser_ShouldNotUpdateFields_WhenNullValuesAreProvided() {
        // Arrange
        when(jwtUtils.getEmailFromJwtToken("validToken123")).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        UserDTO partialUpdateDTO = new UserDTO(user.getId(), null, null, user.getEmail(), null, user.getEnabled(), user.getRevoked());

        when(userMapper.toDTO(user)).thenReturn(partialUpdateDTO);

        // Act
        UserDTO result = userService.updateUser(partialUpdateDTO);

        // Assert
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toDTO(user);

        Assertions.assertEquals("Test", user.getFirstName());
        Assertions.assertEquals("Test", user.getLastName());
        Assertions.assertEquals("password", user.getPassword());

        Assertions.assertEquals(partialUpdateDTO, result);
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(jwtUtils.getEmailFromJwtToken("validToken123")).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> userService.updateUser(userDTO));

        verify(userRepository, times(0)).save(any(User.class));
        verify(userMapper, times(0)).toDTO(any(User.class));
    }

    @Test
    void disableUser_ShouldDisableUser_WhenUserIsNotRevoked() {
        // Arrange
        when(jwtUtils.getEmailFromJwtToken("validToken123")).thenReturn("test@test.com");
        when(userRepository.findByEmail("test@test.com")).thenReturn(java.util.Optional.of(user));

        // Act
        userService.disableUser();

        // Assert
        Assertions.assertTrue(user.getRevoked(), "User should be revoked");
        Assertions.assertNotNull(user.getRevokedTimestamp());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void disableUser_ShouldNotDisableUser_WhenUserIsAlreadyRevoked() {
        // Arrange
        when(jwtUtils.getEmailFromJwtToken("validToken123")).thenReturn("test@test.com");
        when(userRepository.findByEmail("test@test.com")).thenReturn(java.util.Optional.of(user));
        user.setRevoked(true);

        // Act
        userService.disableUser();

        // Assert
        Assertions.assertTrue(user.getRevoked(), "User should remain revoked");
        verify(userRepository, never()).save(user);
    }

    @Test
    void activateUser_ShouldActivateUser_WhenCodeIsValid() {
        // Arrange
        String validCode = "123456";
        when(userRepository.findByUniqueCode(validCode)).thenReturn(java.util.Optional.of(user));

        // Act
        userService.activateUser(validCode);

        // Assert
        Assertions.assertTrue(user.getEnabled(), "User should be enabled");
        Assertions.assertNull(user.getUniqueCode(), "Unique code should be cleared");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void activateUser_ShouldThrowNotFoundException_WhenCodeIsInvalid() {
        // Arrange
        String invalidCode = "654321";
        when(userRepository.findByUniqueCode(invalidCode)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> userService.activateUser(invalidCode));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void forgotPassword_ShouldGenerateUniqueCodeAndSendMail_WhenEmailIsValid() throws MessagingException {
        // Arrange
        when(userRepository.findByEmail(forgotPasswordRequest.email())).thenReturn(Optional.of(user));

        // Act
        userService.forgotPassword(forgotPasswordRequest);

        // Assert
        Assertions.assertNotNull(user.getUniqueCode(), "Unique code should be generated");
        verify(userRepository, times(1)).save(user);
        verify(mailService, times(1)).sendResetPasswordMail(user);
    }

    @Test
    void forgotPassword_ShouldThrowNotFoundException_WhenEmailIsInvalid() throws MessagingException {
        // Arrange
        when(userRepository.findByEmail(forgotPasswordRequest.email())).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> userService.forgotPassword(forgotPasswordRequest));
        verify(userRepository, never()).save(any(User.class));
        verify(mailService, never()).sendResetPasswordMail(any(User.class));
    }

    @Test
    void forgotPassword_ShouldThrowIllegalArgumentException_WhenMailServiceFails() throws MessagingException {
        // Arrange
        when(userRepository.findByEmail(forgotPasswordRequest.email())).thenReturn(Optional.of(user));
        doThrow(new MessagingException()).when(mailService).sendResetPasswordMail(any(User.class));

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.forgotPassword(forgotPasswordRequest));
        verify(userRepository, times(1)).save(user);
    }
}