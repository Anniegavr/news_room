package backend.dao;

import backend.models.NewsPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsPostRepository extends JpaRepository<NewsPost, Long> {
    @Override
    Optional<NewsPost> findById(Long aLong);
}
