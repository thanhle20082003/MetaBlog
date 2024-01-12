package fullstack.website.blog.model.mappper;

import fullstack.website.blog.model.dto.PostDto;
import fullstack.website.blog.model.response.PostResponse;
import fullstack.website.blog.service.IAccountService;
import fullstack.website.blog.service.ICategoryService;
import fullstack.website.blog.service.ILikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PostResponseMapper implements Function<PostDto, PostResponse> {

    private final IAccountService accountService;
    private final ICategoryService categoryService;
    private final ILikeService likeService;

    @Override
    public PostResponse apply(PostDto postDto) {
        return PostResponse.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .image(postDto.getImage())
                .totalLike((long) likeService.findAllByPostId(postDto.getId()).size())
                .createAt(postDto.getCreateAt())
                .account(accountService.findById(postDto.getAccountId()))
                .category(categoryService.findByCategoryId(postDto.getCategoryId()))
                .imageContent(postDto.getListContentImages())
                .status(postDto.getStatus())
                .build();
    }
}
