package fullstack.website.blog.service;

import jakarta.mail.MessagingException;

public interface IMailerService {
    void sendVerifyEmail(String receiverEmail, String token, String api) throws MessagingException;

    void sendWelcomeEmail(String receiverEmail, String username) throws MessagingException;

    void sendChangePassEmail(String receiverEmail, String username) throws MessagingException;
}
