package com.dereekb.gae.test.applications.api.api.login;

import java.util.List;

import org.junit.Assert;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.dereekb.gae.test.spring.WebServiceTester;
import com.dereekb.gae.test.spring.web.builder.WebServiceRequestBuilder;
import com.dereekb.gae.web.api.auth.controller.token.TokenValidationRequest;
import com.dereekb.gae.web.api.auth.controller.token.impl.TokenValidationRequestImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		loginRequestBuilder.param("refreshToken", token);
		loginRequestBuilder.contentType("application/x-www-form-urlencoded");

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

		String content = result.getResponse().getContentAsString();

		if (result.getResponse().getStatus() != 200) {
			Assert.fail("Reset response failed: " + content);
		}

		// {"data":{"type":"modified","data":"2"}}

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

	public String registerTokensWithLogin(String loginToken,
	                                      List<String> tokens)
	        throws Exception {
		WebServiceRequestBuilder serviceRequestBuilder = this.webServiceTester.getRequestBuilder();
		MockHttpServletRequestBuilder registerRequestBuilder = serviceRequestBuilder
		        .post("/login/auth/register/tokenss");

		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(tokens);

		registerRequestBuilder.content(content);

		MvcResult result = this.webServiceTester.performSecureHttpRequest(registerRequestBuilder, loginToken)
		        .andReturn();
		return this.getTokenFromResponse(result);
	}

	// MARK: Validate Token
	public MockHttpServletResponse validateLoginToken(String token) throws Exception {
		TokenValidationRequestImpl request = new TokenValidationRequestImpl(token);
		request.setQuick(true);

		return this.validateLoginToken(request);
	}

	public MockHttpServletResponse validateLoginToken(TokenValidationRequest request) throws Exception {

		WebServiceRequestBuilder serviceRequestBuilder = this.webServiceTester.getRequestBuilder();
		MockHttpServletRequestBuilder validateRequestBuilder = serviceRequestBuilder.post("/login/auth/token/validate");
		validateRequestBuilder.contentType(MediaType.APPLICATION_FORM_URLENCODED);

		validateRequestBuilder.param("token", request.getToken());

		if (request.getSignature() != null) {
			validateRequestBuilder.param("signature", request.getSignature());
		}

		if (request.getContent() != null) {
			validateRequestBuilder.param("content", request.getContent());
		}

		validateRequestBuilder.param("quick", request.getQuick().toString());

		MvcResult result = this.webServiceTester.mockMvcPerform(validateRequestBuilder).andReturn();
		return result.getResponse();
	}

	// MARK: Password
	public String loginWithPassword(String username,
	                                String password)
	        throws Exception {
		MvcResult result = this.sendLoginWithPassword(username, password);
		return this.getTokenFromResponse(result);
	}

	public MvcResult sendLoginWithPassword(String username,
	                                       String password)
	        throws Exception {
		WebServiceRequestBuilder serviceRequestBuilder = this.webServiceTester.getRequestBuilder();
		MockHttpServletRequestBuilder createRequestBuilder = serviceRequestBuilder.post("/login/auth/pass");
		createRequestBuilder.param("username", username);
		createRequestBuilder.param("password", password);
		createRequestBuilder.accept("application/json");
		return this.webServiceTester.mockMvcPerform(createRequestBuilder).andReturn();
	}

	public String createPasswordLogin(String username,
	                                  String password)
	        throws Exception {
		MvcResult result = this.sendCreatePasswordLogin(username, password);
		return this.getTokenFromResponse(result);
	}

	public MvcResult sendCreatePasswordLogin(String username,
	                                         String password)
	        throws Exception {
		WebServiceRequestBuilder serviceRequestBuilder = this.webServiceTester.getRequestBuilder();
		MockHttpServletRequestBuilder createRequestBuilder = serviceRequestBuilder.post("/login/auth/pass/create");
		createRequestBuilder.param("username", username);
		createRequestBuilder.param("password", password);
		createRequestBuilder.accept("application/json");

		return this.webServiceTester.mockMvcPerform(createRequestBuilder).andReturn();
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
		loginRequestBuilder.contentType("application/x-www-form-urlencoded");
		loginRequestBuilder.accept("application/json");

		return this.webServiceTester.mockMvcPerform(loginRequestBuilder).andReturn();
	}

	public String createOAuthLoginWithCode(String type,
	                                       String authCode,
	                                       String codeType)
	        throws Exception {
		MvcResult result = this.sendCreateOAuthLoginWithCode(type, authCode, codeType);
		return this.getTokenFromResponse(result);
	}

	public MvcResult sendCreateOAuthLoginWithCode(String serviceType,
	                                              String authCode,
	                                              String codeType)
	        throws Exception {

		WebServiceRequestBuilder serviceRequestBuilder = this.webServiceTester.getRequestBuilder();
		MockHttpServletRequestBuilder loginRequestBuilder = serviceRequestBuilder
		        .post("/login/auth/oauth/" + serviceType + "/code");
		loginRequestBuilder.param("code", authCode);

		if (codeType != null) {
			loginRequestBuilder.param("type", codeType);
		}

		loginRequestBuilder.contentType("application/x-www-form-urlencoded");
		loginRequestBuilder.accept("application/json");

		return this.webServiceTester.mockMvcPerform(loginRequestBuilder).andReturn();
	}

	// MARK: Tokens
	public String getTokenFromResponse(MvcResult result) throws Exception {
		MockHttpServletResponse response = result.getResponse();
		return this.getTokenFromResponse(response);
	}

	public String getTokenFromResponse(MockHttpServletResponse response) throws Exception {
		String responseData = response.getContentAsString();

		Assert.assertTrue("Expected 200 but got " + response.getStatus() + ". " + responseData,
		        response.getStatus() == 200);
		Assert.assertNotNull(responseData);

		this.webServiceTester.waitForTaskQueueToComplete();

		JsonElement createResponseJson = PARSER.parse(responseData);
		JsonObject object = createResponseJson.getAsJsonObject();
		String token = object.get("token").getAsString();

		Assert.assertNotNull(token);

		return token;
	}

}
