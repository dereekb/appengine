package com.dereekb.gae.test.server.auth;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenEncoderDecoderImpl;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;
import com.dereekb.gae.utilities.time.DateUtility;

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
		loginToken.setRefreshAllowed(true);

		String encodedToken = encoderDecoder.encodeLoginToken(loginToken);
		Assert.assertNotNull(encodedToken);

		LoginToken decodedToken = encoderDecoder.decodeLoginToken(encodedToken).getLoginToken();
		Assert.assertTrue(decodedToken.getLoginPointerId().equals(loginToken.getLoginPointerId()));
		Assert.assertTrue(decodedToken.getLoginId().equals(loginToken.getLoginId()));
		Assert.assertTrue(decodedToken.isRefreshAllowed());
	}

	@Test
	public void testEncodingDecodingNewUser() {
		LoginTokenEncoderDecoderImpl encoderDecoder = new LoginTokenEncoderDecoderImpl(SECRET);

		LoginTokenImpl loginToken = new LoginTokenImpl();
		loginToken.setPointerType(LoginPointerType.PASSWORD);
		loginToken.setLoginPointer("pointer");
		loginToken.setExpiration(new Date(new Date().getTime() + (60 * 1000)));
		loginToken.setIssued(new Date());

		Assert.assertTrue(loginToken.isNewUser());

		String encodedToken = encoderDecoder.encodeLoginToken(loginToken);
		Assert.assertNotNull(encodedToken);

		LoginToken decodedToken = encoderDecoder.decodeLoginToken(encodedToken).getLoginToken();
		Assert.assertTrue(decodedToken.getLoginPointerId().equals(loginToken.getLoginPointerId()));
		Assert.assertTrue(decodedToken.isNewUser());
	}

	@Test
	public void testEncodingDecodingTime() {
		LoginTokenEncoderDecoderImpl encoderDecoder = new LoginTokenEncoderDecoderImpl(SECRET);

		LoginTokenImpl loginToken = new LoginTokenImpl();
		loginToken.setPointerType(LoginPointerType.PASSWORD);
		loginToken.setLoginPointer("pointer");

		Date date = new Date();
		Long validTime = 1200000L;
		Long expectedExpireTime = date.getTime() + validTime;

		loginToken.setIssued(date);
		loginToken.setExpiration(validTime);

		Long expireTime = loginToken.getExpiration().getTime();

		// 1488072457082
		// 1488072337082
		Assert.assertTrue(expectedExpireTime.equals(expireTime));

		String encodedToken = encoderDecoder.encodeLoginToken(loginToken);
		Assert.assertNotNull(encodedToken);

		LoginToken decodedToken = encoderDecoder.decodeLoginToken(encodedToken).getLoginToken();

		// JWT rounds down to the nearest millisecond, meaning these will never
		// be accurate.
		Long roundedIssued = DateUtility.roundDateDownToSecond(loginToken.getIssued()).getTime();
		Long decodedIssued = decodedToken.getIssued().getTime();

		Assert.assertTrue(decodedIssued.equals(roundedIssued));
		Assert.assertTrue(
		        decodedToken.getExpiration().equals(DateUtility.roundDateDownToSecond(loginToken.getExpiration())));

	}

	@Test
	public void testEncodingDecodingAnonymousToken() {
		LoginTokenEncoderDecoderImpl encoderDecoder = new LoginTokenEncoderDecoderImpl(SECRET);

		LoginTokenImpl loginToken = new LoginTokenImpl();
		loginToken.setPointerType(LoginPointerType.ANONYMOUS);

		String encodedToken = encoderDecoder.encodeLoginToken(loginToken);
		Assert.assertNotNull(encodedToken);

		LoginToken decodedToken = encoderDecoder.decodeLoginToken(encodedToken).getLoginToken();
		Assert.assertTrue(decodedToken.isAnonymous());
	}

	@Test
	public void testEncodingDecodingWithNoSecret() {
		LoginTokenEncoderDecoderImpl encoder = new LoginTokenEncoderDecoderImpl(SECRET);
		LoginTokenEncoderDecoderImpl decoder = new LoginTokenEncoderDecoderImpl(null);

		LoginTokenImpl loginToken = new LoginTokenImpl();
		loginToken.setLogin(1L);
		loginToken.setLoginPointer("pointer");
		loginToken.setExpiration(new Date(new Date().getTime() + (60 * 1000)));
		loginToken.setIssued(new Date());
		loginToken.setRefreshAllowed(true);

		String encodedToken = encoder.encodeLoginToken(loginToken);
		Assert.assertNotNull(encodedToken);

		LoginToken decodedToken = decoder.decodeLoginToken(encodedToken).getLoginToken();
		Assert.assertTrue(decodedToken.getLoginPointerId().equals(loginToken.getLoginPointerId()));
		Assert.assertTrue(decodedToken.getLoginId().equals(loginToken.getLoginId()));
		Assert.assertTrue(decodedToken.isRefreshAllowed());
	}

}
