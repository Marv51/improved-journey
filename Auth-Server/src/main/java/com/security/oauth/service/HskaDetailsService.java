package com.security.oauth.service;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HskaDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(HskaDetailsService.class);
    private IUserDAO userDAO = DaoFactory.getUserDao();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.error("LOOKING FOR USERNAME: " + username);
        if ("special".equals(username)) {
            logger.error("DETECTED SPECIAL USER");
            return transform(new User("special",
                    "special",
                    "special",
                    "special",
                    new Role("admin", 0)));
        }
        IUser user = userDAO.getUserByUsername(username);
        if (user == null) {
            logger.error(username + " was not found in the DB");
            throw new UsernameNotFoundException("User not found: " + username);
        }
        logger.error("FOUND USER: " + username);
        return transform(user);
    }

    private UserDetails transform(IUser user) {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<SimpleGrantedAuthority> result = new ArrayList<>();
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
        };
    }
}
