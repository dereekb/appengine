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

		KeyLoginStatusService statusService = serviceManager.getService(login);

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
		statusService = serviceManager.getService(login);
		Assert.assertTrue(statusService.isEnabled());

	}

	// MARK: Mock Tests
	@Test
	public void testAPIAuthLogin() throws Exception {

		// Already Logged In. Must enable.
		MockHttpServletRequestBuilder enableRequestBuilder = MockMvcRequestBuilders.put("/login/key/enable");

		MvcResult enableResult = this.performHttpRequest(enableRequestBuilder).andReturn();
		Assert.assertTrue("Enabling login failed.", enableResult.getResponse().getStatus() == 200);
		
		//Create an API Key
		MockHttpServletRequestBuilder createApiKeyRequestBuilder = MockMvcRequestBuilders.post("/loginkey/create");
		createApiKeyRequestBuilder.accept("application/json");
		
		LoginKey newKey = new LoginKey();
		
		newKey.setName("My API Key");
		newKey.setVerification(VERIFICATION_KEY);

		ApiCreateRequest<LoginKey> createRequest = new ApiCreateRequest<LoginKey>();
		createRequest.setDataElement(newKey);
		
		String newApiKeyJson = gson.toJson(createRequest);
		createApiKeyRequestBuilder.content(newApiKeyJson);
		createApiKeyRequestBuilder.contentType("application/json");
		
		MvcResult createApiKeyResult = this.performHttpRequest(createApiKeyRequestBuilder).andReturn();
		MockHttpServletResponse createApiKeyResponse = createApiKeyResult.getResponse();
		
		Assert.assertTrue("Creating API Key Failed.", createApiKeyResponse.getStatus() == 200);
		String createApiKeyContent = createApiKeyResponse.getContentAsString();

		Assert.assertFalse(createApiKeyContent.isEmpty());
		JsonElement createApiKeyElement = parser.parse(createApiKeyContent);
		
		String apiKeyId = createApiKeyElement.getAsJsonObject().get("data").getAsJsonObject().get("data").getAsJsonArray().get(0).getAsJsonObject().get("key").getAsString();
		Assert.assertFalse(apiKeyId.isEmpty());
		
		//Login with the API Key
		MockHttpServletRequestBuilder keyLoginRequestBuilder = MockMvcRequestBuilders.post("/login/key");
		keyLoginRequestBuilder.param("key", apiKeyId);
		keyLoginRequestBuilder.param("verification", VERIFICATION_KEY);
		
		MvcResult loginResult = this.mockMvc.perform(keyLoginRequestBuilder).andReturn();
		MockHttpServletResponse loginResultResponse = loginResult.getResponse();

		Assert.assertTrue("API Key Authentication Failed.", loginResultResponse.getStatus() == 200);
		String loginResultContent = loginResultResponse.getContentAsString();
		JsonElement loginResultElement = parser.parse(loginResultContent);

		String apiKeyLoginToken = loginResultElement.getAsJsonObject().get("token").getAsString();
		Assert.assertFalse(loginResultContent.isEmpty());
		Assert.assertFalse(apiKeyLoginToken.isEmpty());
		
		//Test API Key Cannot Access /loginkey
		MockHttpServletRequestBuilder tryApiKeyRequestBuilder = MockMvcRequestBuilders.post("/loginkey/create");
		tryApiKeyRequestBuilder.accept("application/json");
		
		ApiCreateRequest<LoginKey> tryRequest = new ApiCreateRequest<LoginKey>();
		tryRequest.setDataElement(newKey);

		String tryApiKeyJson = gson.toJson(tryRequest);
		tryApiKeyRequestBuilder.content(tryApiKeyJson);
		tryApiKeyRequestBuilder.contentType("application/json");
		
		MvcResult tryApiKeyResult = this.performSecureHttpRequest(tryApiKeyRequestBuilder, apiKeyLoginToken).andReturn();
		MockHttpServletResponse tryApiKeyResponse = tryApiKeyResult.getResponse();

		Assert.assertTrue("LoginKey API should be forbidden to API keys.", tryApiKeyResponse.getStatus() == 403);
		
	}

}
