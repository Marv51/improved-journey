package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import de.hska.vis.webshop.core.database.model.IUser;
import de.hska.vis.webshop.core.database.model.impl.Role;
import de.hska.vis.webshop.core.database.model.impl.User;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import hska.iwi.eShopMaster.clients.UserClient;
import hska.iwi.eShopMaster.controller.LoginAction;
import hska.iwi.eShopMaster.model.businessLogic.manager.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.http.ResponseEntity;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public class UserManagerImpl implements UserManager {
	UserClient helper;

	private final static Logger logger = LoggerFactory.getLogger(UserManagerImpl.class);
	
	public UserManagerImpl() {
		helper = Feign.builder().decoder(new ResponseEntityDecoder(new JacksonDecoder()))
				.target(UserClient.class, "http://localhost:8080");
	}

	
	public void registerUser(String username, String name, String lastname, String password, Role role) {
		throw new NotImplementedException();
		//User user = new User(username, name, lastname, password, role);
		//helper.saveObject(user);
	}

	
	public IUser getUserByUsername(String username) {
		if (username == null || username.equals("")) {
			return null;
		}
		ResponseEntity<IUser> response = helper.getUserByUsername(username);
		logger.error("Got response from User-Service" + response.toString());
		return response.getBody();
	}

	public Role getRoleByLevel(int level) {
		throw new NotImplementedException();
		//RoleDAO roleHelper = new RoleDAO();
		//return roleHelper.getRoleByLevel(level);
	}

	public boolean doesUserAlreadyExist(String username) {
		
    	IUser dbUser = this.getUserByUsername(username);
    	
    	if (dbUser != null){
    		return true;
    	}
    	else {
    		return false;
    	}
	}
	

	public boolean validate(User user) {
		if (user.getFirstname().isEmpty() || user.getPassword().isEmpty() || user.getRole() == null || user.getLastname() == null || user.getUsername() == null) {
			return false;
		}
		
		return true;
	}

}
