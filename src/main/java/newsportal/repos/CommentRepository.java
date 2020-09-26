package newsportal.repos;

import newsportal.model.Comment;
import newsportal.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllCommentByNews(News news);
    void deleteCommentById(Long id);

}
