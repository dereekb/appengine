package com.dereekb.gae.test.applications.api.api.login.password;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
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
public class PasswordLoginApiControllerTest extends ApiApplicationTestContext {

	private static final String TEST_USERNAME = "pUsername";
	private static final String TEST_PASSWORD = "pPassword";

	@Autowired
	public PasswordLoginController passwordController;

	@Autowired
	public LoginTokenService loginTokenService;

	@Autowired
	@Qualifier("loginPointerRegistry")
	public ObjectifyRegistry<LoginPointer> loginPointerRegistry;

	// MARK: Direct Controller Tests
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

			Assert.assertTrue(pointer.equals(loginToken.getLoginPointerId()));
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

	// MARK: Mock Tests
	@Test
	public void testLoginRequests() throws Exception {
		JsonParser parser = new JsonParser();

		MockHttpServletRequestBuilder createRequestBuilder = this.serviceRequestBuilder.post("/login/auth/pass/create");
		createRequestBuilder.param("username", TEST_USERNAME);
		createRequestBuilder.param("password", TEST_PASSWORD);
		createRequestBuilder.accept("application/json");

		MvcResult createResult = this.mockMvcPerform(createRequestBuilder).andReturn();
		MockHttpServletResponse createResponse = createResult.getResponse();
		String createResponseData = createResponse.getContentAsString();

		Assert.assertTrue("Expected 200 but got " + createResponse.getStatus() + ".",
		        createResponse.getStatus() == 200);
		Assert.assertNotNull(createResponseData);

		this.waitForTaskQueueToComplete();

		JsonElement createResponseJson = parser.parse(createResponseData);
		JsonObject object = createResponseJson.getAsJsonObject();
		String token = object.get("token").getAsString();

		Assert.assertNotNull(token);

		try {
			this.loginTokenService.decodeLoginToken(token);
		} catch (Exception e) {
			Assert.fail("Should decode token.");
		}

		MockHttpServletRequestBuilder loginRequestBuilder = this.serviceRequestBuilder.post("/login/auth/pass");
		loginRequestBuilder.param("username", TEST_USERNAME);
		loginRequestBuilder.param("password", TEST_PASSWORD);
		loginRequestBuilder.accept("application/json");

		MvcResult loginResult = this.mockMvcPerform(loginRequestBuilder).andReturn();
		MockHttpServletResponse loginResponse = loginResult.getResponse();
		String loginResponseData = loginResponse.getContentAsString();

		Assert.assertTrue("Expected 200 but got " + loginResponse.getStatus() + ".", loginResponse.getStatus() == 200);
		Assert.assertNotNull(loginResponseData);
	}

}
