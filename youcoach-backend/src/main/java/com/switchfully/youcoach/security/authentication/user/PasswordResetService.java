package com.switchfully.youcoach.security.authentication.user;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.ProfileRepository;
import com.switchfully.youcoach.email.MessageSender;
import com.switchfully.youcoach.security.authentication.user.api.PasswordChangeRequestDto;
import com.switchfully.youcoach.security.authentication.user.api.PasswordChangeResultDto;
import com.switchfully.youcoach.security.authentication.user.api.PasswordResetRequestDto;
import com.switchfully.youcoach.security.authentication.user.event.ResetPasswordRequestReceived;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PasswordResetService {
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;
    private final SignatureService signatureService;
    private final String activeProfiles;
    private final MessageSender messageSender;

    @Autowired
    public PasswordResetService(PasswordEncoder passwordEncoder,
                                ProfileRepository profileRepository,
                                SignatureService signatureService,
                                @Value("${spring.profiles.active}") String activeProfiles,
                                MessageSender messageSender) {

        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
        this.messageSender = messageSender;
        this.signatureService = signatureService;
        this.activeProfiles = activeProfiles;
    }

    public void requestPasswordReset(PasswordResetRequestDto request) {
        if (!activeProfiles.contains("development")) return;

        messageSender.handle(new ResetPasswordRequestReceived(request.getEmail()));
    }

    public PasswordChangeResultDto performPasswordChange(PasswordChangeRequestDto request) {
        if (!signatureService.verifyBased64SignaturePasses(request.getToken(), request.getEmail())) {
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
}
