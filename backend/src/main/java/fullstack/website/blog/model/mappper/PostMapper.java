package fullstack.website.blog.model.mappper;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.entity.Category;
import fullstack.website.blog.entity.Post;
import fullstack.website.blog.model.dto.ContentImageDto;
import fullstack.website.blog.model.dto.PostDto;
import fullstack.website.blog.repository.ContentImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostMapper implements Function<Post, PostDto> {
    private final ContentImageRepository contentImageRepository;
    private final ContentImageMapper contentImageMapper;
    @Override
    public PostDto apply(Post post) {

        Set<ContentImageDto> contentImageDtos = contentImageRepository.findAllByPostId(post.getId())
                .stream().map(contentImageMapper::apply)
                .collect(Collectors.toSet());
        return PostDto.builder()
                .id(post.getId())
                .accountId(post.getAccount().getId())
                .categoryId(post.getCategory().getId())
                .title(post.getTitle())
                .status(post.getStatus())
                .content(post.getContent())
                .createAt(post.getCreateAt())
                .image(post.getImage())
                .listContentImages(contentImageDtos)
                .build();
    }

    public Post applyToPost (PostDto postDto, Account account, Category category) {
        return Post.builder()
                .id(postDto.getId())
                .account(account)
                .category(category)
                .status(postDto.getStatus())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .createAt(postDto.getCreateAt())
                .image(postDto.getImage())
                .build();
    }
}
