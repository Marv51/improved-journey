package hska.iwi.eShopMaster.controller;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.opensymphony.xwork2.ActionSupport;
import hska.iwi.eShopMaster.WebShopApi;

public class LoginAction extends ActionSupport {
    private static final long serialVersionUID = -983183915002226000L;
    private String url;

    @Override
    public String execute() throws Exception {

        final OAuth20Service service = new ServiceBuilder("acme")
                .apiSecret("acmesecret")
                .callback("http://localhost:8787/OauthResponseAction")
                .state("testing123")
                .scope("openid").debug()
                .build(WebShopApi.instance());

        url = service.getAuthorizationUrl();
        return "redirect";
    }

    public String getUrl() {
        return url;
    }
}
