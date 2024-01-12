package fullstack.website.blog.service;

import fullstack.website.blog.model.dto.CategoryDto;
import fullstack.website.blog.utils.common.SearchCriteria;
import org.springframework.data.domain.Page;

public interface ICategoryService {
    Page<CategoryDto> findAllCategory(SearchCriteria searchCriteria);

    CategoryDto findByCategoryId(Long id);

    /*
     * create and save a new category
     * return new category
     */
    CategoryDto save(CategoryDto categoryDto);

    void delete(Long id);
}
