package com.dereekb.gae.test.applications.api.api.login.token;

import org.junit.Test;

import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.applications.api.api.login.LoginApiTestUtility;

public class LoginTokenControllerTest extends ApiApplicationTestContext {

	private static final String TEST_USERNAME = "tokenUsername";
	private static final String TEST_PASSWORD = "tokenPassword";

	// MARK: Mock Tests
	@Test
	public void testLoginRequests() throws Exception {
		// Create a password LoginPointer/Token
		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);
		String token = testUtility.createPasswordLogin(TEST_USERNAME, TEST_PASSWORD);

		// Register
		String fullUserToken = testUtility.register(token);

		// Get a refresh token.
		String refreshToken = testUtility.getRefreshToken(fullUserToken);

		// Authenticate with the token.
		String reauthToken = testUtility.authWithRefreshToken(refreshToken);

	}

}
