package com.dereekb.gae.test.applications.api.api.login;

import org.junit.Assert;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.test.spring.WebServiceTester;
import com.dereekb.gae.test.spring.web.builder.WebServiceRequestBuilder;
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
		WebServiceRequestBuilder serviceRequestBuilder = this.webServiceTester.getRequestBuilder();
		MockHttpServletRequestBuilder registerRequestBuilder = serviceRequestBuilder.get("/login/auth/token/refresh");
		MvcResult result = this.webServiceTester.performSecureHttpRequest(registerRequestBuilder, token).andReturn();
		return this.getTokenFromResponse(result);
	}

	public String authWithRefreshToken(String token) throws Exception {
		WebServiceRequestBuilder serviceRequestBuilder = this.webServiceTester.getRequestBuilder();
		MockHttpServletRequestBuilder loginRequestBuilder = serviceRequestBuilder.post("/login/auth/token/login");
		loginRequestBuilder.content(token);
		loginRequestBuilder.contentType("application/json");

		MvcResult result = this.webServiceTester.mockMvcPerform(loginRequestBuilder).andReturn();
		return this.getTokenFromResponse(result);
	}

	public String resetTokenAuth(String token) throws Exception {
		return this.resetTokenAuth(token, "");
	}

	public String resetTokenAuth(String token,
	                             String key)
	        throws Exception {
		MvcResult result = this.sendTokenAuthReset(token, key);

		// {"data":{"type":"modified","data":"2"}}
		String content = result.getResponse().getContentAsString();

		JsonElement json = PARSER.parse(content);
		return json.getAsJsonObject().get("data").getAsJsonObject().get("data").getAsString();
	}

	public MvcResult sendTokenAuthReset(String token,
	                                    String key)
	        throws Exception {
		WebServiceRequestBuilder serviceRequestBuilder = this.webServiceTester.getRequestBuilder();
		MockHttpServletRequestBuilder loginRequestBuilder = serviceRequestBuilder.post("/login/auth/token/reset" + key);
		return this.webServiceTester.performSecureHttpRequest(loginRequestBuilder, token).andReturn();
	}

	// MARK: Register
	public String register(String token) throws Exception {
		WebServiceRequestBuilder serviceRequestBuilder = this.webServiceTester.getRequestBuilder();
		MockHttpServletRequestBuilder registerRequestBuilder = serviceRequestBuilder.post("/login/auth/register");
		MvcResult result = this.webServiceTester.performSecureHttpRequest(registerRequestBuilder, token).andReturn();
		return this.getTokenFromResponse(result);
	}

	// MARK: Password
	public String createPasswordLogin(String username,
	                                  String password)
	        throws Exception {
		WebServiceRequestBuilder serviceRequestBuilder = this.webServiceTester.getRequestBuilder();
		MockHttpServletRequestBuilder createRequestBuilder = serviceRequestBuilder.post("/login/auth/pass/create");
		createRequestBuilder.param("username", username);
		createRequestBuilder.param("password", password);
		createRequestBuilder.accept("application/json");

		MvcResult result = this.webServiceTester.mockMvcPerform(createRequestBuilder).andReturn();
		return this.getTokenFromResponse(result);
	}

	// MARK: OAuth
	public String createOAuthLoginWithToken(String type,
	                                        String token)
	        throws Exception {
		MvcResult result = this.sendCreateOAuthLoginWithToken(type, token);
		return this.getTokenFromResponse(result);
	}

	public MvcResult sendCreateOAuthLoginWithToken(String type,
	                                               String token)
	        throws Exception {

		WebServiceRequestBuilder serviceRequestBuilder = this.webServiceTester.getRequestBuilder();
		MockHttpServletRequestBuilder loginRequestBuilder = serviceRequestBuilder
		        .post("/login/auth/oauth/" + type + "/token");
		loginRequestBuilder.param("token", token);
		loginRequestBuilder.accept("application/json");

		return this.webServiceTester.mockMvcPerform(loginRequestBuilder).andReturn();
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

		this.webServiceTester.waitForTaskQueueToComplete();

		JsonElement createResponseJson = PARSER.parse(createResponseData);
		JsonObject object = createResponseJson.getAsJsonObject();
		String token = object.get("token").getAsString();

		Assert.assertNotNull(token);

		return token;
	}

}
