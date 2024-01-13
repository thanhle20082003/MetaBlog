package fullstack.website.blog.facade;


import fullstack.website.blog.entity.Account;
import fullstack.website.blog.entity.Category;
import fullstack.website.blog.entity.Post;
import fullstack.website.blog.exception.common.*;
import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.model.dto.AccountDto;
import fullstack.website.blog.model.dto.CategoryDto;
import fullstack.website.blog.model.dto.PostDto;
import fullstack.website.blog.model.request.PostRequest;
import fullstack.website.blog.model.response.PostResponse;
import fullstack.website.blog.repository.AccountRepository;
import fullstack.website.blog.repository.CategoryRepository;
import fullstack.website.blog.service.IAccountService;
import fullstack.website.blog.service.ICategoryService;
import fullstack.website.blog.service.IPostService;
import fullstack.website.blog.utils.common.SearchCriteria;
import fullstack.website.blog.utils.enums.PostStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostFacade {

    private final IPostService postService;
    private final ICategoryService categoryService;
    private final IAccountService accountService;
    public Page<PostResponse> findAllPost(SearchCriteria searchCriteria, PostStatus status) throws ArchitectureException {
        Page<PostResponse> result = postService.findAllByStatus(searchCriteria, status);
        if (result.isEmpty()) {
            throw new NotFoundException();
        }
        return result;
    }

    public PostDto createPost(PostRequest postRequest)  throws ArchitectureException{
        if(postRequest.getId() != null) throw new IdMustBeNullException(Post.class.getSimpleName());
        checkValidParams(postRequest);
        return postService.save(postRequest);
    }

    public PostDto updatePost(PostRequest postRequest, Long postId) throws ArchitectureException{
        checkPost(postId);
        checkValidParams(postRequest);
        return postService.updatePost(postRequest, postId);
    }

    public void deletePost(Long postId) throws ArchitectureException{
        checkPost(postId);
        try {

            postService.delete(postId);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new CanNotDeleteException("post");
        }
    }

    private void checkPost(Long postId) throws NotFoundException {
        PostDto postDto = postService.findByPostId(postId);
        if(postDto == null) {
            throw new NotFoundException();
        }
    }

    private void checkValidParams(PostRequest postRequest) throws ForeignKeyIsNotFound, InvalidParamException {
        CategoryDto categoryDto = categoryService.findByCategoryId(postRequest.getCategoryId());
        AccountDto accountDto = accountService.findById(postRequest.getAccountId());
        if(categoryDto == null) throw new ForeignKeyIsNotFound("Category must be not empty");
        if(accountDto == null) throw new ForeignKeyIsNotFound("Account must be not empty");
        if(postRequest.getImageFile().isEmpty()) throw new InvalidParamException();
    }

}
