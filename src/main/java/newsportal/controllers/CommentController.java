package newsportal.controllers;

import newsportal.dto.CommentDto;
import newsportal.services.CommentService;
import newsportal.services.NewsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    private CommentService commentService;
    private NewsServices newsServices;

    @Autowired
    public CommentController(CommentService commentService, NewsServices newsServices) {
        this.commentService = commentService;
        this.newsServices = newsServices;
    }


    @PostMapping(path = "/addComment/{newsId}")
    public String newComment(
            @PathVariable("newsId") Long newsId,
            @ModelAttribute("comment") CommentDto comment) {
        comment.setNewsId(newsId);
        commentService.createComment(comment);

        return "redirect:/news/";
    }

    @PostMapping(path = "/deleteComment/{newsId}/{commentId}")
    public String deleteComment(
            @PathVariable("newsId") Long newsId,
            @PathVariable("commentId") Long commentId) {
        Long thatNews= newsId;
        commentService.deleteComment(commentId);
        return "redirect:/news/{thatNews}";
    }

}
