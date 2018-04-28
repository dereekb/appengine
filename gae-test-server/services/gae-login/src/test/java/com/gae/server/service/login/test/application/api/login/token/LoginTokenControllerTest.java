package com.gae.server.service.login.test.application.api.login.token;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityService;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;
import com.dereekb.gae.server.auth.security.token.refresh.impl.RefreshTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.refresh.impl.RefreshTokenServiceImpl;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.applications.api.api.login.LoginApiTestUtility;
import com.dereekb.gae.utilities.time.DateUtility;
import com.dereekb.gae.web.api.auth.controller.token.impl.TokenValidationRequestImpl;

public class LoginTokenControllerTest extends ApiApplicationTestContext {

	private static final String TEST_USERNAME = "tokenUsername";
	private static final String TEST_PASSWORD = "tokenPassword";

	@Autowired
	@Qualifier("loginTokenService")
	private LoginTokenService<LoginToken> loginTokenService;

	@Autowired
	@Qualifier("appLoginSecurityService")
	private AppLoginSecurityService appLoginSecurityService;

	@Autowired
	@Qualifier("refreshTokenEncoderDecoder")
	private RefreshTokenEncoderDecoder refreshEncoderDecoder;

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
		DecodedLoginToken<LoginTokenImpl> decodedRefreshToken = this.refreshEncoderDecoder
		        .decodeLoginToken(refreshToken);
		Assert.assertNotNull(decodedRefreshToken.getLoginToken().getLoginId());

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
	public void testValidateTokenWithNoSignature() throws Exception {
		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);

		LoginTokenImpl loginToken = new LoginTokenImpl();
		loginToken.setLogin(1L);
		loginToken.setLoginPointer("pointer");
		loginToken.setExpiration(DateUtility.getDateIn(60 * 1000L));
		loginToken.setIssued(new Date());
		loginToken.setRefreshAllowed(true);

		String encodedToken = this.loginTokenService.encodeLoginToken(loginToken);

		TokenValidationRequestImpl request = new TokenValidationRequestImpl(encodedToken);
		request.setQuick(true);

		MockHttpServletResponse response = testUtility.validateLoginToken(request);

		int status = response.getStatus();
		Assert.assertTrue(status == 200);
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

	@Test
	public void testRefreshTokenLimiting() throws Exception {

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
		try {
			testUtility.getRefreshToken(reauthToken);
			Assert.fail();
		} catch (AssertionError e) {

		}
	}

}
