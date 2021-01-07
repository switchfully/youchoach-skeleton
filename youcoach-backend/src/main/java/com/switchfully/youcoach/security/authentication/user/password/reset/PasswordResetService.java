package com.switchfully.youcoach.security.authentication.user.password.reset;

import com.switchfully.youcoach.email.MessageSender;
import com.switchfully.youcoach.security.authentication.user.api.*;
import com.switchfully.youcoach.security.authentication.user.event.ResetPasswordRequestReceived;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PasswordResetService {
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;
    private final SignatureService signatureService;
    private final MessageSender messageSender;

    @Autowired
    public PasswordResetService(PasswordEncoder passwordEncoder,
                                AccountService accountService,
                                SignatureService signatureService,
                                MessageSender messageSender) {
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
        this.messageSender = messageSender;
        this.signatureService = signatureService;
    }

    public void requestPasswordReset(PasswordResetRequestDto request) {
        messageSender.handle(new ResetPasswordRequestReceived(request.getEmail(), signatureService.digitallySignAndEncodeBase64(request.getEmail())));
    }

    public PasswordChangeResultDto performPasswordChange(PasswordChangeRequestDto request) {
        if (!signatureService.verifyBased64SignaturePasses(request.getToken(), request.getEmail())) {
            return new PasswordChangeResultDto(false);
        }
        Optional<? extends Account> userOpt = accountService.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return new PasswordChangeResultDto(false);
        }

        Account account = userOpt.get();
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        return new PasswordChangeResultDto(true);
    }
}
