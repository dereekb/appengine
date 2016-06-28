package com.dereekb.gae.test.server.auth;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenEncoderDecoderImpl;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;


public class LoginTokenTests {

	private static final String SECRET = "3zRepP7IfiCCDa7EE5aCradtF94giUGizNr9yb8E/QU=";

	@Test
	public void testEncodingDecoding() {
		LoginTokenEncoderDecoderImpl encoderDecoder = new LoginTokenEncoderDecoderImpl(SECRET);

		LoginTokenImpl loginToken = new LoginTokenImpl();
		loginToken.setLogin(1L);
		loginToken.setLoginPointer("pointer");
		loginToken.setExpiration(new Date(new Date().getTime() + (60 * 1000)));
		loginToken.setIssued(new Date());

		String encodedToken = encoderDecoder.encodeLoginToken(loginToken);
		Assert.assertNotNull(encodedToken);

		LoginToken decodedToken = encoderDecoder.decodeLoginToken(encodedToken);
		Assert.assertTrue(decodedToken.getLoginPointer().equals(loginToken.getLoginPointer()));
		Assert.assertTrue(decodedToken.getLogin().equals(loginToken.getLogin()));

	}

}
