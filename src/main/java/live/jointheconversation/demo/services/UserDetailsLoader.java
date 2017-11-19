package live.jointheconversation.demo.services;

import live.jointheconversation.demo.models.User;
import live.jointheconversation.demo.models.UserWithRoles;
import live.jointheconversation.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collections;

@Service
public class UserDetailsLoader implements UserDetailsService {
    private final UserRepository users;

    @Autowired
    public UserDetailsLoader(UserRepository users){
        this.users=users;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for " + username);
        }

        return new UserWithRoles(user, Collections.emptyList());
    }

}