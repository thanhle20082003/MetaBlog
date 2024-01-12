package fullstack.website.blog.service;

import fullstack.website.blog.model.dto.CommentDto;
import fullstack.website.blog.utils.common.SearchCriteria;
import org.springframework.data.domain.Page;

public interface ICommentService {
    Page<CommentDto> findAllComment(SearchCriteria searchCriteria);

    CommentDto findByCommentId(Long id);

    /*
     * create and save a new comment
     * return new comment
     */
    CommentDto save(CommentDto categoryDto);

    /*
     * delete a comment
     * param id
     */
    void delete(Long id);
}
