package newsportal.services;

import newsportal.dto.HashtagDto;
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

    @Transactional
    public void setFollowedHashtag(String hashtag) {
        User loggedInUser = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        loggedInUser.addHashtag(hashtagRepository.findHashtagByName(hashtag));
    }

    @Transactional
    public List<HashtagDto> followedHashtags() {
        User loggedInUser = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<HashtagDto> followedHashtags = new ArrayList<>();
        for (int i = 0; i < loggedInUser.getFollowedHashtags().size(); i++) {
            HashtagDto hashtagDto = new HashtagDto();
            hashtagDto.setName(loggedInUser.getFollowedHashtags().get(i).getName());
            followedHashtags.add(hashtagDto);
        }


        return followedHashtags;
    }
}