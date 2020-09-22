package newsportal.services;

import newsportal.dto.NewsDto;
import newsportal.modell.Hashtag;
import newsportal.modell.News;
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
    EntityManager em;

    private NewsRepository newsRepository;
    private HashtagRepository hashtagRepository;

    @Autowired
    public NewsServices(NewsRepository newsRepository, HashtagRepository hashtagRepository) {
        this.newsRepository = newsRepository;
        this.hashtagRepository = hashtagRepository;
    }

    @Transactional
    public Page<News> onePage(int page) {
        Pageable pageable = PageRequest.of(page, 3);
        return newsRepository.findAll(pageable);
    }

    @Transactional
    public Page<News> allNews() {
        Pageable pageable = PageRequest.of(0, 3);

        return newsRepository.findAll(pageable);
    }

    @Transactional
    public void createNews(NewsDto newNewsDto) {
        //új news-t létrehoz
        News news = new News();
        news.setAuthor("Szerkesztő");
        news.setTitle(newNewsDto.getTitle());
        news.setContent(newNewsDto.getText());
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

    private List<Hashtag> setNewsToDublicate(List<String> hashtagNameList, News news) {
        List<Hashtag> newList = new ArrayList<>();
        Hashtag hashtag;
        int i = 0;
        boolean found = false;
        //TODO tesztelni
        while (i < hashtagNameList.size() && !found) {
            hashtag = hashtagRepository.findHashtagByName(hashtagNameList.get(i));
            if(hashtag != null) {
                //létezik már a hashtag, news-t hozzáad
                hashtagRepository.findHashtagByName(hashtag.getName()).addNews(news);
                newList.add(hashtag);
                found = true;
            } else {
                //nem létezik még, létrehoz új hashtaget, news-t hozzáad
                Hashtag newHashtag = new Hashtag();
                newHashtag.setName(hashtagNameList.get(i));
                newHashtag.addNews(news);
                newList.add(newHashtag);
                em.persist(newHashtag);
            }
        }
        return newList;
    }


    private List<String> split (String original) {
        String[] hashes = (original.split(", "));
        List<String> hashtags = Arrays.asList(hashes);

        return hashtags;
    }

}
