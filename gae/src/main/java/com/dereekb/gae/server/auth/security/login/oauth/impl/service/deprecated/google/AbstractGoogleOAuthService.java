package com.dereekb.gae.server.auth.security.login.oauth.impl.service.google;

import java.util.List;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthAccessToken;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthClientConfig;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthService;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 * Abstract {@link OAuthService} implementation that uses the Google Client API.
 *
 * @author dereekb
 *
 */
@Deprecated
public abstract class AbstractGoogleOAuthService extends AbstractOAuthService
        implements OAuthService {

	private String authorizationGrantType = "authorization_code";

	private boolean allowRefreshCredentials = false;

	public AbstractGoogleOAuthService(String serverLoginTokenUrl,
	        String serverAuthTokenUrl,
	        OAuthClientConfig clientConfig,
	        List<String> scopes) throws IllegalArgumentException {
		super(serverLoginTokenUrl, serverAuthTokenUrl, clientConfig, scopes);
	}

	public AbstractGoogleOAuthService(String serverLoginTokenUrl,
	        String serverAuthTokenUrl,
	        OAuthClientConfig clientConfig,
	        String state,
	        List<String> scopes) throws IllegalArgumentException {
		super(serverLoginTokenUrl, serverAuthTokenUrl, clientConfig, state, scopes);
	}

	public String getAuthorizationGrantType() {
		return this.authorizationGrantType;
	}

	public void setAuthorizationGrantType(String authorizationGrantType) {
		this.authorizationGrantType = authorizationGrantType;
	}

	public boolean isAllowRefreshCredentials() {
		return this.allowRefreshCredentials;
	}

	public void setAllowRefreshCredentials(boolean allowRefreshCredentials) {
		this.allowRefreshCredentials = allowRefreshCredentials;
	}

	// MARK: OAuthLoginService
	@Override
	public String getAuthorizationCodeRequestUrl() {
		GoogleAuthorizationCodeRequestUrl request = this.getAuthorizationCodeRequest();

		if (this.getState() != null) {
			request = request.setState(this.getState());
		}

		String url = request.build();
		return url;
	}

	public GoogleAuthorizationCodeRequestUrl getAuthorizationCodeRequest() {
		OAuthClientConfig config = this.getClientConfig();
		return new GoogleAuthorizationCodeRequestUrl(this.getServerLoginTokenUrl(), config.getClientId(),
		        this.getLoginTokenRedirectUrl(), this.getScopes());
	}

	// MARK: Internal
	protected BasicAuthentication getClientServerAuthentication() {
		OAuthClientConfig config = this.getClientConfig();
		return new BasicAuthentication(config.getClientId(), config.getClientSecret());
	}

	protected AuthorizationCodeTokenRequest makeAuthorizationCodeTokenRequest(String authCode) {

		GenericUrl serverUrl = new GenericUrl(this.getServerAuthTokenUrl());
		BasicAuthentication clientServerAuthentication = this.getClientServerAuthentication();

		return new AuthorizationCodeTokenRequest(new NetHttpTransport(), new JacksonFactory(), serverUrl, authCode)
		        .setRedirectUri(this.getAuthTokenRedirectUrl()).setClientAuthentication(clientServerAuthentication)
		        .setGrantType(this.authorizationGrantType);
	}

	/**
	 * Automatically builds a credential using the {@link OAuthAccessToken}. If
	 * it contains a refresh token, a full {@link Credential} will be generated
	 * using {@link #buildFullCredential()}
	 *
	 * @param accessToken
	 * @return
	 */
	protected Credential buildCredential(OAuthAccessToken token) {
		String accessToken = token.getAccessToken();
		String refreshToken = token.getRefreshToken();
		Long expiration = token.getExpiresIn();

		Credential credential = null;

		if (this.allowRefreshCredentials && refreshToken != null) {
			credential = this.buildFullCredential(accessToken, refreshToken, expiration);
		} else {
			credential = this.buildAccessTokenCredential(accessToken, expiration);
		}

		return credential;
	}

	protected Credential buildAccessTokenCredential(String accessToken,
	                                                Long expiration) {
		return new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(accessToken)
		        .setExpiresInSeconds(expiration);
	}

	protected Credential buildFullCredential(String accessToken,
	                                         String refreshToken,
	                                         Long expiration) {
		GenericUrl serverUrl = new GenericUrl(this.getServerAuthTokenUrl());
		BasicAuthentication clientServerAuthentication = this.getClientServerAuthentication();

		Credential credential = new Credential.Builder(BearerToken.authorizationHeaderAccessMethod())
		        .setTransport(new NetHttpTransport()).setJsonFactory(new JacksonFactory()).setTokenServerUrl(serverUrl)
		        .setClientAuthentication(clientServerAuthentication).build();

		credential.setAccessToken(accessToken);
		credential.setRefreshToken(refreshToken);
		credential.setExpiresInSeconds(expiration);

		return credential;
	}

}
