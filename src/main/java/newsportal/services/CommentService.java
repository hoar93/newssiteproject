package newsportal.services;

import newsportal.dto.CommentDto;
import newsportal.dto.CommentShowDto;
import newsportal.dto.FlaggedCommentDto;
import newsportal.enums.NotificationType;
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
    private NotificationService notificationService;
    // private User loggedInUser; //TODO jó ezt be autowiredezni?

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, NewsRepository newsRepository, NotificationService notificationService) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;
        this.notificationService = notificationService;
    }

    public List<FlaggedCommentDto> getFlaggedComments() {
        List<Comment> commentList = commentRepository.findAllCommentByIsFlaggedTrue();
        List<FlaggedCommentDto> flaggedCommentDtoList = new ArrayList<>();
        for(Comment comment : commentList) {
            flaggedCommentDtoList.add(new FlaggedCommentDto(comment.getId(), comment.getMessage(), comment.getNews().getId()));
        }
        return flaggedCommentDtoList;
    }

    @Transactional
    public void flagComment(Long id) {
        commentRepository.flagComment(id);
    }

    @Transactional
    public void removeFlag(Long id) {
        commentRepository.setChecked(id);
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
                oneComment.setChecked(comment.isChecked());
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
    public void deleteOwnComment(Long commentId) {
        commentRepository.deleteCommentById(commentId);
    }

    @Transactional
    public void deleteComment(Long commentId, Long newsId) {
        Long userId = commentRepository.findById(commentId).get().getCreator().getId();
        //TODO a noti service-nek a create DELETED_COMMENT metódus hívódjun meg, ne az általános
        notificationService.createDeleteCommentNotification(newsId, userId);
        // notificationService.createNotification(NotificationType.REMOVED_MESSAGE, newsId, userId);
        commentRepository.deleteCommentById(commentId);
    }

    @Transactional
    public void updateCommentMessage(Long id, String message) {
        commentRepository.updateCommentMessage(id, message);
        commentRepository.removeChecked(id);
    }
}
