package fullstack.website.blog.model.mappper;

import fullstack.website.blog.entity.Category;
import fullstack.website.blog.model.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CategoryMapper implements Function<Category, CategoryDto> {

    @Override
    public CategoryDto apply(Category category) {
        return CategoryDto
                .builder()
                .id(category.getId())
                .description(category.getDescription())
                .name(category.getName())
                .build();
    }

    public Category applyToCategory(CategoryDto categoryDto){
        return  Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .build();

    }
}
