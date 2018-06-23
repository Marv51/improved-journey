package de.hska.vis.webshop.core.user;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.hska.vis.webshop.core.database.dao.DaoFactory;
import de.hska.vis.webshop.core.database.dao.IUserDAO;
import de.hska.vis.webshop.core.database.model.IUser;
import de.hska.vis.webshop.core.database.model.impl.User;
import de.hska.vis.webshop.core.util.HelperUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private IUserDAO dao = DaoFactory.getUserDao();
    private HelperUtility<IUser, Integer> helper = new HelperUtility<>();

    @HystrixCommand
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public ResponseEntity<IUser> getUser(@PathVariable String username) {
        IUser user = dao.getUserByUsername(username);

        HttpStatus code = (user == null) ? HttpStatus.NOT_FOUND
                : HttpStatus.OK;
        return new ResponseEntity<>(user, code);
    }

    @HystrixCommand
    @RequestMapping(value = "/users/id/{stringId}", method = RequestMethod.GET)
    public ResponseEntity<IUser> getUserById(@PathVariable String stringId) {
        return helper.getResponse(stringId, dao, Integer::parseInt);
    }

    @HystrixCommand
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<Void> registerUser(@RequestBody User user) {
        HttpStatus code;
        if (dao.saveObject(user)) {
            code = HttpStatus.OK;
        } else {
            code = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(code);
    }

    @HystrixCommand
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<IUser>> getUserList() {
        return new ResponseEntity<>(dao.getObjectList(), HttpStatus.OK);
    }
}