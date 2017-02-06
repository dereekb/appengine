package com.dereekb.gae.server.auth.old.gae.security.authentication;

import javax.servlet.ServletException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.Assert;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.old.security.authentication.LoginAuthenticationFactory;
import com.dereekb.gae.server.auth.old.security.authentication.LoginTuple;
import com.dereekb.gae.server.auth.old.security.authentication.LoginTupleImpl;
import com.dereekb.gae.server.auth.old.security.filter.exception.LoginInconsistencyException;
import com.dereekb.gae.server.auth.old.security.filter.exception.NewLoginAuthenticationException;
import com.dereekb.gae.server.auth.old.security.registration.creator.LoginCreator;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * {@link AuthenticationProvider} for Google Accounts.
 *
 * @author dereekb
 *
 */
public class GoogleAccountAuthenticationProvider
        implements AuthenticationProvider, InitializingBean {

	private String loginSystem;
	private Getter<LoginPointer> loginPointerGetter;
	private Getter<Login> loginGetter;
	private LoginCreator loginCreator;
	private LoginAuthenticationFactory authenticationFactory;

	public GoogleAccountAuthenticationProvider() {}

	public String getLoginSystem() {
		return this.loginSystem;
	}

	public void setLoginSystem(String loginSystem) {
		this.loginSystem = loginSystem;
	}

	public Getter<LoginPointer> getLoginPointerGetter() {
		return this.loginPointerGetter;
	}

	public void setLoginPointerGetter(Getter<LoginPointer> loginPointerGetter) {
		this.loginPointerGetter = loginPointerGetter;
	}

	public Getter<Login> getLoginGetter() {
		return this.loginGetter;
	}

	public void setLoginGetter(Getter<Login> loginGetter) {
		this.loginGetter = loginGetter;
	}

	public LoginCreator getLoginCreator() {
		return this.loginCreator;
	}

	public void setLoginCreator(LoginCreator loginCreator) {
		this.loginCreator = loginCreator;
	}

	public LoginAuthenticationFactory getAuthenticationFactory() {
		return this.authenticationFactory;
	}

	public void setAuthenticationFactory(LoginAuthenticationFactory authenticationFactory) {
		this.authenticationFactory = authenticationFactory;
	}

	@Override
	public Authentication authenticate(Authentication authentication)
	        throws AuthenticationException,
	            NewLoginAuthenticationException,
	            LoginInconsistencyException {
		Authentication resultAuthentication = null;

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user != null) {
			String email = user.getEmail();
			resultAuthentication = this.authenticate(email);
		}

		return resultAuthentication;
	}

	public Authentication authenticate(String email)
	        throws AuthenticationException,
	            NewLoginAuthenticationException,
	            LoginInconsistencyException {
		Authentication authentication = null;

		ModelKey key = new ModelKey(email);
		LoginPointer pointer = this.loginPointerGetter.get(key);

		if (pointer == null) {
			// Create a new login.
			LoginTuple tuple = this.loginCreator.newLogin(email);
			throw new NewLoginAuthenticationException(tuple);
		} else {
			// Build authentication for the provided login.
			ModelKey loginKey = pointer.getLoginModelKey();
			Login login = this.loginGetter.get(loginKey);

			if (login != null) {
				LoginTupleImpl tuple = new LoginTupleImpl(this.loginSystem, login, pointer);
				authentication = this.authenticationFactory.makeAuthentication(tuple);
			} else {
				throw new LoginInconsistencyException(pointer);
			}
		}

		return authentication;
	}

	/**
	 * Indicate that this provider only supports
	 * PreAuthenticatedAuthenticationToken (sub)classes.
	 */
	@Override
	public final boolean supports(Class<?> authentication) {
		return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@Override
	public void afterPropertiesSet() throws ServletException {
		Assert.notNull(this.loginSystem, "LoginSystem must be set");
		Assert.notNull(this.loginPointerGetter, "LoginPointerGetter must be set");
		Assert.notNull(this.loginGetter, "LoginGetter must be set");
		Assert.notNull(this.loginCreator, "LoginCreator must be set");
		Assert.notNull(this.authenticationFactory, "LoginAuthenticationFactory must be set");
	}

}