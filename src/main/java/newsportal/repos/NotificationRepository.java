package newsportal.repos;

import newsportal.model.Notification;
import newsportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    //List<Notification> findAllNotificationByUser(User user);

    @Modifying
    @Query("update Notification n set n.isSeen = true where n.id =:id")
    void setSeen(@Param(value = "id") long id);

}
