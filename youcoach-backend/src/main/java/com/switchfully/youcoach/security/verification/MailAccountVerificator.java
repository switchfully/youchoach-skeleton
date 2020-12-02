package com.switchfully.youcoach.security.verification;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.email.EmailSender;
import com.switchfully.youcoach.email.command.accountverification.AccountVerificationEmailCommand;
import com.switchfully.youcoach.email.exception.SendingMailError;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@org.springframework.context.annotation.Profile("production")
@Service
@Transactional
public class MailAccountVerificator implements AccountVerificator {
    private final AccountVerificationRepository accountVerificationRepository;
    private final Environment environment;
    private final EmailSender emailExecutor;

    @Autowired
    public MailAccountVerificator(AccountVerificationRepository accountVerificationRepository,
                                  Environment environment,
                                  EmailSender emailExecutor) {
        this.accountVerificationRepository = accountVerificationRepository;
        this.environment = environment;
        this.emailExecutor = emailExecutor;
    }

    @Override
    public boolean sendVerificationEmail(Profile profile) {
        AccountVerification accountVerification = accountVerificationRepository.save(generateAccountVerification(profile));
        try {
            emailExecutor.execute(new AccountVerificationEmailCommand(profile, accountVerification));
        } catch (SendingMailError e) {
            e.printStackTrace();
            removeAccountVerification(profile);
            return false;
        }
        return true;
    }

    @Override
    public boolean resendVerificationEmailFor(Profile profile) {
        if (profile.isAccountEnabled()) {
            return false;
        }
        removeAccountVerification(profile);
        return sendVerificationEmail(profile);
    }

    @Override
    public boolean enableAccount(String verificationCode, Profile profile) {
        if (!doesVerificationCodeMatch(verificationCode, profile)) {
            return false;
        }
        profile.enableAccount();
        removeAccountVerification(profile);
        return true;
    }

    private AccountVerification generateAccountVerification(Profile profile) {
        String code = DigestUtils.md5Hex(environment.getProperty("app.emailverification.salt") + profile.getEmail()).toUpperCase();
        return new AccountVerification(profile, code);
    }

    private void removeAccountVerification(Profile profile) {
        accountVerificationRepository.deleteAccountVerificationByProfile(profile);
    }

    private boolean doesVerificationCodeMatch(String verificationCode, Profile profile) {
        return accountVerificationRepository.findAccountVerificationByProfile(profile)
                .map(account -> account.getVerificationCode().equals(verificationCode))
                .orElse(false);
    }
}
