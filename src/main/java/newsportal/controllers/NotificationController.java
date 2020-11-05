package newsportal.controllers;

import newsportal.dto.ShowNotificationDto;
import newsportal.enums.NotificationType;
import newsportal.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NotificationController {

    private NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public String showNotifications(Model model) {
        notificationService.seenNotifications();
        List<ShowNotificationDto> notificationList = notificationService.NotificationDtoList();
        model.addAttribute("notificationList", notificationList);

        return "notifications";
    }

}
