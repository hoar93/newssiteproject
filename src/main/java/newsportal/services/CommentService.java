package newsportal.services;

import newsportal.dto.CommentDto;
import newsportal.dto.CommentShowDto;
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

    public List<Comment> getFlaggedComments() {
        return commentRepository.findAllCommentByIsFlaggedTrue();
    }

    @Transactional
    public void flagComment(Long id) {
        commentRepository.flagComment(id);
    }

    @Transactional
    public void removeFlag(Long id) {
        commentRepository.removeFlagComment(id);
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

    public List<CommentShowDto> allComments(News news) {
        User loggedInUser = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Comment> commentsRaw = commentRepository.findAllCommentByNews(news);
        List<CommentShowDto> comments = new ArrayList<>();

        if (commentsRaw.size() > 0) {
            for(Comment comment : commentsRaw) {
                CommentShowDto oneComment = new CommentShowDto();
                oneComment.setCreator(comment.getCreator().getUsername());
                oneComment.setId(comment.getId());
                oneComment.setCreationTime(comment.getCreationTime());
                oneComment.setMessage(comment.getMessage());
                oneComment.setFlagged(comment.isFlagged());
                if (loggedInUser.equals(null)) { //vannak bajok
                    oneComment.setAuthor(false);
                } else {
                    if(loggedInUser.equals(comment.getCreator())) {
                        oneComment.setAuthor(true);
                    } else {
                        oneComment.setAuthor(false);
                    }
                }
                comments.add(oneComment);
            }
        }
        return comments;
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
