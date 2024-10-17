package at.fhtw.websec.backend;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final List<UserDetails> users = new ArrayList<>();

    public UserService(@Qualifier("passwordEncoder") PasswordEncoder passwordEncoder) {
        users.add(User.builder().username("admin").password(passwordEncoder.encode("s3cur!ty")).authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))).build());
        users.add(User.builder().username("user").password(passwordEncoder.encode("password")).authorities(new ArrayList<>()).build());
        users.add(User.builder().username("user2").password(passwordEncoder.encode("12345678")).authorities(new ArrayList<>()).build());
    }

    public boolean verifyAccess(String username, int id) {
        if (id < 0 || id >= users.size()) { // avoid ArrayOutOfBounds exception
            return true;
        }
        return users.get(id).getUsername().equals(username);
    }

    public UserDetails loadUserByUsername(String username) {
        return this.loadUserById(this.getIdOfUser(username));
    }

    public int getIdOfUser(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1; // no user found
    }

    public UserDetails loadUserById(int id) {
        while (id < 0) {// avoid ArrayOutOfBounds exception
            id += users.size();
        }
        id %= users.size();
        return users.get(id);
    }
}
