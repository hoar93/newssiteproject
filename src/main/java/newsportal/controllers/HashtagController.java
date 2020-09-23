package newsportal.controllers;

import newsportal.dto.HashtagDto;
import newsportal.services.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HashtagController {

    private HashtagService hashtagService;

    @Autowired
    public HashtagController(HashtagService hashtagService) {
        this.hashtagService = hashtagService;
    }

    @GetMapping("/hashtagFollowTest")
    public String showHashtags(Model model) {
        List<HashtagDto> hashtagList = hashtagService.allHashtagsName();
        HashtagDto newDto = new HashtagDto();
        model.addAttribute("allHashtags",hashtagList);
        model.addAttribute("hashtag", newDto);

        return "hashtagTests";
    }
    @PostMapping("/addHashtag")
    public String addHashtag(@ModelAttribute ("hashtag") String hashtag) {
        return "redirect:/home/";
    }
}
