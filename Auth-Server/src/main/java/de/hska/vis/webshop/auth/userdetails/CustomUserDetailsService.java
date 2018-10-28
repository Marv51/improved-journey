package de.hska.vis.webshop.auth.userdetails;

import de.hska.vis.webshop.auth.clients.UserClient;
import de.hska.vis.webshop.core.database.dao.DaoFactory;
import de.hska.vis.webshop.core.database.dao.IUserDAO;
import de.hska.vis.webshop.core.database.model.IUser;
import de.hska.vis.webshop.core.database.model.impl.Role;
import de.hska.vis.webshop.core.database.model.impl.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private IUserDAO userDAO = DaoFactory.getUserDao();
    @Autowired
    UserClient client;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        LOG.info("LOOKING FOR USER: " + username);
        IUser user;
        if ("special".equals(username) || SpecialUserDetails.getString().equals(username)) {
            LOG.info("Found someone special or a circle...");
            user = new User(username, // username -> "special"
                    username, // firstname
                    username, // lastname
                    username, // password
                    new Role("ADMIN", 0)); // adminuser
        } else {

            try {
                ResponseEntity<IUser> userByUsername = client.getUserByUsername(username);
                user = userByUsername.getBody();
            } catch (Exception e) {
                LOG.error("Exception while fetching a User");
                LOG.error(e.getMessage());
                throw new UsernameNotFoundException("User not found: " + username);
            }
            /*
            user = userDAO.getUserByUsername(username);
            if (user == null) {
                LOG.info("NO USER " + username + " exists");
            }
            */
        }

        return new ModifiedUserDetails(user);
    }
}

class ModifiedUserDetails implements UserDetails {

    private final IUser user;

    ModifiedUserDetails(@NotNull IUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> result = new ArrayList<>();
        // should result in an authority "ADMIN" or "USER"
        result.add(new SimpleGrantedAuthority(user.getRole().getTyp().toUpperCase()));
        return result;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
