package fullstack.website.blog.service;

import fullstack.website.blog.model.dto.LikeDto;
import fullstack.website.blog.utils.common.SearchCriteria;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface ILikeService {
    Page<LikeDto> findAllLike(SearchCriteria searchCriteria);

    /*
     * find like by id
     * param id
     * return like
     */
    LikeDto findByLikeId(Long id);

    /*
     * find like by post id
     * param id
     * return like
     */
    LikeDto findByPostId(Long postId);

    LikeDto save(LikeDto likeDto);

    Set<LikeDto> findAllByPostId(Long postId);

    void delete(Long id);
}
