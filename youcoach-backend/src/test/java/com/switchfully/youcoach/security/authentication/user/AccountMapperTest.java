package com.switchfully.youcoach.security.authentication.user;

import com.switchfully.youcoach.domain.profile.Profile;
import com.switchfully.youcoach.security.authentication.user.api.AccountMapper;
import com.switchfully.youcoach.security.authentication.user.api.SecuredUserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.switchfully.youcoach.domain.profile.ProfileTestBuilder.profile;

class AccountMapperTest {

    private final AccountMapper accountMapper = new AccountMapper();

    @Test
    void userToUserDto(){
        Profile profile = profile().build();

        SecuredUserDto expected = new SecuredUserDto(profile.getId(), profile.getEmail(), profile.isAccountEnabled());

        SecuredUserDto actual = accountMapper.toUserDto(profile);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

}
