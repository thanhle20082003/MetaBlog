package fullstack.website.blog.repository;

import fullstack.website.blog.entity.ContentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ContentImageRepository extends JpaRepository<ContentImage, Long> {
    Set<ContentImage> findAllByPostId(Long postId);
}
