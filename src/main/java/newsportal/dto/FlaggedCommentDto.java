package newsportal.dto;

public class FlaggedCommentDto {
    private Long id;
    private String message;
    private Long newsId;

    public FlaggedCommentDto(Long id, String message, Long newsId) {
        this.id = id;
        this.message = message;
        this.newsId = newsId;
    }

    public FlaggedCommentDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }
}
