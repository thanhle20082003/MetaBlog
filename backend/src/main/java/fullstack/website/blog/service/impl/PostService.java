package fullstack.website.blog.service.impl;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.entity.Category;
import fullstack.website.blog.entity.ContentImage;
import fullstack.website.blog.entity.Post;
import fullstack.website.blog.model.dto.ContentImageDto;
import fullstack.website.blog.model.dto.PostDto;
import fullstack.website.blog.model.mappper.ContentImageMapper;
import fullstack.website.blog.model.mappper.PostMapper;
import fullstack.website.blog.model.mappper.PostRequestMapper;
import fullstack.website.blog.model.mappper.PostResponseMapper;
import fullstack.website.blog.model.request.PostRequest;
import fullstack.website.blog.model.response.PostResponse;
import fullstack.website.blog.repository.AccountRepository;
import fullstack.website.blog.repository.CategoryRepository;
import fullstack.website.blog.repository.ContentImageRepository;
import fullstack.website.blog.repository.PostRepository;
import fullstack.website.blog.service.IPostService;
import fullstack.website.blog.service.IStorageService;
import fullstack.website.blog.utils.common.SearchCriteria;
import fullstack.website.blog.utils.enums.PostStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static fullstack.website.blog.utils.common.Search.getPageable;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

    private final PostRepository postRepository;
    private final ContentImageRepository contentImageRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final PostMapper postMapper;
    private final ContentImageMapper contentImageMapper;
    private final PostRequestMapper postRequestMapper;
    private final IStorageService storageService;
    private final PostResponseMapper postResponseMapper;
    @Override
    public Page<PostResponse> findAllByStatus(SearchCriteria searchCriteria, PostStatus status) {
        Page<Post> posts = postRepository.findAllByStatus(getPageable(searchCriteria), status);
        Page<PostDto> postDtos = posts.map(postMapper::apply);
        return postDtos.map(postResponseMapper::apply);
    }

    @Override
    public PostDto save(PostRequest postRequest) {
        PostDto postDto = postRequestMapper.apply(postRequest);
        Account account = accountRepository.findById(postRequest.getAccountId()).get();
        Category category = categoryRepository.findById(postRequest.getCategoryId()).get();
        Post post = postRepository.save(postMapper.applyToPost(postDto, account, category));

        saveContentImages(post, postDto.getListContentImages());

        return postMapper.apply(post);
    }

    private void saveContentImages(Post post, Set<ContentImageDto> contentImages) {
        Set<ContentImage> contentImageEntities = contentImages.stream()
                .map(contentImageDto -> {
                    // Lưu file và lấy đường dẫn
                    String storedImagePath = storageService.store(contentImageDto.getImageFile());

                    // Tạo entity ContentImage từ ContentImageDto và đường dẫn đã lưu
                    return contentImageMapper.applyToContentImage(
                            ContentImageDto.builder().image(storedImagePath).build(), post);
                })
                .collect(Collectors.toSet());

        contentImageRepository.saveAll(contentImageEntities);
    }
}
