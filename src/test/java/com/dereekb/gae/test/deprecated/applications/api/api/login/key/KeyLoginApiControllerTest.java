package com.dereekb.gae.test.applications.api.api.login.key;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginAuthenticationService;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginStatusService;
import com.dereekb.gae.server.auth.security.login.key.KeyLoginStatusServiceManager;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginUnavailableException;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.mock.auth.MockKeyLoginControllerUtility;
import com.dereekb.gae.test.server.auth.TestLoginTokenPair;
import com.dereekb.gae.web.api.auth.controller.key.KeyLoginController;
import com.dereekb.gae.web.api.model.crud.request.ApiCreateRequest;
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

	private MockKeyLoginControllerUtility mockLoginUtil = new MockKeyLoginControllerUtility(this);

	// MARK: KeyLogin tests
	@Test
	public void testKeyLoginServices() {

		TestLoginTokenPair pair = this.testLoginTokenContext.generateLogin("keyLogin");
		Login login = pair.getLogin();

		KeyLoginStatusService statusService = this.serviceManager.getService(login);

		// Test is not yet enabled.
		assertFalse(statusService.isEnabled());

		// Test it is not available.
		try {
			statusService.getKeyLoginPointerKey();
			fail();
		} catch (KeyLoginUnavailableException e) {
		}

		// Enable
		LoginPointer keyPointer = statusService.enable();

		assertNotNull(keyPointer);
		assertTrue(keyPointer.getLogin().equals(login.getObjectifyKey()));
		assertTrue(statusService.isEnabled());

		// Test it is still enabled even with a new service.
		statusService = this.serviceManager.getService(login);
		assertTrue(statusService.isEnabled());

	}

	// MARK: Mock Tests
	@Test
	public void testAPIAuthLogin() throws Exception {

		// Already Logged In. Must enable.
		this.testLoginTokenContext.generateLogin("REGULAR_USER");
		this.mockLoginUtil.assertMockEnableApiKey();

		// Create an API Key
		String name = "My API Key";
		String verification = VERIFICATION_KEY;

		String apiKeyId = this.createApiKey(name, verification, true);

		// Login with the API Key
		String apiKeyLoginToken = this.mockLoginUtil.tryApiLogin(apiKeyId, VERIFICATION_KEY, true);

		// Test API Key Cannot Access /loginkey
		MockHttpServletRequestBuilder tryApiKeyRequestBuilder = this.serviceRequestBuilder.post("/loginkey/create");
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

		assertTrue("LoginKey API should be forbidden to API keys.", tryApiKeyResponse.getStatus() == 403);

		// Test API Key

	}

	@Test
	public void testIncompleteAPIAuthLogin() throws Exception {
		MockHttpServletResponse response = this.mockLoginUtil.mockApiLoginRequest(null, "NONE");
		assertTrue("API Key Authentication should fail.", response.getStatus() == 400);
	}

	@Test
	public void testNonexistantAPIAuthLogin() throws Exception {
		MockHttpServletResponse response = this.mockLoginUtil.mockApiLoginRequest("101010101", "NONE");
		assertTrue("API Key Authentication should fail.", response.getStatus() == 403);
	}

	@Test
	public void testInvalidAPIAuthLogin() throws Exception {
		String name = "My API Key";
		String verification = VERIFICATION_KEY;

		this.testLoginTokenContext.generateLogin("REGULAR_USER");
		this.mockLoginUtil.assertMockEnableApiKey();

		String apiKeyId = this.createApiKey(name, verification, true);

		MockHttpServletResponse response = this.mockLoginUtil.mockApiLoginRequest(apiKeyId, "INVALID VERIFICATION");
		assertTrue("API Key Authentication should fail.",
		        response.getStatus() == HttpServletResponse.SC_FORBIDDEN);

		response = this.mockLoginUtil.mockApiLoginRequest(apiKeyId, null);
		assertTrue("API Key Authentication should fail.",
		        response.getStatus() == HttpServletResponse.SC_BAD_REQUEST);
	}

	private String createApiKey(String name,
	                            String verification,
	                            boolean assertSuccess)
	        throws Exception {
		MockHttpServletResponse response = this.mockApiKeyCreate(name, verification);

		boolean success = response.getStatus() == 200;

		if (assertSuccess) {
			assertTrue("Creating API Key Failed.", success);
		}

		String apiKeyId = null;

		if (success) {
			String createApiKeyContent = response.getContentAsString();
			assertFalse(createApiKeyContent.isEmpty());

			JsonElement createApiKeyElement = this.parser.parse(createApiKeyContent);

			apiKeyId = createApiKeyElement.getAsJsonObject().get("data").getAsJsonObject().get("data").getAsJsonArray()
			        .get(0).getAsJsonObject().get("key").getAsString();

			if (assertSuccess) {
				assertFalse(apiKeyId.isEmpty());
			}
		}

		return apiKeyId;
	}

	private MockHttpServletResponse mockApiKeyCreate(String name,
	                                                 String verification)
	        throws Exception {

		MockHttpServletRequestBuilder createApiKeyRequestBuilder = this.serviceRequestBuilder.post("/loginkey/create");
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
