package newsportal.dto;

import java.time.LocalDateTime;

public class ShowNotificationDto {
    private String message;
    private String linkMessage;
    private String linkPath;
    private boolean isSeen;
    private LocalDateTime creationTime;

    public ShowNotificationDto(String message, String linkMessage, String linkPath, boolean isSeen, LocalDateTime creationTime) {
        this.message = message;
        this.linkMessage = linkMessage;
        this.linkPath = linkPath;
        this.isSeen = isSeen;
        this.creationTime = creationTime;
    }

    public ShowNotificationDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLinkMessage() {
        return linkMessage;
    }

    public void setLinkMessage(String linkMessage) {
        this.linkMessage = linkMessage;
    }

    public String getLinkPath() {
        return linkPath;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
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
