package com.dereekb.gae.server.auth.security.login.oauth.impl;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthService;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;

/**
 * {@link OAuthService} implementation.
 *
 * @author dereekb
 *
 */
public class GoogleOAuthLoginService extends AbstractOAuthLoginService {

	private static final String GOOGLE_OAUTH_SERVER = "https://accounts.google.com/o/oauth2/v2/auth";

	private static final List<String> GOOGLE_OAUTH_SCOPES = Arrays.asList(
	        "https://www.googleapis.com/auth/userinfo.email", "https://www.googleapis.com/auth/userinfo.profile");

	public GoogleOAuthLoginService(String clientId, String redirectUrl) {
		super(GOOGLE_OAUTH_SERVER, clientId, redirectUrl, GOOGLE_OAUTH_SCOPES);
	}


	@Override
	public OAuthLoginInfo processAuthorizationToken(HttpServletRequest request) {
		String authToken = this.getAuthToken(request);
		return this.processAuthorizationToken(authToken);
	}

	private String getAuthToken(HttpServletRequest request) {
		String fullRequestUrl = this.getFullRequestUrl(request);
		AuthorizationCodeResponseUrl authResponse = new AuthorizationCodeResponseUrl(fullRequestUrl);

		// check for user-denied error
		if (authResponse.getError() != null) {
			// authorization denied...
		} else {
			// request access token using authResponse.getCode()...
		}

		return null;
	}

	public String getFullRequestUrl(HttpServletRequest request) {
		StringBuffer fullUrlBuf = request.getRequestURL();

		if (request.getQueryString() != null) {
			fullUrlBuf.append('?').append(request.getQueryString());
		}

		return fullUrlBuf.toString();
	}

	@Override
	public OAuthLoginInfo processAuthorizationToken(String authToken) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public OAuthAccessToken getAuthorizationToken(String authCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OAuthLoginInfo getLoginInfo(OAuthAccessToken accessToken) {
		// TODO Auto-generated method stub
		return null;
	}


}
