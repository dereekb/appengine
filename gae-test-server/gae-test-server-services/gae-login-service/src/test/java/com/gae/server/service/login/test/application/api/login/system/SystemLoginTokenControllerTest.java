package com.gae.server.service.login.test.application.api.login.system;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MvcResult;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.applications.api.api.login.LoginApiTestUtility;

public class SystemLoginTokenControllerTest extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("loginTokenService")
	private LoginTokenService<LoginToken> loginTokenService;

	// MARK: Mock Test
	@Test
	public void testMakeSystemLoginToken() throws Exception {

		String appId = "1234";
		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);

		EncodedLoginToken encodedLoginToken = testUtility.createSystemLoginToken(appId);
		String tokenString = encodedLoginToken.getEncodedLoginToken();

		Assert.assertTrue(tokenString.isEmpty() == false);

		DecodedLoginToken<LoginToken> decodedLoginToken = this.loginTokenService.decodeLoginToken(tokenString);
		LoginToken loginToken = decodedLoginToken.getLoginToken();

		Assert.assertTrue(loginToken.getPointerType() == LoginPointerType.SYSTEM);
		Assert.assertTrue(loginToken.getApp().equals(appId));
	}

	@Test
	public void testMakeSystemLoginTokenWithNoAppIdFails() throws Exception {
		String appId = "";
		LoginApiTestUtility testUtility = new LoginApiTestUtility(this);

		MvcResult result = testUtility.sendCreateSystemLoginToken(appId);
		Assert.assertTrue(result.getResponse().getStatus() != 200);
	}

}
