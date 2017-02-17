package com.dereekb.gae.server.auth.security.login.oauth.impl.service.scribe.google;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthClientConfig;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginInfo;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthInsufficientException;
import com.dereekb.gae.server.auth.security.login.oauth.impl.AbstractOAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.impl.service.scribe.AbstractScribeOAuthService;
import com.dereekb.gae.utilities.json.JsonUtility;
import com.dereekb.gae.utilities.json.JsonUtility.JsonObjectReader;
import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonElement;

/**
 * {@link OAuthService} implementation for Google OAuth.
 * 
 * @author dereekb
 *
 */
public class GoogleOAuthService extends AbstractScribeOAuthService {

	private static final List<String> GOOGLE_OAUTH_SCOPES = Arrays.asList("email");

	public GoogleOAuthService(OAuthClientConfig clientConfig) throws IllegalArgumentException {
		super(clientConfig, GOOGLE_OAUTH_SCOPES);
	}

	// MARK: OAuthService
	@Override
	public LoginPointerType getLoginType() {
		return LoginPointerType.OAUTH_FACEBOOK;
	}

	// MARK: ScribeOAuthService
	@Override
	protected OAuth20Service buildScribeService(ServiceBuilder builder) {
		return builder.build(GoogleApi20.instance());
	}

	// MARK: Authorization Info
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String AUTHORIZATION_FORMAT = "Bearer %s";

	private static final String GOOGLE_USER_REQUEST_URL = "https://www.googleapis.com/userinfo/v2/me";

	@Override
	protected String getLoginInfoRequestUrl(OAuthAccessToken token) {
		return GOOGLE_USER_REQUEST_URL;
	}

	@Deprecated
	protected void configureConnection(OAuthAccessToken token,
	                                   HttpURLConnection connection) {
		// super.configureConnection(token, connection);

		/*
		 * GET HTTP/1.1
		 * Host: www.googleapis.com
		 * Content-length: 0
		 * Authorization: Bearer ya29.Ci8mA15jelsR3ZO-
		 * QYxL5lG0APiNzX2k7WsU083XLtLRm5gvfNd5_2ytyNreU6Y2NA
		 */
		String accessToken = token.getAccessToken();
		String bearer = String.format(AUTHORIZATION_FORMAT, accessToken);
		connection.setRequestProperty(AUTHORIZATION_HEADER, bearer);
	}

	@Override
	protected OAuthAuthorizationInfo parseResultFromData(OAuthAccessToken token,
	                                                     String data)
	        throws Exception {

		/*
		 * { "family_name": "Burgman", "name": "Derek Burgman", "picture":
		 * "https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg",
		 * "locale": "en", "gender": "male", "email": "dereekb@gmail.com",
		 * "link": "https://plus.google.com/107661756513647214258",
		 * "given_name": "Derek", "id": "107661756513647214258",
		 * "verified_email": true }
		 */
		JsonElement json = JsonUtility.parseJson(data);
		return GoogleOAuthAuthorizationInfoImpl.fromResult(token, json);
	}

	/**
	 * {@link OAuthAuthorizationInfo} implementation for
	 * {@link GoogleOAuthService}.
	 *
	 * @author dereekb
	 *
	 */
	public static class GoogleOAuthAuthorizationInfoImpl extends AbstractOAuthAuthorizationInfo
	        implements GoogleOAuthAuthorizationInfo {

		private static final String ID_KEY = "id";
		private static final String EMAIL_KEY = "email";
		private static final String NAME_KEY = "name";
		private static final String VERIFIED_KEY = "verified_email";

		private JsonObjectReader reader;

		private GoogleOAuthAuthorizationInfoImpl(OAuthAccessToken accessToken, JsonElement json) {
			super(accessToken);
			this.setJson(json);
		}

		public static GoogleOAuthAuthorizationInfo fromResult(OAuthAccessToken accessToken,
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

			return new GoogleOAuthAuthorizationInfoImpl(accessToken, json);
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
