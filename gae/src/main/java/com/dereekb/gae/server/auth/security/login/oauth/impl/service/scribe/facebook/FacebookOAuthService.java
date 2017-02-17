package com.dereekb.gae.server.auth.security.login.oauth.impl.service.scribe.facebook;

import java.util.Arrays;
import java.util.List;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthCode;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthClientConfig;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginInfo;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthInsufficientException;
import com.dereekb.gae.server.auth.security.login.oauth.impl.AbstractOAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.impl.service.scribe.AbstractScribeOAuthService;
import com.dereekb.gae.utilities.json.JsonUtility;
import com.dereekb.gae.utilities.json.JsonUtility.JsonObjectReader;
import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.apis.facebook.FacebookAccessTokenErrorResponse;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonElement;

/**
 * {@link OAuthService} implementation for Facebook OAuth.
 * 
 * @author dereekb
 *
 */
public class FacebookOAuthService extends AbstractScribeOAuthService {

	private static final List<String> FACEBOOK_OAUTH_SCOPES = Arrays.asList("email");

	public FacebookOAuthService(OAuthClientConfig clientConfig) throws IllegalArgumentException {
		super(clientConfig, FACEBOOK_OAUTH_SCOPES);
	}

	// MARK: OAuthService
	@Override
	public LoginPointerType getLoginType() {
		return LoginPointerType.OAUTH_FACEBOOK;
	}

	// MARK: ScribeOAuthService
	@Override
	protected OAuth20Service buildScribeService(ServiceBuilder builder) {
		return builder.build(FacebookApi.instance());
	}

	@Override
	public void handleTokenOAuthException(OAuthException e) throws OAuthAuthorizationTokenRequestException {
		if (FacebookAccessTokenErrorResponse.class.isAssignableFrom(e.getClass())) {
			FacebookAccessTokenErrorResponse tokenError = (FacebookAccessTokenErrorResponse) e;

			String code = tokenError.getCode();
			String type = tokenError.getType();
			String message = tokenError.getMessage();

			throw new OAuthAuthorizationTokenRequestException(code, type, message, e);
		}
	}

	// MARK: OAuth
	@Override
	public OAuthAuthorizationInfo processAuthorizationCode(OAuthAuthCode code)
	        throws OAuthConnectionException,
	            OAuthAuthorizationTokenRequestException {
		String codeType = code.getCodeType();

		if (codeType != null) {
			// TODO: Build a long-term authentication code from a short-term
			// code if the type matches a specific string.
		}

		return this.processAuthorizationCode(code.getAuthCode());
	}

	// MARK: Authorization Info
	private static final String FACEBOOK_USER_REQUEST_URL_PREFIX = "https://graph.facebook.com/v2.8/me?fields=id,name,email&access_token=";

	@Override
	protected String getLoginInfoRequestUrl(OAuthAccessToken token) {
		String accessToken = token.getAccessToken();
		return FACEBOOK_USER_REQUEST_URL_PREFIX + accessToken;
	}

	@Override
	protected OAuthAuthorizationInfo parseResultFromData(OAuthAccessToken token,
	                                                     String data)
	        throws Exception {
		/*
		 * {
		 * "id": "10152497154619532",
		 * "name": "Derek Burgman",
		 * "email": "dereekb@gmail.com"
		 * }
		 */
		JsonElement json = JsonUtility.parseJson(data);
		return new FacebookOAuthAuthorizationInfoImpl(token, json);
	}

	/**
	 * {@link OAuthAuthorizationInfo} implementation for
	 * {@link FacebookOAuthService}.
	 *
	 * @author dereekb
	 *
	 */
	public static class FacebookOAuthAuthorizationInfoImpl extends AbstractOAuthAuthorizationInfo
	        implements FacebookOAuthAuthorizationInfo {

		private static final String ID_KEY = "id";
		private static final String EMAIL_KEY = "email";
		private static final String NAME_KEY = "name";

		private JsonObjectReader reader;

		private FacebookOAuthAuthorizationInfoImpl(OAuthAccessToken accessToken, JsonElement json) {
			super(accessToken);
			this.setJson(json);
		}

		public static FacebookOAuthAuthorizationInfo fromResult(OAuthAccessToken accessToken,
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

			return new FacebookOAuthAuthorizationInfoImpl(accessToken, json);
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
			return LoginPointerType.OAUTH_FACEBOOK;
		}

		@Override
		public String getId() {
			return this.reader.getString(ID_KEY);
		}

		@Override
		public String getName() {
			return this.reader.getString(NAME_KEY);
		}

		@Override
		public String getEmail() {
			return this.reader.getString(EMAIL_KEY);
		}

		@Override
		public boolean isAcceptable() {
			return (this.getId() != null);
		}

		@Override
		public String toString() {
			return "FacebookOAuthUserResult [reader=" + this.reader + "]";
		}

	}

}
