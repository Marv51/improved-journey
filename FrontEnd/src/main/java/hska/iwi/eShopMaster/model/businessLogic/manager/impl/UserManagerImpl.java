package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import de.hska.vis.webshop.core.database.model.IUser;
import de.hska.vis.webshop.core.database.model.impl.Role;
import de.hska.vis.webshop.core.database.model.impl.User;
import feign.Feign;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import hska.iwi.eShopMaster.clients.UserClient;
import hska.iwi.eShopMaster.clients.configuration.OAuth2FeignClientConfiguration;
import hska.iwi.eShopMaster.model.businessLogic.manager.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.http.ResponseEntity;

public class UserManagerImpl implements UserManager {
    private final static Logger logger = LoggerFactory.getLogger(UserManagerImpl.class);
    private UserClient helper;

    public UserManagerImpl() {
        helper = Feign.builder()
                .errorDecoder(new UserErrorDecoder())
                .requestInterceptor(OAuth2FeignClientConfiguration.oauth2FeignRequestInterceptor())
                .decoder(new ResponseEntityDecoder(new JacksonDecoder()))
                .encoder(new JacksonEncoder())
                .target(UserClient.class, "http://zuul:8081");
    }

    public boolean registerUser(String username, String name, String lastname, String password) {
        User user = new User(username, name, lastname, password, new Role("USER", 1));
        return helper.saveUser(user).getStatusCode().is2xxSuccessful();
    }

    public IUser getUserByUsername(String username) {
        if (username == null || username.equals("")) {
            return null;
        }
        logger.error("Getting users from User-Service");
        try {
            ResponseEntity<IUser> response = helper.getUserByUsername(username);
            logger.error("Got response from User-Service" + response.toString());
            return response.getBody();
        } catch (UserNotExist ex) {
            return null;
        }
    }

    public Role getRoleByLevel(int level) {
        throw new UnsupportedOperationException();
        //RoleDAO roleHelper = new RoleDAO();
        //return roleHelper.getRoleByLevel(level);
    }

    public boolean doesUserAlreadyExist(String username) {
        return this.getUserByUsername(username) != null;
    }

    public boolean validate(User user) {
        return !user.getFirstname().isEmpty()
                && !user.getPassword().isEmpty()
                && user.getRole() != null
                && user.getLastname() != null
                && user.getUsername() != null;
    }

    private class UserErrorDecoder implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {
            logger.error("Error decoder methodKey: " + methodKey);
            logger.error("Response status: " + response.status());
            return new UserNotExist();
        }
    }

    public class UserNotExist extends Exception {
    }
}
