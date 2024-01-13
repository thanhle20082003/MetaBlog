package fullstack.website.blog.controller;

import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.facade.PostFacade;
import fullstack.website.blog.model.common.ResponseHandler;
import fullstack.website.blog.model.request.PostRequest;
import fullstack.website.blog.utils.common.SearchCriteria;
import fullstack.website.blog.utils.enums.PostStatus;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static fullstack.website.blog.utils.api.ConstantsApi.Post.POST_PATH;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(POST_PATH)
public class PostController {

    private final PostFacade postFacade;

    @GetMapping
    @Operation(summary = "Find All post", description = "Find Post")
    public ResponseEntity<Object> getAllPost(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String columSort,
            @RequestParam(required = false) PostStatus status
    ) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,
                postFacade.findAllPost(new SearchCriteria(page, size, columSort), status),true);
    }

    @PostMapping("/create")
    @Operation(summary = "Create new a post", description = "Create a new post")
    public ResponseEntity<Object> createPost(PostRequest postRequest) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.CREATED, postFacade.createPost(postRequest), true);
    }

    @PutMapping("/update/{postId}")
    @Operation(summary = "Update a post", description = "Update a post")
    public ResponseEntity<Object> updatePost(PostRequest postRequest,
                                             @PathVariable Long postId
    ) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK, postFacade.updatePost(postRequest, postId), true);
    }

    @DeleteMapping("/delete/{postId}")
    @Operation(summary = "Delete a post", description = "Delete a post for writer")
    public ResponseEntity<Object> deletePost(@PathVariable Long postId) throws ArchitectureException {
        postFacade.deletePost(postId);
        return ResponseHandler.response(HttpStatus.OK, "Delete this post successfully", true);
    }
}
