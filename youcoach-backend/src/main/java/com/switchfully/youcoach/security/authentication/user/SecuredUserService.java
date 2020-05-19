package com.switchfully.youcoach.security.authentication.user;

import com.switchfully.youcoach.datastore.repositories.AdminRepository;
import com.switchfully.youcoach.datastore.repositories.CoachRepository;
import com.switchfully.youcoach.datastore.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final CoachRepository coachRepository;

    public SecuredUserService(UserRepository userRepository, CoachRepository coachRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.coachRepository = coachRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
        com.switchfully.youcoach.datastore.entities.User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));

        Collection<GrantedAuthority> authorities = determineGrantedAuthorities(user);

        return new ValidatedUser(user.getEmail(), user.getPassword(), authorities, user.isAccountEnabled());
    }

    public Collection<GrantedAuthority> determineGrantedAuthorities(com.switchfully.youcoach.datastore.entities.User user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(UserRoles.ROLE_COACHEE);
        if(isAdmin(user)) authorities.add(UserRoles.ROLE_ADMIN);
        if(isCoach(user)) authorities.add(UserRoles.ROLE_COACH);
        return authorities;
    }

    private boolean isAdmin(com.switchfully.youcoach.datastore.entities.User user){
        return adminRepository.findAdminByUser(user).isPresent();
    }

    private boolean isCoach(com.switchfully.youcoach.datastore.entities.User user){
        return coachRepository.findCoachByUser(user).isPresent();
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
