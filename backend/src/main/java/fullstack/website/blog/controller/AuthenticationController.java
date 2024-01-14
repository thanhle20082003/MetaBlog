package fullstack.website.blog.controller;

import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.facade.AuthenticationFacade;
import fullstack.website.blog.model.common.ResponseHandler;
import fullstack.website.blog.model.request.ForgotPWRequest;
import fullstack.website.blog.model.request.LoginRequest;
import fullstack.website.blog.model.request.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static fullstack.website.blog.utils.api.ConstantsApi.Authentication.AUTHENTICATION_PATH;

@RestController
@RequestMapping(AUTHENTICATION_PATH)
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for Authentication login and register")
public class AuthenticationController {

    private final AuthenticationFacade authenticationFacade;
    /**
     * Only user can register
     */
    @PostMapping("/register")
    @Operation(summary = "Register new account", description = "Only user can register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request)
            throws ArchitectureException, MessagingException {
        authenticationFacade.verifyEmailRegister(request);
        return ResponseHandler.response(HttpStatus.OK,"Please check your email to verify account", true);
    }

    /**
     * Anyone can log in
     */
    @PostMapping("/login")
    @Operation(summary = "Login account", description = "Anyone can log in")
    public ResponseEntity<Object> login(@RequestBody(required = false) LoginRequest request,
                                        @RequestParam(required = false) String token)
            throws ArchitectureException, MessagingException {
        System.out.println(token);
        return ResponseHandler.response(HttpStatus.OK, authenticationFacade.login(request, token), true);
    }

    /**
     * Anyone can get new access token
     */
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ArchitectureException, IOException {
        authenticationFacade.refreshToken(request, response);
    }

    /**
     * Anyone can access
     */
    @PostMapping("/forgot-password")
    @Operation(summary = "Verify email", description = "Anyone can get new password")
    public ResponseEntity<Object> forGotPassword(@RequestBody ForgotPWRequest request)
            throws ArchitectureException, MessagingException, IOException {
        return ResponseHandler.response(HttpStatus.OK,
                authenticationFacade.forGotPass(request), true);
    }

}
