package hska.iwi.eShopMaster.model.businessLogic.manager;


import de.hska.vis.webshop.core.database.model.IUser;
import de.hska.vis.webshop.core.database.model.impl.Role;

public interface UserManager {
    
    public void registerUser(String username, String name, String lastname, String password, Role role);
    
    public IUser getUserByUsername(String username);
    
    //public boolean deleteUserById(int id);
    
    public Role getRoleByLevel(int level);
    
    public boolean doesUserAlreadyExist(String username);
}
