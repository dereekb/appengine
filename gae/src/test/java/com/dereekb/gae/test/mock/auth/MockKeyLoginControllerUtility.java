package com.dereekb.gae.test.mock.auth;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dereekb.gae.test.mock.AbstractMockWebServiceTestUtility;
import com.dereekb.gae.test.mock.MockWebServiceTestUtility;
import com.dereekb.gae.test.spring.WebServiceTester;
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
		Assert.assertTrue("Failed enabling API Key. Status: " + enableResponse.getStatus(),
		        enableResponse.getStatus() == HttpServletResponse.SC_OK);
	}

	public MockHttpServletResponse mockEnableApiKey() throws Exception {
		return this.mockEnableApiKey(null);
	}

	public MockHttpServletResponse mockEnableApiKey(String overrideToken) throws Exception {
		MockHttpServletRequestBuilder enableRequestBuilder = MockMvcRequestBuilders.put("/login/auth/key/enable");

		WebServiceTester tester = this.getWebServiceTester();
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
			Assert.assertTrue("API Key Authentication Failed.", success);
		}

		String apiKeyLoginToken = null;

		if (success) {
			String loginResultContent = loginResultResponse.getContentAsString();
			JsonElement loginResultElement = parser.parse(loginResultContent);

			apiKeyLoginToken = loginResultElement.getAsJsonObject().get("token").getAsString();

			if (assertSuccess) {
				Assert.assertFalse(loginResultContent.isEmpty());
				Assert.assertFalse(apiKeyLoginToken.isEmpty());
			}
		}

		return apiKeyLoginToken;
	}

	public MockHttpServletResponse mockApiLoginRequest(String key,
	                                                   String verification)
	        throws Exception {
		MockHttpServletRequestBuilder keyLoginRequestBuilder = MockMvcRequestBuilders.post("/login/auth/key");
		keyLoginRequestBuilder.param("key", key);

		if (verification != null) {
			keyLoginRequestBuilder.param("verification", verification);
		}

		WebServiceTester tester = this.getWebServiceTester();
		MvcResult loginResult = tester.mockMvcPerform(keyLoginRequestBuilder).andReturn();
		MockHttpServletResponse loginResultResponse = loginResult.getResponse();
		return loginResultResponse;
	}

}