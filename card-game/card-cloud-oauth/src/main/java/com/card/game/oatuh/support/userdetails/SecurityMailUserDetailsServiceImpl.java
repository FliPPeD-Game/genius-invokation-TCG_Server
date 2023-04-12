package com.card.game.oatuh.support.userdetails;

import com.card.game.api.user.dto.SysUserDTO;
import com.card.game.common.result.ResultCode;
import com.card.game.oatuh.api.UserService;
import com.card.game.oatuh.constant.SecurityConstants;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author tomyou
 * @version v1.0 2023-01-07-8:21 PM
 */
@Service
@RequiredArgsConstructor
public class SecurityMailUserDetailsServiceImpl extends UserDetailsServiceAdapter {

    private final UserService userService;

    @Override
    public UserDetails loadUserByMailAccount(String mailAccount) {
        SecurityMailUserDetails userDetails = null;

        SysUserDTO userByMailAccount = userService.getUserByMailAccount(mailAccount).getData();
        if (Objects.nonNull(userByMailAccount)) {
            userDetails = new SecurityMailUserDetails(userByMailAccount);
        }

        if (Objects.isNull(userByMailAccount)) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMessage());
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException(SecurityConstants.ACCOUNT_DISABLE);
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException(SecurityConstants.ACCOUNT_LOCKED);
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException(SecurityConstants.ACCOUNT_EXPIRED);
        }
        return userDetails;
    }
}
