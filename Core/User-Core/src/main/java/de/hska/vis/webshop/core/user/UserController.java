package de.hska.vis.webshop.core.user;

import de.hska.vis.webshop.core.database.dao.DaoFactory;
import de.hska.vis.webshop.core.database.dao.IUserDAO;
import de.hska.vis.webshop.core.database.model.IUser;
import de.hska.vis.webshop.core.database.model.impl.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private IUserDAO dao = DaoFactory.getUserDao();

    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public ResponseEntity<IUser> getUser(@PathVariable String username) {
        IUser user = dao.getUserByUsername(username);

        HttpStatus code = (user == null) ? HttpStatus.NOT_FOUND
                : HttpStatus.OK;
        return new ResponseEntity<>(user, code);
    }

    @RequestMapping(value = "/users/id/{stringId}", method = RequestMethod.GET)
    public ResponseEntity<IUser> getUserById(@PathVariable String stringId) {
        HttpStatus code = HttpStatus.OK;
        IUser user;
        try {
            Integer parsedId = Integer.parseInt(stringId);
            user = dao.getObjectById(parsedId);
            if (user == null) {
                code = HttpStatus.NOT_FOUND;
            }
        } catch (NumberFormatException ex) {
            // invalid number supplied; returning null and HTTP/400
            user = null;
            code = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(user, code);
    }

    @RequestMapping(value = "/users/", method = RequestMethod.PUT)
    public ResponseEntity<Void> getUserById(User user) {
        HttpStatus code;
        if (dao.saveObject(user)) {
            code = HttpStatus.OK;
        } else {
            code = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(code);
    }
}
