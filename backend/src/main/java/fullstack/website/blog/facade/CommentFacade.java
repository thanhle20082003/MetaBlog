package fullstack.website.blog.facade;

import fullstack.website.blog.entity.Comment;
import fullstack.website.blog.exception.common.CanNotDeleteException;
import fullstack.website.blog.exception.common.IdMustBeNullException;
import fullstack.website.blog.exception.common.NotFoundException;
import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.model.dto.CommentDto;
import fullstack.website.blog.service.ICommentService;
import fullstack.website.blog.utils.common.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentFacade {

    private final ICommentService commentService;

    public Page<CommentDto> findAllComment(SearchCriteria searchCriteria) throws ArchitectureException {
        Page<CommentDto> result = commentService.findAllComment(searchCriteria);
        if(result.isEmpty()) {
            throw new NotFoundException();
        }
        return result;
    }

    public CommentDto create(CommentDto commentDto) throws ArchitectureException {
        if(commentDto.getId() != null) {
            throw new IdMustBeNullException(Comment.class.getSimpleName());
        }
        return  commentService.createComment(commentDto);
    }

    public CommentDto update(CommentDto commentDto, Long commentId) throws ArchitectureException {
        CommentDto commentOld = checkNotNull(commentId);
        commentDto.setId(commentId);
        commentDto.setCreateAt(commentOld.getCreateAt());
        return commentService.updateComment(commentDto);
    }

    public CommentDto findById(Long commentId) throws ArchitectureException {
        return checkNotNull(commentId);
    }

    public void delete(Long commentId) throws ArchitectureException {
        checkNotNull(commentId);
        try {
            commentService.delete(commentId);
        } catch (DataIntegrityViolationException e) {
            throw new CanNotDeleteException("comment");
        }
    }

    public CommentDto checkNotNull(Long commentId) throws NotFoundException {
        CommentDto commentDto = commentService.findByCommentId(commentId);
        if (commentDto == null) {
            throw new NotFoundException();
        }
        return commentDto;
    }
}
