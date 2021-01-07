package com.switchfully.youcoach.security.authentication.user;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.domain.profile.role.Role;
import com.switchfully.youcoach.security.PasswordConfig;
import com.switchfully.youcoach.security.authentication.jwt.JwtGenerator;
import com.switchfully.youcoach.security.authentication.user.accountverification.AccountVerificationService;
import com.switchfully.youcoach.security.authentication.user.api.AccountMapper;
import com.switchfully.youcoach.security.authentication.user.api.AccountService;
import com.switchfully.youcoach.security.authentication.user.api.CreateSecuredUserDto;
import com.switchfully.youcoach.security.authentication.user.password.reset.SignatureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.switchfully.youcoach.domain.profile.ProfileTestBuilder.profile;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SecuredUserServiceTest {
    @Mock
    private AccountService accountService;
    @Mock
    private JwtGenerator jwtGenerator;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccountVerificationService accountVerificationService;
    @Spy
    private final SignatureService signatureService = new SignatureService(new PasswordConfig().keyPair());
    @Spy
    private AccountMapper accountMapper = new AccountMapper();
    @InjectMocks
    private SecuredUserService securedUserService;

    @Test
    public void userIsCoachee() {
        Optional profile1 = of(profile().role(Role.COACHEE).build());

        when(accountService.findByEmail("username")).thenReturn(profile1);

        assertThat(securedUserService.loadUserByUsername("username")).isEqualTo(new SecuredUser("example@example.com", "1Lpassword", newArrayList(Authority.COACHEE), false));
    }

    @Test
    void saveAUser() {
        Profile profile = new Profile(1,"Test","Service","test@hb.be","Test123456","classYear");

        CreateSecuredUserDto createSecuredUserDto = new CreateSecuredUserDto("Test", "Service", "classYear", "test@hb.be", "Test123456");
        when(accountService.createAccount(createSecuredUserDto)).thenReturn(profile);

        securedUserService.registerAccount(createSecuredUserDto);

        verify(accountService).existsByEmail(profile.getEmail());
        verify(accountVerificationService).sendVerificationEmail(profile);
    }



    @Test
    void emailDuplication() {
        CreateSecuredUserDto userWithDuplicateEmail = new CreateSecuredUserDto("Test", "Service", "classYear", "dummy@Mail.com", "Test123456");
        Mockito.when(accountService.existsByEmail(Mockito.anyString())).thenReturn(true);

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> securedUserService.registerAccount(userWithDuplicateEmail));
    }

    @Test
    public void userIsAdmin() {
        Optional profile = of(profile().role(Role.ADMIN).build());

        when(accountService.findByEmail("username")).thenReturn(profile);

        assertThat(securedUserService.loadUserByUsername("username")).isEqualTo(new SecuredUser("example@example.com", "1Lpassword", newArrayList(Authority.COACHEE, Authority.COACH, Authority.ADMIN), false));
    }

    @Test
    public void userIsCoach() {
        Optional profile = of(profile().role(Role.COACH).build());

        when(accountService.findByEmail("username")).thenReturn(profile);

        assertThat(securedUserService.loadUserByUsername("username")).isEqualTo(new SecuredUser("example@example.com", "1Lpassword", newArrayList(Authority.COACHEE, Authority.COACH), false));
    }


}
