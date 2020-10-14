package newsportal.controllers;

import newsportal.dto.CommentDto;
import newsportal.dto.CommentShowDto;
import newsportal.dto.NewsDto;
import newsportal.dto.NewsUpdateDto;
import newsportal.model.Comment;
import newsportal.model.News;
import newsportal.model.User;
import newsportal.repos.UserRepository;
import newsportal.services.CommentService;
import newsportal.services.NewsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewsController {

    private NewsServices newsServices;
    private CommentService commentService;

    @Autowired
    public NewsController(NewsServices newsServices, CommentService commentService) {
        this.newsServices = newsServices;
        this.commentService = commentService;
    }

    public String showNews() {
        return null;
    }

    @GetMapping(path = "/news/{newsId}")
    public String showOneNews(
            @PathVariable("newsId") Long newsId,
            Model model) {
        News oneNews = newsServices.oneNews(newsId);
        List<CommentShowDto> comments = commentService.allComments(oneNews);
        CommentDto commentDto = new CommentDto();
        commentDto.setNewsId(newsId);
        //boolean isAdmin = false;
        NewsUpdateDto newsUpdateDto = new NewsUpdateDto();

        model.addAttribute("oneNews", oneNews);
        model.addAttribute("comments", comments);
        model.addAttribute("commentDto", commentDto);
        model.addAttribute("newsDto", newsUpdateDto);

        return "news";
    }

    @GetMapping(path = "/hashtag/{hashtagId}")
    public String showArticlesWithHashtag(
            @PathVariable("hashtagId") Long hashtagId) {
            //TODO
        return null;
    }


    @GetMapping(path = "editNews/{newsId}")
    public String showEditNews(
            @PathVariable("newsId") Long newsId,
            Model model) {
        //TODO - default valuehez/null esetén kell
        // model.addAttribute(/*newsupdatedto*/)

        return "editNews";
    }

    //TODO redirekt (máshol is)
    @PostMapping(path = "editNews/{newsId}")
    public String editNews(@PathVariable("newsId") Long newsId,
                           @ModelAttribute("news") NewsUpdateDto news) {
        newsServices.updateNews(newsId, news);
        return "redirect:/news/";
    }


    @GetMapping(path = "/createNews")
    public String showCreate(Model model) {
        model.addAttribute("news", new NewsDto());
        return "createNews";
    }

    @PostMapping(path = "/createNews")
    public String createNews(@ModelAttribute("news") NewsDto news) {
        newsServices.createNews(news);
        return "redirect:/home";
    }
    @PostMapping(path = "/updateNewsContent/{newsId}")
    public String updateComment(
            @PathVariable("newsId") Long newsId,
            @ModelAttribute("content") String content) {
        commentService.updateCommentMessage(newsId, content);

        return "redirect:/news/";
    }


}
