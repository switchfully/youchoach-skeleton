package com.switchfully.youcoach.domain.profile;

import com.switchfully.youcoach.domain.profile.api.ProfileDto;
import com.switchfully.youcoach.domain.profile.api.ProfileUpdatedDto;
import com.switchfully.youcoach.domain.profile.api.UpdateProfileDto;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachListingDto;
import com.switchfully.youcoach.domain.profile.role.coach.api.CoachProfileDto;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import com.switchfully.youcoach.security.authentication.user.api.SecuredUserDto;
import com.switchfully.youcoach.security.authorization.AuthorizationService;
import com.switchfully.youcoach.security.verification.PasswordResetService;
import com.switchfully.youcoach.security.verification.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@CrossOrigin
public class ProfileController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

    private final ProfileService profileService;
    private final PasswordResetService passwordResetService;
    private final AuthorizationService authorizationService;

    @Autowired
    public ProfileController(ProfileService profileService, PasswordResetService passwordResetService, AuthorizationService authorizationService) {
        this.profileService = profileService;
        this.passwordResetService = passwordResetService;
        this.authorizationService = authorizationService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SecuredUserDto createUser(@RequestBody CreateSecuredUserDto createValidatedUserDto) {
        LOGGER.info("user was added");
        return profileService.createUser(createValidatedUserDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<ProfileDto> geProfiles() {
        return profileService.getProfiles();
    }

    @PreAuthorize("hasRole('ROLE_COACHEE')")
    @GetMapping(produces = "application/json;charset=UTF-8", path = "/find-coach")
    public CoachListingDto getCoachProfiles() {
        return profileService.getCoachProfiles();
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COACHEE')")
    @GetMapping(produces = "application/json;charset=UTF-8", path = "/profile/{id}")
    public ProfileDto getSpecificCoacheeProfile(@PathVariable("id") long id, Authentication principal) {
        if(!authorizationService.canAccessProfile(principal, id)) {
            throw new InsufficientAuthenticationException("You don't have access to this profile");
        }
        return profileService.getCoacheeProfile(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_COACHEE', 'ROLE_COACH', 'ROLE_ADMIN')")
    @GetMapping(produces = "application/json;charset=UTF-8", path = "/coach/profile/{id}")
    public CoachProfileDto getSpecificCoachProfile(Principal principal, @PathVariable("id") long id) {
        return profileService.getCoachProfile(principal, id);
    }

    @PreAuthorize("hasAnyRole('ROLE_COACHEE', 'ROLE_ADMIN')")
    @PutMapping(produces = "application/json;charset=UTF-8", consumes = "application/json", path = "/profile/{id}")
    public ProfileUpdatedDto updateCoacheeProfile(@RequestBody UpdateProfileDto updateProfileDto, @PathVariable("id") long id, Authentication principal) {
        if(!authorizationService.canAccessProfile(principal, id)) {
            throw new InsufficientAuthenticationException("You don't have access to this profile");
        }
        if(!authorizationService.canChangeRole(principal)) {
            updateProfileDto.clearRole();
        }
        String email = profileService.getUserById(id).getEmail();
        return profileService.updateProfile(email, updateProfileDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_COACH','ROLE_ADMIN')")
    @PutMapping(produces = "application/json;charset=UTF-8", path = "/coach/profile/{id}")
    public CoachProfileDto updateCoachInformation(@RequestBody CoachProfileDto coachProfileDto, @PathVariable("id") long id, Authentication principal) {
        if(!authorizationService.canAccessProfile(principal, id)) {
            throw new InsufficientAuthenticationException("You don't have access to this profile");
        }
        String email = profileService.getUserById(id).getEmail();
        return profileService.updateCoachInformation(email, coachProfileDto);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", path = "/validate")
    public VerificationResultDto validateAccount(@RequestBody ValidateAccountDto validationData) {
        return profileService.validateAccount(validationData);
    }

    @PreAuthorize("isAnonymous()")
    @PatchMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", path = "/validate")
    public ResendVerificationDto resendValidation(@RequestBody ResendVerificationDto validationData) {
        return profileService.resendValidation(validationData);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(consumes = "application/json;charset=UTF-8", path = "/password/reset")
    public void requestPasswordResetToken(@RequestBody PasswordResetRequestDto resetRequest) {
        passwordResetService.requestPasswordReset(resetRequest);
    }

    @PreAuthorize("isAnonymous()")
    @PatchMapping(consumes = "application/json;charset=UTF-8", path = "/password/reset", produces = "application/json;charset=UTF-8")
    public PasswordChangeResultDto performPasswordChange(@RequestBody PasswordChangeRequestDto changeRequest) {
        return passwordResetService.performPasswordChange(changeRequest);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidFieldsException(IllegalStateException ex, HttpServletResponse response) throws IOException {
        LOGGER.info(ex.getMessage());
        response.sendError(400, ex.getMessage());
    }
}
