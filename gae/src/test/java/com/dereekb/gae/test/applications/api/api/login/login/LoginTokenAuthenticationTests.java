package com.dereekb.gae.test.applications.api.api.login.login;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.LoginRegisterService;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthenticationProvider;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetailsBuilder;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.web.api.auth.controller.password.PasswordLoginController;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;


public class LoginTokenAuthenticationTests extends ApiApplicationTestContext {

	private static final String TEST_USERNAME = "username";
	private static final String TEST_PASSWORD = "password";

	@Autowired
	@Qualifier("passwordLoginController")
	public PasswordLoginController passwordController;

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
	public LoginTokenService loginTokenService;

	@Autowired
	@Qualifier("loginTokenAuthenticationProvider")
	private LoginTokenAuthenticationProvider authenticationProvider;

	@Autowired
	@Qualifier("loginTokenUserDetailsBuilder")
	private LoginTokenUserDetailsBuilder builder;

	@Test
	public void testLoginTokenAuthentication() throws LoginExistsException {
		LoginTokenPair primary = this.passwordController.create(TEST_USERNAME, TEST_PASSWORD);
		LoginPointer pointer = this.loginPointerRegistry.get(primary.getLoginPointerKey());
		Login login = this.registerService.register(pointer);

		login.setRoles(0b0111L);
		this.loginRegistry.save(login, false);

		LoginTokenPair tokenPair = this.passwordController.login(TEST_USERNAME, TEST_PASSWORD);
		String token = tokenPair.getToken();

		LoginToken decodedToken = this.loginTokenService.decodeLoginToken(token);
		LoginTokenAuthentication authentication = this.authenticationProvider.authenticate(decodedToken, null);

		LoginToken authLoginToken = authentication.getCredentials();
		Assert.assertNotNull(authLoginToken);

		LoginTokenUserDetails authLoginTokenUserDetails = authentication.getPrincipal();
		Assert.assertNotNull(authLoginTokenUserDetails);

		Login authLogin = authLoginTokenUserDetails.getLogin();
		Assert.assertNotNull(authLogin);

		LoginPointer authLoginPointer = authLoginTokenUserDetails.getLoginPointer();
		Assert.assertNotNull(authLoginPointer);
	}

}
