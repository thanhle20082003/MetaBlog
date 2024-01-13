package fullstack.website.blog.controller;

import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.facade.LikeFacade;
import fullstack.website.blog.model.common.ResponseHandler;
import fullstack.website.blog.model.dto.CommentDto;
import fullstack.website.blog.model.dto.LikeDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static fullstack.website.blog.utils.api.ConstantsApi.Like.LIKE_PATH;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(LIKE_PATH)
public class LikeController {

    private final LikeFacade  likeFacade;

    @PostMapping("/like")
    @Operation(summary = "Like the post", description = "Add new like for database")
    public ResponseEntity<Object> createComment(@RequestParam Long accountId, @RequestParam Long postId) throws ArchitectureException {
        likeFacade.create(accountId, postId);
        return ResponseHandler.response(HttpStatus.CREATED, "Like this post successfully",true);
    }

    @DeleteMapping("/unlike")
    @Operation(summary = "Unlike the post", description = "Unlike for database")
    public ResponseEntity<Object> deleteComment(@RequestParam Long accountId, @RequestParam Long postId) throws ArchitectureException {
        likeFacade.delete(accountId, postId);
        return ResponseHandler.response(HttpStatus.OK, "Unlike this post successfully", true);
    }
}
