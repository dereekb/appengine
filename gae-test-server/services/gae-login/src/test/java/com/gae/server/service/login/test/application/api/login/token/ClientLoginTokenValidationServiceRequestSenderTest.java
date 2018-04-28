package com.gae.server.service.login.test.application.api.login.token;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationRequest;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationResponse;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationServiceRequestSender;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenExpiredException;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenInvalidException;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenInvalidSignatureException;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenValidationException;
import com.dereekb.gae.client.api.auth.token.impl.ClientLoginTokenValidationRequestImpl;
import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityService;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.utilities.time.DateUtility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * {@link ClientLoginTokenValidationServiceRequestSender} tests.
 *
 * @author dereekb
 *
 */
public class ClientLoginTokenValidationServiceRequestSenderTest extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("loginTokenService")
	private LoginTokenService<LoginToken> loginTokenService;

	@Autowired
	@Qualifier("appLoginSecurityService")
	private AppLoginSecurityService appLoginSecurityService;

	@Autowired
	@Qualifier("clientLoginTokenValidationServiceRequestSender")
	private ClientLoginTokenValidationServiceRequestSender sender;

	@Test
	public void testValidateTokenWithClaimsAndNoSignature()
	        throws ClientLoginTokenExpiredException,
	            ClientLoginTokenInvalidException,
	            ClientLoginTokenInvalidSignatureException,
	            ClientIllegalArgumentException,
	            ClientRequestFailureException {

		LoginTokenImpl loginToken = new LoginTokenImpl();
		loginToken.setLogin(1L);
		loginToken.setLoginPointer("pointer");
		loginToken.setExpiration(DateUtility.getDateIn(60 * 1000L));
		loginToken.setIssued(new Date());
		loginToken.setRefreshAllowed(true);

		String encodedToken = this.loginTokenService.encodeLoginToken(loginToken);
		ClientLoginTokenValidationRequest request = new ClientLoginTokenValidationRequestImpl(encodedToken, true);

		try {
			ClientLoginTokenValidationResponse response = this.sender.validateToken(request);
			Map<String, String> claimsMap = response.getClaimsMap();
			Map<String, Object> test = new HashMap<String, Object>(claimsMap);

			Claims claims = Jwts.claims(test);
			DecodedLoginToken<LoginToken> decoded = this.loginTokenService.decodeLoginTokenFromClaims(claims);

			LoginToken token = decoded.getLoginToken();
			Assert.assertTrue(token.getLoginId().equals(loginToken.getLoginId()));

		} catch (ClientLoginTokenValidationException e) {
			Assert.fail("Should have not failed.");
		}
	}

	@Test
	public void testValidateTokenWithExpiredToken()
	        throws ClientLoginTokenExpiredException,
	            ClientLoginTokenInvalidException,
	            ClientLoginTokenInvalidSignatureException,
	            ClientIllegalArgumentException,
	            ClientRequestFailureException {

		LoginTokenImpl loginToken = new LoginTokenImpl();
		loginToken.setLogin(1L);
		loginToken.setLoginPointer("pointer");

		// Set Expired
		loginToken.setIssued(DateUtility.getDateIn(60 * -1000L));
		loginToken.setExpiration(DateUtility.getDateIn(30 * -1000L));
		loginToken.setRefreshAllowed(true);

		String encodedToken = this.loginTokenService.encodeLoginToken(loginToken);
		ClientLoginTokenValidationRequest request = new ClientLoginTokenValidationRequestImpl(encodedToken);

		try {
			this.sender.validateToken(request);
			Assert.fail("Should have failed.");
		} catch (ClientLoginTokenExpiredException e) {
			// Success
		} catch (ClientLoginTokenValidationException e) {
			Assert.fail("Should have not failed for this reason.");
		}
	}

	@Test
	public void testValidateTokenWithInvalidSignature()
	        throws ClientLoginTokenExpiredException,
	            ClientLoginTokenInvalidException,
	            ClientLoginTokenInvalidSignatureException,
	            ClientIllegalArgumentException,
	            ClientRequestFailureException {

		LoginTokenImpl loginToken = new LoginTokenImpl();
		loginToken.setLogin(1L);
		loginToken.setLoginPointer("pointer");
		loginToken.setExpiration(DateUtility.getDateIn(60 * 1000L));
		loginToken.setIssued(new Date());
		loginToken.setRefreshAllowed(true);
		loginToken.setApp("1234");

		String encodedToken = this.loginTokenService.encodeLoginToken(loginToken);
		String invalidSignature = "INVALID_SIGNATURE";
		ClientLoginTokenValidationRequest request = new ClientLoginTokenValidationRequestImpl(encodedToken,
		        invalidSignature);

		try {
			this.sender.validateToken(request);
			Assert.fail("Should have failed.");
		} catch (ClientLoginTokenInvalidSignatureException e) {
			// Success
		} catch (ClientLoginTokenValidationException e) {
			Assert.fail("Should have not failed for this reason.");
		}
	}

}
