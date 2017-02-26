package com.dereekb.gae.test.applications.api.api.login.token;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MvcResult;

import com.dereekb.gae.server.auth.security.token.refresh.impl.RefreshTokenServiceImpl;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.applications.api.api.login.LoginApiTestUtility;

public class LoginTokenControllerTest extends ApiApplicationTestContext {

	private static final String TEST_USERNAME = "tokenUsername";
	private static final String TEST_PASSWORD = "tokenPassword";

	@Autowired
	@Qualifier("refreshTokenService")
	private RefreshTokenServiceImpl refreshTokenService;

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

		// Reset Authentication
		this.refreshTokenService.setResetCooldown(0L);	// Disable reset
		                                              	// cooldown for test

		String modified = testUtility.resetTokenAuth(reauthToken);
		Assert.assertNotNull(modified);

		// Try to authenticate again. It should fail.
		try {
			testUtility.authWithRefreshToken(refreshToken);
			Assert.fail("Should have failed");
		} catch (AssertionError e) {
			// Don't throw assertion error.
		}

		// Also try to get a new refresh token. It should fail.
		try {
			refreshToken = testUtility.getRefreshToken(fullUserToken);
			Assert.fail("Should have failed");
		} catch (AssertionError e) {
			// Don't throw assertion error.
		}
	}

	@Test
	public void testLoginRateLimiting() throws Exception {

		// Create a password LoginPointer/Token
		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);
		String token = testUtility.createPasswordLogin(TEST_USERNAME, TEST_PASSWORD);

		// Register
		String fullUserToken = testUtility.register(token);

		// Get a refresh token.
		String refreshToken = testUtility.getRefreshToken(fullUserToken);

		// Authenticate with the token.
		String reauthToken = testUtility.authWithRefreshToken(refreshToken);

		// Reset Authentication
		MvcResult result = testUtility.sendTokenAuthReset(reauthToken, "");
		Assert.assertTrue(result.getResponse().getStatus() == 429);
	}

}
