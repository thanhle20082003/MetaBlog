package fullstack.website.blog.controller;

import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.facade.CommentFacade;
import fullstack.website.blog.model.common.ResponseHandler;
import fullstack.website.blog.model.dto.CommentDto;
import fullstack.website.blog.utils.common.SearchCriteria;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static fullstack.website.blog.utils.api.ConstantsApi.Comment.COMMENT_PATH;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping(COMMENT_PATH)
public class CommentController {
    private final CommentFacade commentFacade;

    @GetMapping
    @Operation(summary = "Find all comment", description = "Find all comment for database")
    public ResponseEntity<Object> findAllCategory(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue =  "5") Integer size,
            @RequestParam(defaultValue = "id") String columSort) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK, commentFacade.findAllComment(new SearchCriteria(page, size, columSort)), true);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Find comment by id", description = "Find comment by id for database")
    public ResponseEntity<Object> findById(@PathVariable Long id) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK, commentFacade.findById(id),true);
    }
    @PostMapping("/create")
    @Operation(summary = "Create comment", description = "Add new comment for database")
    public ResponseEntity<Object> createComment(@RequestBody CommentDto commentDto) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.CREATED, commentFacade.create(commentDto),true);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update comment", description = "Update comment for database")
    public ResponseEntity<Object> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK, commentFacade.update(commentDto, id), true);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete comment", description = "Delete comment for database")
    public ResponseEntity<Object> deleteComment(@PathVariable Long id) throws ArchitectureException {
        commentFacade.delete(id);
        return ResponseHandler.response(HttpStatus.OK, "Delete comment successfully", true);
    }
}
