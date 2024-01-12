package fullstack.website.blog.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import fullstack.website.blog.entity.Account;
import fullstack.website.blog.model.mappper.RegisterMapper;
import fullstack.website.blog.model.request.RegisterRequest;
import fullstack.website.blog.model.response.JWTAuthenticationResponse;
import fullstack.website.blog.repository.AccountRepository;
import fullstack.website.blog.service.IAuthenticationService;
import fullstack.website.blog.service.IEncodePassword;
import fullstack.website.blog.service.IJWTService;
import fullstack.website.blog.service.IMailerService;
import fullstack.website.blog.utils.enums.AccountType;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService  implements IAuthenticationService {

    private final IJWTService jwtService;
    private final RegisterMapper registerMapper;
    private final IMailerService mailerService;
    private final IEncodePassword encodePassword;
    private final AccountRepository accountRepository;

    @Override
    @Transactional(rollbackOn = MessagingException.class)
    public String verifyEmailRegister(RegisterRequest request) throws MessagingException {
        System.out.println(request.getPassword());
        Account account = registerMapper.apply(request);
        String token = jwtService.generateToken(account);
        account.setAccessToken(token);
        accountRepository.save(account);
//        mailerService.sendVerifyEmail(account.getEmail(), token, URL_LOGIN);
        return token;
    }

    @Override
    @Transactional(rollbackOn = MessagingException.class)
    public void register(Account account) throws MessagingException {
        account.setType(AccountType.ACTIVE);
        Account accountUpdate = accountRepository.save(account);
        mailerService.sendWelcomeEmail(accountUpdate.getEmail(), accountUpdate.getUsername());
    }

    @Override
    public JWTAuthenticationResponse login(Account account) {
        var accessToken = jwtService.generateToken(account);
        var refreshToken = jwtService.generateRefreshToken(account);
        account.setAccessToken(accessToken);
        account.setLastAccessDate(new Date());
        accountRepository.save(account);
        return JWTAuthenticationResponse.apply(account, accessToken, refreshToken);
    }

    @Override
    public void refreshToken(
            String usernameOrEmail,
            String refreshToken,
            HttpServletResponse response
    ) throws IOException {

            var user = accountRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                var authResponse = JWTAuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
    }
}
