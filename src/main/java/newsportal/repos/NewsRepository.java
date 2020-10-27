package newsportal.repos;

import newsportal.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

//public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findFirst10ByOrderByCreationTimeAsc();

    News findNewsById(Long id);
    @Modifying
    @Query("update News n set n.content = :content where n.id = :id")
    void updateNewsContent(@Param(value = "id") long id, @Param(value = "content") String content);

    @Modifying
    @Query("update News n set n.mainContent = :mainContent where n.id = :id")
    void updateNewsMainContent(@Param(value = "id") long id, @Param(value = "mainContent") String mainContent);

    @Modifying
    @Query("update News n set n.title = :title where n.id = :id")
    void updateNewsTitle(@Param(value = "id") long id, @Param(value = "title") String title);

    List<News> findAll();
}
