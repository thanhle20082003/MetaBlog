package fullstack.website.blog.service;

import fullstack.website.blog.model.dto.PostDto;
import fullstack.website.blog.model.request.PostRequest;
import fullstack.website.blog.model.response.PostResponse;
import fullstack.website.blog.utils.common.SearchCriteria;
import org.springframework.data.domain.Page;

public interface IPostService {
    Page<PostResponse> findAll (SearchCriteria searchCriteria);

    PostDto save(PostRequest postRequest);
}
