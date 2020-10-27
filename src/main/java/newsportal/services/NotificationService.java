package newsportal.services;

import newsportal.dto.ShowNotificationDto;
import newsportal.enums.NotificationType;
import newsportal.model.Notification;
import newsportal.model.User;
import newsportal.repos.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @PersistenceContext
    private EntityManager em;

    private NewsServices newsServices;
    private NotificationRepository notificationRepository;
    private UserService userService;

    @Autowired
    public NotificationService(NewsServices newsServices, NotificationRepository notificationRepository, UserService userService) {
        this.newsServices = newsServices;
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    @Transactional
    public void createNotification(NotificationType notType, String stringId, User user) {
        Notification newNotification = new Notification();
        newNotification.setType(notType);
        newNotification.setLinkMessage(stringId);
        newNotification.setUser(user);
        user.addNotification(newNotification);
        em.persist(newNotification);
    }

    public List<ShowNotificationDto> NotificationDtoList() {
        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Notification> notifications = notificationRepository.findAllNotificationByUser(user);
        List<ShowNotificationDto> notificationDtos = new ArrayList<>();
        for (Notification oneNotification : notifications) {
            ShowNotificationDto oneNotificationDto = new ShowNotificationDto();
            oneNotificationDto = setNotificationDtoParams(oneNotificationDto, oneNotification);
            notificationDtos.add(oneNotificationDto);
        }
        return notificationDtos;
    }

    public ShowNotificationDto setNotificationDtoParams(ShowNotificationDto showNoti, Notification newNoti) {
        switch (newNoti.getType())
        {
            case NEW_ARTICLE:
                showNoti.setLinkPath("/news/"+Long.parseLong(newNoti.getLinkMessage()));
                showNoti.setMessage("Új cikk az általad követett témában: ");
                break;
            case CHANGES:
                showNoti.setLinkPath("");
                showNoti.setMessage("Változások az oldalon. Bővebben: ");
                break;
            case REMOVED_MESSAGE:
                showNoti.setLinkPath("/news/"+Long.parseLong(newNoti.getLinkMessage()));
                showNoti.setMessage("Törölték a hozzászólásodat az alábbi cikknél: ");
                break;
        }
        showNoti.setLinkMessage(newNoti.getLinkMessage());

        return showNoti;
    }

}
