package com.switchfully.youcoach.security.authentication.user;


import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Service
public class SecuredUserService implements UserDetailsService {

    private static final int TOKEN_TIME_TO_LIVE  = 3600000;

    private final ProfileRepository profileRepository;
    private String jwtSecret;

    public SecuredUserService(ProfileRepository profileRepository, @Value("${jwt.secret}") String jwtSecret) {
        this.profileRepository = profileRepository;
        this.jwtSecret = jwtSecret;
    }

    @Override
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
        Profile profile = profileRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));

        Collection<GrantedAuthority> authorities = determineGrantedAuthorities(profile);

        return new SecuredUser(profile.getEmail(), profile.getPassword(), authorities, profile.isAccountEnabled());
    }

    public Collection<GrantedAuthority> determineGrantedAuthorities(Profile profile) {
        return new ArrayList<>(profile.getRoles());
    }

    public boolean isAdmin(String email){
        UserDetails ud = loadUserByUsername(email);
        return ud.getAuthorities().contains(UserRoles.ROLE_ADMIN);
    }

    public String generateAuthorizationBearerTokenForUser(String email) {
        UserDetails ud = loadUserByUsername(email);
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(ud.getUsername(),null, ud.getAuthorities());

        return generateJwtToken(user);
    }

    public String generateJwtToken(Authentication authentication) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .setIssuer("secure-api")
                .setAudience("secure-app")
                .setSubject(authentication.getName())
                .setExpiration(new Date(new Date().getTime() + TOKEN_TIME_TO_LIVE))
                .claim("rol", authentication.getAuthorities())
                .claim("id", profileRepository.findByEmail(authentication.getName()).map(Profile::getId).map(Object::toString).orElse(null))
                .compact();
    }

}
