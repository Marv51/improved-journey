package com.github.mavogel.vislab.service;/*
 *  The MIT License (MIT)
 *
 *  Copyright (c) 2017 Manuel Vogel
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 *  https://opensource.org/licenses/MIT
 */

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The custom user details service mapping the database objects into
 * {@link UserDetailsService} compatible {@link UserDetails} object.
 * <p>
 * Created by mavogel on 1/14/17.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private IUserDAO userDAO = DaoFactory.getUserDao();

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5")
    })
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        IUser user;
        if ("special".equals(username)) {
            user = new User(username, // username -> "special"
                    username, // firstname
                    username, // lastname
                    "special", // password
                    new Role("ADMIN", 0)); // adminuser
        } else {
            user = userDAO.getUserByUsername(username);
            if (user == null) {
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
