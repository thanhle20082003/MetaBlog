package fullstack.website.blog.repository;

import fullstack.website.blog.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostId(Long postId);

    Set<Like> findAllByPostId(Long postId);
}
