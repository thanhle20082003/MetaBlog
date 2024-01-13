package fullstack.website.blog.repository;

import fullstack.website.blog.entity.Post;
import fullstack.website.blog.utils.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where (?1 IS NULL OR p.status = ?1)")
    Page<Post> findAllByStatus(Pageable pageable, PostStatus status);
}
