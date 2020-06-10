package com.switchfully.youcoach.security.authentication.user;


import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.admin.AdminRepository;
import com.switchfully.youcoach.domain.coach.CoachRepository;
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
public class ValidatedUserService implements UserDetailsService {

    private static final int TOKEN_TIME_TO_LIVE  = 3600000;

    private final ProfileRepository profileRepository;
    private final AdminRepository adminRepository;
    private final CoachRepository coachRepository;
    private String jwtSecret;

    public ValidatedUserService(ProfileRepository profileRepository, CoachRepository coachRepository, AdminRepository adminRepository, @Value("${jwt.secret}") String jwtSecret) {
        this.profileRepository = profileRepository;
        this.coachRepository = coachRepository;
        this.adminRepository = adminRepository;
        this.jwtSecret = jwtSecret;
    }

    @Override
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
        Profile profile = profileRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));

        Collection<GrantedAuthority> authorities = determineGrantedAuthorities(profile);

        return new ValidatedUser(profile.getEmail(), profile.getPassword(), authorities, profile.isAccountEnabled());
    }

    public Collection<GrantedAuthority> determineGrantedAuthorities(Profile profile) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(UserRoles.ROLE_COACHEE);
        if(isAdmin(profile)) authorities.add(UserRoles.ROLE_ADMIN);
        if(isCoach(profile)) authorities.add(UserRoles.ROLE_COACH);
        return authorities;
    }

    private boolean isAdmin(Profile profile){
        return adminRepository.findAdminByProfile(profile).isPresent();
    }

    public boolean isAdmin(String email){
        UserDetails ud = loadUserByUsername(email);
        return ud.getAuthorities().contains(UserRoles.ROLE_ADMIN);
    }

    private boolean isCoach(Profile profile){
        return coachRepository.findCoachByProfile(profile).isPresent();

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
                .compact();
    }

}
