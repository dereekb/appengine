package com.dereekb.gae.test.app.web.api.login;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletResponse;

import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationRequest;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationResponse;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationServiceRequestSender;
import com.dereekb.gae.client.api.auth.token.impl.ClientLoginTokenValidationRequestImpl;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.RemoteAppLoginTokenSecurityConfigurerImpl;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.AppLoginSecurityLevel;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityService;
import com.dereekb.gae.server.auth.security.app.service.impl.PreConfiguredAppLoginSecuritySigningServiceImpl;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.refresh.impl.RefreshTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.refresh.impl.RefreshTokenServiceImpl;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.app.mock.context.AbstractAppTestingContext;
import com.dereekb.gae.web.api.auth.controller.token.impl.TokenValidationRequestImpl;

/**
 * Tests a remote app that is in the system authenticating with this one.
 *
 * @author dereekb
 *
 * @see RemoteAppLoginTokenSecurityConfigurerImpl
 * @see SystemLoginTokenControllerTest
 */
public class RemoteSystemAppToLoginAppLoginTokenTests extends AbstractAppTestingContext {

	private static final String TEST_USERNAME = "tokenUsername";
	private static final String TEST_PASSWORD = "tokenPassword";

	private static final String TEST_APP_NAME = "remote";
	private static final String TEST_APP_SECRET = "secret";

	@Autowired
	@Qualifier("loginTokenService")
	private LoginTokenService<LoginToken> loginTokenService;

	@Autowired
	@Qualifier("appLoginSecurityService")
	private AppLoginSecurityService appLoginSecurityService;

	@Autowired
	@Qualifier("refreshTokenEncoderDecoder")
	private RefreshTokenEncoderDecoder refreshEncoderDecoder;

	@Autowired
	@Qualifier("refreshTokenService")
	private RefreshTokenServiceImpl refreshTokenService;

	@Autowired
	@Qualifier("appRegistry")
	private ObjectifyRegistry<App> appRegistry;

	@Autowired
	@Qualifier("clientLoginTokenValidationServiceRequestSender")
	private ClientLoginTokenValidationServiceRequestSender sender;

	// MARK: Mock Tests
	@Test
	public void testRemoteAppComponentsValidatesValidSignedTokenUsingTokenValidationRequestSender() throws Exception {
		App app = this.generateTestApp();
		PreConfiguredAppLoginSecuritySigningServiceImpl signingService = new PreConfiguredAppLoginSecuritySigningServiceImpl(
		        app);

		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);

		String appId = app.getModelKey().toString();
		EncodedLoginToken encodedLoginToken = testUtility.createSystemLoginToken(appId);
		String tokenString = encodedLoginToken.getEncodedLoginToken();

		String content = "";	// TODO
		SignedEncodedLoginToken signedToken = signingService.signWithToken(tokenString, content);

		ClientLoginTokenValidationRequest clientRequest = new ClientLoginTokenValidationRequestImpl(signedToken);

		SerializedClientApiResponse<ClientLoginTokenValidationResponse> response = this.sender
		        .sendRequest(clientRequest, ClientRequestSecurityImpl.none());

		assertTrue(response.getSerializedResponse() != null);
	}

	/**
	 * Manually configures/injects components that test a login token generated
	 * on the login server is properly validated through another app's
	 * configuration.
	 */
	@Test
	public void testRemoteAppComponentsValidatesValidSignedToken() throws Exception {

		App app = this.generateTestApp();
		PreConfiguredAppLoginSecuritySigningServiceImpl signingService = new PreConfiguredAppLoginSecuritySigningServiceImpl(
		        app);

		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);

		String appId = app.getModelKey().toString();
		EncodedLoginToken encodedLoginToken = testUtility.createSystemLoginToken(appId);
		String tokenString = encodedLoginToken.getEncodedLoginToken();

		String content = "";	// TODO
		SignedEncodedLoginToken signedToken = signingService.signWithToken(tokenString, content);

		TokenValidationRequestImpl validationRequest = new TokenValidationRequestImpl(signedToken);
		MockHttpServletResponse response = testUtility.validateLoginToken(validationRequest);

		assertTrue(response.getStatus() == HttpStatus.OK_200);
	}

	/**
	 * Tests that the login server does not validate a server token with an
	 * invalid signature.
	 */
	@Test
	public void testRemoteAppComponentsFailsWithInvalidTokenSignature() throws Exception {

		App app = this.generateTestApp();
		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);

		String appId = app.getModelKey().toString();
		EncodedLoginToken encodedLoginToken = testUtility.createSystemLoginToken(appId);
		String tokenString = encodedLoginToken.getEncodedLoginToken();

		String invalidSignature = "SIGNATURE";

		TokenValidationRequestImpl validationRequest = new TokenValidationRequestImpl(tokenString, invalidSignature);
		MockHttpServletResponse response = testUtility.validateLoginToken(validationRequest);

		assertTrue(response.getStatus() == HttpStatus.UNAUTHORIZED_401);
	}

	private App generateTestApp() {

		// Create an App
		App app = new App();

		app.setApp(TEST_APP_NAME);
		app.setSystemKey(TEST_APP_NAME);
		app.setLevel(AppLoginSecurityLevel.SYSTEM);

		app.setSecret(TEST_APP_SECRET);

		this.appRegistry.store(app);

		return app;
	}

}
