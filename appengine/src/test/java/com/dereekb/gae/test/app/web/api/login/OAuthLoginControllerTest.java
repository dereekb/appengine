package com.dereekb.gae.test.app.web.api.login;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.exception.LoginDisabledException;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthServiceManager;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthAuthorizationInfoImpl;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthLoginInfoImpl;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.app.mock.context.AbstractAppTestingContext;
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
 * @see OAuthLoginController
 */
public class OAuthLoginControllerTest extends AbstractAppTestingContext {

	private JsonParser parser = new JsonParser();

	@Autowired
	private OAuthServiceManager serverManager;

	@SuppressWarnings("unused")
	@Autowired
	private OAuthLoginController loginController;

	@Autowired
	@Qualifier("loginPointerRegistry")
	private ObjectifyRegistry<LoginPointer> loginPointerRegistry;

	private LoginApiTestUtility loginTestUtility = new LoginApiTestUtility(this);

	// MARK: OAuthServer tests
	@Test
	public void testOAuthLogin() {

		OAuthLoginInfo loginInfo = new OAuthLoginInfoImpl(LoginPointerType.OAUTH_GOOGLE, "abcde", "name",
		        "test@test.com");
		OAuthAuthorizationInfo info = new OAuthAuthorizationInfoImpl(loginInfo);

		LoginPointer loginPointer = this.serverManager.login(info);
		assertNotNull(loginPointer);
		assertNotNull(loginPointer.getIdentifier());
	}

	@Test
	public void testOAuthLoginWithDisabledLoginThrowsException() {

		OAuthLoginInfo loginInfo = new OAuthLoginInfoImpl(LoginPointerType.OAUTH_GOOGLE, "abcde", "name",
		        "test@test.com");
		OAuthAuthorizationInfo info = new OAuthAuthorizationInfoImpl(loginInfo);

		LoginPointer loginPointer = this.serverManager.login(info);
		assertNotNull(loginPointer);

		this.loginTestUtility.setLoginPointerDisabled(this.loginPointerRegistry, loginPointer.getModelKey());

		try {
			loginPointer = this.serverManager.login(info);
			fail("Expected login pointer to be disabled.");
		} catch (LoginDisabledException e) {
			LoginPointer disabledLoginPointer = e.getLoginPointer();
			assertNotNull(disabledLoginPointer);
		}
	}

	// MARK: Mock Tests
	@Test
	public void testInvalidAuthToken() throws Exception {
		MvcResult loginResult = this.loginTestUtility.sendCreateOAuthLoginWithToken("google", "INVALID_TOKEN");
		MockHttpServletResponse loginResponse = loginResult.getResponse();
		String loginResponseData = loginResponse.getContentAsString();

		assertTrue(loginResponse.getStatus() == 400, "Expected 400 but got " + loginResponse.getStatus() + ".");
		assertNotNull(loginResponseData);

		this.waitForTaskQueueToComplete();

		try {
			JsonElement loginResponseJson = this.parser.parse(loginResponseData);
			JsonObject object = loginResponseJson.getAsJsonObject();
			assertNotNull(object);
		} catch (JsonSyntaxException e) {
			fail("Response should be a JSON string.");
		}
	}

	@Test
	public void testInvalidRequestType() throws Exception {
		MvcResult loginResult = this.loginTestUtility.sendCreateOAuthLoginWithToken("UNAVAILABLE", "INVALID_TOKEN");
		MockHttpServletResponse loginResponse = loginResult.getResponse();
		String loginResponseData = loginResponse.getContentAsString();

		assertTrue(loginResponse.getStatus() == 400, "Expected a bad request but got: " + loginResponse.getStatus());
		assertNotNull(loginResponseData);
	}

	@Test
	public void testMissingAccessToken() throws Exception {
		MvcResult loginResult = this.loginTestUtility.sendCreateOAuthLoginWithToken("google", null);
		MockHttpServletResponse loginResponse = loginResult.getResponse();

		assertTrue(loginResponse.getStatus() == 400, "Expected a bad request but got: " + loginResponse.getStatus());
	}

}
