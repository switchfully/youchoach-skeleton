package com.switchfully.youcoach.security.authentication.user.accountverification;

import com.switchfully.youcoach.email.MessageSender;
import com.switchfully.youcoach.security.authentication.user.api.Account;
import com.switchfully.youcoach.security.authentication.user.event.AccountCreated;
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
    private final MessageSender messageSender;

    @Autowired
    public MailAccountVerificator(AccountVerificationRepository accountVerificationRepository,
                                  Environment environment,
                                  MessageSender messageSender) {
        this.accountVerificationRepository = accountVerificationRepository;
        this.environment = environment;
        this.messageSender = messageSender;
    }

    @Override
    public boolean sendVerificationEmail(Account profile) {
        AccountVerification accountVerification = accountVerificationRepository.save(generateAccountVerification(profile));
        try {
            messageSender.handle(new AccountCreated(profile, accountVerification));
        } catch (SendingMailError e) {
            e.printStackTrace();
            removeAccountVerification(profile);
            return false;
        }
        return true;
    }

    @Override
    public boolean resendVerificationEmailFor(Account profile) {
        if (profile.isAccountEnabled()) {
            return false;
        }
        removeAccountVerification(profile);
        return sendVerificationEmail(profile);
    }

    @Override
    public boolean enableAccount(String verificationCode, Account profile) {
        if (!doesVerificationCodeMatch(verificationCode, profile)) {
            return false;
        }
        profile.enableAccount();
        removeAccountVerification(profile);
        return true;
    }

    private AccountVerification generateAccountVerification(Account profile) {
        String code = DigestUtils.md5Hex(environment.getProperty("app.emailverification.salt") + profile.getEmail()).toUpperCase();
        return new AccountVerification(profile, code);
    }

    private void removeAccountVerification(Account profile) {
        accountVerificationRepository.deleteAccountVerificationByProfileId(profile.getId());
    }

    private boolean doesVerificationCodeMatch(String verificationCode, Account profile) {
        return accountVerificationRepository.findAccountVerificationByProfileId(profile.getId())
                .map(account -> account.getVerificationCode().equals(verificationCode))
                .orElse(false);
    }
}
