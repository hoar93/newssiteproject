package newsportal.model;



import org.springframework.format.annotation.DateTimeFormat;

//import javax.persistence.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String title;
    private String content;

    @ManyToMany (mappedBy = "news")
    private List<Hashtag> hashtagList = new ArrayList<>();

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Hashtag> getHashtagList() {
        return hashtagList;
    }

    @DateTimeFormat(pattern = "yyyy/MMMM/dd HH:mm")
    private LocalDateTime creationTime;

    public News() {

    }

    public void setHashtagList(List<Hashtag> hashtagList) {
        this.hashtagList = hashtagList;
    }

    /*
    public void removeHashtag(Hashtags hashtag) {
        hashtags.remove(hashtag);
        hashtag.getNews().remove(hashtag);
    }
    */

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

