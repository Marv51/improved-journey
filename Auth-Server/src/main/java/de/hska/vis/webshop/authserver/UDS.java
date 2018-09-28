package de.hska.vis.webshop.authserver;

import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.hska.vis.webshop.authserver.exception.UserNotExist;
import de.hska.vis.webshop.core.database.model.IUser;
import feign.Feign;
import feign.jackson.JacksonDecoder;

class UDS implements UserDetailsService {

    private final UserClient userClient;

    public UDS() {
        AuthserverApplication.logger.error("INITIALIZING UDS");
        userClient = Feign.builder().errorDecoder(new UserErrorDecoder()).decoder(new ResponseEntityDecoder(new JacksonDecoder()))
                .target(UserClient.class, "http://zuul:8081");
        AuthserverApplication.logger.error("INITIALIZED UDS");
    }

    private IUser getUser(String username) throws UserNotExist, NullPointerException {
        AuthserverApplication.logger.error("UDS: GETTING USER");
        return userClient.getUserByUsername(username).getBody();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return new ModifiedUser(getUser(username));
        } catch (UserNotExist userNotExist) {
            throw new UsernameNotFoundException("Username " + username + " doesn't exist!", userNotExist);
        }
    }
}
