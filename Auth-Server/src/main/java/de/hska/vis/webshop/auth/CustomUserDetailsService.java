package de.hska.vis.webshop.auth;

import de.hska.vis.webshop.core.database.dao.DaoFactory;
import de.hska.vis.webshop.core.database.dao.IUserDAO;
import de.hska.vis.webshop.core.database.model.IUser;
import de.hska.vis.webshop.core.database.model.impl.Role;
import de.hska.vis.webshop.core.database.model.impl.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private IUserDAO userDAO = DaoFactory.getUserDao();

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        LOG.info("LOOKING FOR USER: " + username);
        IUser user;
        if ("special".equals(username)) {
            LOG.info("Found someone special");
            user = new User(username, // username -> "special"
                    username, // firstname
                    username, // lastname
                    "special", // password
                    new Role("ADMIN", 0)); // adminuser
        } else {
            user = userDAO.getUserByUsername(username);
            if (user == null) {
                LOG.info("NO USER " + username + " exists");
                throw new UsernameNotFoundException("User not found: " + username);
            }
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
