package com.example.oauth2uppgift;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class Authenticator extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public Authenticator() {
        super();

        setUseReferer(false);
    }
}
