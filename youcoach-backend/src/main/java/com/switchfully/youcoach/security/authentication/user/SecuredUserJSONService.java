package com.switchfully.youcoach.security.authentication.user;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Service
public class SecuredUserJSONService implements UserDetailsService {
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

        return new ValidatedUser(user.getUsername(), user.getPassword(), authorities, true);
    }

    Collection<GrantedAuthority> determineGrantedAuthorities(SecuredUser user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if(user.getUsername().contains("coach") && !user.getUsername().contains("coachee")) authorities.add(UserRoles.ROLE_COACH);
        if(user.getUsername().contains("admin")) authorities.add(UserRoles.ROLE_ADMIN);

        authorities.add(UserRoles.ROLE_COACHEE);
        return authorities;
    }

}
