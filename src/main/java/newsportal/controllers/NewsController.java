package newsportal.controllers;

import newsportal.dto.NewsDto;
import newsportal.services.NewsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NewsController {

    NewsServices newsServices;

    @Autowired
    public NewsController(NewsServices newsServices) {
        this.newsServices = newsServices;
    }

    @GetMapping(path = "/news")
    public String showNews() {
        return null;
    }

    /*
    @GetMapping(path = "/news/{newsId}")
    public String showOneNews(
            @PathVariable("newsId") Long newsId,
            Model model) {
        News oneNews = newsServices.oneNews(newsId);
        model.addAttribute("oneNews", oneNews);
        return "news";
    }

     */

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


}
