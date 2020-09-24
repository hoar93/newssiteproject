package newsportal.services;

import newsportal.model.Comment;
import newsportal.repos.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Service
public class CommentService {

    @PersistenceContext
    EntityManager em;

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void createComment(String message) {
        Comment comment = new Comment();
        comment.setCreationTime(LocalDateTime.now());
        //comment.setCreator(); //TODO logged in user
        comment.setMessage(message);
        em.persist(comment);
    }
}
