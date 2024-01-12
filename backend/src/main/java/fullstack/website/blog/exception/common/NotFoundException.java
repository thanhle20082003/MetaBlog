package fullstack.website.blog.exception.common;

import fullstack.website.blog.exception.core.ArchitectureException;
import org.springframework.http.HttpStatus;

import static fullstack.website.blog.utils.exception.ConstantsException.Entity.ENTITY_NOT_FOUND;
import static fullstack.website.blog.utils.exception.ConstantsException.Entity.ENTITY_NOT_FOUND_CODE;

public class NotFoundException extends ArchitectureException {
    //region
    private static final long serialVersionUID = 1L;
    //endregion

    public NotFoundException() {
        super();
        this.code = ENTITY_NOT_FOUND_CODE;
        this.message = ENTITY_NOT_FOUND;
        this.status = HttpStatus.NOT_FOUND;
    }
}
