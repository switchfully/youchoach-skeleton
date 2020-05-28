package com.switchfully.youcoach.security;

import com.switchfully.youcoach.security.authentication.user.SecuredUserJSONService;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("development")
@EnableWebSecurity(debug=false)
public class SecurityConfigJSONSwagger extends SecurityConfig {
    private final SecuredUserJSONService securedUserJSONService;
    private final PasswordEncoder passwordEncoder;
    public SecurityConfigJSONSwagger(SecuredUserService securedUserService, SecuredUserJSONService securedUserJSONService,
                                     PasswordEncoder passwordEncoder, @Value("${jwt.secret}") String jwtSecret) {
        super(securedUserService, passwordEncoder, jwtSecret);

        this.securedUserJSONService = securedUserJSONService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/v3/api-docs/**",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/webjars/**");
        super.configure(web);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securedUserJSONService).passwordEncoder(passwordEncoder);
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/v3/api-docs/**",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/webjars/**").permitAll();

        super.configure(http);

    }


}
