package fullstack.website.blog.service;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.model.request.RegisterRequest;
import fullstack.website.blog.model.response.JWTAuthenticationResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import java.io.IOException;

public interface IAuthenticationService {
    @Transactional(rollbackOn = MessagingException.class)
    String verifyEmailRegister(RegisterRequest request) throws MessagingException;

    @Transactional(rollbackOn = MessagingException.class)
    void register(Account account) throws MessagingException;

    JWTAuthenticationResponse login(Account account);

    void refreshToken(
            String usernameOrEmail,
            String refreshToken,
            HttpServletResponse response
    ) throws IOException;
}
