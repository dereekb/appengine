package com.thevisitcompany.gae.deprecated.authentication.login.security.providers;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.google.appengine.api.users.User;
import com.thevisitcompany.gae.deprecated.authentication.login.security.LoginAuthentication;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.deprecated.model.users.login.LoginRegistry;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.Permissions;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.roles.RoleType;

public class GoogleAccountsAuthenticationProvider implements AuthenticationProvider, MessageSourceAware {
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private LoginRegistry registry = new LoginRegistry();

    @Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	User googleUser = (User)authentication.getPrincipal();
        String email = googleUser.getEmail();

        Login login = this.registry.findLoginWithEmail(email);

        if (login == null) {
          	login = new Login(googleUser.getNickname(), googleUser.getEmail());
          	Permissions permissions = login.getPermissions();
          	permissions.addRoleType(RoleType.NEW_USER);

        } else {

            if (!login.isActive()) {
                throw new DisabledException("Account is disabled.");
            }
        }

        return new LoginAuthentication(login, authentication.getDetails());
    }

    /**
     * Indicate that this provider only supports PreAuthenticatedAuthenticationToken (sub)classes.
     */
    @Override
	public final boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
	public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}