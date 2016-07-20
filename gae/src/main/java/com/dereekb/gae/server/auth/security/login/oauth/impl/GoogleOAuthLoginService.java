package com.dereekb.gae.server.auth.security.login.oauth.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthService;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthDeniedException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthException;
import com.dereekb.gae.utilities.data.url.ConnectionUtility;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * {@link OAuthService} implementation.
 *
 * @author dereekb
 *
 */
public class GoogleOAuthLoginService extends AbstractOAuthLoginService {

	private static final String GOOGLE_OAUTH_SERVER = "https://accounts.google.com/o/oauth2/v2/auth";

	private static final String GOOGLE_USER_REQUEST_URI = "https://www.googleapis.com/userinfo/v2/me";




	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String AUTHORIZATION_FORMAT = "Bearer %s";

	private static final List<String> GOOGLE_OAUTH_SCOPES = Arrays.asList("https://www.googleapis.com/auth/userinfo.email", "https://www.googleapis.com/auth/userinfo.profile");

	public GoogleOAuthLoginService(String clientId, String clientSecret, String redirectUrl)
	        throws IllegalArgumentException {
		super(GOOGLE_OAUTH_SERVER, clientId, clientSecret, redirectUrl, GOOGLE_OAUTH_SCOPES);
	}

	@Override
	public OAuthAuthorizationInfo processAuthorizationCodeResponse(HttpServletRequest request) {
		String authCode = this.getAuthCode(request);
		return this.processAuthorizationCode(authCode);
	}

	private String getAuthCode(HttpServletRequest request) throws OAuthDeniedException {
		String fullRequestUrl = this.getFullRequestUrl(request);
		AuthorizationCodeResponseUrl authResponse = new AuthorizationCodeResponseUrl(fullRequestUrl);
		String authCode = null;

		// check for user-denied error
		if (authResponse.getError() != null) {
			throw new OAuthDeniedException(authResponse.getError(), authResponse.getErrorDescription());
		} else {
			authCode = authResponse.getCode();
		}

		return authCode;
	}

	public String getFullRequestUrl(HttpServletRequest request) {
		StringBuffer fullUrlBuf = request.getRequestURL();

		if (request.getQueryString() != null) {
			fullUrlBuf.append('?').append(request.getQueryString());
		}

		return fullUrlBuf.toString();
	}

	@Override
	public OAuthAuthorizationInfo processAuthorizationCode(String authCode) {
		OAuthAccessToken accessToken = this.getAuthorizationToken(authCode);
		return this.getLoginInfo(accessToken);
	}


	@Override
	public OAuthAccessToken getAuthorizationToken(String authCode)
	        throws OAuthAuthorizationTokenRequestException,
	            OAuthConnectionException {

		OAuthAccessToken result = null;

		 try {
			AuthorizationCodeTokenRequest request = this.makeAuthorizationCodeTokenRequest(authCode);
			TokenResponse response = request.execute();

			String accessToken = response.getAccessToken();
			String refreshToken = response.getRefreshToken();
			Long expiration = response.getExpiresInSeconds();

			result = new OAuthAccessTokenImpl(accessToken, refreshToken, expiration);
		} catch (TokenResponseException e) {
			TokenErrorResponse response = e.getDetails();

			// TODO: Complete Exception Throwing here

			if (e.getDetails() != null) {

				System.err.println("Error: " + e.getDetails().getError());
		        if (e.getDetails().getErrorDescription() != null) {
		          System.err.println(e.getDetails().getErrorDescription());
		        }
		        if (e.getDetails().getErrorUri() != null) {
		          System.err.println(e.getDetails().getErrorUri());
		        }
		      } else {
		        System.err.println(e.getMessage());
		      }
		} catch (IOException e) {
			throw new OAuthConnectionException(e);
		}

		return result;
	}

	@Override
	public OAuthAuthorizationInfo getLoginInfo(OAuthAccessToken token) throws OAuthConnectionException {
		GoogleOAuthUserResult result = null;

		try {
			result = this.getLoginInfoFromServer(token);
		} catch (IOException e) {
			throw new OAuthConnectionException(e);
		} catch (Exception e) {
			throw new OAuthException(e);
		}

		return result;
	}

	private GoogleOAuthUserResult getLoginInfoFromServer(OAuthAccessToken token)
	        throws JsonSyntaxException,
	            IOException {
		String accessToken = token.getAccessToken();
		String bearer = String.format(AUTHORIZATION_FORMAT, accessToken);
		URL url = new URL(GOOGLE_USER_REQUEST_URI);

		/*
		GET  HTTP/1.1
		Host: www.googleapis.com
		Content-length: 0
		Authorization: Bearer ya29.Ci8mA15jelsR3ZO-QYxL5lG0APiNzX2k7WsU083XLtLRm5gvfNd5_2ytyNreU6Y2NA
		 */
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty(AUTHORIZATION_HEADER, bearer);
		connection.setInstanceFollowRedirects(false);

		/*
		 {
		  "family_name": "Burgman",
		  "name": "Derek Burgman",
		  "picture": "https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg",
		  "locale": "en",
		  "gender": "male",
		  "email": "dereekb@gmail.com",
		  "link": "https://plus.google.com/107661756513647214258",
		  "given_name": "Derek",
		  "id": "107661756513647214258",
		  "verified_email": true
		}
		 */
		JsonElement json = ConnectionUtility.readJsonFromConnection(connection);
		JsonObject jsonRoot = json.getAsJsonObject();

		return GoogleOAuthUserResult.fromResult(token, json);
	}

	/**
	 * {@link OAuthAuthorizationInfo} implementation for {@link
	 *
	 * @author dereekb
	 *
	 */
	public static class GoogleOAuthUserResult extends AbstractOAuthAuthorizationInfo
	        implements OAuthAuthorizationInfo, OAuthLoginInfo {

		private JsonElement json;

		private GoogleOAuthUserResult(OAuthAccessToken accessToken, JsonElement json) throws IllegalArgumentException {
			super(accessToken);
			this.setJson(json);
		}

		public static GoogleOAuthUserResult fromResult(OAuthAccessToken accessToken,
		                                               JsonElement json) {

			// TODO: Assert an email is available.

			// TODO Auto-generated method stub
			return new GoogleOAuthUserResult(accessToken, json);
		}

		public JsonElement getJson() {
			return this.json;
		}

		public void setJson(JsonElement json) {
			this.json = json;
		}

		// MARK: OAuthAuthorizationInfo
		@Override
		public OAuthLoginInfo getLoginInfo() {
			return this;
		}

		// MARK: OAuthLoginInfo
		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getEmail() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isAcceptable() {
			// TODO Auto-generated method stub
			return false;
		}

	}

}
