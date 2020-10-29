package newsportal.model;

import newsportal.enums.NotificationType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<User> users;
    private String linkMessage;
    private NotificationType type;
    private boolean isSeen;
    private LocalDateTime creationTime;

    public Notification() {
        this.isSeen = false;
        this.creationTime = LocalDateTime.now();
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user){
        users.add(user);
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public NotificationType getType() {
        return type;
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
