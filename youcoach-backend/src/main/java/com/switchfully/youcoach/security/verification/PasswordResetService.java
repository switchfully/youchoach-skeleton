package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.email.EmailExecutor;
import com.switchfully.youcoach.email.command.resetpassword.ResetPasswordEmailCommand;
import com.switchfully.youcoach.security.verification.api.PasswordChangeRequestDto;
import com.switchfully.youcoach.security.verification.api.PasswordChangeResultDto;
import com.switchfully.youcoach.security.verification.api.PasswordResetRequestDto;
import com.switchfully.youcoach.email.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PasswordResetService {
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;
    private final VerificationService verificationService;
    private final String activeProfiles;
    private final EmailExecutor emailExecutor;

    @Autowired
    public PasswordResetService(PasswordEncoder passwordEncoder,
                                ProfileRepository profileRepository,
                                VerificationService verificationService,
                                @Value("${spring.profiles.active}") String activeProfiles,
                                EmailExecutor emailExecutor) {

        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
        this.emailExecutor = emailExecutor;
        this.verificationService = verificationService;
        this.activeProfiles = activeProfiles;
    }

    public void requestPasswordReset(PasswordResetRequestDto request) {
        if (!verificationService.isSigningAndVerifyingAvailable() || activeProfiles.contains("development")) return;

        emailExecutor.execute(new ResetPasswordEmailCommand(request.getEmail()));
    }

    public PasswordChangeResultDto performPasswordChange(PasswordChangeRequestDto request) {
        if (!digitalSigningAvailableAndSignatureMatchesAndPasswordFollowsValidFormat(request)) {
            return new PasswordChangeResultDto(false);
        }
        Optional<Profile> userOpt = profileRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return new PasswordChangeResultDto(false);
        }

        Profile profile = userOpt.get();
        profile.setPassword(passwordEncoder.encode(request.getPassword()));
        return new PasswordChangeResultDto(true);

    }

    private boolean digitalSigningAvailableAndSignatureMatchesAndPasswordFollowsValidFormat(PasswordChangeRequestDto request) {
        return verificationService.isSigningAndVerifyingAvailable() &&
                verificationService.verifyBased64SignaturePasses(request.getToken(), request.getEmail()) &&
                verificationService.isPasswordValid(request.getPassword());
    }
}
