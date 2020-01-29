package com.dereekb.gae.test.app.web.api.login;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityService;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;
import com.dereekb.gae.server.auth.security.token.refresh.impl.RefreshTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.refresh.impl.RefreshTokenServiceImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.app.mock.context.AbstractAppTestingContext;
import com.dereekb.gae.utilities.time.DateUtility;
import com.dereekb.gae.web.api.auth.controller.token.impl.TokenValidationRequestImpl;

public class LoginTokenControllerTest extends AbstractAppTestingContext {

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

	@Autowired
	@Qualifier("loginRegistry")
	private ObjectifyRegistry<Login> loginRegistry;

	@Autowired
	@Qualifier("loginPointerRegistry")
	private ObjectifyRegistry<LoginPointer> loginPointerRegistry;

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
		assertNotNull(decodedRefreshToken.getLoginToken().getLoginId());

		// Authenticate with the token.
		String reauthToken = testUtility.authWithRefreshToken(refreshToken);

		// Reset Authentication
		this.refreshTokenService.setResetCooldown(0L);	// Disable reset
		                                              	// cooldown for test

		String modified = testUtility.resetTokenAuth(reauthToken);
		assertNotNull(modified);

		// Try to authenticate again. It should fail.
		try {
			testUtility.authWithRefreshToken(refreshToken);
			fail("Should have failed");
		} catch (AssertionError e) {
			// Don't throw assertion error.
		}

		// Also try to get a new refresh token. It should fail.
		try {
			refreshToken = testUtility.getRefreshToken(fullUserToken);
			fail("Should have failed");
		} catch (AssertionError e) {
			// Don't throw assertion error.
		}
	}

	@Test
	public void testValidateTokenWithNoSignature() throws Exception {
		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);

		LoginTokenImpl loginToken = (LoginTokenImpl) this.loginTokenService.makeToken();
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
		assertTrue(status == 200);
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
		assertTrue(result.getResponse().getStatus() == 429);
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
			fail();
		} catch (AssertionError e) {

		}
	}

	@Test
	public void testLoginWithDisabledLoginPointerFails() throws Exception {

		// Create a password LoginPointer/Token
		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);
		String token = testUtility.createPasswordLogin(TEST_USERNAME, TEST_PASSWORD);

		// Register
		String fullUserToken = testUtility.register(token);

		// Get a refresh token.
		String refreshToken = testUtility.getRefreshToken(fullUserToken);

		DecodedLoginToken<LoginTokenImpl> decodedRefreshToken = this.refreshEncoderDecoder
		        .decodeLoginToken(refreshToken);
		String loginPointerId = decodedRefreshToken.getLoginToken().getLoginPointerId();

		testUtility.setLoginPointerDisabled(this.loginPointerRegistry, ModelKey.safe(loginPointerId));

		MvcResult loginResult = testUtility.sendLoginWithPassword(TEST_USERNAME, TEST_PASSWORD);
		MockHttpServletResponse response = loginResult.getResponse();

		assertFalse(response.getStatus() == 200);

		/*
		 * // Authenticating with existing refresh tokens is still valid. The
		 * authDate invalidates them.
		 *
		 * MvcResult reauthTokenResult =
		 * testUtility.sendAuthWithRefreshToken(refreshToken);
		 * MockHttpServletResponse response = reauthTokenResult.getResponse();
		 *
		 * assertFalse(response.getStatus() == 200);
		 */
	}

	@Test
	public void testLoginWithPermissionsMask() throws Exception {

		// Create a password LoginPointer/Token
		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);
		String token = testUtility.createPasswordLogin(TEST_USERNAME, TEST_PASSWORD);

		// Register
		String fullUserToken = testUtility.register(token);

		// Get a refresh token.
		String refreshToken = testUtility.getRefreshToken(fullUserToken);

		DecodedLoginToken<LoginTokenImpl> decodedRefreshToken = this.refreshEncoderDecoder
		        .decodeLoginToken(refreshToken);
		Long loginId = decodedRefreshToken.getLoginToken().getLoginId();

		Login login = this.loginRegistry.get(ModelKey.safe(loginId));
		login.setRoles(0x1111L);
		this.loginRegistry.update(login);

		Long rolesMask = 0x0110L;

		String loginToken = testUtility.authWithRefreshToken(refreshToken, rolesMask);

		DecodedLoginToken<LoginToken> decodedLoginToken = this.loginTokenService.decodeLoginToken(loginToken);

		Long roles = decodedLoginToken.getLoginToken().getRoles();
		assertTrue(roles.equals(rolesMask));
	}

}
