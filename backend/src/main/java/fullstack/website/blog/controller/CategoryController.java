package fullstack.website.blog.controller;

import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.facade.CategoryFacade;
import fullstack.website.blog.model.common.ResponseHandler;
import fullstack.website.blog.model.dto.CategoryDto;
import fullstack.website.blog.utils.common.SearchCriteria;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static fullstack.website.blog.utils.api.ConstantsApi.Category.CATEGORY_PATH;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping(CATEGORY_PATH)
public class CategoryController {

    private final CategoryFacade categoryFacade;

    @PostMapping("/create")
    @Operation(summary = "Create Category", description = "Add new Category for database, only admin can access")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryDto categoryDto) throws ArchitectureException{
        return ResponseHandler.response(HttpStatus.OK, categoryFacade.create(categoryDto),true);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update Category", description = "Update Category for database, only admin can access")
    public ResponseEntity<Object> updateCategory(@PathVariable Long id,
                                                 @RequestBody CategoryDto categoryDto) throws ArchitectureException {
        return ResponseHandler.response(HttpStatus.OK, categoryFacade.update(categoryDto, id),true);
    }

    @DeleteMapping("/delete/{categoryId}")
    @Operation(summary = "Delete Category", description = "delete Category for database, only admin can access")
    public ResponseEntity<Object> deleteCategory(@PathVariable("categoryId") Long categoryId) throws ArchitectureException{
        categoryFacade.delete(categoryId);
        return ResponseHandler.response(HttpStatus.OK,"update successfully!",true);
    }

    @GetMapping("/id/{categoryId}")
    @Operation(summary = "Find category by id", description = "Find Category by id for database")
    public ResponseEntity<Object> getCategoryById(@PathVariable("categoryId") Long categoryId) throws ArchitectureException{
        return ResponseHandler.response(HttpStatus.OK,categoryFacade.findById(categoryId),true);
    }

    @GetMapping
    @Operation(summary = "Find All category", description = "Find Category")
    public ResponseEntity<Object> getCategoryByName(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String columSort
    ) throws ArchitectureException{
        return ResponseHandler.response(HttpStatus.OK,
                categoryFacade.findAllCategory(new SearchCriteria(page, size, columSort)),true);
    }
}
