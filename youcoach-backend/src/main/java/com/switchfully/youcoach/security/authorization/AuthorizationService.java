package com.switchfully.youcoach.security.authorization;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static com.switchfully.youcoach.security.authentication.user.UserRole.ROLE_ADMIN;

@Service
public class AuthorizationService {

    private ProfileRepository profileRepository;

    public AuthorizationService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public boolean canAccessProfile(Authentication authentication, long profileIdentifier) {
        return authentication.getAuthorities().contains(ROLE_ADMIN) || profileRepository.findByEmail(authentication.getName()).map(Profile::getId).map(id -> id.equals(profileIdentifier)).orElse(false);
    }

    public boolean canChangeRole(Authentication authentication) {
        return authentication.getAuthorities().contains(ROLE_ADMIN);
    }

    public boolean canAccessSession(Authentication authentication, long profileIdentifier) {
        return canAccessProfile(authentication, profileIdentifier);
    }
}
