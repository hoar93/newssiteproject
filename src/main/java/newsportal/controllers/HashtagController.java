package newsportal.controllers;

import newsportal.dto.HashtagDto;
import newsportal.services.HashtagService;
import newsportal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(path = "/followedHashtags")
    public String showHashtags(Model model) {
        List<HashtagDto> hashtagList = hashtagService.allHashtagsName();

        HashtagDto newDto = new HashtagDto();
        List<HashtagDto> allFollowed = userService.followedHashtags();

        List<HashtagDto> clearList = removeFollowed(hashtagList, allFollowed);


        model.addAttribute("allHashtags",clearList);
        model.addAttribute("allFollowed", allFollowed);
        model.addAttribute("hashtagdto", newDto);

        return "hashtagTests";
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
    @PostMapping("/addHashtag")
    public String addHashtag(@ModelAttribute ("hashtag") HashtagDto hashtag) {
        userService.setFollowedHashtag(hashtag.getName());
        //return "redirect:/followedHashtags/";
        return "redirect:/home/";
    }

    @GetMapping(path = "/addNewHashtag/{hashtagId}")
    public String addHashtagById(@PathVariable("hashtagId") Long hashtagId) {
        userService.setFollowedById(hashtagId);
        //return "redirect:/followedHashtags/";
        return "redirect:/home/";
    }

    @GetMapping(path = "/removeHashtagById/{hashtagId}")
    public String removeHashtagById(@PathVariable("hashtagId") Long hashtagId) {
        userService.removeFollowedHashtagById(hashtagId);
        //return "redirect:/followedHashtags/";
        return "redirect:/home/";
    }


    @PostMapping(path = "/removeHashtag")
    public String removeHashtag(@ModelAttribute ("removeTag") HashtagDto hashtag) {
        userService.removeFollowedHashtag(hashtag.getName());
        //return "redirect:/followedHashtags/";
        return "redirect:/home/";
    }
}
