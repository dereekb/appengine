package com.dereekb.gae.server.auth.old.gae.security.registration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

import com.dereekb.gae.server.auth.deprecated.old.gae.security.authentication.GoogleAccountAuthenticationProvider;
import com.dereekb.gae.server.auth.deprecated.old.security.authentication.LoginTuple;
import com.dereekb.gae.server.auth.deprecated.old.security.filter.exception.NewLoginAuthenticationException;
import com.dereekb.gae.server.auth.deprecated.old.security.registration.NewLoginHandler;
import com.dereekb.gae.server.auth.deprecated.old.security.role.ConfiguredLoginRoleWriter;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Implementation for auto-enabling an administrator's login and setting their
 * roles.
 *
 * @author dereekb
 */
@Deprecated
public class GoogleAccountNewAdminLoginHandler
        implements NewLoginHandler, InitializingBean {

	private ConfiguredLoginRoleWriter configuredWriter;
	private GoogleAccountAuthenticationProvider authenticationProvider;

	public GoogleAccountNewAdminLoginHandler() {}

	public GoogleAccountNewAdminLoginHandler(ConfiguredLoginRoleWriter configuredWriter,
	        GoogleAccountAuthenticationProvider authenticationProvider) {
		this.configuredWriter = configuredWriter;
		this.authenticationProvider = authenticationProvider;
	}

	public ConfiguredLoginRoleWriter getConfiguredWriter() {
		return this.configuredWriter;
	}

	public void setConfiguredWriter(ConfiguredLoginRoleWriter configuredWriter) {
		this.configuredWriter = configuredWriter;
	}

	public GoogleAccountAuthenticationProvider getAuthenticationProvider() {
		return this.authenticationProvider;
	}

	public void setAuthenticationProvider(GoogleAccountAuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	@Override
	public Authentication handleNewLogin(NewLoginAuthenticationException e,
	                                     HttpServletRequest request,
	                                     HttpServletResponse response) throws AuthenticationException {
		Authentication authentication = null;

		UserService userService = UserServiceFactory.getUserService();
		boolean isAdmin = userService.isUserAdmin();

		if (isAdmin) {
			User user = userService.getCurrentUser();

			if (this.accountsAreTheSame(user, e)) {
				Login login = e.getLogin();
				this.configuredWriter.changeRoles(login);

				String email = user.getEmail();
				authentication = this.authenticationProvider.authenticate(email);
			} else {
				throw new AuthenticationServiceException("Google Account login information was mixed.");
			}
		}

		return authentication;
	}

	private boolean accountsAreTheSame(User user,
	                                   LoginTuple tuple) {
		LoginPointer loginPointer = tuple.getLoginPointer();

		String identifier = loginPointer.getIdentifier();
		String email = user.getEmail();

		return (identifier.equals(email));
	}

	@Override
	public void afterPropertiesSet() throws ServletException {
		notNull(this.configuredWriter, "ConfiguredLoginRoleWriter must be set");
		notNull(this.authenticationProvider, "GoogleAccountAuthenticationProvider must be set");
	}

}
