package fullstack.website.blog.exception.common;

import fullstack.website.blog.exception.core.ArchitectureException;
import org.springframework.http.HttpStatus;

public class DescriptionException extends ArchitectureException {

    private static final long serialVersionUID = 1L;

    public DescriptionException(String message) {
        super();
        this.code = "1001";
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

}
