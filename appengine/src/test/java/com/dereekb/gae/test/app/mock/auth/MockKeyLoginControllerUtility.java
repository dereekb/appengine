package com.dereekb.gae.test.app.mock.auth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.test.app.mock.web.MockWebServiceTestUtility;
import com.dereekb.gae.test.app.mock.web.WebServiceTester;
import com.dereekb.gae.test.app.mock.web.builder.WebServiceRequestBuilder;
import com.dereekb.gae.test.app.mock.web.impl.AbstractMockWebServiceTestUtility;
import com.dereekb.gae.web.api.auth.controller.key.KeyLoginController;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * {@link MockWebServiceTestUtility} for interacting with the
 * {@link KeyLoginController}.
 *
 * @author dereekb
 *
 */
public class MockKeyLoginControllerUtility extends AbstractMockWebServiceTestUtility {

	public MockKeyLoginControllerUtility() {
		super();
	}

	public MockKeyLoginControllerUtility(WebServiceTester webServiceTester) {
		super(webServiceTester);
	}

	public static MockKeyLoginControllerUtility make(WebServiceTester tester) {
		return new MockKeyLoginControllerUtility(tester);
	}

	// MARK: Enable Utilities
	public void assertMockEnableApiKey() throws Exception {
		this.assertMockEnableApiKey(null);
	}

	public void assertMockEnableApiKey(String overrideToken) throws Exception {
		MockHttpServletResponse enableResponse = this.mockEnableApiKey(overrideToken);
		assertTrue(enableResponse.getStatus() == HttpServletResponse.SC_OK, "Failed enabling API Key. Status: " + enableResponse.getStatus());
	}

	public MockHttpServletResponse mockEnableApiKey() throws Exception {
		return this.mockEnableApiKey(null);
	}

	public MockHttpServletResponse mockEnableApiKey(String overrideToken) throws Exception {

		WebServiceTester tester = this.getWebServiceTester();
		WebServiceRequestBuilder serviceRequestBuilder = tester.getRequestBuilder();
		MockHttpServletRequestBuilder enableRequestBuilder = serviceRequestBuilder.put("/login/auth/key/enable");
		MvcResult enableResult = tester.performHttpRequest(enableRequestBuilder, overrideToken).andReturn();
		return enableResult.getResponse();
	}

	// MARK: Login
	private static final JsonParser parser = new JsonParser();

	public String tryApiLogin(String key,
	                          String verification,
	                          boolean assertSuccess)
	        throws Exception {

		MockHttpServletResponse loginResultResponse = this.mockApiLoginRequest(key, verification);
		boolean success = loginResultResponse.getStatus() == 200;

		if (assertSuccess) {
			assertTrue(success, "API Key Authentication Failed: " + loginResultResponse.getContentAsString());
		}

		String apiKeyLoginToken = null;

		if (success) {
			String loginResultContent = loginResultResponse.getContentAsString();
			JsonElement loginResultElement = parser.parse(loginResultContent);

			apiKeyLoginToken = loginResultElement.getAsJsonObject().get("token").getAsString();

			if (assertSuccess) {
				assertFalse(loginResultContent.isEmpty());
				assertFalse(apiKeyLoginToken.isEmpty());
			}
		}

		return apiKeyLoginToken;
	}

	public MockHttpServletResponse mockApiLoginRequest(String key,
	                                                   String verification)
	        throws Exception {

		WebServiceTester tester = this.getWebServiceTester();
		WebServiceRequestBuilder serviceRequestBuilder = tester.getRequestBuilder();

		MockHttpServletRequestBuilder keyLoginRequestBuilder = serviceRequestBuilder.post("/login/auth/key");
		keyLoginRequestBuilder.param("key", key);

		if (verification != null) {
			keyLoginRequestBuilder.param("verification", verification);
		}

		MvcResult loginResult = tester.mockMvcPerform(keyLoginRequestBuilder).andReturn();
		MockHttpServletResponse loginResultResponse = loginResult.getResponse();
		return loginResultResponse;
	}

}
