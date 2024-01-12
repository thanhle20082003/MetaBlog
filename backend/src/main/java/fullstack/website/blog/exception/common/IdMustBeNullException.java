package fullstack.website.blog.exception.common;


import fullstack.website.blog.exception.core.ArchitectureException;
import org.springframework.http.HttpStatus;

import static fullstack.website.blog.utils.exception.ConstantsException.Exception.Common.ID_MUST_BE_NULL;
import static fullstack.website.blog.utils.exception.ConstantsException.Exception.Common.ID_MUST_BE_NULL_CODE;


public class IdMustBeNullException extends ArchitectureException {

    private static final long serialVersionUID = 1L;

    public IdMustBeNullException(String message) {
        super();
        this.code = ID_MUST_BE_NULL_CODE;
        this.message = message + ID_MUST_BE_NULL;
        this.status = HttpStatus.BAD_REQUEST;
    }
}