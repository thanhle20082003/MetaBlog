package fullstack.website.blog.exception.common;

import fullstack.website.blog.exception.core.ArchitectureException;
import org.springframework.http.HttpStatus;

import static fullstack.website.blog.utils.exception.ConstantsException.Exception.Common.*;

public class RefreshTokenIsNotNullException extends ArchitectureException {
    private static final long serialVersionUID = 1L;
    //endregion

    public RefreshTokenIsNotNullException() {
        super();
        this.code = RETOKEN_IS_NOT_NULL_CODE;
        this.message = RETOKEN_IS_NOT_NULL;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
