package fullstack.website.blog.facade;


import fullstack.website.blog.entity.Account;
import fullstack.website.blog.entity.Category;
import fullstack.website.blog.entity.Post;
import fullstack.website.blog.exception.common.ForeignKeyIsNotFound;
import fullstack.website.blog.exception.common.IdMustBeNullException;
import fullstack.website.blog.exception.common.InvalidParamException;
import fullstack.website.blog.exception.common.NotFoundException;
import fullstack.website.blog.exception.core.ArchitectureException;
import fullstack.website.blog.model.dto.PostDto;
import fullstack.website.blog.model.request.PostRequest;
import fullstack.website.blog.model.response.PostResponse;
import fullstack.website.blog.repository.AccountRepository;
import fullstack.website.blog.repository.CategoryRepository;
import fullstack.website.blog.service.IPostService;
import fullstack.website.blog.utils.common.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostFacade {

    private final IPostService postService;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;
    public Page<PostResponse> findAllPost(SearchCriteria searchCriteria) throws ArchitectureException {
        Page<PostResponse> result = postService.findAll(searchCriteria);
        if (result.isEmpty()) {
            throw new NotFoundException();
        }
        return result;
    }

    public PostDto createPost(PostRequest postRequest)  throws ArchitectureException{

        Optional<Category> category = categoryRepository.findById(postRequest.getCategoryId());
        Optional<Account> account = accountRepository.findById(postRequest.getAccountId());
        if(postRequest.getId() != null) throw new IdMustBeNullException(Post.class.getSimpleName());
        if(category.isEmpty()) throw new ForeignKeyIsNotFound("Category must be not empty");
        if(account.isEmpty()) throw new ForeignKeyIsNotFound("Account must be not empty");
        if(postRequest.getImageFile().isEmpty()) throw new InvalidParamException();
        return postService.save(postRequest);
    }
}
