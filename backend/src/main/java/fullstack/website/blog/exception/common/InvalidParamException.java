package fullstack.website.blog.exception.common;

import fullstack.website.blog.exception.core.ArchitectureException;
import org.springframework.http.HttpStatus;

import static fullstack.website.blog.utils.exception.ConstantsException.Exception.Common.INVALID_PARAM;
import static fullstack.website.blog.utils.exception.ConstantsException.Exception.Common.INVALID_PARAM_CODE;

public class InvalidParamException extends ArchitectureException {

    //region
    private static final long serialVersionUID = 1L;
    //endregion

    public InvalidParamException() {
        super();
        this.code = INVALID_PARAM_CODE;
        this.message = INVALID_PARAM;
        this.status = HttpStatus.BAD_REQUEST;
    }

}
