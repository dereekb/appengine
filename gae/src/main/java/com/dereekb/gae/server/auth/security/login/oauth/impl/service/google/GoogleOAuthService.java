package com.dereekb.gae.server.auth.security.login.oauth.impl.service.google;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthService;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthDeniedException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthInsufficientException;
import com.dereekb.gae.server.auth.security.login.oauth.impl.AbstractOAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthAccessTokenImpl;
import com.dereekb.gae.server.auth.security.login.oauth.impl.service.AbstractOAuthService;
import com.dereekb.gae.utilities.data.url.ConnectionUtility;
import com.dereekb.gae.utilities.json.JsonUtility;
import com.dereekb.gae.utilities.json.JsonUtility.JsonObjectReader;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.gson.JsonElement;

/**
 * {@link OAuthService} implementation.
 *
 * @author dereekb
 *
 */
public class GoogleOAuthService extends AbstractOAuthService {

	private static final String GOOGLE_ACCOUNT_LOGIN_PATH = "https://accounts.google.com/o/oauth2/v2/auth";
	private static final String GOOGLE_AUTH_TOKEN_PATH = "https://www.googleapis.com/oauth2/v4/token";

	private static final String GOOGLE_USER_REQUEST_URI = "https://www.googleapis.com/userinfo/v2/me";

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String AUTHORIZATION_FORMAT = "Bearer %s";

	private static final List<String> GOOGLE_OAUTH_SCOPES = Arrays.asList(
	        "https://www.googleapis.com/auth/userinfo.email", "https://www.googleapis.com/auth/userinfo.profile");

	public GoogleOAuthService(String clientId, String clientSecret) throws IllegalArgumentException {
		super(GOOGLE_ACCOUNT_LOGIN_PATH, GOOGLE_AUTH_TOKEN_PATH, clientId, clientSecret, GOOGLE_OAUTH_SCOPES);
	}

	@Override
	public LoginPointerType getLoginType() {
		return LoginPointerType.OAUTH_GOOGLE;
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
		return this.getAuthorizationInfo(accessToken);
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
			TokenErrorResponse errorResponse = e.getDetails();

			if (errorResponse != null) {
				String code = errorResponse.getError();
				String description = errorResponse.getErrorDescription();

				throw new OAuthAuthorizationTokenRequestException(code, description);
			} else {
				Integer httpCode = e.getStatusCode();
				String statusMessage = e.getStatusMessage();
				throw new OAuthAuthorizationTokenRequestException(httpCode.toString(), statusMessage);
			}
		} catch (IOException e) {
			throw new OAuthConnectionException(e);
		}

		return result;
	}

	@Override
	public GoogleOAuthUserResult getAuthorizationInfo(OAuthAccessToken token)
	        throws OAuthAuthorizationTokenRequestException,
	            OAuthConnectionException {
		return this.getLoginInfoFromServer(token);
	}

	private GoogleOAuthUserResult getLoginInfoFromServer(OAuthAccessToken token)
	        throws OAuthAuthorizationTokenRequestException,
	            OAuthConnectionException {
		String accessToken = token.getAccessToken();
		String bearer = String.format(AUTHORIZATION_FORMAT, accessToken);
		URL url;

		try {
			url = new URL(GOOGLE_USER_REQUEST_URI);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

		/*
		 * GET HTTP/1.1
		 * Host: www.googleapis.com
		 * Content-length: 0
		 * Authorization: Bearer ya29.Ci8mA15jelsR3ZO-
		 * QYxL5lG0APiNzX2k7WsU083XLtLRm5gvfNd5_2ytyNreU6Y2NA
		 */
		HttpURLConnection connection = null;
		GoogleOAuthUserResultImpl result = null;

		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty(AUTHORIZATION_HEADER, bearer);
			connection.setInstanceFollowRedirects(false);

			/*
			 * { "family_name": "Burgman", "name": "Derek Burgman", "picture":
			 * "https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg",
			 * "locale": "en", "gender": "male", "email": "dereekb@gmail.com",
			 * "link": "https://plus.google.com/107661756513647214258",
			 * "given_name": "Derek", "id": "107661756513647214258",
			 * "verified_email": true }
			 */
			JsonElement json = ConnectionUtility.readJsonFromConnection(connection);
			result = GoogleOAuthUserResultImpl.fromResult(token, json);
		} catch (IOException e) {
			this.handleLoginConnectionException(e, connection);
		} catch (Exception e) {
			throw new OAuthException(e);
		}

		return result;
	}

	/**
	 * {@link OAuthAuthorizationInfo} implementation for
	 * {@link GoogleOAuthService}.
	 *
	 * @author dereekb
	 *
	 */
	public static class GoogleOAuthUserResultImpl extends AbstractOAuthAuthorizationInfo
	        implements GoogleOAuthUserResult {

		private static final String ID_KEY = "id";
		private static final String EMAIL_KEY = "email";
		private static final String NAME_KEY = "name";
		private static final String VERIFIED_KEY = "verified_email";

		private JsonObjectReader reader;

		private GoogleOAuthUserResultImpl(OAuthAccessToken accessToken, JsonElement json) {
			super(accessToken);
			this.setJson(json);
		}

		public static GoogleOAuthUserResultImpl fromResult(OAuthAccessToken accessToken,
		                                                   JsonElement json)
		        throws OAuthInsufficientException,
		            IllegalArgumentException {

			if (json == null) {
				throw new IllegalArgumentException("Json cannot be null.");
			}

			String id = JsonUtility.getString(json, "id");

			if (id == null) {
				throw new OAuthInsufficientException("This account has no identifier.");
			}

			return new GoogleOAuthUserResultImpl(accessToken, json);
		}

		public JsonElement getJson() {
			return this.reader.getElement();
		}

		public void setJson(JsonElement json) {
			this.reader = new JsonObjectReader(json);
		}

		// MARK: OAuthAuthorizationInfo
		@Override
		public OAuthLoginInfo getLoginInfo() {
			return this;
		}

		// MARK: OAuthLoginInfo
		@Override
		public LoginPointerType getLoginType() {
			return LoginPointerType.OAUTH_GOOGLE;
		}

		@Override
		public String getEmail() {
			return this.reader.getString(EMAIL_KEY);
		}

		@Override
		public String getId() {
			return this.reader.getString(ID_KEY);
		}

		@Override
		public String getName() {
			return this.reader.getString(NAME_KEY);
		}

		public boolean isVerifiedEmail() {
			return this.reader.getBoolean(VERIFIED_KEY);
		}

		@Override
		public boolean isAcceptable() {
			return (this.getId() != null && this.getEmail() != null);
		}

		@Override
		public String toString() {
			return "GoogleOAuthUserResult [reader=" + this.reader + "]";
		}

	}

}
