package com.switchfully.youcoach.security.authentication.user;


import com.switchfully.youcoach.domain.member.Member;
import com.switchfully.youcoach.domain.admin.AdminRepository;
import com.switchfully.youcoach.domain.coach.CoachRepository;
import com.switchfully.youcoach.domain.member.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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

    private final MemberRepository userRepository;
    private final AdminRepository adminRepository;
    private final CoachRepository coachRepository;

    public SecuredUserService(MemberRepository memberRepository, CoachRepository coachRepository, AdminRepository adminRepository) {
        this.userRepository = memberRepository;
        this.coachRepository = coachRepository;
        this.adminRepository = adminRepository;
    }

    @Override

    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
        Member member = userRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));

        Collection<GrantedAuthority> authorities = determineGrantedAuthorities(member);

        return new ValidatedUser(member.getEmail(), member.getPassword(), authorities, member.isAccountEnabled());
    }

    public Collection<GrantedAuthority> determineGrantedAuthorities(Member member) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(UserRoles.ROLE_COACHEE);
        if(isAdmin(member)) authorities.add(UserRoles.ROLE_ADMIN);
        if(isCoach(member)) authorities.add(UserRoles.ROLE_COACH);
        return authorities;
    }

    private boolean isAdmin(Member member){
        return adminRepository.findAdminByMember(member).isPresent();
    }

    public boolean isAdmin(String email){
        UserDetails ud = loadUserByUsername(email);
        return ud.getAuthorities().contains(UserRoles.ROLE_ADMIN);
    }

    private boolean isCoach(Member member){
        return coachRepository.findCoachByMember(member).isPresent();

    }


    public String generateAuthorizationBearerTokenForUser(String email, String jwtSecret) {
        UserDetails ud = loadUserByUsername(email);
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(ud.getUsername(),null, ud.getAuthorities());

        return generateJwtToken(user,jwtSecret);
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
