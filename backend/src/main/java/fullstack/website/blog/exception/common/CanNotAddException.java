package fullstack.website.blog.exception.common;

import fullstack.website.blog.exception.core.ArchitectureException;
import org.springframework.http.HttpStatus;

import static fullstack.website.blog.utils.exception.ConstantsException.Exception.Common.CAN_NOT_ADD;
import static fullstack.website.blog.utils.exception.ConstantsException.Exception.Common.CAN_NOT_ADD_CODE;

public class CanNotAddException extends ArchitectureException {

    private static final long serialVersionUID = 1L;
    //endregion

    public CanNotAddException(String field) {
        super();
        this.code = CAN_NOT_ADD_CODE;
        this.message = field + CAN_NOT_ADD;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
