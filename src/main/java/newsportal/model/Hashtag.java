package newsportal.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany //(mappedBy = "hashtags")
    private List<News> news = new ArrayList<>();
    @ManyToMany
    private List<User> userList = new ArrayList<>();

    public Hashtag() {
    }

    public Hashtag(String name) {
        this.name = name;
    }

    public List<User> getUserList() {
        return userList;
    }
    public void addUser(User user) {
        userList.add(user);
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void addNews(News newNews) {
        news.add(newNews);
    }
}
