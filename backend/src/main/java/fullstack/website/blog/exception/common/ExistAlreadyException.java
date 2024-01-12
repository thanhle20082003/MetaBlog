package fullstack.website.blog.exception.common;

import fullstack.website.blog.exception.core.ArchitectureException;
import org.springframework.http.HttpStatus;

import static fullstack.website.blog.utils.exception.ConstantsException.Entity.ENTITY_ALREADY_EXIST;
import static fullstack.website.blog.utils.exception.ConstantsException.Entity.ENTITY_ALREADY_EXIST_CODE;

public class ExistAlreadyException extends ArchitectureException {
    //region
    private static final long serialVersionUID = 1L;
    //endregion

    public ExistAlreadyException() {
        super();
        this.code = ENTITY_ALREADY_EXIST_CODE;
        this.message = ENTITY_ALREADY_EXIST;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
