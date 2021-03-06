package hska.iwi.eShopMaster.model.businessLogic.manager;

import de.hska.vis.webshop.core.database.model.IUser;
import de.hska.vis.webshop.core.database.model.impl.Role;

public interface UserManager {
    boolean registerUser(String username, String name, String lastname, String password);

    IUser getUserByUsername(String username);

    Role getRoleByLevel(int level);

    boolean doesUserAlreadyExist(String username);
}
