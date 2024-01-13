package fullstack.website.blog.service;

import fullstack.website.blog.model.dto.PostDto;
import fullstack.website.blog.model.request.PostRequest;
import fullstack.website.blog.model.response.PostResponse;
import fullstack.website.blog.utils.common.SearchCriteria;
import fullstack.website.blog.utils.enums.PostStatus;
import org.springframework.data.domain.Page;

public interface IPostService {
    Page<PostResponse> findAllByStatus (SearchCriteria searchCriteria, PostStatus status);

    PostDto save(PostRequest postRequest);
}
