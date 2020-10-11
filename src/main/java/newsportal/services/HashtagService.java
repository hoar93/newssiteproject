package newsportal.services;

import newsportal.dto.HashtagDto;
import newsportal.model.Hashtag;
import newsportal.repos.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class HashtagService {

    @PersistenceContext
    private EntityManager em;

    private HashtagRepository hashtagRepository;

    @Autowired
    public HashtagService(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    //@Transactional
    public List<Hashtag> findAllHashtags() {
        return hashtagRepository.findAll();
    }

    public List<Hashtag> topHashtags() {
        List<Hashtag> allHashtags = hashtagRepository.findAll();
        class SortByNewsCount implements Comparator<Hashtag>
        {
            public int compare(Hashtag a, Hashtag b)
            {
                return b.getNews().size() - a.getNews().size();
            }
        }
        List<Hashtag> topHashtags = new ArrayList<>();
        Collections.sort(allHashtags, new SortByNewsCount());
        topHashtags = allHashtags.subList(0,5);
        return topHashtags;
    }

    public List<HashtagDto> allHashtagsName() { //TODO minusz a followed-ok
        List<Hashtag> hashtags = findAllHashtags();
        List<HashtagDto> hashtagDtoList = new ArrayList<>();
        for (int i = 0; i < hashtags.size(); i++) {
            HashtagDto oneHash = new HashtagDto();
            oneHash.setName(hashtags.get(i).getName());
            hashtagDtoList.add(oneHash);
        }
        return hashtagDtoList;
    }
}
