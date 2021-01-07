package com.switchfully.youcoach.security.authentication.user.accountverification;

import com.switchfully.youcoach.email.MessageSender;
import com.switchfully.youcoach.security.authentication.user.api.Account;
import com.switchfully.youcoach.security.authentication.user.event.AccountCreated;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AccountVerificationService {
    private final AccountVerificationRepository accountVerificationRepository;
    private final MessageSender messageSender;
    private final String salt;
    private final String activeProfiles;

    @Autowired
    public AccountVerificationService(AccountVerificationRepository accountVerificationRepository,
                                      MessageSender messageSender,
                                      @Value("${app.emailverification.salt}") String salt,
                                      @Value("${spring.profiles.active}") String activeProfiles
    ) {
        this.accountVerificationRepository = accountVerificationRepository;
        this.messageSender = messageSender;
        this.salt = salt;
        this.activeProfiles = activeProfiles;
    }

    public void sendVerificationEmail(Account profile) {
        AccountVerification accountVerification = accountVerificationRepository.save(generateAccountVerification(profile));
        messageSender.handle(new AccountCreated(profile, accountVerification));

        if (activeProfiles.contains("development")) {
            profile.enableAccount();
        }
    }

    public void resendVerificationEmailFor(Account profile) {
        removeAccountVerification(profile);
        sendVerificationEmail(profile);
    }

    public boolean enableAccount(String verificationCode, Account profile) {
        if (!doesVerificationCodeMatch(verificationCode, profile)) {
            return false;
        }
        profile.enableAccount();
        removeAccountVerification(profile);
        return true;
    }

    private AccountVerification generateAccountVerification(Account account) {
        return new AccountVerification(account.getId(), DigestUtils.md5Hex(salt + account.getEmail()).toUpperCase());
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
