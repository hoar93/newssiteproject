package newsportal.controllers;

import newsportal.dto.CommentDto;
import newsportal.enums.NotificationType;
import newsportal.services.CommentService;
import newsportal.services.NewsServices;
import newsportal.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class CommentController {
    private CommentService commentService;
    private NewsServices newsServices;
    private NotificationService notificationService;

    @Autowired
    public CommentController(CommentService commentService, NewsServices newsServices, NotificationService notificationService) {
        this.commentService = commentService;
        this.newsServices = newsServices;
        this.notificationService = notificationService;
    }

    @PostMapping(path = "/removeFlag/{commentId}")
    public String removeFlag(
            @PathVariable("commentId") Long commentId) {
        commentService.removeFlag(commentId);
        return "redirect:/flaggedComments/";
    }


    @PostMapping(path = "/deleteFlaggedComment/{commentId}/{newsId}")
    public String deleteFlaggedComment(
            @PathVariable("commentId") Long commentId,
            @PathVariable("newsId") Long newsId) {
        commentService.deleteComment(commentId, newsId);
        return "redirect:/flaggedComments/";
    }

    @GetMapping("/flaggedComments/")
    public String flaggedComments(Model model) {
        model.addAttribute("allFlaggedComment", commentService.getFlaggedComments());

        return "flaggedComments";
    }

    @PostMapping("/flagComment/{newsId}/{commentId}/")
    public String reportComment(
            @PathVariable("newsId") Long newsId,
            @PathVariable("commentId") Long commentId) {
        commentService.flagComment(commentId);
        return "redirect:/news/{newsId}";

    }

    @PostMapping(path = "/addComment/{newsId}")
    public String newComment(
            @PathVariable("newsId") Long newsId,
            @ModelAttribute("comment") CommentDto comment) {
        comment.setNewsId(newsId);
        commentService.createComment(comment);

        return "redirect:/news/{newsId}";
    }

    @PostMapping(path = "/updateComment/{newsId}/{commentId}")
    public String updateComment(
            @PathVariable("newsId") int newsId,
            @PathVariable("commentId") Long commentId,
            @ModelAttribute("message") String message) {
        commentService.updateCommentMessage(commentId, message);

        return "redirect:/news/{newsId}";
    }



    @PostMapping(path = "/deleteComment/{newsId}/{commentId}") //(path = "/deleteComment/{newsId}/{commentId}")
    public String deleteComment(
            @PathVariable("newsId") int newsId,
            @PathVariable("commentId") Long commentId) {
        //int thatNews= newsId;

        commentService.deleteOwnComment(commentId);

        return "redirect:/news/{newsId}";
    }

}
