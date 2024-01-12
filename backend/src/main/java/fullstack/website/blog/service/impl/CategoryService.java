package fullstack.website.blog.service.impl;

import fullstack.website.blog.entity.Category;
import fullstack.website.blog.model.dto.CategoryDto;
import fullstack.website.blog.model.mappper.CategoryMapper;
import fullstack.website.blog.repository.CategoryRepository;
import fullstack.website.blog.service.ICategoryService;
import fullstack.website.blog.utils.common.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static fullstack.website.blog.utils.common.Search.getPageable;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDto> findAllCategory(SearchCriteria searchCriteria){
        Page<Category> categories = categoryRepository.findAll(getPageable(searchCriteria));
        return categories.map(categoryMapper::apply);
    }
    /*
     * find category by id
     * param id
     * return category
     */

    @Override
    public CategoryDto findByCategoryId(Long id){
        Optional<Category> category = categoryRepository.findById(id);
        return  category.map(categoryMapper::apply).orElse(null);
    }


    /*
     * create and save a new category
     * return new category
     */
    @Override
    public CategoryDto save(CategoryDto categoryDto){
        return categoryMapper.apply(categoryRepository.save(categoryMapper.applyToCategory(categoryDto)));
    }


    /*
     * delete a category
     * param id
     */

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
