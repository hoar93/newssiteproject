package newsportal.repos;

import newsportal.modell.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Hashtag findHashtagByName(String name);
}
