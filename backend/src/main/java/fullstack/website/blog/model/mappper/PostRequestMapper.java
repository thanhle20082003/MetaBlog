package fullstack.website.blog.model.mappper;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.entity.Category;
import fullstack.website.blog.entity.Post;
import fullstack.website.blog.model.dto.ContentImageDto;
import fullstack.website.blog.model.dto.PostDto;
import fullstack.website.blog.model.request.PostRequest;
import fullstack.website.blog.service.IStorageService;
import fullstack.website.blog.utils.enums.PostStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostRequestMapper implements Function<PostRequest, PostDto> {

    private final IStorageService storageService;
    @Override
    public PostDto apply(PostRequest postRequest) {
        Set<ContentImageDto> contentImages = postRequest.getListImageFiles()
                .stream()
                .map(file -> ContentImageDto.builder().imageFile(file).build())
                .collect(Collectors.toSet());
        return PostDto.builder()
                .id(postRequest.getId())
                .content(postRequest.getContent())
                .title(postRequest.getTitle())
                .accountId(postRequest.getAccountId())
                .categoryId(postRequest.getCategoryId())
                .image(storageService.store(postRequest.getImageFile()))
                .createAt(new Date())
                .status(PostStatus.PENDING)
                .listContentImages(contentImages)
                .build();
    }
}
