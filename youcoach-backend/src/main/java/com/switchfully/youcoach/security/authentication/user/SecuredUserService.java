package com.switchfully.youcoach.security.authentication.user;


import com.switchfully.youcoach.domain.profile.exception.ProfileNotFoundException;
import com.switchfully.youcoach.security.authentication.jwt.JwtGenerator;
import com.switchfully.youcoach.security.authentication.user.accountverification.AccountVerificationService;
import com.switchfully.youcoach.security.authentication.user.api.*;
import com.switchfully.youcoach.security.authentication.user.password.reset.PasswordResetService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;


@Service
@Transactional
public class SecuredUserService implements UserDetailsService {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    private final PasswordEncoder passwordEncoder;

    private final AccountVerificationService accountVerificationService;
    private final PasswordResetService passwordResetService;

    public SecuredUserService(AccountService accountService, AccountMapper accountMapper, PasswordEncoder passwordEncoder, AccountVerificationService accountVerificationService, PasswordResetService passwordResetService) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.passwordEncoder = passwordEncoder;
        this.accountVerificationService = accountVerificationService;
        this.passwordResetService = passwordResetService;
    }

    @Override
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
        Account account = accountService.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));

        Collection<Authority> authorities = determineGrantedAuthorities(account);

        return new SecuredUser(account.getEmail(), account.getPassword(), authorities, account.isAccountEnabled());
    }

    private Collection<Authority> determineGrantedAuthorities(Account account) {
        return new ArrayList<>(account.getAuthorities());
    }

    public SecuredUserDto registerAccount(CreateSecuredUserDto createSecuredUserDto) {
        if (emailExists(createSecuredUserDto.getEmail())) {
            throw new IllegalStateException("Email already exists!");
        }
        Account account = accountService.createAccount(createSecuredUserDto);
        account.setPassword(passwordEncoder.encode(createSecuredUserDto.getPassword()));

        accountVerificationService.sendVerificationEmail(account);

        return accountMapper.toUserDto(account);
    }

    private boolean emailExists(String email) {
        return accountService.existsByEmail(email);
    }

    public VerificationResultDto validateAccount(ValidateAccountDto validationData) {
        Account account = accountService.findByEmail(validationData.getEmail()).orElseThrow(() -> new ProfileNotFoundException(""));
        return new VerificationResultDto(accountVerificationService.enableAccount(validationData.getVerificationCode(), account));
    }

    public void resendValidation(ResendVerificationDto validationData) {
        Account account = accountService.findByEmail(validationData.getEmail()).orElseThrow(() -> new RuntimeException("Account not found"));
        accountVerificationService.resendVerificationEmailFor(account);
    }

    public void requestPasswordReset(PasswordResetRequestDto resetRequest) {
        passwordResetService.requestPasswordReset(resetRequest);
    }

    public PasswordChangeResultDto performPasswordChange(PasswordChangeRequestDto changeRequest) {
        return passwordResetService.performPasswordChange(changeRequest);
    }
}
