package fullstack.website.blog.facade;

import fullstack.website.blog.entity.Comment;
import fullstack.website.blog.entity.Like;
import fullstack.website.blog.exception.common.CanNotDeleteException;
import fullstack.website.blog.exception.common.IdMustBeNullException;
import fullstack.website.blog.exception.common.NotFoundException;
import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.model.dto.CommentDto;
import fullstack.website.blog.model.dto.LikeDto;
import fullstack.website.blog.service.ILikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeFacade {

    private final ILikeService likeService;

    public LikeDto create(LikeDto likeDto) throws ArchitectureException {
        if(likeDto.getId() != null) {
            throw new IdMustBeNullException(Like.class.getSimpleName());
        }
        return  likeService.save(likeDto);
    }

    public void delete(Long commentId) throws ArchitectureException {
        checkNotNull(commentId);
        try {
            likeService.delete(commentId);
        } catch (DataIntegrityViolationException e) {
            throw new CanNotDeleteException("comment");
        }
    }

    public LikeDto checkNotNull(Long likeId) throws NotFoundException {
        LikeDto likeDto = likeService.findByLikeId(likeId);
        if (likeDto == null) {
            throw new NotFoundException();
        }
        return likeDto;
    }
}
