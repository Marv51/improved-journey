package de.hska.vis.webshop.core.user;

import de.hska.vis.webshop.core.user.model.IUser;
import de.hska.vis.webshop.core.user.model.impl.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    // TODO stub to show the idea
    public ResponseEntity<IUser> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(new User(), HttpStatus.OK);
    }
}
