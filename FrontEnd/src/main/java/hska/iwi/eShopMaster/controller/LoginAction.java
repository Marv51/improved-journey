package hska.iwi.eShopMaster.controller;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.opensymphony.xwork2.ActionSupport;
import hska.iwi.eShopMaster.WebShopApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginAction extends ActionSupport {
    private final static Logger logger = LoggerFactory.getLogger(LoginAction.class);

    private static final long serialVersionUID = -983183915002226000L;
    private String username = null;
    private String password = null;
    private String firstname;
    private String lastname;
    private String role;


    private String url;

    public String getUrl() {
        return url;
    }


    @Override
    public String execute() throws Exception {

        final OAuth20Service service = new ServiceBuilder("acme")
                .apiSecret("acmesecret")
                .callback("http://localhost:8787/OauthResponseAction")
                .state("testing123")
                .scope("openid").debug()
                .build(WebShopApi.instance());

        final String authorizationUrl = service.getAuthorizationUrl();

        url = authorizationUrl;
        return "redirect";

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
    }

    @Override
    public void validate() {
        if (getUsername() == null || getUsername().length() == 0) {
            addActionError(getText("error.username.required"));
        }
        if (getPassword() == null || getPassword().length() == 0) {
            addActionError(getText("error.password.required"));
        }
    }

    public String getUsername() {
        return (this.username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return (this.password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
