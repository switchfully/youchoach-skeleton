package com.switchfully.youcoach.security.authentication.user;

import com.switchfully.youcoach.security.authentication.user.api.*;
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
public class SecuredUserController {
    private final static Logger LOGGER = LoggerFactory.getLogger(SecuredUserController.class);

    private final PasswordResetService passwordResetService;
    private final SecuredUserService securedUserService;

    public SecuredUserController(PasswordResetService passwordResetService, SecuredUserService securedUserService) {
        this.passwordResetService = passwordResetService;
        this.securedUserService = securedUserService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    public SecuredUserDto createUser(@RequestBody CreateSecuredUserDto createSecuredUserDto) {
        if (!isEmailValid(createSecuredUserDto.getEmail())) {
            throw new IllegalStateException("Email is not valid !");
        }
        if (!isPasswordValid(createSecuredUserDto.getPassword())) {
            throw new IllegalStateException("Password needs te be 8 characters : --> 1 capital, 1 lowercase and 1 one number ");
        }
        LOGGER.info("User was added");
        return securedUserService.registerAccount(createSecuredUserDto);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", path = "/validate")
    public VerificationResultDto validateAccount(@RequestBody ValidateAccountDto validationData) {
        return securedUserService.validateAccount(validationData);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", path = "/resend-validation")
    public ResendVerificationDto resendValidation(@RequestBody ResendVerificationDto validationData) {
        return securedUserService.resendValidation(validationData);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(consumes = "application/json;charset=UTF-8", path = "/password/request-reset")
    public void requestPasswordResetToken(@RequestBody PasswordResetRequestDto resetRequest) {
        passwordResetService.requestPasswordReset(resetRequest);
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", path = "/password")
    public PasswordChangeResultDto performPasswordChange(@RequestBody PasswordChangeRequestDto changeRequest) {
        if(!isPasswordValid(changeRequest.getPassword())){
            return new PasswordChangeResultDto(false);
        }
        return passwordResetService.performPasswordChange(changeRequest);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidFieldsException(IllegalStateException ex, HttpServletResponse response) throws IOException {
        LOGGER.info(ex.getMessage());
        response.sendError(400, ex.getMessage());
    }

    boolean isEmailValid(String email) {
        if (email == null) return false;

        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    boolean isPasswordValid(String password) {
        if (password == null) return false;

        String pattern = "(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8,}";
        return password.matches(pattern);
    }
}
