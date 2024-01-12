package fullstack.website.blog.facade;

import fullstack.website.blog.entity.Category;
import fullstack.website.blog.exception.common.CanNotDeleteException;
import fullstack.website.blog.exception.common.IdMustBeNullException;
import fullstack.website.blog.exception.common.NotFoundException;
import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.model.dto.CategoryDto;
import fullstack.website.blog.service.ICategoryService;
import fullstack.website.blog.utils.common.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryFacade {

    private final ICategoryService categoryService;

    public CategoryDto findById(Long categoryId) throws ArchitectureException {
        return checkNotNull(categoryId);
    }

    public CategoryDto checkNotNull(Long categoryId) throws NotFoundException {
        CategoryDto categoryDto = categoryService.findByCategoryId(categoryId);
        if (categoryDto == null) {
            throw new NotFoundException();
        }
        return categoryDto;
    }

    public Page<CategoryDto> findAllCategory(SearchCriteria searchCriteria) throws ArchitectureException {
        Page<CategoryDto> result = categoryService.findAllCategory(searchCriteria);
        if (result.isEmpty()) {
            throw new NotFoundException();
        }
        return result;
    }

    public CategoryDto create(CategoryDto categoryDto) throws ArchitectureException {
        if (categoryDto.getId() != null) {
            throw new IdMustBeNullException(Category.class.getSimpleName());
        }
        return categoryService.save(categoryDto);
    }

    public CategoryDto update(CategoryDto categoryDto,Long id) throws ArchitectureException {
        checkNotNull(id);
        categoryDto.setId(id);
        return categoryService.save(categoryDto);
    }

    public void delete(Long categoryId) throws ArchitectureException {
        checkNotNull(categoryId);
        try {
            categoryService.delete(categoryId);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new CanNotDeleteException("category");
        }
    }

}
