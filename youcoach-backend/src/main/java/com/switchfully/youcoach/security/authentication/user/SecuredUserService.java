package com.switchfully.youcoach.security.authentication.user;

import com.switchfully.youcoach.security.authorization.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
public class SecuredUserService implements UserDetailsService {

    private SecuredUserRepository securedUserRepository;

    public SecuredUserService(SecuredUserRepository securedUserRepository) {
        this.securedUserRepository = securedUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SecuredUser user = securedUserRepository.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }
        return new User(user.getUsername(), user.getPassword(), toAuthority(user));
    }

    private List<SimpleGrantedAuthority> toAuthority(SecuredUser user) {
        return user.getRoles().stream()
                .map(Role::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
