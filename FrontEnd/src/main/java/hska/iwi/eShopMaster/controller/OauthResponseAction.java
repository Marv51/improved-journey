package hska.iwi.eShopMaster.controller;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import hska.iwi.eShopMaster.WebShopApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class OauthResponseAction extends ActionSupport {
    private static Logger logger = LoggerFactory.getLogger(OauthResponseAction.class);

    public String state;
    public String code;

    @Override
    public String execute() throws Exception {
        final OAuth20Service service = new ServiceBuilder("acme")
                .apiSecret("acmesecret")
                .callback("http://localhost:8787/OauthResponseAction")
                .state("testing123")
                .scope("openid").debug()
                .build(WebShopApi.instance());

        OAuth2AccessToken token = service.getAccessToken(code);
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("WebShopAccessToken", token.getAccessToken());

        logger.error("\n\n###################################################\n\n");
        logger.error(token.getRawResponse());
        logger.error("\n\n###################################################\n\n");
/*
    // Return string:
    String result = "input";

    UserManager userManager = new UserManagerImpl();

    // Get user from DB:
    IUser user = userManager.getUserByUsername(getUsername());
    // Does user exist?
        if (user != null) {
        // Is the password correct?
        if (user.getPassword().equals(getPassword())) {
            // Get session to save user role and login:
            Map<String, Object> session = ActionContext.getContext().getSession();

            // Save user object in session:
            session.put("webshop_user", user);
            session.put("message", "");
            firstname = user.getFirstname();
            lastname = user.getLastname();
            role = user.getRole().getTyp();
            result = "success";
        } else {
            addActionError(getText("error.password.wrong"));
            logger.error("PASSWORD IS WRONG");
        }
    } else {
        // user does not exist
        addActionError(getText("error.username.wrong"));
        logger.error("ERROR USERNAME WRONG");
    }
        logger.error("LOGGED IN SUCCESSFULLY");


        return result;*/
        return "success";
    }

}
