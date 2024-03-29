package com.card.game.oatuh.support.email;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * @author tomyou
 * @version v1.0 2023-01-07-8:05 PM
 */
public class MailAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    @Getter
    private String password;

    public MailAuthenticationToken(Object principal, String mailCode) {
        super(null);
        this.principal = principal;
        this.password = mailCode;
        this.setAuthenticated(false);
    }

    public MailAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }


    public static MailAuthenticationToken unauthenticated(Object principal, String password) {
        return new MailAuthenticationToken(principal, password);
    }


    public static MailAuthenticationToken authenticated(Object principal,
                                                        Collection<? extends GrantedAuthority> authorities) {
        return new MailAuthenticationToken(principal, authorities);
    }


    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
