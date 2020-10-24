package newsportal.utils;

import newsportal.model.Authority;
import newsportal.model.Hashtag;
import newsportal.model.News;
import newsportal.model.User;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class DBInitializer {
    @PersistenceContext
    private EntityManager em;
    @EventListener(ContextRefreshedEvent.class)
    public void onAppStartup(ContextRefreshedEvent ev) throws ServletException {
        DBInitializer me = ev.getApplicationContext().getBean(DBInitializer.class);
        me.init();
    }

    @Transactional
    public void init() {
        createAuthoritiesIfNotExist();
        createUsersIfNotExist();
    }

    private void createAuthoritiesIfNotExist() {
        long authorityCount = em.createQuery("select count(a) from Authority a", Long.class).getSingleResult();
        if (authorityCount == 0) {
            Authority userAuthority = new Authority("ROLE_USER");
            Authority adminAuthority = new Authority("ROLE_ADMIN");
            em.persist(userAuthority);
            em.persist(adminAuthority);
        }
    }

    private void createUsersIfNotExist() {
        long userCount = em.createQuery("select count(u) from User u", Long.class).getSingleResult();

        if (userCount == 0) {
            Authority userAuthority = em.createQuery("select a from Authority a where a.name = 'ROLE_USER'", Authority.class).getSingleResult();
            Authority adminAuthority = em.createQuery("select a from Authority a where a.name = 'ROLE_ADMIN'", Authority.class).getSingleResult();

            User user = new User("user", "password", "user@gmail.com", LocalDate.of(1990, 7, 15), "én egy próba user vagyok, sok dolgom nincs egyébként");
            user.setAuthorities(Collections.singleton(userAuthority));
            User admin = new User("admin", "password", "admin@gmail.com", LocalDate.of(1997, 6, 11), "Én vagyok az admin, van jó sok admin jogom is. Amiket használok is, ha úgy van! Bizony, hiszed vagy sem.");
            admin.setAuthorities(Collections.singleton(adminAuthority));

            em.persist(user);
            em.persist(admin);
        }

        if (em.createQuery("select count(n) from News n", Long.class).getSingleResult() < 1) {

            //TODO ciklussal

            News news1 = new News("admin", "cim 1", "bevezeto content, roviden 1", "ez pedig a nagyjából a main content az első cikknél");
            News news2 = new News("admin", "cim 2", "bevezeto content, roviden 2", "ez pedig a nagyjából a main content a második cikknél");
            News news3 = new News("admin", "cim 3", "bevezeto content, roviden 3", "ez pedig a nagyjából a main content a harmadik cikknél");
            News news4 = new News("admin", "cim 4", "bevezeto content, roviden 4", "ez pedig a nagyjából a main content a negyedik cikknél");
            News news5 = new News("admin", "cim 5", "bevezeto content, roviden 5", "ez pedig a nagyjából a main content az ötödik cikknél");
            News news6 = new News("admin", "cim 6", "bevezeto content, roviden 6", "ez pedig a nagyjából a main content a hatodik cikknél");
            News news7 = new News("admin", "cim 7", "bevezeto content, roviden 7", "ez pedig a nagyjából a main content a hetedik cikknél");
            News news8 = new News("admin", "cim 8", "bevezeto content, roviden 8", "ez pedig a nagyjából a main content a nyolcadik cikknél");
            News news9 = new News("admin", "cim 9", "bevezeto content, roviden 9", "ez pedig a nagyjából a main content a kilencedik cikknél");

            List<Hashtag> hashtags = new ArrayList<>();
            hashtags.add(new Hashtag("elso"));
            hashtags.add(new Hashtag("masodik"));
            hashtags.add(new Hashtag("harmadik"));
            hashtags.add(new Hashtag("negyedik"));
            hashtags.add(new Hashtag("otodik"));
            hashtags.add(new Hashtag("hatodik"));
            hashtags.add(new Hashtag("hetedik"));
            hashtags.add(new Hashtag("nyolcadik"));

            setToEachother(hashtags, news1, "012");
            setToEachother(hashtags, news2, "123");
            setToEachother(hashtags, news3, "456");
            setToEachother(hashtags, news4, "567");
            setToEachother(hashtags, news5, "124");
            setToEachother(hashtags, news6, "013");
            setToEachother(hashtags, news7, "467");
        }
    }

    //(összes hashtag listája, azEgyHír, string: egybe számok, int lesz belőlük. ezeket a hashtatageket (id) szetteli egymáshoz, és perzisztál mindent
    private void setToEachother(List<Hashtag> hashtags, News news, String hashtagsToSet){
        List<Integer> listInInt = split(hashtagsToSet);
        List<Hashtag> hashesToSet = new ArrayList<>();
        for (int i = 0; i < listInInt.size(); i++) {
            Hashtag actualHashtag = hashtags.get(listInInt.get(i));
            actualHashtag.addNews(news);
            hashesToSet.add(actualHashtag);
            em.persist(actualHashtag);
        }
        news.setHashtagList(hashesToSet);
        em.persist(news);
    }

    private List<Integer> split (String setTheseList)
    {
        List<Integer> splittedList = new ArrayList<>();
        String[] setThese = (setTheseList.split(""));
        List<String> splittedListString = Arrays.asList(setThese);
        for (String s : splittedListString) {
            splittedList.add(Integer.valueOf(s));
        }
        return splittedList;
    }
}
