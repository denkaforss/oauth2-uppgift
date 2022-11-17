package com.example.oauth2uppgift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Controller {

    private final OAuth2AuthorizedClientService oauthService;

    @Autowired
    public Controller(
            OAuth2AuthorizedClientService oauthService
    ) {
        this.oauthService = oauthService;
    }

    @GetMapping("/user")
    public Map<String, Object> user(
            @AuthenticationPrincipal OAuth2User principal,
            Authentication auth
            ) {
        var oauthToken = (OAuth2AuthenticationToken) auth;
        var client =
                oauthService.loadAuthorizedClient(
                        oauthToken.getAuthorizedClientRegistrationId(),
                        oauthToken.getName()
                );
        System.out.println(client.getAccessToken().getTokenValue());
        return principal.getAttributes();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.oauth2Login()
                .successHandler(new Authenticator())
                .and()
                .authorizeRequests()
                .antMatchers("/user")
                .authenticated()
                .antMatchers("/**")
                .permitAll()
                .and()
                .build();
    }
}
