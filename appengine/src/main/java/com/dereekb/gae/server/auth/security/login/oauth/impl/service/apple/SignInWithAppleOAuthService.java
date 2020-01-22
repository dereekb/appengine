package com.dereekb.gae.server.auth.security.login.oauth.impl.service.apple;

import java.security.PrivateKey;
import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthCode;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthService;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthAuthorizationTokenRequestException;
import com.dereekb.gae.server.auth.security.login.oauth.exception.OAuthConnectionException;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthAccessTokenImpl;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthAuthorizationInfoImpl;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthLoginInfoImpl;
import com.dereekb.gae.server.auth.security.token.model.impl.UnsignedJwtStringDecoder;
import com.dereekb.gae.utilities.json.JsonUtility;
import com.google.gson.JsonObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * {@link OAuthService} implementation for Sign in with Apple.
 *
 * @author dereekb
 *
 */
public class SignInWithAppleOAuthService
        implements OAuthService {

	private static String APPLE_TOKEN_AUDIENCE = "https://appleid.apple.com";
	private static String APPLE_AUTH_URL = "https://appleid.apple.com/auth/token";

	private SignInWithAppleOAuthConfig config;

	public SignInWithAppleOAuthService(SignInWithAppleOAuthConfig config) {
		super();
		this.setConfig(config);
	}

	public SignInWithAppleOAuthConfig getConfig() {
		return this.config;
	}

	public void setConfig(SignInWithAppleOAuthConfig config) {
		if (config == null) {
			throw new IllegalArgumentException("config cannot be null.");
		}

		this.config = config;
	}

	// MARK: OAuthService
	@Override
	public LoginPointerType getLoginType() {
		return LoginPointerType.OAUTH_APPLE;
	}

	@Override
	public OAuthAuthorizationInfo processAuthorizationCode(OAuthAuthCode authCode)
	        throws OAuthConnectionException,
	            OAuthAuthorizationTokenRequestException {

		String serverToken = null;

		try {
			serverToken = this.generateJwtForServer();
		} catch (Exception e) {
			e.printStackTrace();
			throw new OAuthAuthorizationTokenRequestException();
		}

		// Configure Headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// Send Request
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		String url = APPLE_AUTH_URL;

		// Configure Request Map
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("client_id", this.config.getClientId());
		map.add("client_secret", serverToken);
		map.add("grant_type", "authorization_code");
		map.add("code", authCode.getAuthCode());
		// map.add("redirect_uri", "authorization_code");

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map,
		        headers);

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
			String body = response.getBody();

			// System.out.println("Response: " + body);

			OAuthAuthorizationInfo info = this.readAuthorizationInfoFromAuthCodeResponse(body);
			return info;
		} catch (RestClientResponseException e) {
			throw new OAuthAuthorizationTokenRequestException();

			/*
				String errorBody = e.getResponseBodyAsString();
				String message = e.getMessage();
				message.toString();
				System.out.println("Error: " + errorBody);
			*/
		}
	}

	@Override
	public OAuthAuthorizationInfo retrieveAuthorizationInfo(OAuthAccessToken token)
	        throws OAuthConnectionException,
	            OAuthAuthorizationTokenRequestException {
		throw new OAuthAuthorizationTokenRequestException("Can only sign in with an auth code.");
	}

	// MARK: Internal
	private String generateJwtForServer() throws Exception {

		String keyId = this.config.getKeyId();
		String teamId = this.config.getTeamId();
		String clientId = this.config.getClientId();

		PrivateKey privateKey = this.config.getPrivateKey();

		String token = Jwts.builder()
				.setHeaderParam(JwsHeader.KEY_ID, keyId)
				.setIssuer(teamId)
		        .setAudience(APPLE_TOKEN_AUDIENCE)
		        .setSubject(clientId)
		        .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 5)))
		        .setIssuedAt(new Date(System.currentTimeMillis()))
		        .signWith(privateKey, SignatureAlgorithm.ES256)
		        .compact();

		return token;
	}

	private OAuthAuthorizationInfo readAuthorizationInfoFromAuthCodeResponse(String body) {

		JsonObject jsonResponse = JsonUtility.parseJson(body).getAsJsonObject();
		String refreshToken = jsonResponse.get("access_token").getAsString();
		String idToken = jsonResponse.get("id_token").getAsString();

		Claims claims = UnsignedJwtStringDecoder.SINGLETON.decodeTokenClaims(idToken);
		String userId = claims.getSubject();
		String name = null; // TODO: ...	// claims.get("email", String.class);
		String email = claims.get("email", String.class);

		OAuthLoginInfo loginInfo = new OAuthLoginInfoImpl(LoginPointerType.OAUTH_APPLE, userId, name, email);
		OAuthAccessToken accessToken = new OAuthAccessTokenImpl(idToken, refreshToken);

		return new OAuthAuthorizationInfoImpl(loginInfo, accessToken);
	}

	// MARK: Internal Classes
	public static class TokenResponse {

		private String access_token;
		private String token_type;
		private Long expires_in;
		private String refresh_token;
		private String id_token;

		public String getAccess_token() {
			return this.access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public String getToken_type() {
			return this.token_type;
		}

		public void setToken_type(String token_type) {
			this.token_type = token_type;
		}

		public Long getExpires_in() {
			return this.expires_in;
		}

		public void setExpires_in(Long expires_in) {
			this.expires_in = expires_in;
		}

		public String getRefresh_token() {
			return this.refresh_token;
		}

		public void setRefresh_token(String refresh_token) {
			this.refresh_token = refresh_token;
		}

		public String getId_token() {
			return this.id_token;
		}

		public void setId_token(String id_token) {
			this.id_token = id_token;
		}

	}

	public static class IdTokenPayload {

		private String iss;
		private String aud;
		private Long exp;
		private Long iat;
		private String sub;// users unique id
		private String at_hash;
		private Long auth_time;

		public String getIss() {
			return this.iss;
		}

		public void setIss(String iss) {
			this.iss = iss;
		}

		public String getAud() {
			return this.aud;
		}

		public void setAud(String aud) {
			this.aud = aud;
		}

		public Long getExp() {
			return this.exp;
		}

		public void setExp(Long exp) {
			this.exp = exp;
		}

		public Long getIat() {
			return this.iat;
		}

		public void setIat(Long iat) {
			this.iat = iat;
		}

		public String getSub() {
			return this.sub;
		}

		public void setSub(String sub) {
			this.sub = sub;
		}

		public String getAt_hash() {
			return this.at_hash;
		}

		public void setAt_hash(String at_hash) {
			this.at_hash = at_hash;
		}

		public Long getAuth_time() {
			return this.auth_time;
		}

		public void setAuth_time(Long auth_time) {
			this.auth_time = auth_time;
		}

	}

}
