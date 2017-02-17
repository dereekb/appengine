package com.dereekb.gae.test.applications.api.api.login.oauth;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthServiceManager;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthAuthorizationInfoImpl;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthLoginInfoImpl;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.web.api.auth.controller.oauth.OAuthLoginController;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * Tests the OAuth login controller and server manager.
 *
 * @author dereekb
 *
 */
public class OAuthApiControllerTest extends ApiApplicationTestContext {

	private JsonParser parser = new JsonParser();

	@Autowired
	private OAuthServiceManager serverManager;

	@SuppressWarnings("unused")
	@Autowired
	private OAuthLoginController loginController;

	// MARK: OAuthServer tests
	@Test
	public void testOAuthLogin() {

		OAuthLoginInfo loginInfo = new OAuthLoginInfoImpl(LoginPointerType.OAUTH_GOOGLE, "abcde", "name",
		        "test@test.com");
		OAuthAuthorizationInfo info = new OAuthAuthorizationInfoImpl(loginInfo);

		LoginPointer loginPointer = this.serverManager.login(info);
		Assert.assertNotNull(loginPointer);
		Assert.assertNotNull(loginPointer.getIdentifier());
	}

	// MARK: Mock Tests
	@Test
	public void testInvalidAuthToken() throws Exception {
		MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders
		        .post("/login/auth/oauth/google/token");
		loginRequestBuilder.param("token", "INVALID_TOKEN");
		loginRequestBuilder.accept("application/json");

		MvcResult loginResult = this.mockMvcPerform(loginRequestBuilder).andReturn();
		MockHttpServletResponse loginResponse = loginResult.getResponse();
		String loginResponseData = loginResponse.getContentAsString();

		Assert.assertTrue("Expected 400 but got " + loginResponse.getStatus() + ".", loginResponse.getStatus() == 400);
		Assert.assertNotNull(loginResponseData);

		TestLocalTaskQueueCallback.waitUntilComplete();

		try {
			JsonElement loginResponseJson = this.parser.parse(loginResponseData);
			JsonObject object = loginResponseJson.getAsJsonObject();
			Assert.assertNotNull(object);
		} catch (JsonSyntaxException e) {
			Assert.fail("Response should be a JSON string.");
		}
	}

	@Test
	public void testInvalidRequestType() throws Exception {
		MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders
		        .post("/login/auth/oauth/UNAVAILABLE/token");
		loginRequestBuilder.param("accessToken", "INVALID_TOKEN");
		loginRequestBuilder.accept("application/json");

		MvcResult loginResult = this.mockMvcPerform(loginRequestBuilder).andReturn();
		MockHttpServletResponse loginResponse = loginResult.getResponse();
		String loginResponseData = loginResponse.getContentAsString();

		Assert.assertTrue("Expected a bad request but got: " + loginResponse.getStatus(),
		        loginResponse.getStatus() == 400);
		Assert.assertNotNull(loginResponseData);
	}

	@Test
	public void testMissingAccessToken() throws Exception {
		MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders
		        .post("/login/auth/oauth/google/token");
		loginRequestBuilder.accept("application/json");

		MvcResult loginResult = this.mockMvcPerform(loginRequestBuilder).andReturn();
		MockHttpServletResponse loginResponse = loginResult.getResponse();

		Assert.assertTrue("Expected a bad request but got: " + loginResponse.getStatus(),
		        loginResponse.getStatus() == 400);
	}

}
