package com.switchfully.youcoach.security;

import com.switchfully.youcoach.security.authentication.user.SecuredUserJSONService;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("development")
@EnableWebSecurity(debug=false)
public class SecurityConfigJSON extends SecurityConfig {
    private final SecuredUserJSONService securedUserJSONService;
    private final PasswordEncoder passwordEncoder;
    public SecurityConfigJSON(SecuredUserService securedUserService, SecuredUserJSONService securedUserJSONService,
                              PasswordEncoder passwordEncoder, @Value("${jwt.secret}") String jwtSecret) {
        super(securedUserService, passwordEncoder, jwtSecret);

        this.securedUserJSONService = securedUserJSONService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securedUserJSONService).passwordEncoder(passwordEncoder);
        super.configure(auth);
    }

}
