package com.dereekb.gae.test.applications.api.api.login.password;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.web.api.auth.controller.password.PasswordLoginController;
import com.dereekb.gae.web.api.auth.exception.ApiLoginException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;

/**
 * Tests creating and logging in using the password API.
 *
 * @author dereekb
 *
 */
public class PasswordLoginApiControllerTest extends ApiApplicationTestContext {

	private static final String TEST_USERNAME = "username";
	private static final String TEST_PASSWORD = "password";

	@Autowired
	public PasswordLoginController passwordController;

	@Autowired
	public LoginTokenService loginTokenService;

	@Autowired
	@Qualifier("loginPointerRegistry")
	public ObjectifyRegistry<LoginPointer> loginPointerRegistry;

	@Test
	public void testCreate() {
		LoginTokenPair pair = this.passwordController.create(TEST_USERNAME, TEST_PASSWORD);

		String pointer = pair.getLoginPointer();
		String token = pair.getToken();

		Assert.assertNotNull(pointer);
		Assert.assertNotNull(token);

		Assert.assertTrue(this.loginPointerRegistry.exists(pair.getLoginPointerKey()));

		try {
			LoginToken loginToken = this.loginTokenService.decodeLoginToken(token);
			Assert.assertNotNull(loginToken);

			Assert.assertTrue(pointer.equals(loginToken.getLoginPointer()));
		} catch (Exception e) {
			Assert.fail("Login token failed decoding.");
		}
	}

	@Test
	public void testLogin() {
		LoginTokenPair createPair = this.passwordController.create(TEST_USERNAME, TEST_PASSWORD);
		LoginTokenPair loginPair = this.passwordController.login(TEST_USERNAME, TEST_PASSWORD);

		Assert.assertTrue(createPair.getLoginPointer().equals(loginPair.getLoginPointer()));
	}

	@Test
	public void testLoginAttemptWithoutLoginAvailable() {
		try {
			this.passwordController.login(TEST_USERNAME, TEST_PASSWORD);
			Assert.fail("Login should not exist.");
		} catch (ApiLoginException e) {
			Assert.assertTrue(e.getReason().equals(ApiLoginException.LoginExceptionReason.UNAVAILABLE));
		}
	}

	@Test
	public void testLoginAttemptWithWrongPassword() {
		this.passwordController.create(TEST_USERNAME, TEST_PASSWORD);

		try {
			this.passwordController.login(TEST_USERNAME, "InvalidPassword");
			Assert.fail("Login info should be invalid.");
		} catch (ApiLoginException e) {
			Assert.assertTrue(e.getReason().equals(ApiLoginException.LoginExceptionReason.INVALID_CREDENTIALS));
		}
	}
}
