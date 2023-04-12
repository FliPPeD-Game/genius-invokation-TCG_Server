package com.card.game.oatuh.support.userdetails;

import com.card.game.api.user.dto.SysUserDTO;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author tomyou
 * @version v1.0 2023-01-07-9:06 PM
 */
public class SecurityMailUserDetails implements UserDetails {

    private final String userName;

    private final Boolean lockFlg;
    @Getter
    public final SysUserDTO sysUserDTO;

    @Getter
    @Setter
    private String mailAccount;

    // TODO 后面改回来
    private Collection<GrantedAuthority> authorities;


    public SecurityMailUserDetails(SysUserDTO sysUserDTO) {
        this.sysUserDTO = sysUserDTO;
        this.userName = sysUserDTO.getUsername();
        this.lockFlg = sysUserDTO.getLockFlag();
        this.mailAccount = sysUserDTO.getEmail();
        // 测试给每个人管理员
        this.authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.sysUserDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.lockFlg;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void clearPassword() {
        this.getSysUserDTO().setPassword(null);
    }
}
