package com.dereekb.gae.server.auth.security.login.oauth.impl.service.scribe.facebook;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthCode;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthClientConfig;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginInfo;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthenticationException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthDeniedException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthInsufficientException;
import com.dereekb.gae.server.auth.security.login.oauth.impl.AbstractOAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthAccessTokenImpl;
import com.dereekb.gae.server.auth.security.login.oauth.impl.service.scribe.AbstractScribeOAuthService;
import com.dereekb.gae.utilities.data.url.ConnectionUtility;
import com.dereekb.gae.utilities.json.JsonUtility;
import com.dereekb.gae.utilities.json.JsonUtility.JsonObjectReader;
import com.dereekb.gae.utilities.regex.RegexHelper;
import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.apis.facebook.FacebookAccessTokenErrorResponse;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * {@link OAuthService} implementation for Facebook OAuth.
 * 
 * @author dereekb
 *
 */
public class FacebookOAuthService extends AbstractScribeOAuthService {

	public static final String SHORT_TERM_ACCESS_TOKEN_TYPE_KEY = "access_token";

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

			String responseJson = tokenError.getRawResponse();
			throw new OAuthAuthorizationTokenRequestException(tokenError.getMessage(), responseJson);
		}
	}

	// MARK: OAuth
	@Override
	public OAuthAuthorizationInfo processAuthorizationCode(OAuthAuthCode code)
	        throws OAuthConnectionException,
	            OAuthAuthorizationTokenRequestException {
		String codeType = code.getCodeType();

		if (codeType != null && codeType.equalsIgnoreCase(SHORT_TERM_ACCESS_TOKEN_TYPE_KEY)) {
			OAuthAccessToken accessToken = this.processShortTermAuthorizationCode(code.getAuthCode());
			return this.retrieveAuthorizationInfo(accessToken);
		}

		return this.processAuthorizationCode(code.getAuthCode());
	}

	private static final String FACEBOOK_SHORT_TERM_AUTH_URL_FORMAT = "https://graph.facebook.com/v2.8/oauth/access_token?grant_type=fb_exchange_token&client_id=%s&client_secret=%s&fb_exchange_token=%s";

	public OAuthAccessToken processShortTermAuthorizationCode(String authCode) {

		if (RegexHelper.containsPunctuation(authCode)) {
			throw new IllegalArgumentException("Input auth code contained invalid characters.");
		}

		String stringUrl = this.makeShortTermAuthCodeUrl(authCode);
		HttpURLConnection connection = null;

		try {
			URL url = new URL(stringUrl);

			/*
			 * Request:
			 * 
			 * https://graph.facebook.com/v2.8/oauth/access_token?grant_type=
			 * fb_exchange_token&client_id=1815005125414904&client_secret=
			 * ffdfeb73c8ed4958cd8c74d513022aa9&fb_exchange_token=
			 * EAAZAyvMZCEDZCgBAA3EaYtHL1n2esv82A2ba4YSgKUHB7IglS10cZAAHRzFZC36S7GelsTSK8tNZCuE5btVdQXvhJWhiiISnlmNZAS5svGykL5PqA91ZABG8YvRPxYoz32CrpncZBogJrF1ZBy4rGStHP3pIS5bSnEYiFMe9mjTCceTmEOi1ZARfq90eGy6txGEtUcuvl9SsiSn2cPoYdEvUuNIqTnjM0olBM8ZD
			 */
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setDoOutput(true);

			JsonElement json = ConnectionUtility.readJsonFromConnection(connection);
			JsonObject jsonObject = json.getAsJsonObject();

			/*
			 * Response:
			 * 
			 * {"access_token":
			 * "EAAZAyvMZCEDZCgBAKPtDwPuwpdEeo2ovZCteleLoJXugggQxtYeBElJhsWKObBssV8l8ZA6GZBrpQwX7ubClqfmrFfIhlKukIJU5QEFb6u9UudNZBAAKNonaHHhysqc71B0ZB605nVZAq8QzFAWFzeCG2YbrSsuLr0YADwZC9DjJCnigZDZD"
			 * ,"token_type":"bearer","expires_in":5184000}
			 */
			String accessToken = jsonObject.get("access_token").getAsString();
			Long expiresIn = jsonObject.get("expires_in").getAsLong();
			return new OAuthAccessTokenImpl(accessToken, expiresIn);
		} catch (MalformedURLException e) {
			throw new RuntimeException();
		} catch (OAuthAuthenticationException e) {
			throw e;
		} catch (IOException e) {

			JsonElement json = null;

			try {
				// Try to read the error stream.
				InputStream errorStream = connection.getErrorStream();
				json = ConnectionUtility.readJsonFromInputStream(errorStream);
			} catch (Exception errorException) {
				throw new OAuthConnectionException();
			}

			throw new OAuthDeniedException("Facebook Error Response", json.toString());
		} catch (Exception e) {
			throw new OAuthAuthenticationException();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public String makeShortTermAuthCodeUrl(String accessToken) {
		OAuthClientConfig clientConfig = this.getClientConfig();

		String clientId = clientConfig.getClientId();
		String clientSecret = clientConfig.getClientSecret();

		return String.format(FACEBOOK_SHORT_TERM_AUTH_URL_FORMAT, clientId, clientSecret, accessToken);
	}

	// MARK: Authorization Info
	private static final String FACEBOOK_USER_REQUEST_URL_PREFIX = "https://graph.facebook.com/v2.8/me?fields=id,name,email&access_token=";

	private static final String FACEBOOK_APP_SECRET_HASH_ALGORITHM = "HmacSHA256";
	private static final String FACEBOOK_APP_SECRET_PROOF_PARAM = "appsecret_proof";

	@Override
	protected String getLoginInfoRequestUrl(OAuthAccessToken token) {
		String accessToken = token.getAccessToken();
		return FACEBOOK_USER_REQUEST_URL_PREFIX + accessToken;
	}

	@Override
	protected OAuthRequest buildRequest(OAuthAccessToken token) {
		OAuthRequest request = super.buildRequest(token);

		// HMAC signature
		try {
			String secret = this.getClientConfig().getClientSecret();
			byte[] secretBytes = secret.getBytes("utf-8");
			SecretKeySpec keySpec = new SecretKeySpec(secretBytes, FACEBOOK_APP_SECRET_HASH_ALGORITHM);

			Mac mac = Mac.getInstance(FACEBOOK_APP_SECRET_HASH_ALGORITHM);
			mac.init(keySpec);

			String accessToken = token.getAccessToken();
			byte[] accessTokenBytes = accessToken.getBytes("utf-8");

			byte[] sig = mac.doFinal(accessTokenBytes);
			String hex = DatatypeConverter.printHexBinary(sig).toLowerCase();

			request.addParameter(FACEBOOK_APP_SECRET_PROOF_PARAM, hex);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		return request;
	}

	@Override
	protected OAuthAuthorizationInfo parseResultFromData(OAuthAccessToken token,
	                                                     String data)
	        throws Exception {

		JsonElement json = JsonUtility.parseJson(data);

		FacebookOAuthAuthorizationError.assertResponseIsSuccessful(json);

		return FacebookOAuthAuthorizationInfoImpl.fromResult(token, json);
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

			/*
			 * {
			 * "id": "10152497154619532",
			 * "name": "Derek Burgman",
			 * "email": "dereekb@gmail.com"
			 * }
			 */
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

	public static class FacebookOAuthAuthorizationError {

		private static final String ERROR_KEY = "error";
		private static final String ERROR_MESSAGE_KEY = "message";

		protected static void assertResponseIsSuccessful(JsonElement json) {
			JsonObject response = json.getAsJsonObject();

			if (response.has(ERROR_KEY)) {
				JsonObject error = response.get(ERROR_KEY).getAsJsonObject();
				throw makeException(error);
			}
		}

		public static OAuthException makeException(JsonObject element) {

			/*
			 * {"error":{
			 * "message":"Error validating access token: Session has expired on Friday, 17-Feb-17 02:00:00 PST. The current time is Friday, 17-Feb-17 13:05:06 PST."
			 * ,"type":"OAuthException","code":190,"error_subcode":463,
			 * "fbtrace_id":
			 * "Bfbt7IB3Ips"}}
			 */
			String message = element.get(ERROR_MESSAGE_KEY).getAsString();
			throw new OAuthDeniedException(message, element.toString());
		}

	}

}
