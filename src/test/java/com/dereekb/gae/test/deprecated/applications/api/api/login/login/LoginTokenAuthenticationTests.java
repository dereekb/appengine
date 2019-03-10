package com.dereekb.gae.test.applications.api.api.login.login;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.LoginRegisterService;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginRegistrationRejectedException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthenticationProvider;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetailsBuilder;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.web.api.auth.controller.anonymous.AnonymousLoginController;
import com.dereekb.gae.web.api.auth.controller.password.PasswordLoginController;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

public class LoginTokenAuthenticationTests extends ApiApplicationTestContext {

	private static final String TEST_USERNAME = "tUsername";
	private static final String TEST_PASSWORD = "tPassword";

	@Autowired
	@Qualifier("passwordLoginController")
	public PasswordLoginController passwordController;

	@Autowired
	@Qualifier("anonymousLoginController")
	public AnonymousLoginController anonymousController;

	@Autowired
	@Qualifier("loginRegisterService")
	public LoginRegisterService registerService;

	@Autowired
	@Qualifier("loginPointerRegistry")
	public ObjectifyRegistry<LoginPointer> loginPointerRegistry;

	@Autowired
	@Qualifier("loginRegistry")
	public ObjectifyRegistry<Login> loginRegistry;

	@Autowired
	@Qualifier("loginTokenService")
	public LoginTokenService<LoginToken> loginTokenService;

	@Autowired
	@Qualifier("loginTokenAuthenticationProvider")
	private LoginTokenAuthenticationProvider<LoginToken> authenticationProvider;

	@Autowired
	@Qualifier("loginTokenUserDetailsBuilder")
	private LoginTokenUserDetailsBuilder<LoginToken> builder;

	@Test
	public void testLoginTokenAuthentication() throws LoginExistsException, LoginRegistrationRejectedException {
		LoginTokenPair primary = this.passwordController.create(TEST_USERNAME, TEST_PASSWORD);
		LoginPointer pointer = this.loginPointerRegistry.get(primary.getLoginPointerKey());
		Login login = this.registerService.register(pointer);

		login.setRoles(0b0111L);
		this.loginRegistry.forceStore(login);

		LoginTokenPair tokenPair = this.passwordController.login(TEST_USERNAME, TEST_PASSWORD);
		String token = tokenPair.getToken();

		DecodedLoginToken<LoginToken> decodedToken = this.loginTokenService.decodeLoginToken(token);
		LoginTokenAuthentication<LoginToken> authentication = this.authenticationProvider.authenticate(decodedToken, null);

		LoginToken authLoginToken = authentication.getCredentials().getLoginToken();
		assertNotNull(authLoginToken);

		LoginTokenUserDetails<LoginToken> authLoginTokenUserDetails = authentication.getPrincipal();
		assertNotNull(authLoginTokenUserDetails);

		Login authLogin = authLoginTokenUserDetails.getLogin();
		assertNotNull(authLogin);

		LoginPointer authLoginPointer = authLoginTokenUserDetails.getLoginPointer();
		assertNotNull(authLoginPointer);

		Collection<? extends GrantedAuthority> authorities = authLoginTokenUserDetails.getAuthorities();
		assertNotNull(authorities);
	}

	@Test
	public void testAnonymousAuthentication() {
		LoginTokenPair pair = this.anonymousController.login(null);
		String token = pair.getToken();

		DecodedLoginToken<LoginToken> decodedToken = this.loginTokenService.decodeLoginToken(token);
		LoginTokenAuthentication<LoginToken> authentication = this.authenticationProvider.authenticate(decodedToken, null);

		LoginToken authLoginToken = authentication.getCredentials().getLoginToken();
		assertNotNull(authLoginToken);

		LoginTokenUserDetails<LoginToken> authLoginTokenUserDetails = authentication.getPrincipal();
		assertNotNull(authLoginTokenUserDetails);

		assertTrue(decodedToken.getLoginToken().isAnonymous());

		Collection<? extends GrantedAuthority> authorities = authLoginTokenUserDetails.getAuthorities();
		assertNotNull(authorities);
	}

}
