package com.psychology.product.service;

import com.psychology.product.controller.request.ForgotPasswordRequest;
import com.psychology.product.controller.request.SignUpRequest;
import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.repository.model.User;
import jakarta.mail.MessagingException;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    /**
     * Retrieves the current authenticated user.
     *
     * @return the UserDTO object representing the current user
     */
    UserDTO getCurrentUser();

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user to retrieve
     * @return the UserDTO object representing the user
     */
    UserDTO getUserFromEmail(String email);

    /**
     * Creates a new user with the provided sign-up request details.
     *
     * @param signUpRequest the sign-up request containing user details
     * @throws MessagingException if there is an error while sending the registration email
     */
    void createNewUser(SignUpRequest signUpRequest) throws MessagingException;

    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user to find
     * @return the UserDAO object representing the user
     */
    User findUserByEmail(String email);

    /**
     * Authenticates the given user.
     *
     * @param user the UserDAO object representing the user to authenticate
     * @return the Authentication object representing the authenticated user
     */
    Authentication userAuthentication(User user);

    /**
     * Disables the current authenticated user.
     *
     * @throws AuthException if there is an error during the disabling process
     */
    void disableUser() throws AuthException;

    /**
     * Updates the details of an existing user.
     *
     * @param updated the UserDTO object containing updated user details
     * @return the updated UserDTO object
     */
    UserDTO updateUser(UserDTO updated);

    /**
     * Retrieves a list of all users.
     *
     * @return a list of UserDTO objects representing all users
     */
    List<UserDTO> findAllUsers();

    /**
     * Activates a user account using the provided unique activation code.
     *
     * @param uniqueCode the unique activation code
     */
    void activateUser(String uniqueCode);

    /**
     * Initiates the password reset process for the user associated with the provided email address.
     *
     * @param email the ForgotPasswordRequest object containing the user's email address
     */
    void forgotPassword(ForgotPasswordRequest email);
}
