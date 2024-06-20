package com.psychology.product.service;

import com.psychology.product.repository.model.User;
import jakarta.mail.MessagingException;

public interface MailService {
    /**
     * Sends a registration email to the given user.
     *
     * @param user the user to whom the registration email will be sent
     * @throws MessagingException if there is an error while sending the email
     */
    void sendRegistrationMail(User user) throws MessagingException;

    /**
     * Sends a password reset email to the given user.
     *
     * @param user the user to whom the password reset email will be sent
     * @throws MessagingException if there is an error while sending the email
     */
    void sendResetPasswordMail(User user) throws MessagingException;
}
