package newsportal.dto;

import newsportal.model.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class CommentShowDto {
    private long id;
    private String creator;
    private String message;
    @DateTimeFormat(pattern = "yyyy/MMMM/dd HH:mm")
    private LocalDateTime creationTime;
    private boolean isAuthor;
    private boolean isFlagged;
    private boolean isChecked;

    public CommentShowDto() {
    }

    public CommentShowDto(long id, String creator, String message, LocalDateTime creationTime, boolean isAuthor, boolean isFlagged, boolean isChecked) {
        this.id = id;
        this.creator = creator;
        this.message = message;
        this.creationTime = creationTime;
        this.isAuthor = isAuthor;
        this.isFlagged = isFlagged;
        this.isChecked = isChecked;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isAuthor() {
        return isAuthor;
    }

    public void setAuthor(boolean author) {
        isAuthor = author;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}