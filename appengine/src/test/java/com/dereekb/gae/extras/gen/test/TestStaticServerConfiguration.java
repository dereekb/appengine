package com.dereekb.gae.extras.gen.test;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthClientConfig;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthClientConfigImpl;

public class TestStaticServerConfiguration {

	// Development
	/**
	 * Matches the docker container hosting the nginx proxy server.
	 */
	public static final String DEVELOPMENT_PROXY_URL = "http://app-nginx:8080";

	// Facebook
	public static final OAuthClientConfig TEST_FACEBOOK_OAUTH_CONFIG = new OAuthClientConfigImpl("431391914300748",
	        "102a10dd9bfa5e2783e57a2f09b0c2ac");

	// Firebase
	public static final String FIREBASE_DATABASE_URL = "https://gae-web-app-237603.firebaseio.com";

}
