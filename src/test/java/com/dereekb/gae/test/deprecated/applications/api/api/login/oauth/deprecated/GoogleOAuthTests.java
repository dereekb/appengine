package com.dereekb.gae.test.deprecated.applications.api.api.login.oauth;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginInfo;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthAccessTokenImpl;
import com.dereekb.gae.server.auth.security.login.oauth.impl.service.scribe.google.GoogleOAuthService;

/**
 * Tests google auth logins.
 *
 * @author dereekb
 *
 */
@Deprecated
public class GoogleOAuthTests extends AbstractOAuthTests {

	/**
	 * Authorization code retrieved from the Google OAuth Page.
	 *
	 *
	 * https://developers.google.com/oauthplayground
	 */
	private String TEMPORARY_AUTHORIZATION_CODE = "4/_hMbnHzUbhZqbyMh74tunuzqh85t4cKx35Rx0FVMaU4";

	private String TEMPORARY_ACCESS_TOKEN = "ya29.Ci8pAyQzTRW-2fS6C0W-u4z0WBbK51vPVjTuqDt4-YprMs82OVImL0rCKX6gk_AEgw";

	private GoogleOAuthService googleOAuthService;

	@Autowired
	public void setService(GoogleOAuthService googleOAuthService) {
		this.googleOAuthService = googleOAuthService;
		super.setService(googleOAuthService);
	}

	@Ignore // Comment out when testing with useful auth token.
	@Test
	public void testProcessAuthorizationCode() {

		OAuthAuthorizationInfo authorizationInfo = this.googleOAuthService
		        .processAuthorizationCode(this.TEMPORARY_AUTHORIZATION_CODE);
		Assert.assertNotNull(authorizationInfo);

		OAuthLoginInfo info = authorizationInfo.getLoginInfo();
		Assert.assertNotNull(info);

		Assert.assertNotNull(info.getEmail());

		OAuthAccessToken token = authorizationInfo.getAccessToken();
		Assert.assertNotNull(token);

	}

	@Ignore // Comment out when testing with useful access token.
	@Test
	public void testGetLoginInfo() {
		OAuthAccessToken token = new OAuthAccessTokenImpl(this.TEMPORARY_ACCESS_TOKEN);
		OAuthAuthorizationInfo authorizationInfo = this.googleOAuthService.retrieveAuthorizationInfo(token);
		Assert.assertNotNull(authorizationInfo);
	}

}
