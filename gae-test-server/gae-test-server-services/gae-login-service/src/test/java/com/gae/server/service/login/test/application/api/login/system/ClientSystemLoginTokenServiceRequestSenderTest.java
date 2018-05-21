package com.gae.server.service.login.test.application.api.login.system;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.client.api.auth.system.ClientSystemLoginTokenServiceRequestSender;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationRequest;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationServiceRequestSender;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenValidationException;
import com.dereekb.gae.client.api.auth.token.impl.ClientLoginTokenValidationRequestImpl;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityService;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.applications.api.api.login.LoginApiTestUtility;

/**
 * {@link ClientLoginTokenValidationServiceRequestSender} tests.
 *
 * @author dereekb
 *
 */
public class ClientSystemLoginTokenServiceRequestSenderTest extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("appRegistry")
	private ObjectifyRegistry<App> appRegistry;

	@Autowired
	@Qualifier("loginTokenService")
	private LoginTokenService<LoginToken> loginTokenService;

	@Autowired
	@Qualifier("appLoginSecurityService")
	private AppLoginSecurityService appLoginSecurityService;

	@Autowired
	@Qualifier("clientSystemLoginTokenServiceRequestSender")
	private ClientSystemLoginTokenServiceRequestSender clientSystemLoginTokenService;

	@Autowired
	@Qualifier("clientLoginTokenValidationServiceRequestSender")
	private ClientLoginTokenValidationServiceRequestSender clientLoginTokenValidation;

	// MARK: Mock Test
	@Test
	public void testValidateAppSystemToken() throws Exception {

		String secret = "secret";

		// Create Test App
		App testApp = new App();
		testApp.setApp("test");
		testApp.setSecret(secret);
		this.appRegistry.store(testApp);

		ModelKey testAppKey = testApp.getModelKey();

		// Request System Token
		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);
		EncodedLoginToken encodedLoginToken = testUtility.createSystemLoginToken(testAppKey.toString());

		SignedEncodedLoginToken signedToken = this.appLoginSecurityService.signToken(secret,
		        encodedLoginToken.getEncodedLoginToken(), "");

		// Test Validation
		ClientLoginTokenValidationRequest request = new ClientLoginTokenValidationRequestImpl(signedToken);

		try {
			this.clientLoginTokenValidation.validateToken(request);
		} catch (ClientLoginTokenValidationException e) {
			Assert.fail("Should have not failed.");
		}
	}

}
