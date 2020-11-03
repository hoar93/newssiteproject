package newsportal.services;

import newsportal.dto.HashtagDto;
import newsportal.dto.ShowNotificationDto;
import newsportal.enums.NotificationType;
import newsportal.model.Hashtag;
import newsportal.model.Notification;
import newsportal.model.User;
import newsportal.repos.NewsRepository;
import newsportal.repos.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class NotificationService {

    @PersistenceContext
    private EntityManager em;

    private NewsRepository newsRepository;
    private NotificationRepository notificationRepository;
    private UserService userService;

    @Autowired
    public NotificationService(NewsRepository newsRepository, NotificationRepository notificationRepository, UserService userService) {
        this.newsRepository = newsRepository;
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    public void createNewArticleNotifications(List<Hashtag> hashtagList, Long newsId) {
        List<Long> usersToNotify = new ArrayList<>();
        for (Hashtag hashtag : hashtagList) {
            for (User user : hashtag.getUserList()) {
                if (!usersToNotify.contains(user.getId())) {
                    usersToNotify.add(user.getId());
                }
            }
        }

        createNotification(NotificationType.NEW_ARTICLE, newsId, usersToNotify);

        /*
        Map<User, List<Long>> notificateThem = new HashMap<>(); //user, szükséges hashtagek idjei
        List<Long> userIdList = new ArrayList<>();

        for (Hashtag hashtag : hashtagList) {
            for (User user : hashtag.getUserList()) {

            }
        }
        //egy hír. egy usernek, az összes, a cikkbe jelölt ÉS általa követett hashtagek metszete
        //notificateThem.put(user, hashtags);

         */
    }
    //TODO innen induljon a törölt komment értesítés, ezt hívja meg a comment service
    // public void createDeleteCommentNotification


    @Transactional
    public void createNotification(NotificationType notType, Long stringId, List<Long> userId) {
        Notification newNotification = new Notification();
        newNotification.setType(notType);
        //TODO hírnél, első a hír id-je, több részlet a hashtagek)
        newNotification.setLinkMessage(stringId);
        List<User> userList = new ArrayList<>();

        for (Long oneUser : userId) {
            userService.findUserById(oneUser).addNotification(newNotification);
            userList.add(userService.findUserById(oneUser));
        }
        newNotification.setUsers(userList);
        em.persist(newNotification);
    }

    public List<ShowNotificationDto> NotificationDtoList() {
        User user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Notification> notifications = user.getNotifications();
                //notificationRepository.findAllNotificationByUser(user);
        List<ShowNotificationDto> notificationDtos = new ArrayList<>();
        for (Notification oneNotification : notifications) {
            ShowNotificationDto oneNotificationDto = new ShowNotificationDto();
            oneNotificationDto = setNotificationDtoParams(oneNotificationDto, oneNotification);
            notificationDtos.add(oneNotificationDto);
        }
        return notificationDtos;
    }

    public ShowNotificationDto setNotificationDtoParams(ShowNotificationDto showNoti, Notification newNoti) {
        switch (newNoti.getType()) {
            case NEW_ARTICLE:
                //cikk perzisztálása előtt létrehozni a notit, a témát
                //TODO felsorolni a hashtageket link mögé, zárójelben pl
                showNoti.setLinkPath("/news/" + newNoti.getLinkMessage());
                showNoti.setLinkMessage(newsRepository.findNewsById(newNoti.getLinkMessage()).getTitle());
                showNoti.setMessage("Új cikk az egyik általad követett témában: ");
                break;
            case CHANGES:
                showNoti.setLinkPath("");
                showNoti.setMessage("Változások az oldalon. Bővebben: ");
                break;
            case REMOVED_MESSAGE:
                showNoti.setLinkPath("/news/" + newNoti.getLinkMessage());
                showNoti.setLinkMessage(newsRepository.findNewsById(newNoti.getLinkMessage()).getTitle());
                        //findNewsTitleById(newNoti.getLinkMessage()));
                showNoti.setMessage("Törölték a hozzászólásodat az alábbi cikknél: ");
                break;
        }
        showNoti.setCreationTime(newNoti.getCreationTime());
        return showNoti;
    }
}
