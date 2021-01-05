package com.switchfully.youcoach.security;

import com.switchfully.youcoach.domain.profile.ProfileService;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import com.switchfully.youcoach.security.authentication.user.api.SecuredUserDto;
import com.switchfully.youcoach.security.verification.PasswordResetService;
import com.switchfully.youcoach.security.verification.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(path = "/security/account")
@CrossOrigin
public class SecurityAccountController {
    private final static Logger LOGGER = LoggerFactory.getLogger(SecurityAccountController.class);

    private final PasswordResetService passwordResetService;
    private final ProfileService profileService;

    public SecurityAccountController(PasswordResetService passwordResetService, ProfileService profileService) {
        this.passwordResetService = passwordResetService;
        this.profileService = profileService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    public SecuredUserDto createUser(@RequestBody CreateSecuredUserDto createValidatedUserDto) {
        LOGGER.info("user was added");
        return profileService.createUser(createValidatedUserDto);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", path = "/validate")
    public VerificationResultDto validateAccount(@RequestBody ValidateAccountDto validationData) {
        return profileService.validateAccount(validationData);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", path = "/resend-validation")
    public ResendVerificationDto resendValidation(@RequestBody ResendVerificationDto validationData) {
        return profileService.resendValidation(validationData);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(consumes = "application/json;charset=UTF-8", path = "/password/request-reset")
    public void requestPasswordResetToken(@RequestBody PasswordResetRequestDto resetRequest) {
        passwordResetService.requestPasswordReset(resetRequest);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", path = "/password")
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
