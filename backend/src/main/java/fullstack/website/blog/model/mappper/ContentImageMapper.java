package fullstack.website.blog.model.mappper;

import fullstack.website.blog.entity.ContentImage;
import fullstack.website.blog.entity.Post;
import fullstack.website.blog.model.dto.ContentImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ContentImageMapper implements Function<ContentImage, ContentImageDto> {
    @Override
    public ContentImageDto apply(ContentImage contentImage) {
        return ContentImageDto
                .builder()
                .image(contentImage.getImage())
                .id(contentImage.getId())
                .postId(contentImage.getPost().getId())
                .build();
    }

    public ContentImage applyToContentImage (ContentImageDto contentImageDto, Post post) {
        return ContentImage
                .builder()
                .id(contentImageDto.getId())
                .image(contentImageDto.getImage())
                .post(post)
                .build();
    }
}
