package newsportal.controllers;

import newsportal.modell.News;
import newsportal.services.NewsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class HomeController {

    NewsServices newsServices;

    @Autowired
    public HomeController(NewsServices newsServices) {
        this.newsServices = newsServices;
    }

    @GetMapping("/home/{pageNumber}")
    public String listByPage(Model model,
                             @PathVariable("pageNumber") int currentPage) {
        Page<News> page =  newsServices.onePage(currentPage);
        long totalNews = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<News> allNews = page.getContent();
        //List<News> allNews = newsServices.allNews();
        model.addAttribute("totalNews", totalNews);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("allNews", allNews);
        return "home";
    }


    @RequestMapping(value = {"/", "/home"}, method = GET)
    public String messengerHome(Model model) {
        Page<News> page =  newsServices.allNews();
        long totalNews = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<News> allNews = page.getContent();
        //List<News> allNews = newsServices.allNews();
        model.addAttribute("totalNews", totalNews);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("allNews", allNews);
        return "home";
    }

}
