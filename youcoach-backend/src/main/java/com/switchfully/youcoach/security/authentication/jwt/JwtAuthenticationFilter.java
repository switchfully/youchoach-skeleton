package com.switchfully.youcoach.security.authentication.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchfully.youcoach.security.authentication.user.SecuredUser;
import com.switchfully.youcoach.security.authentication.user.api.Account;
import com.switchfully.youcoach.security.authentication.user.api.AccountService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final AccountService accountService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   AuthenticationFailureHandler failureHandler,
                                   JwtGenerator jwtGenerator, AccountService accountService) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.accountService = accountService;

        setFilterProcessesUrl("/security/login");
        setAuthenticationFailureHandler(failureHandler);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {

        getFailureHandler().onAuthenticationFailure(request, response, failed);
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

        Account account = accountService.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("Could not find account"));

        String token = jwtGenerator.generateToken(account);

        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
    }

}
