package com.switchfully.youcoach.security.authentication.user;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Service
public class SecuredUserJSONService implements UserDetailsService {

    private static final int TOKEN_TIME_TO_LIVE  = 3600000;

    private final SecuredUserRepository securedUserRepository;

    public SecuredUserJSONService(SecuredUserRepository securedUserRepository) {
        this.securedUserRepository = securedUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
        SecuredUser user = securedUserRepository.findByUsername(userName);
        if(user == null) {
            throw new UsernameNotFoundException(userName);
        }

        Collection<GrantedAuthority> authorities = determineGrantedAuthorities(user);

        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    Collection<GrantedAuthority> determineGrantedAuthorities(SecuredUser user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        switch(user.getUsername().toUpperCase()){
            case "COACH":
                authorities.add(UserRoles.COACH);
                break;
            case "ADMIN":
                authorities.add(UserRoles.ADMIN);
                break;
        }
        authorities.add(UserRoles.COACHEE);
        return authorities;
    }

    public String generateJwtToken(Authentication authentication, String jwtSecret) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .setIssuer("secure-api")
                .setAudience("secure-app")
                .setSubject(authentication.getName())
                .setExpiration(new Date(new Date().getTime() + TOKEN_TIME_TO_LIVE))
                .claim("rol", authentication.getAuthorities())
                .compact();
    }

}
