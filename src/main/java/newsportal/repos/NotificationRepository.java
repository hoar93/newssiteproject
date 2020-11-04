package newsportal.repos;

import newsportal.model.Notification;
import newsportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    //List<Notification> findAllNotificationByUser(User user);
}