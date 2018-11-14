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
    public String error = null;
    public String error_description;

    @Override
    public String execute() throws Exception {
        final OAuth20Service service = new ServiceBuilder("iwi-Webshop")
                .apiSecret("thisisverysecure")
                .callback("http://localhost:8787/OauthResponseAction")
                .state("testing123")
                .scope("openid").debug()
                .build(WebShopApi.instance());

        if (error != null){
            return "input";
        }

        OAuth2AccessToken token = service.getAccessToken(code);
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("WebShopAccessToken", token.getAccessToken());

        try {
            IUser user = decodeJWT(token.getAccessToken());
            session.put("webshop_user", user);
            session.put("message", "");
        } catch (IllegalArgumentException e) {
            logger.error("Error while decoding token", e);
            return "input";
        }
        return "success";
    }

    /**
     * Decode the JWT token from the oauth client.
     * <p>
     * Here we only decode the JWT Token to get the username, so we have something to show to the user easily.
     * DISCLAIMER:
     * We do NOT verify the token here, and probably never ourselves. We give the token to the FeignClients
     * {@link hska.iwi.eShopMaster.clients.UserClient}, {@link hska.iwi.eShopMaster.clients.CategoryClient}
     * and {@link hska.iwi.eShopMaster.clients.ProductClient} which authenticate their requests with this token.
     * It is then checked automatically by the receiver of those requests. This is usually the Zuul-Server.
     * Therefore we don't need to validate the token by ourselves.
     *
     * @param accessToken the access token from the auth callback this are 3 Bas64 encoded string parts, with a "."
     *                    as a divider; decoded the 3 parts are:
     *                    * Header
     *                    * Body
     *                    * Signature
     * @return an IUser object with the information from the JWT body
     * @throws IllegalArgumentException is thrown when the JSON decoding finds an error somewhere, i.e.
     *                                  when the token is not correct
     */
    private IUser decodeJWT(String accessToken) throws IllegalArgumentException {
        String[] split_string = accessToken.split("\\.");
        // String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        // String base64EncodedSignature = split_string[2];

        Base64.Decoder base64Url = Base64.getDecoder();
        // String header = new String(base64Url.decode(base64EncodedHeader));
        String body = new String(base64Url.decode(base64EncodedBody));

        // JWT Header :
        /*
        {
            "alg":"RS256",
            "typ":"JWT"
        }
         */
        // JWT Body :
        /*
        {
            "exp":1540847199,
            "user_name":"special",
            "authorities":["ADMIN"],
            "jti":"f9c9a0c8-9aea-4916-912e-1f83aa644572",
            "client_id":"acme",
            "scope":["openid"]}
         */

        // decode JSON object
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
