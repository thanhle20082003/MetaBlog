package fullstack.website.blog.exception.common;

import fullstack.website.blog.exception.core.ArchitectureException;
import org.springframework.http.HttpStatus;

import static fullstack.website.blog.utils.exception.ConstantsException.Exception.Common.CAN_NOT_DELETE;
import static fullstack.website.blog.utils.exception.ConstantsException.Exception.Common.CAN_NOT_DELETE_CODE;

public class CanNotDeleteException extends ArchitectureException {

    private static final long serialVersionUID = 1L;

    public CanNotDeleteException(String field) {
        super();
        this.code = CAN_NOT_DELETE_CODE;
        this.message = "Can not delete" + field + CAN_NOT_DELETE;
        this.status = HttpStatus.CONFLICT;
    }

}