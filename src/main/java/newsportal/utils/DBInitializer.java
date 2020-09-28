package newsportal.utils;

import newsportal.model.Authority;
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
import java.util.Collections;

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

            User user = new User("user", "password", "user@gmail.com", LocalDate.of(1990, 7, 15));
            user.setAuthorities(Collections.singleton(userAuthority));
            User admin = new User("admin", "password", "admin@gmail.com", LocalDate.of(1997, 6, 11));
            admin.setAuthorities(Collections.singleton(adminAuthority));

            em.persist(user);
            em.persist(admin);
        }
        //TODO FONTOS contetn/main content csere, news.html-ben is
        if (em.createQuery("select count(n) from News n", Long.class).getSingleResult() < 7) {

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

            em.persist(news1);
            em.persist(news2);
            em.persist(news3);
            em.persist(news4);
            em.persist(news5);
            em.persist(news6);
            em.persist(news7);
            em.persist(news8);
            em.persist(news9);
        }


    }

}
