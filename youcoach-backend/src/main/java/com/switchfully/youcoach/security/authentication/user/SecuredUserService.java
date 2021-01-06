package com.switchfully.youcoach.security.authentication.user;


import com.switchfully.youcoach.domain.profile.exception.ProfileNotFoundException;
import com.switchfully.youcoach.security.authentication.jwt.JwtGenerator;
import com.switchfully.youcoach.security.authentication.user.accountverification.AccountVerificator;
import com.switchfully.youcoach.security.authentication.user.api.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;


@Service
@Transactional
public class SecuredUserService implements UserDetailsService {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    private final JwtGenerator jwtGenerator;
    private final PasswordEncoder passwordEncoder;
    private final AccountVerificator accountVerificator;

    public SecuredUserService(AccountService accountService, AccountMapper accountMapper, JwtGenerator jwtGenerator, PasswordEncoder passwordEncoder, AccountVerificator accountVerificator) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.jwtGenerator = jwtGenerator;
        this.passwordEncoder = passwordEncoder;
        this.accountVerificator = accountVerificator;
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

    public String generateToken(Authentication authentication) {
        return jwtGenerator.generateJwtToken(accountService.findByEmail(authentication.getName()).map(Account::getId).map(Object::toString).orElse(null), authentication.getName(), authentication.getAuthorities());
    }

    public SecuredUserDto registerAccount(CreateSecuredUserDto createSecuredUserDto) {
        if (emailExists(createSecuredUserDto.getEmail())) {
            throw new IllegalStateException("Email already exists!");
        }
        Account account = accountService.createAccount(createSecuredUserDto);
        account.setPassword(passwordEncoder.encode(createSecuredUserDto.getPassword()));

        accountVerificator.sendVerificationEmail(account);

        return accountMapper.toUserDto(account);
    }

    private boolean emailExists(String email) {
        return accountService.existsByEmail(email);
    }

    public VerificationResultDto validateAccount(ValidateAccountDto validationData) {
        Account account = accountService.findByEmail(validationData.getEmail()).orElseThrow(() -> new ProfileNotFoundException(""));
        return new VerificationResultDto(accountVerificator.enableAccount(validationData.getVerificationCode(), account));
    }

    public ResendVerificationDto resendValidation(ResendVerificationDto validationData) {
        Optional<? extends Account> account = accountService.findByEmail(validationData.getEmail());
        if(account.isPresent()) {
            boolean result = accountVerificator.resendVerificationEmailFor(account.get());
            validationData.setValidationBeenResend(result);
        } else {
            validationData.setValidationBeenResend(true);
        }
        return validationData;
    }
}
