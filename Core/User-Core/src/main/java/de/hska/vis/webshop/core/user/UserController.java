package de.hska.vis.webshop.core.user;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.hska.vis.webshop.core.database.dao.DaoFactory;
import de.hska.vis.webshop.core.database.dao.IRoleDAO;
import de.hska.vis.webshop.core.database.dao.IUserDAO;
import de.hska.vis.webshop.core.database.model.IRole;
import de.hska.vis.webshop.core.database.model.IUser;
import de.hska.vis.webshop.core.database.model.impl.Role;
import de.hska.vis.webshop.core.database.model.impl.User;
import de.hska.vis.webshop.core.util.HelperUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private IUserDAO userDao = DaoFactory.getUserDao();
    private IRoleDAO roleDao = DaoFactory.getRoleDao();
    private HelperUtility<IUser, Integer> helper = new HelperUtility<>();

    @HystrixCommand
    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public ResponseEntity<IUser> getUser(@PathVariable String username) {
        IUser user = userDao.getUserByUsername(username);

        HttpStatus code = (user == null) ? HttpStatus.NOT_FOUND
                : HttpStatus.OK;
        return new ResponseEntity<>(user, code);
    }

    @HystrixCommand
    @RequestMapping(value = "/users/id/{stringId}", method = RequestMethod.GET)
    public ResponseEntity<IUser> getUserById(@PathVariable String stringId) {
        return helper.getResponse(stringId, userDao, Integer::parseInt);
    }

    @HystrixCommand
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        HttpStatus code;
        if (userDao.updateObject(user)) {
            code = HttpStatus.OK;
        } else {
            code = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(code);
    }

    @HystrixCommand
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<IUser>> getUserList() {
        return new ResponseEntity<>(userDao.getObjectList(), HttpStatus.OK);
    }

    @HystrixCommand
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Void> registerUser(@RequestBody User user) {
        HttpStatus code;

        IRole role = roleDao.getRoleByLevel(user.getRole().getLevel());
        IUser transformedUser = new User(
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getPassword(),
                (Role) role);

        if (userDao.saveObject(transformedUser)) {
            code = HttpStatus.OK;
        } else {
            code = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(code);
    }
}