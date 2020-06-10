package com.switchfully.youcoach.domain.profile;

import com.switchfully.youcoach.domain.coach.api.CoachListingDto;
import com.switchfully.youcoach.domain.coach.api.CoachProfileDto;
import com.switchfully.youcoach.security.verification.api.ResendVerificationDto;
import com.switchfully.youcoach.security.verification.PasswordResetService;
import com.switchfully.youcoach.domain.profile.api.ProfileUpdatedDto;
import com.switchfully.youcoach.domain.profile.api.ProfileDto;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import com.switchfully.youcoach.domain.profile.api.UpdateProfileDto;
import com.switchfully.youcoach.security.authentication.user.api.SecuredUserDto;
import com.switchfully.youcoach.security.verification.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping(path = "/users")
@CrossOrigin
public class ProfileController {
    private final ProfileService profileService;
    private final static Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
    private final PasswordResetService passwordResetService;

    @Autowired
    public ProfileController(ProfileService profileService, PasswordResetService passwordResetService) {
        this.profileService = profileService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SecuredUserDto createUser(@RequestBody CreateSecuredUserDto createSecuredUserDto) {
        LOGGER.info("user was added");
        return profileService.createUser(createSecuredUserDto);
    }

    @PreAuthorize("hasRole('ROLE_COACHEE')")
    @GetMapping(produces = "application/json;charset=UTF-8", path = "/profile")
    public ProfileDto getCoacheeProfile(Principal principal){
        return profileService.getCoacheeProfile(principal.getName());
    }

    @PreAuthorize("hasRole('ROLE_COACHEE')")
    @GetMapping(produces = "application/json;charset=UTF-8", path = "/find-coach")
    public CoachListingDto getCoachProfiles(){
        return profileService.getCoachProfiles();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(produces = "application/json;charset=UTF-8", path = "/profile/{id}")
    public ProfileDto getSpecificCoacheeProfile(@PathVariable("id") long id){
        return profileService.getCoacheeProfile(id);
    }

    @PreAuthorize("hasRole('ROLE_COACHEE')")
    @GetMapping(produces = "application/json;charset=UTF-8", path = "/coach/profile/{id}")
    public CoachProfileDto getSpecificCoachProfile(Principal principal, @PathVariable("id") long id){
        return profileService.getCoachProfile(principal, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(produces = "application/json;charset=UTF-8", path = "/profile/{id}")
    public ProfileUpdatedDto updateCoacheeProfile(@RequestBody UpdateProfileDto updateProfileDto, @PathVariable("id") long id){
        String email = profileService.getUserById(id).getEmail();
        return profileService.updateProfile(email, updateProfileDto);
    }

    @PreAuthorize("hasRole('ROLE_COACHEE')")
    @PutMapping(produces = "application/json;charset=UTF-8", path = "/profile")
    public ProfileUpdatedDto updateCoacheeProfile(Principal principal, @RequestBody UpdateProfileDto updateProfileDto){
        return profileService.updateProfile(principal.getName(), updateProfileDto);
    }

    @PreAuthorize("hasRole('ROLE_COACH')")
    @GetMapping(produces = "application/json;charset=UTF-8", path="/coach/profile")
    public CoachProfileDto getCoachProfile(Principal principal){
        return profileService.getCoachProfileForUserWithEmail(principal.getName());
    }

    @PreAuthorize("hasRole('ROLE_COACH')")
    @PutMapping(produces = "application/json;charset=UTF-8", path="/coach/profile")
    public CoachProfileDto updateCoachInformation(Principal principal, @RequestBody CoachProfileDto coachProfileDto){
        return profileService.updateCoachInformation(principal.getName(),coachProfileDto);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", path="/validate")
    public VerificationResultDto validateAccount(@RequestBody ValidateAccountDto validationData){
        return profileService.validateAccount(validationData);
    }

    @PreAuthorize("isAnonymous()")
    @PatchMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", path = "/validate")
    public ResendVerificationDto resendValidation(@RequestBody ResendVerificationDto validationData){
        return profileService.resendValidation(validationData);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(consumes = "application/json;charset=UTF-8", path = "/password/reset")
    public void requestPasswordResetToken(@RequestBody PasswordResetRequestDto resetRequest){
        passwordResetService.requestPasswordReset(resetRequest);
    }
    @PreAuthorize("isAnonymous()")
    @PatchMapping(consumes = "application/json;charset=UTF-8", path = "/password/reset", produces= "application/json;charset=UTF-8")
    public PasswordChangeResultDto performPasswordChange(@RequestBody PasswordChangeRequestDto changeRequest){
        return passwordResetService.performPasswordChange(changeRequest);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidFieldsException(IllegalStateException ex, HttpServletResponse response) throws IOException {
        LOGGER.info(ex.getMessage());
        response.sendError(400, ex.getMessage());
    }
}
