package com.dereekb.gae.test.applications.api.api.login.oauth;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.auth.security.login.oauth.OAuthService;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;

public abstract class AbstractOAuthTests extends ApiApplicationTestContext {

	private OAuthService service;

	public OAuthService getService() {
		return this.service;
	}

	public void setService(OAuthService service) {
		this.service = service;
	}

	@Test
	public void testOAuthService() {
		String url = this.service.getAuthorizationCodeRequestUrl();
		Assert.assertNotNull(url);
	}

}
