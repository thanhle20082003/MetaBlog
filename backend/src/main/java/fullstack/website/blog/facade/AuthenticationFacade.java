package fullstack.website.blog.facade;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.exception.common.DescriptionException;
import fullstack.website.blog.exception.common.InvalidParamException;
import fullstack.website.blog.exception.common.NotFoundException;
import fullstack.website.blog.exception.common.RefreshTokenIsNotNullException;
import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.model.dto.AccountDto;
import fullstack.website.blog.model.request.LoginRequest;
import fullstack.website.blog.model.request.RegisterRequest;
import fullstack.website.blog.model.response.JWTAuthenticationResponse;
import fullstack.website.blog.repository.AccountRepository;
import fullstack.website.blog.service.IAuthenticationService;
import fullstack.website.blog.service.IJWTService;
import fullstack.website.blog.utils.enums.AccountType;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationFacade {

    private final IAuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final IJWTService jwtService;
    private final AccountFacade accountFacade;
    private final AccountRepository accountRepository;

    public void verifyEmailRegister(RegisterRequest request) throws ArchitectureException, MessagingException {
        AccountDto accountDto = accountFacade
                .findByUsernameOrEmail(request.getUsername(), request.getEmail());
        if (accountDto != null) {
            // Nếu tài khoản chưa kích hoạt và token chưa hết hạn thì báo lỗi đã tồn tại
            // Nếu hết hạn thì xóa tài khoản
            try {
                if (accountDto.getType() == AccountType.UNVERIFIED &&
                        jwtService.isTokenValid(accountDto.getAccessToken(), null)) {
                    throw new DescriptionException("Token for verify email is non expired");
                } else if (accountDto.getUsername().equals(request.getUsername())) {
                    throw new DescriptionException("Username is already exist");
                } else if (accountDto.getEmail().equals(request.getEmail())) {
                    throw new DescriptionException("Email is already exist");
                }

            } catch (ExpiredJwtException e) {
                accountRepository.deleteById(accountDto.getId());
            }
        }
        authenticationService.verifyEmailRegister(request);
    }

    public JWTAuthenticationResponse login(LoginRequest request, String token
    ) throws ArchitectureException, MessagingException {
        Account account;
        //Kiểm tra xem có token không và tài khoản có bị khóa không
        if(token != null) {

            account = jwtService.extractAndValid(token, request.getUsernameOrEmail());

            Account accountOld = checkAccount(account.getUsername(), account.getEmail());

            if(!accountOld.isEnabled()) {

                authenticationService.register(accountOld);

            } else if(!accountOld.isAccountNonLocked()) throw new LockedException("User account is locked");

            account = accountOld;

        } else {

            account = request.getUsernameOrEmail().contains("@") ?
                    checkAccount(null, request.getUsernameOrEmail()) :
                    checkAccount(request.getUsernameOrEmail(), null);

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword()));

        }

        return authenticationService.login(account);

    }

    public Account checkAccount(String username, String email) throws ArchitectureException {
        return accountRepository.findByUsernameOrEmail(username, email)
                .orElseThrow(NotFoundException::new);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws ArchitectureException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String usernameOrEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            throw new RefreshTokenIsNotNullException();
        }
        refreshToken = authHeader.substring(7);
        usernameOrEmail = jwtService.extractUsername(refreshToken);
        if(usernameOrEmail != null) throw new InvalidParamException();
        authenticationService.refreshToken(usernameOrEmail, refreshToken,response);
    }
}

