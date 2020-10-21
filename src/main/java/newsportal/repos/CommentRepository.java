package newsportal.repos;

import newsportal.model.Comment;
import newsportal.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllCommentByNews(News news);
    void deleteCommentById(Long id);

    @Modifying
    @Query("update Comment c set c.isFlagged = true where c.id =:id")
    void flagComment(@Param(value = "id") long id);

    @Modifying
    @Query("update Comment c set c.message = :message where c.id = :id")
    void updateCommentMessage(@Param(value = "id") long id, @Param(value = "message") String message);


}
