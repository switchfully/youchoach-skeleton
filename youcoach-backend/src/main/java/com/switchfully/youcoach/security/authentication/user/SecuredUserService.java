package com.switchfully.youcoach.security.authentication.user;


import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.security.authentication.jwt.JwtGenerator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


@Service
public class SecuredUserService implements UserDetailsService {

    private final ProfileRepository profileRepository;
    private final JwtGenerator jwtGenerator;
    private String jwtSecret;

    public SecuredUserService(ProfileRepository profileRepository, JwtGenerator jwtGenerator) {
        this.profileRepository = profileRepository;
        this.jwtGenerator = jwtGenerator;
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
        return ud.getAuthorities().contains(UserRole.ROLE_ADMIN);
    }

    public String generateToken(String email) {
        UserDetails ud = loadUserByUsername(email);
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(ud.getUsername(),null, ud.getAuthorities());
        return generateToken(user);
    }


    public String generateToken(Authentication authentication) {
        return jwtGenerator.generateJwtToken(profileRepository.findByEmail(authentication.getName()).map(Profile::getId).map(Object::toString).orElse(null), authentication.getName(), authentication.getAuthorities());
    }
}
