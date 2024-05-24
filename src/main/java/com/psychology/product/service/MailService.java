package com.psychology.product.service;

import com.psychology.product.repository.model.UserDAO;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendRegistrationMail(UserDAO user) throws MessagingException;
    void sendResetPasswordMail(UserDAO user) throws MessagingException;
}
