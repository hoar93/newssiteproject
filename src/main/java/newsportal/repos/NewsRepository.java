package newsportal.repos;

import newsportal.model.News;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface NewsRepository extends PagingAndSortingRepository<News, Long> {

//public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findFirst10ByOrderByCreationTimeAsc();
    //News findById();

    //List<News> findAll();

}
