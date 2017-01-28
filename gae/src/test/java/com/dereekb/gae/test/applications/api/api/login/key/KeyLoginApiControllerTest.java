package com.dereekb.gae.test.applications.api.api.login.key;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginAuthenticationService;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginStatusService;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginStatusServiceManager;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginUnavailableException;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.server.auth.TestLoginTokenPair;
import com.dereekb.gae.web.api.auth.controller.key.KeyLoginController;
import com.dereekb.gae.web.api.model.request.ApiCreateRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Tests the login key controller and server manager.
 *
 * @author dereekb
 *
 */
public class KeyLoginApiControllerTest extends ApiApplicationTestContext {

	private Gson gson = new GsonBuilder().create();
	private JsonParser parser = new JsonParser();

	private static final String VERIFICATION_KEY = "VERIFICATION";

	@Autowired
	private KeyLoginStatusServiceManager serviceManager;

	@SuppressWarnings("unused")
	@Autowired
	private KeyLoginAuthenticationService authenticationService;

	@SuppressWarnings("unused")
	@Autowired
	private KeyLoginController keyLoginController;

	// MARK: KeyLogin tests
	@Test
	public void testKeyLoginServices() {

		TestLoginTokenPair pair = this.testLoginTokenContext.generateLogin("keyLogin");
		Login login = pair.getLogin();

		KeyLoginStatusService statusService = this.serviceManager.getService(login);

		// Test is not yet enabled.
		Assert.assertFalse(statusService.isEnabled());

		// Test it is not available.
		try {
			statusService.getKeyLoginPointerKey();
			Assert.fail();
		} catch (KeyLoginUnavailableException e) {
		}

		// Enable
		LoginPointer keyPointer = statusService.enable();

		Assert.assertNotNull(keyPointer);
		Assert.assertTrue(keyPointer.getLogin().equals(login.getObjectifyKey()));
		Assert.assertTrue(statusService.isEnabled());

		// Test it is still enabled even with a new service.
		statusService = this.serviceManager.getService(login);
		Assert.assertTrue(statusService.isEnabled());

	}

	// MARK: Mock Tests
	@Test
	public void testAPIAuthLogin() throws Exception {

		// Already Logged In. Must enable.
		MockHttpServletResponse enableResponse = this.mockEnableApiKey();
		Assert.assertTrue("Enabling login failed.", enableResponse.getStatus() == 200);

		// Create an API Key
		String name = "My API Key";
		String verification = VERIFICATION_KEY;

		String apiKeyId = this.createApiKey(name, verification, true);

		// Login with the API Key
		String apiKeyLoginToken = this.tryApiLogin(apiKeyId, VERIFICATION_KEY, true);

		// Test API Key Cannot Access /loginkey
		MockHttpServletRequestBuilder tryApiKeyRequestBuilder = MockMvcRequestBuilders.post("/loginkey/create");
		tryApiKeyRequestBuilder.accept("application/json");

		ApiCreateRequest<LoginKey> tryRequest = new ApiCreateRequest<LoginKey>();

		LoginKey newKey = new LoginKey();
		newKey.setName(name);
		newKey.setVerification(verification);

		tryRequest.setDataElement(newKey);

		String tryApiKeyJson = this.gson.toJson(tryRequest);
		tryApiKeyRequestBuilder.content(tryApiKeyJson);
		tryApiKeyRequestBuilder.contentType("application/json");

		MvcResult tryApiKeyResult = this.performSecureHttpRequest(tryApiKeyRequestBuilder, apiKeyLoginToken)
		        .andReturn();
		MockHttpServletResponse tryApiKeyResponse = tryApiKeyResult.getResponse();

		Assert.assertTrue("LoginKey API should be forbidden to API keys.", tryApiKeyResponse.getStatus() == 403);

		// Test API Key

	}

	@Test
	public void testIncompleteAPIAuthLogin() throws Exception {
		MockHttpServletResponse response = this.mockApiLogin(null, "NONE");
		Assert.assertTrue("API Key Authentication should fail.", response.getStatus() == 400);
	}

	@Test
	public void testNonexistantAPIAuthLogin() throws Exception {
		MockHttpServletResponse response = this.mockApiLogin("101010101", "NONE");
		Assert.assertTrue("API Key Authentication should fail.", response.getStatus() == 403);
	}

	@Test
	public void testInvalidAPIAuthLogin() throws Exception {
		String name = "My API Key";
		String verification = VERIFICATION_KEY;

		this.mockEnableApiKey();

		String apiKeyId = this.createApiKey(name, verification, true);

		MockHttpServletResponse response = this.mockApiLogin(apiKeyId, "INVALID VERIFICATION");
		Assert.assertTrue("API Key Authentication should fail.", response.getStatus() == 403);

		response = this.mockApiLogin(apiKeyId, null);
		Assert.assertTrue("API Key Authentication should fail.", response.getStatus() == 400);
	}

	private MockHttpServletResponse mockEnableApiKey() throws Exception {
		MockHttpServletRequestBuilder enableRequestBuilder = MockMvcRequestBuilders.put("/login/key/enable");
		MvcResult enableResult = this.performHttpRequest(enableRequestBuilder).andReturn();

		return enableResult.getResponse();
	}

	private String tryApiLogin(String key,
	                           String verification,
	                           boolean assertSuccess)
	        throws Exception {

		MockHttpServletResponse loginResultResponse = this.mockApiLogin(key, verification);

		boolean success = loginResultResponse.getStatus() == 200;

		if (assertSuccess) {
			Assert.assertTrue("API Key Authentication Failed.", success);
		}

		String apiKeyLoginToken = null;

		if (success) {
			String loginResultContent = loginResultResponse.getContentAsString();
			JsonElement loginResultElement = this.parser.parse(loginResultContent);

			apiKeyLoginToken = loginResultElement.getAsJsonObject().get("token").getAsString();

			if (assertSuccess) {
				Assert.assertFalse(loginResultContent.isEmpty());
				Assert.assertFalse(apiKeyLoginToken.isEmpty());
			}
		}

		return apiKeyLoginToken;
	}

	private MockHttpServletResponse mockApiLogin(String key,
	                                             String verification)
	        throws Exception {
		MockHttpServletRequestBuilder keyLoginRequestBuilder = MockMvcRequestBuilders.post("/login/key");
		keyLoginRequestBuilder.param("key", key);

		if (verification != null) {
			keyLoginRequestBuilder.param("verification", verification);
		}

		MvcResult loginResult = this.mockMvc.perform(keyLoginRequestBuilder).andReturn();
		MockHttpServletResponse loginResultResponse = loginResult.getResponse();

		return loginResultResponse;
	}

	private String createApiKey(String name,
	                            String verification,
	                            boolean assertSuccess)
	        throws Exception {
		MockHttpServletResponse response = this.mockApiKeyCreate(name, verification);

		boolean success = response.getStatus() == 200;

		if (assertSuccess) {
			Assert.assertTrue("Creating API Key Failed.", success);
		}

		String apiKeyId = null;

		if (success) {
			String createApiKeyContent = response.getContentAsString();
			Assert.assertFalse(createApiKeyContent.isEmpty());

			JsonElement createApiKeyElement = this.parser.parse(createApiKeyContent);

			apiKeyId = createApiKeyElement.getAsJsonObject().get("data").getAsJsonObject().get("data").getAsJsonArray()
			        .get(0).getAsJsonObject().get("key").getAsString();

			if (assertSuccess) {
				Assert.assertFalse(apiKeyId.isEmpty());
			}
		}

		return apiKeyId;
	}

	private MockHttpServletResponse mockApiKeyCreate(String name,
	                                                 String verification)
	        throws Exception {

		MockHttpServletRequestBuilder createApiKeyRequestBuilder = MockMvcRequestBuilders.post("/loginkey/create");
		createApiKeyRequestBuilder.accept("application/json");

		LoginKey newKey = new LoginKey();

		newKey.setName(name);
		newKey.setVerification(verification);

		ApiCreateRequest<LoginKey> createRequest = new ApiCreateRequest<LoginKey>();
		createRequest.setDataElement(newKey);

		String newApiKeyJson = this.gson.toJson(createRequest);
		createApiKeyRequestBuilder.content(newApiKeyJson);
		createApiKeyRequestBuilder.contentType("application/json");

		MvcResult createApiKeyResult = this.performHttpRequest(createApiKeyRequestBuilder).andReturn();
		MockHttpServletResponse createApiKeyResponse = createApiKeyResult.getResponse();

		return createApiKeyResponse;
	}

}
