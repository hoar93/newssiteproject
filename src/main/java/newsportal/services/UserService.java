package newsportal.services;

import newsportal.dto.HashtagDto;
import newsportal.model.Authority;
import newsportal.model.Hashtag;
import newsportal.model.User;
import newsportal.repos.HashtagRepository;
import newsportal.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;

    private UserRepository userRepository;
    private HashtagRepository hashtagRepository;

    @Autowired
    public UserService(UserRepository userRepository, HashtagRepository hashtagRepository) {
        this.userRepository = userRepository;
        this.hashtagRepository = hashtagRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findUserByUsername(username);
            return user;
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }

    //TODO átnevezni változókat
    //  duplikációt nem engedni!
    @Transactional
    public void setFollowedHashtag(String hashtag) {
        User loggedInUser = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Hashtag hashtag1 = hashtagRepository.findHashtagByName(hashtag);
        hashtag1.addUser(loggedInUser);
        loggedInUser.addHashtag(hashtagRepository.findHashtagByName(hashtag));
    }

    @Transactional
    public void setFollowedById(Long hashtagId) {
        User loggedInUser = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Hashtag hashtag1 = hashtagRepository.findHashtagById(hashtagId);
        hashtag1.addUser(loggedInUser);
        loggedInUser.addHashtag(hashtagRepository.findHashtagById(hashtagId));
    }

    @Transactional
    public List<HashtagDto> followedHashtags() {
        User loggedInUser = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<HashtagDto> followedHashtags = new ArrayList<>();
        for (int i = 0; i < loggedInUser.getHashtags().size(); i++) {
            HashtagDto hashtagDto = new HashtagDto();
            hashtagDto.setName(loggedInUser.getHashtags().get(i).getName());
            //hashtagDto.setId(loggedInUser.getHashtags().get(i).getId());
            followedHashtags.add(hashtagDto);
        }
        return followedHashtags;
    }

    @Transactional
    public void removeFollowedHashtag(String hashtagMame) {
        User loggedInUser = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        loggedInUser.removeHashtag(hashtagRepository.findHashtagByName(hashtagMame));
    }
    @Transactional
    public void createUser(User user) {
        //exception is already exist
        //TODO átírni - register.html git/messengerapp
        Authority userAuthority = em.createQuery("select a from Authority a where a.name = 'ROLE_USER'", Authority.class).getSingleResult();
        user.addAuthority(userAuthority);
        em.persist(user);
    }
}
