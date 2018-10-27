package hska.iwi.eShopMaster;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.oauth2.clientauthentication.ClientAuthentication;
import com.github.scribejava.core.oauth2.clientauthentication.RequestBodyAuthenticationScheme;

public class WebShopApi extends DefaultApi20 {

    protected WebShopApi() {
    }

    private static class InstanceHolder {
        private static final WebShopApi INSTANCE = new WebShopApi();
    }

    public static WebShopApi instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "http://localhost:8080/uaa/oauth/token";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "http://localhost:8080/uaa/oauth/authorize";
    }

    @Override
    public ClientAuthentication getClientAuthentication() {
        return RequestBodyAuthenticationScheme.instance();
    }
}
