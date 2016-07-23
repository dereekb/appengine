package com.dereekb.gae.test.applications.api.api.login.oauth;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthAuthorizationInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginInfo;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthServiceManager;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthAuthorizationInfoImpl;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthLoginInfoImpl;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.web.api.auth.controller.oauth.OAuthLoginController;

/**
 * Tests the OAuth login controller and server manager.
 *
 * @author dereekb
 *
 */
public class OAuthApiControllerTest extends ApiApplicationTestContext {

	@Autowired
	private OAuthServiceManager serverManager;

	// @Autowired
	private OAuthLoginController loginController;

	// MARK: OAuthServer tests
	@Test
	public void testOAuthLogin() {

		OAuthLoginInfo loginInfo = new OAuthLoginInfoImpl(LoginPointerType.OAUTH_GOOGLE, "abcde", "name", "test@test.com");
		OAuthAuthorizationInfo info = new OAuthAuthorizationInfoImpl(loginInfo);

		LoginPointer loginPointer = this.serverManager.login(info);
		Assert.assertNotNull(loginPointer);
		Assert.assertNotNull(loginPointer.getIdentifier());
	}

}
