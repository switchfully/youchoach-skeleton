package com.switchfully.youcoach.security;

import com.switchfully.youcoach.security.authentication.OnAuthenticationFailureHandler;
import com.switchfully.youcoach.security.authentication.jwt.JwtAuthenticationFilter;
import com.switchfully.youcoach.security.authentication.jwt.JwtAuthorizationFilter;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Profile("production")
@EnableWebSecurity(debug=false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final String jwtSecret;
    private final SecuredUserService securedUserService;

    public SecurityConfig(SecuredUserService securedUserService, PasswordEncoder passwordEncoder,
                          @Value("${jwt.secret}") String jwtSecret) {
        this.securedUserService = securedUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtSecret = jwtSecret;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.PATCH, "/users/validate").permitAll()
                .antMatchers(HttpMethod.POST, "/users/validate").permitAll()
                .antMatchers(HttpMethod.PATCH, "/users/password/reset").permitAll()
                .antMatchers(HttpMethod.POST, "/users/password/reset").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtSecret, securedUserService, new OnAuthenticationFailureHandler()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtSecret))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    CorsFilter corsFilter(@Value("${youcoach.allowed.origins}") String origins) {

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setMaxAge(8000L);
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOrigin(origins);
//        corsConfig.addAllowedOrigin("https://you-coach-south.herokuapp.com");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("GET");
        corsConfig.addAllowedMethod("POST");
        corsConfig.addAllowedMethod("PUT");
        corsConfig.addAllowedMethod("PATCH");
        corsConfig.addAllowedMethod("DELETE");


        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsFilter(source);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securedUserService).passwordEncoder(passwordEncoder);
    }

}
