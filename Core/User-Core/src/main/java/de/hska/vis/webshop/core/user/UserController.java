package de.hska.vis.webshop.core.user;

import de.hska.vis.webshop.core.database.dao.DaoFactory;
import de.hska.vis.webshop.core.database.dao.IUserDAO;
import de.hska.vis.webshop.core.database.model.IUser;
import de.hska.vis.webshop.core.database.model.impl.User;
import de.hska.vis.webshop.core.util.HelperUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class UserController {

    private IUserDAO dao = DaoFactory.getUserDao();
    private HelperUtility<IUser, Integer> helper = new HelperUtility<>();

    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public ResponseEntity<IUser> getUser(@PathVariable String username) {
        IUser user = dao.getUserByUsername(username);

        HttpStatus code = (user == null) ? HttpStatus.NOT_FOUND
                : HttpStatus.OK;
        return new ResponseEntity<>(user, code);
    }

    @RequestMapping(value = "/users/id/{stringId}", method = RequestMethod.GET)
    public ResponseEntity<IUser> getUserById(@PathVariable String stringId) {
        return helper.getResponse(stringId, dao, Integer::parseInt);
    }

    @HystrixCommand(fallbackMethod = "fallbackGetUsers")
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