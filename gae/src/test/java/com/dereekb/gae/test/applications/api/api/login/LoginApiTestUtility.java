package com.dereekb.gae.test.applications.api.api.login;

import org.junit.Assert;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dereekb.gae.test.spring.CoreServiceTestingContext.TestLocalTaskQueueCallback;
import com.dereekb.gae.test.spring.WebServiceTester;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Utility for testing client logins.
 * 
 * @author dereekb
 *
 */
public class LoginApiTestUtility {

	private static final JsonParser PARSER = new JsonParser();

	private WebServiceTester webServiceTester;

	public LoginApiTestUtility(WebServiceTester webServiceTester) {
		this.setWebServiceTester(webServiceTester);
	}

	public WebServiceTester getWebServiceTester() {
		return this.webServiceTester;
	}

	public void setWebServiceTester(WebServiceTester webServiceTester) {
		if (webServiceTester == null) {
			throw new IllegalArgumentException("webServiceTester cannot be null.");
		}

		this.webServiceTester = webServiceTester;
	}

	// MARK: Refresh Token
	public String getRefreshToken(String token) throws Exception {
		MockHttpServletRequestBuilder registerRequestBuilder = MockMvcRequestBuilders.get("/login/auth/token/refresh");
		MvcResult result = this.webServiceTester.performSecureHttpRequest(registerRequestBuilder, token).andReturn();
		return this.getTokenFromResponse(result);
	}

	public String authWithRefreshToken(String token) throws Exception {
		MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.post("/login/auth/token/login");
		loginRequestBuilder.content(token);
		loginRequestBuilder.contentType("application/json");

		MvcResult result = this.webServiceTester.mockMvcPerform(loginRequestBuilder).andReturn();
		return this.getTokenFromResponse(result);
	}

	// MARK: Register
	public String register(String token) throws Exception {
		MockHttpServletRequestBuilder registerRequestBuilder = MockMvcRequestBuilders.post("/login/auth/register");
		MvcResult result = this.webServiceTester.performSecureHttpRequest(registerRequestBuilder, token).andReturn();
		return this.getTokenFromResponse(result);
	}

	// MARK: Password
	public String createPasswordLogin(String username,
	                                  String password)
	        throws Exception {
		MockHttpServletRequestBuilder createRequestBuilder = MockMvcRequestBuilders.post("/login/auth/pass/create");
		createRequestBuilder.param("username", username);
		createRequestBuilder.param("password", password);
		createRequestBuilder.accept("application/json");

		MvcResult result = this.webServiceTester.mockMvcPerform(createRequestBuilder).andReturn();
		return this.getTokenFromResponse(result);
	}

	// MARK: Tokens
	public String getTokenFromResponse(MvcResult result) throws Exception {
		MockHttpServletResponse response = result.getResponse();
		return this.getTokenFromResponse(response);
	}

	public String getTokenFromResponse(MockHttpServletResponse response) throws Exception {
		String createResponseData = response.getContentAsString();

		Assert.assertTrue("Expected 200 but got " + response.getStatus() + ".", response.getStatus() == 200);
		Assert.assertNotNull(createResponseData);

		TestLocalTaskQueueCallback.waitUntilComplete();

		JsonElement createResponseJson = PARSER.parse(createResponseData);
		JsonObject object = createResponseJson.getAsJsonObject();
		String token = object.get("token").getAsString();

		Assert.assertNotNull(token);

		return token;
	}

}
