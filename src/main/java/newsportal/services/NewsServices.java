package newsportal.services;

import newsportal.dto.NewsDto;
import newsportal.dto.NewsUpdateDto;
import newsportal.model.Hashtag;
import newsportal.model.News;
import newsportal.repos.HashtagRepository;
import newsportal.repos.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import sun.jvm.hotspot.debugger.Page;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NewsServices {

    @PersistenceContext
    private EntityManager em;

    private NewsRepository newsRepository;
    private HashtagRepository hashtagRepository;

    @Autowired
    public NewsServices(NewsRepository newsRepository, HashtagRepository hashtagRepository) {
        this.newsRepository = newsRepository;
        this.hashtagRepository = hashtagRepository;
    }

    public News oneNews(long id) {
        return newsRepository.findNewsById(id);
    }



    public List<News> allNews() {

        return newsRepository.findAll();
    }

    public List<News> allNewsByHashtag(String hashtag) {
        List<News> allNewsByHashtag = new ArrayList<>();
        List<News> all = hashtagRepository.findHashtagByName(hashtag).getNews();
        /*for (News news : hashtag.getNews()) {
            allNewsByHashtag.add(news);
            //TODO HIBA
            // itt nem jön át a news kontent/mainkontent/cím/, de átjön elv az author és a hashtaglist (?)
        }*/
        return all;
    }

    @Transactional
    public void updateContent(Long id, String content) {
        newsRepository.updateNewsContent(id, content);
    }

    @Transactional
    public void createNews(NewsDto newNewsDto) {
        //új news-t létrehoz
        News news = new News();
        news.setAuthor("Szerkesztő");
        news.setTitle(newNewsDto.getTitle());
        news.setContent(newNewsDto.getText());
        news.setMainContent(newNewsDto.getMainText());
        news.setCreationTime(LocalDateTime.now());

        //hashtagek intézése
        List<String> hashtagNames = split(newNewsDto.getHashtags());

        //      nem újakhoz news-t rendel - pipa
        //      újakat létrehoz, newst rendel, perzisztál - pipa
        //      news-hoz hashtaglistet settel - pipa

        List<Hashtag> hashtags = setNewsToDublicate(hashtagNames, news);
        news.setHashtagList(hashtags);
        em.persist(news);
    }

    //TODO ha null, üres lesz
    @Transactional
    public void updateNews(Long id, NewsUpdateDto news) {
        if (!(news.getTitle().equals(null))) {
            newsRepository.updateNewsTitle(id, news.getTitle());
        }
        if (!(news.getText().equals(null))) {
            newsRepository.updateNewsContent(id, news.getText());
        }
        if (!(news.getMainText().equals(null))) {
            newsRepository.updateNewsMainContent(id, news.getMainText());
        }

    }

    private List<Hashtag> setNewsToDublicate(List<String> hashtagNameList, News news) {
        List<Hashtag> newList = new ArrayList<>();
        Hashtag hashtag;
        int i = 0;
        boolean found = false;
        //TODO lehetne for
        while (i < hashtagNameList.size() /*&& !found˛*/) {
            hashtag = hashtagRepository.findHashtagByName(hashtagNameList.get(i));
            if(hashtag != null) {
                //létezik már a hashtag, news-t hozzáad
                hashtagRepository.findHashtagByName(hashtag.getName()).addNews(news);
                newList.add(hashtag);
                //found = true;
            } else {
                //nem létezik még, létrehoz új hashtaget, news-t hozzáad
                Hashtag newHashtag = new Hashtag();
                newHashtag.setName(hashtagNameList.get(i));
                newHashtag.addNews(news);
                newList.add(newHashtag);
                em.persist(newHashtag);
            }
            i++;
        }
        return newList;
    }


    private List<String> split (String original) {
        String[] hashes = (original.split(", "));
        List<String> hashtags = Arrays.asList(hashes);

        return hashtags;
    }

}
