package newsportal.model;

import newsportal.enums.NotificationType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private String linkMessage;
    private NotificationType type;
    private boolean isSeen;
    private LocalDateTime creationTime;

    public Notification() {
        this.isSeen = false;
        this.creationTime = LocalDateTime.now();
    }

    public NotificationType getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getLinkMessage() {
        return linkMessage;
    }

    public void setLinkMessage(String linkMessage) {
        this.linkMessage = linkMessage;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
