package fullstack.website.blog.service.impl;

import fullstack.website.blog.service.IJWTService;
import fullstack.website.blog.service.IMailerService;
import fullstack.website.blog.service.IThymeleafService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static fullstack.website.blog.utils.mail.ConstantsMail.Mail.CONTENT_TYPE_TEXT_HTML;
import static fullstack.website.blog.utils.mail.ConstantsMail.Mail.Subject.*;

@Service
@RequiredArgsConstructor
public class MailerService implements IMailerService {
    private final IThymeleafService thymeleafService;
    private final IJWTService jwtService;
    private final Message message;

    @Override
    public void sendVerifyEmail(String receiverEmail, String token, String api) throws MessagingException {
        message.setRecipients(
                Message.RecipientType.TO,
                new InternetAddress[]{new InternetAddress(receiverEmail)});

        message.setSubject(VERIFY_EMAIL_SUBJECT);
        message.setContent(thymeleafService.getVerifyEmailContent(token, api), CONTENT_TYPE_TEXT_HTML);
        Transport.send(message);
    }

    @Override
    public void sendWelcomeEmail(String receiverEmail, String username) throws MessagingException {
        message.setRecipients(
                Message.RecipientType.TO,
                new InternetAddress[]{new InternetAddress(receiverEmail)});

        message.setSubject(WELCOME_EMAIL_SUBJECT);
        message.setContent(thymeleafService.getWelcomeEmailContent(username), CONTENT_TYPE_TEXT_HTML);
        Transport.send(message);
    }

    @Override
    public void sendForgotPW(String receiverEmail, String username) throws MessagingException {
        message.setRecipients(
                Message.RecipientType.TO,
                new InternetAddress[]{new InternetAddress(receiverEmail)});

        message.setSubject(CHANGE_PASS_EMAIL_SUBJECT);
        message.setContent(thymeleafService
                        .getForgotPWEmailContent(username),
                CONTENT_TYPE_TEXT_HTML);
        Transport.send(message);
    }

}
