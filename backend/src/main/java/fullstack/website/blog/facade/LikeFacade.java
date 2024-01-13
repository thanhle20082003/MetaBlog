package fullstack.website.blog.facade;

import fullstack.website.blog.entity.Comment;
import fullstack.website.blog.entity.Like;
import fullstack.website.blog.exception.common.CanNotDeleteException;
import fullstack.website.blog.exception.common.IdMustBeNullException;
import fullstack.website.blog.exception.common.InvalidParamException;
import fullstack.website.blog.exception.common.NotFoundException;
import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.model.dto.AccountDto;
import fullstack.website.blog.model.dto.CommentDto;
import fullstack.website.blog.model.dto.LikeDto;
import fullstack.website.blog.service.ILikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LikeFacade {

    private final ILikeService likeService;

    public void create(Long accountId, Long postId) throws ArchitectureException {
        if(accountId == null || postId == null) {
            throw new InvalidParamException();
        }
        likeService.save(accountId, postId);
    }

    public void delete(Long accountId, Long postId) throws ArchitectureException {
        LikeDto likeDto = likeService.findByAccountIdAndPostId(accountId, postId);
        if (likeDto == null) {
            throw new NotFoundException();
        }
        try {
            likeService.delete(likeDto.getId());
        } catch (DataIntegrityViolationException e) {
            throw new CanNotDeleteException("like");
        }
    }

}
