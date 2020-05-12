package com.switchfully.youcoach.security.authentication.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchfully.youcoach.security.authentication.user.SecuredUser;
import com.switchfully.youcoach.security.authentication.user.SecuredUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;
    private final String jwtSecret;
    private final SecuredUserService securedUserService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String jwtSecret, SecuredUserService securedUserService) {
        this.authenticationManager = authenticationManager;
        this.jwtSecret = jwtSecret;
        this.securedUserService = securedUserService;

        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        SecuredUser securedUser = getSecuredUser(request);

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(securedUser.getUsername(), securedUser.getPassword()));
    }

    private SecuredUser getSecuredUser(HttpServletRequest request) {
        try {
            return new ObjectMapper().readValue(request.getInputStream(), SecuredUser.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not read body from request", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {
        String token = securedUserService.generateJwtToken(authentication, jwtSecret);

        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

}
