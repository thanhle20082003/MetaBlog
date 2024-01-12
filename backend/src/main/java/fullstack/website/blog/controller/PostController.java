package fullstack.website.blog.controller;

import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.facade.PostFacade;
import fullstack.website.blog.model.common.ResponseHandler;
import fullstack.website.blog.model.request.PostRequest;
import fullstack.website.blog.utils.common.SearchCriteria;
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
            @RequestParam(defaultValue = "id") String columSort
    ) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK,
                postFacade.findAllPost(new SearchCriteria(page, size, columSort)),true);
    }

    @PostMapping("/create")
    @Operation(summary = "Create new a post", description = "Create a new post")
    public ResponseEntity<Object> createPost(PostRequest postRequest) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.CREATED, postFacade.createPost(postRequest), true);
    }
}
