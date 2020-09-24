package newsportal.controllers;

import newsportal.dto.HashtagDto;
import newsportal.services.HashtagService;
import newsportal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HashtagController {

    private HashtagService hashtagService;
    private UserService userService;

    @Autowired
    public HashtagController(HashtagService hashtagService, UserService userService) {
        this.hashtagService = hashtagService;
        this.userService = userService;
    }

    @GetMapping("/followedHashtags")
    public String showHashtags(Model model) {
        List<HashtagDto> hashtagList = hashtagService.allHashtagsName(); //TODO minusz a followed-ok

        HashtagDto newDto = new HashtagDto();
        List<HashtagDto> allFollowed = userService.followedHashtags();

        model.addAttribute("allHashtags",hashtagList);
        model.addAttribute("allFollowed", allFollowed);
        model.addAttribute("hashtagdto", newDto);

        return "hashtagTests";
    }
    @PostMapping("/addHashtag")
    public String addHashtag(@ModelAttribute ("hashtag") HashtagDto hashtag) {
        userService.setFollowedHashtag(hashtag.getName());
        return "redirect:/followedHashtags/";
    }
    @PostMapping("/removeHashtag")
    public String removeHashtag(@ModelAttribute ("removeTag") HashtagDto hashtag) {
        userService.removeFollowedHashtag(hashtag.getName());
        return "redirect:/followedHashtags/";
    }
}
