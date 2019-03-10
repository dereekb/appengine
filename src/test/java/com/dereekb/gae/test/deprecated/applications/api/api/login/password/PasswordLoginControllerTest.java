package com.dereekb.gae.test.applications.api.api.login.password;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.password.PasswordLoginService;
import com.dereekb.gae.server.auth.security.login.password.recover.PasswordRecoveryService;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.applications.api.api.login.LoginApiTestUtility;
import com.dereekb.gae.web.api.auth.controller.password.PasswordLoginController;
import com.dereekb.gae.web.api.auth.exception.ApiLoginException;
import com.dereekb.gae.web.api.auth.response.LoginTokenPair;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Tests creating and logging in using the password API.
 *
 * @author dereekb
 *
 */
public class PasswordLoginControllerTest extends ApiApplicationTestContext {

	private static final String TEST_USERNAME = "pUsername";
	private static final String TEST_PASSWORD = "pPassword";

	@Autowired
	public PasswordLoginController passwordController;

	@Autowired
	public LoginTokenService<LoginToken> loginTokenService;

	@Autowired
	@Qualifier("loginPointerRegistry")
	public ObjectifyRegistry<LoginPointer> loginPointerRegistry;

	@Autowired
	@Qualifier("passwordLoginService")
	PasswordLoginService passwordLoginService;

	@Autowired
	@Qualifier("passwordRecoveryService")
	PasswordRecoveryService passwordRecoveryService;

	// MARK: Direct Controller Tests
	@Test
	public void testCreate() {
		LoginTokenPair pair = this.passwordController.create(TEST_USERNAME, TEST_PASSWORD);

		String pointer = pair.getLoginPointer();
		String token = pair.getToken();

		assertNotNull(pointer);
		assertNotNull(token);

		assertTrue(this.loginPointerRegistry.exists(pair.getLoginPointerKey()));

		try {
			LoginToken loginToken = this.loginTokenService.decodeLoginToken(token).getLoginToken();
			assertNotNull(loginToken);

			assertTrue(pointer.equals(loginToken.getLoginPointerId()));
		} catch (Exception e) {
			fail("Login token failed decoding.");
		}
	}

	@Test
	public void testLogin() {
		LoginTokenPair createPair = this.passwordController.create(TEST_USERNAME, TEST_PASSWORD);
		LoginTokenPair loginPair = this.passwordController.login(TEST_USERNAME, TEST_PASSWORD);

		assertTrue(createPair.getLoginPointer().equals(loginPair.getLoginPointer()));
	}

	@Test
	public void testLoginAttemptWithoutLoginAvailable() {
		try {
			this.passwordController.login(TEST_USERNAME, TEST_PASSWORD);
			fail("Login should not exist.");
		} catch (ApiLoginException e) {
			assertTrue(e.getReason().equals(ApiLoginException.LoginExceptionReason.UNAVAILABLE));
		}
	}

	@Test
	public void testLoginAttemptWithWrongPassword() {
		this.passwordController.create(TEST_USERNAME, TEST_PASSWORD);

		try {
			this.passwordController.login(TEST_USERNAME, "InvalidPassword");
			fail("Login info should be invalid.");
		} catch (ApiLoginException e) {
			assertTrue(e.getReason().equals(ApiLoginException.LoginExceptionReason.INVALID_CREDENTIALS));
		}
	}

	// MARK: Mock Tests
	@Test
	public void testLoginRequests() throws Exception {

		// Create a password LoginPointer/Token
		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);

		JsonParser parser = new JsonParser();

		MvcResult createResult = testUtility.sendCreatePasswordLogin(TEST_USERNAME, TEST_PASSWORD);
		MockHttpServletResponse createResponse = createResult.getResponse();
		String createResponseData = createResponse.getContentAsString();

		assertTrue("Expected 200 but got " + createResponse.getStatus() + ".",
		        createResponse.getStatus() == 200);
		assertNotNull(createResponseData);

		this.waitForTaskQueueToComplete();

		JsonElement createResponseJson = parser.parse(createResponseData);
		JsonObject object = createResponseJson.getAsJsonObject();
		String token = object.get("token").getAsString();

		assertNotNull(token);

		try {
			DecodedLoginToken<LoginToken> decoded = this.loginTokenService.decodeLoginToken(token);
			assertTrue(decoded.getLoginToken().getPointerType() == LoginPointerType.PASSWORD);
		} catch (Exception e) {
			fail("Should decode token.");
		}

		MvcResult loginResult = testUtility.sendLoginWithPassword(TEST_USERNAME, TEST_PASSWORD);
		MockHttpServletResponse loginResponse = loginResult.getResponse();
		String loginResponseData = loginResponse.getContentAsString();

		assertTrue("Expected 200 but got " + loginResponse.getStatus() + ".", loginResponse.getStatus() == 200);
		assertNotNull(loginResponseData);
	}

	@Test
	public void testCreateLogin() throws Exception {
		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);
		String token = testUtility.createPasswordLogin(TEST_USERNAME, TEST_PASSWORD);

		DecodedLoginToken<LoginToken> decodedPasswordToken = this.loginTokenService.decodeLoginToken(token);
		assertTrue(decodedPasswordToken.getLoginToken().getPointerType() == LoginPointerType.PASSWORD);
	}

	@Test
	public void testCreateLoginWithoutPasswordFails() throws Exception {
		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);

		MvcResult createResult = testUtility.sendCreatePasswordLogin(TEST_USERNAME, "");
		MockHttpServletResponse createResponse = createResult.getResponse();

		assertTrue(createResponse.getStatus() == HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void testCreateLoginWithShortPasswordFails() throws Exception {
		String shortPassword = "abc";
		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);

		MvcResult createResult = testUtility.sendCreatePasswordLogin(TEST_USERNAME, shortPassword);
		MockHttpServletResponse createResponse = createResult.getResponse();

		assertTrue(createResponse.getStatus() == HttpStatus.BAD_REQUEST.value());
	}

	// TODO: Test recover components!

}
