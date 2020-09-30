package newsportal.services;

import newsportal.dto.CommentDto;
import newsportal.model.Comment;
import newsportal.model.News;
import newsportal.model.User;
import newsportal.repos.CommentRepository;
import newsportal.repos.NewsRepository;
import newsportal.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @PersistenceContext
    private EntityManager em;

    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private NewsRepository newsRepository;
    // private User loggedInUser; //TODO jó ezt be autowiredezni?

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, NewsRepository newsRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;
    }

    @Transactional
    public void createComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setCreationTime(LocalDateTime.now());
        User loggedInUser = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        comment.setCreator(loggedInUser);
        comment.setMessage(commentDto.getMessage());
        comment.setNews(newsRepository.findNewsById(commentDto.getNewsId()));
        em.persist(comment);
    }

    public List<Comment> allComments(News news) {
        return commentRepository.findAllCommentByNews(news);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteCommentById(commentId);
    }

    @Transactional
    public void updateCommentMessage(Long id, String message) {
        commentRepository.updateCommentMessage(id, message);
    }
}
