package backend.repository;

import backend.model.Newspost;
import backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsPostRepository extends JpaRepository<Newspost, Long>{
    Newspost findNewspostByPrivateNews(Boolean privateNews);
    Newspost findNewspostByProtectedNews(Boolean protectedNews);
    Newspost findNewspostByCreator(String creator);
}
