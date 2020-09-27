package newsportal.repos;

import newsportal.model.News;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends PagingAndSortingRepository<News, Long> {

//public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findFirst10ByOrderByCreationTimeAsc();
    News findNewsById(Long id);
    @Modifying
    @Query("update News n set n.content = :content where n.id = :id")
    void updateNewsContent(@Param(value = "id") long id, @Param(value = "content") String content);

    //List<News> findAll();

}
