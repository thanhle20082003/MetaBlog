package fullstack.website.blog.exception.core;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class ErrorResponse extends Response {
    public ErrorResponse(ArchitectureException exception) {
        this.result = false;
        this.status = exception.status.value();
        this.error_code = exception.getCode();
        this.message = exception.getMessage();
    }

    public ErrorResponse(String message, String classException, HttpStatus status) {
        this.result = false;
        this.status = status.value();
        this.error_code = status.getReasonPhrase();
        this.message = message;
        this.classException = classException;
    }
}
