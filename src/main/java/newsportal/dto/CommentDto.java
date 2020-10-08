package newsportal.dto;

import newsportal.model.News;

public class CommentDto {

    private Long newsId;
    private String message;

    public CommentDto() {
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
