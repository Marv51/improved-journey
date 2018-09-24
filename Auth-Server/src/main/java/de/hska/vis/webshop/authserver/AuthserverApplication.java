package de.hska.vis.webshop.authserver;

import de.hska.vis.webshop.core.database.dao.DaoFactory;
import de.hska.vis.webshop.core.database.dao.IUserDAO;
import de.hska.vis.webshop.core.database.model.IUser;
import feign.Feign;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.actuate.autoconfigure.ShellProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.security.KeyPair;
import java.util.Collection;

/**
 * The service to handle the authorization and token generation/validation.
 *
 * DISCLAIMER:
 * HACKIEST CLASS / FILE I'VE EVER BUILD; please don't judge me
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableWebSecurity
@EnableFeignClients
@EnableHystrix
public class AuthserverApplication extends WebMvcConfigurerAdapter {

    public static Logger logger = LoggerFactory.getLogger(AuthserverApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AuthserverApplication.class, args);
    }

    @Bean
    FilterRegistrationBean forwardedHeaderFilter() {
        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        filterRegBean.setFilter(new ForwardedHeaderFilter());
        filterRegBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegBean;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/oauth/confirm_access").setViewName("authorize");
    }


    /**
     * Defines the login configuration and wiring of the {@link UDS}.
     */
    @Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
    @Configuration
    protected static class LoginConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .formLogin().loginPage("/login").permitAll()
                    .and().authorizeRequests()
                    .anyRequest().authenticated();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(new UDS()).passwordEncoder(new PlaintextPasswordEncoder())
                    .and()
                    .inMemoryAuthentication()
                    // configuring user:user as in memory user (to distinguish between user and admin from db)
                    .withUser("user").password("user").roles("ADMIN");
        }
    }

    /**
     * Rewrites the OAuth2 configuration.
     */
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            AuthserverApplication.logger.error("CONFIGURATION: GETTING KEYPAIR FROM KEYSTORE");
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "foobar".toCharArray())
                    .getKeyPair("test");
            converter.setKeyPair(keyPair);
            AuthserverApplication.logger.error("CONFIGURATION: GOT KEYPAIR FROM KEYSTORE");
            return converter;
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("acme")
                    .secret("acmesecret")
                    .authorizedGrantTypes(/*"implicit", */"authorization_code", "refresh_token", "password")
                    .scopes("openid");
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .authenticationManager(authenticationManager)
                    .userDetailsService(new UDS())
                    .accessTokenConverter(jwtAccessTokenConverter());
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
        }

    }
}

class UDS implements UserDetailsService {

    private final UserClient userClient;

    public UDS() {
        AuthserverApplication.logger.error("INITIALIZING UDS");
        userClient = Feign.builder().errorDecoder(new UserErrorDecoder()).decoder(new ResponseEntityDecoder(new JacksonDecoder()))
                .target(UserClient.class, "http://zuul:8081");
        AuthserverApplication.logger.error("INITIALIZED UDS");
    }

    private IUser getUser(String username) throws UserNotExist, NullPointerException {
        AuthserverApplication.logger.error("UDS: GETTING USER");
        return userClient.getUserByUsername(username).getBody();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return new ModifiedUser(getUser(username));
        } catch (UserNotExist userNotExist) {
            throw new UsernameNotFoundException("Username " + username + " doesn't exist!", userNotExist);
        }
    }
}

class ModifiedUser implements UserDetails {

    private final IUser user;

    ModifiedUser(IUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO WHAT THE HELL IS THIS?
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

class UserErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return new UserNotExist();
    }
}

class UserNotExist extends Exception {
}