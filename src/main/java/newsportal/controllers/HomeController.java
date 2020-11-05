package newsportal.controllers;

import newsportal.dto.HashtagDto;
import newsportal.model.Hashtag;
import newsportal.model.News;
import newsportal.services.HashtagService;
import newsportal.services.NewsServices;
import newsportal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class HomeController {

    private NewsServices newsServices;
    private HashtagService hashtagService;
    private UserService userService;

    @Autowired
    public HomeController(NewsServices newsServices, HashtagService hashtagService, UserService userService) {
        this.newsServices = newsServices;
        this.hashtagService = hashtagService;
        this.userService = userService;
    }

    /*
    @GetMapping("/home/{pageNumber}")
    public String listByPage(Model model,
                             @PathVariable("pageNumber") int currentPage) {
        Page<News> page = newsServices.onePage(currentPage);
        long totalNews = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<News> allNews = page.getContent();
        //List<News> allNews = newsServices.allNews();
        model.addAttribute("totalNews", totalNews);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("allNews", allNews);
        return "home";
    }
    */


    @RequestMapping(value = {"/", "/home", "/news", "home/"}, method = GET)
    public String messengerHome(Model model) {
        List<News> allNews = newsServices.allNews();
        List<Hashtag> topHashes = hashtagService.topHashtags();
        List<HashtagDto> hashtagList = hashtagService.allHashtagsName();

        HashtagDto newDto = new HashtagDto();
        if (userService.isAnyoneLoggedIn()) {
            List<HashtagDto> allFollowed = new ArrayList<>();
            allFollowed = userService.followedHashtags();
            List<HashtagDto> clearList = removeFollowed(hashtagList, allFollowed);
            model.addAttribute("allHashtags",clearList);
            model.addAttribute("allFollowed", allFollowed);

        }



        model.addAttribute("hashtagdto", newDto);

        model.addAttribute("allNews", allNews);
        model.addAttribute("topHashes", topHashes);
        return "home";

    }
    private List<HashtagDto> removeFollowed(List<HashtagDto> allHashtags, List<HashtagDto> followedHashtags) {
        List <HashtagDto> clearList = allHashtags;
        for(int i = 0; i < followedHashtags.size(); i++) {
            //String actualFollowedHashtag = followedHashtags.get(i).getName();
            for(int j = 0; j < allHashtags.size(); j++) {
                if (followedHashtags.get(i).getName().equals(allHashtags.get(j).getName())) {
                    clearList.remove(allHashtags.get(j));
                }
            }
        }
        return clearList;
    }
}
