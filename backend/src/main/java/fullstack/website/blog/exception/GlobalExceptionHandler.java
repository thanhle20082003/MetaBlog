package fullstack.website.blog.exception;

import fullstack.website.blog.exception.common.StorageFileNotFoundException;
import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.exception.core.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ArchitectureException.class)
    public final ResponseEntity<ErrorResponse> handle(ArchitectureException exception) {
        exception.printStackTrace();
        ErrorResponse response = new ErrorResponse(exception);
        return ResponseEntity
                .status(exception.getStatus())
                .body(response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public final ResponseEntity<ErrorResponse> handle(ExpiredJwtException exception) {
        exception.printStackTrace();
        ErrorResponse response = new ErrorResponse("Your token is expired", exception.getClass().getName(), HttpStatus.UNAUTHORIZED);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ErrorResponse> handle(BadCredentialsException exception) {
        exception.printStackTrace();
        ErrorResponse response = new ErrorResponse("Your username or password is incorrect", exception.getClass().getName(), HttpStatus.BAD_REQUEST);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handle(StorageFileNotFoundException exception) {
        exception.printStackTrace();
        ErrorResponse response = new ErrorResponse(exception.getMessage(), exception.getClass().getName(), HttpStatus.BAD_REQUEST);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleException(Exception exception) {
        exception.printStackTrace();
        ErrorResponse response = new ErrorResponse(exception.getMessage(), exception.getClass().getName(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ErrorResponse> exceptionUnique(DataIntegrityViolationException exception) {
        exception.printStackTrace();
        ErrorResponse response;
        if (exception.getMessage().contains("the REFERENCE constraint"))
            response = new ErrorResponse(
                    "The request could not be fulfilled due to a foreign key constraint conflict",
                    exception.getClass().getName(), HttpStatus.INTERNAL_SERVER_ERROR);
        else
            response = new ErrorResponse(
                    exception.getMessage(),
                    exception.getClass().getName(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

}
