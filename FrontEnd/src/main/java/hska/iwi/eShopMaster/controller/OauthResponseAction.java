package hska.iwi.eShopMaster.controller;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import de.hska.vis.webshop.core.database.model.IUser;
import de.hska.vis.webshop.core.database.model.impl.Role;
import de.hska.vis.webshop.core.database.model.impl.User;
import hska.iwi.eShopMaster.WebShopApi;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
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

        IUser user = decodeJWT(token.getAccessToken());
        session.put("webshop_user", user);
        session.put("message", "");

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

    private IUser decodeJWT(String accessToken) {
        String[] split_string = accessToken.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];

        Base64.Decoder base64Url = Base64.getDecoder();
        String header = new String(base64Url.decode(base64EncodedHeader));
        String body = new String(base64Url.decode(base64EncodedBody));

        // JWT Header : {"alg":"RS256","typ":"JWT"}
        // JWT Body : {"exp":1540847199,"user_name":"special","authorities":["ADMIN"],"jti":"f9c9a0c8-9aea-4916-912e-1f83aa644572","client_id":"acme","scope":["openid"]}

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(body);
            String username = node.get("user_name").asText();
            String[] authorities = mapper.readValue(node.get("authorities"), String[].class);
            return new User(username, username, username, "", new Role(authorities[0], 0));
        } catch (Exception e) {
            logger.error("There is something wrong with the OAuth Token", e);
            throw new IllegalArgumentException(e);
        }
    }
}
