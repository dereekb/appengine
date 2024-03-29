package com.dereekb.gae.test.app.server.auth.security.login.oauth.apple;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.security.PrivateKey;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthCode;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthAuthCodeImpl;
import com.dereekb.gae.server.auth.security.login.oauth.impl.service.apple.SignInWithAppleOAuthConfig;
import com.dereekb.gae.server.auth.security.login.oauth.impl.service.apple.SignInWithAppleOAuthService;
import com.dereekb.gae.server.auth.security.login.oauth.impl.service.apple.impl.SignInWithAppleOAuthConfigImpl;
import com.dereekb.gae.utilities.security.pem.PrivateKeyProvider;
import com.dereekb.gae.utilities.security.pem.impl.FilePrivateKeyProviderImpl;

/**
 * {@link SignInWithAppleOAuthService} tests.
 *
 * @author dereekb
 *
 */
public class SignInWithAppleOAuthServiceTest {

	/**
	 * Manually tests the auth service.
	 * <p>
	 * Replace the authCode with a new code generated by the proper frontend for each test.
	 */
	@Test
	@Disabled
	public void manuallyTestAuthService() {

		String path = "src/test/resources/authkey_QHLNJWPJ2Z.p8";

		File file = new File(path);
		String privateKeyPath = file.getAbsolutePath();

		PrivateKeyProvider provider = new FilePrivateKeyProviderImpl(privateKeyPath);
		PrivateKey key = provider.getPrivateKey();
		assertNotNull(key);

		String teamId = "BLXT66N26B";
		String clientId = "com.gethapier.app";
		String keyId = "QHLNJWPJ2Z";

		SignInWithAppleOAuthConfig config = new SignInWithAppleOAuthConfigImpl(teamId, clientId, keyId, provider);
		SignInWithAppleOAuthService service = new SignInWithAppleOAuthService(config);

		String authCode = "cd4ac8d2b59ff47ceb6b51b93d54ea5b0.0.nruyu.E6K6uFOeeSCBbLNV0GdzMA";

		OAuthAuthCode code = new OAuthAuthCodeImpl(authCode);
		OAuthAuthorizationInfo authorizationInfo = service.processAuthorizationCode(code);

		String id = authorizationInfo.getLoginInfo().getId();
		assertNotNull(id);
	}

}
