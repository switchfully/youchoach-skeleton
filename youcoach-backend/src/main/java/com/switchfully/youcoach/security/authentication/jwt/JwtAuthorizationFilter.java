package com.switchfully.youcoach.security.authentication.jwt;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final JwtGenerator jwtGenerator;


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationFailureHandler authenticationFailureHandler, JwtGenerator jwtGenerator) {
        super(authenticationManager);
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        //ugly code and duplication from SecurityConfig.
        //I got no idea to do this better.
        //This basically means do not test security in these cases, something the SecurityConfig does also
        if (request.getRequestURI().contains("/security") && request.getMethod().equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        var authentication = jwtGenerator.convertToken(request.getHeader("Authorization").replace("Bearer ", ""));
        if (authentication == null) {
            authenticationFailureHandler.onAuthenticationFailure(request, response, new AuthenticationCredentialsNotFoundException(""));
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
