package com.dereekb.gae.test.server.auth;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.SignatureConfiguration;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenEncoderDecoderImpl;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;
import com.dereekb.gae.server.auth.security.token.model.impl.SignatureConfigurationImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.time.DateUtility;

public class LoginTokenTests {

	private static final String SECRET = "3zRepP7IfiCCDa7EE5aCradtF94giUGizNr9yb8E/QU=";
	private static final SignatureConfiguration SIGNATURE = new SignatureConfigurationImpl(SECRET);

	@Test
	public void testEncodingDecoding() {
		LoginTokenEncoderDecoderImpl encoderDecoder = new LoginTokenEncoderDecoderImpl(SIGNATURE);

		LoginTokenImpl loginToken = new LoginTokenImpl();
		loginToken.setLogin(1L);
		loginToken.setLoginPointer("pointer");
		loginToken.setExpiration(new Date(new Date().getTime() + (60 * 1000)));
		loginToken.setIssued(new Date());
		loginToken.setRefreshAllowed(true);

		String encodedToken = encoderDecoder.encodeLoginToken(loginToken);
		assertNotNull(encodedToken);

		LoginToken decodedToken = encoderDecoder.decodeLoginToken(encodedToken).getLoginToken();
		assertTrue(decodedToken.getLoginPointerId().equals(loginToken.getLoginPointerId()));
		assertTrue(decodedToken.getLoginId().equals(loginToken.getLoginId()));
		assertTrue(decodedToken.isRefreshAllowed());
	}

	@Test
	public void testEncodingDecodingNewUser() {
		LoginTokenEncoderDecoderImpl encoderDecoder = new LoginTokenEncoderDecoderImpl(SIGNATURE);

		LoginTokenImpl loginToken = new LoginTokenImpl();
		loginToken.setPointerType(LoginPointerType.PASSWORD);
		loginToken.setLoginPointer("pointer");
		loginToken.setExpiration(new Date(new Date().getTime() + (60 * 1000)));
		loginToken.setIssued(new Date());

		assertTrue(loginToken.isNewUser());

		String encodedToken = encoderDecoder.encodeLoginToken(loginToken);
		assertNotNull(encodedToken);

		LoginToken decodedToken = encoderDecoder.decodeLoginToken(encodedToken).getLoginToken();
		assertTrue(decodedToken.getLoginPointerId().equals(loginToken.getLoginPointerId()));
		assertTrue(decodedToken.isNewUser());
	}

	@Test
	public void testEncodingDecodingTime() {
		LoginTokenEncoderDecoderImpl encoderDecoder = new LoginTokenEncoderDecoderImpl(SIGNATURE);

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
		assertTrue(expectedExpireTime.equals(expireTime));

		String encodedToken = encoderDecoder.encodeLoginToken(loginToken);
		assertNotNull(encodedToken);

		LoginToken decodedToken = encoderDecoder.decodeLoginToken(encodedToken).getLoginToken();

		// JWT rounds down to the nearest millisecond, meaning these will never
		// be accurate.
		Long roundedIssued = DateUtility.roundDateDownToSecond(loginToken.getIssued()).getTime();
		Long decodedIssued = decodedToken.getIssued().getTime();

		assertTrue(decodedIssued.equals(roundedIssued));
		assertTrue(
		        decodedToken.getExpiration().equals(DateUtility.roundDateDownToSecond(loginToken.getExpiration())));

	}

	@Test
	public void testEncodingDecodingAnonymousToken() {
		LoginTokenEncoderDecoderImpl encoderDecoder = new LoginTokenEncoderDecoderImpl(SIGNATURE);

		LoginTokenImpl loginToken = new LoginTokenImpl();
		loginToken.setPointerType(LoginPointerType.ANONYMOUS);

		String encodedToken = encoderDecoder.encodeLoginToken(loginToken);
		assertNotNull(encodedToken);

		LoginToken decodedToken = encoderDecoder.decodeLoginToken(encodedToken).getLoginToken();
		assertTrue(decodedToken.isAnonymous());
	}

	@Test
	public void testEncodingDecodingWithNoSecret() {
		LoginTokenEncoderDecoderImpl encoder = new LoginTokenEncoderDecoderImpl(SIGNATURE);
		LoginTokenEncoderDecoderImpl decoder = new LoginTokenEncoderDecoderImpl((SignatureConfiguration) null);

		LoginTokenImpl loginToken = new LoginTokenImpl();
		loginToken.setLogin(1L);
		loginToken.setLoginPointer("pointer");
		loginToken.setExpiration(new Date(new Date().getTime() + (60 * 1000)));
		loginToken.setIssued(new Date());
		loginToken.setRefreshAllowed(true);

		String encodedToken = encoder.encodeLoginToken(loginToken);
		assertNotNull(encodedToken);

		LoginToken decodedToken = decoder.decodeLoginToken(encodedToken).getLoginToken();
		assertTrue(decodedToken.getLoginPointerId().equals(loginToken.getLoginPointerId()));
		assertTrue(decodedToken.getLoginId().equals(loginToken.getLoginId()));
		assertTrue(decodedToken.isRefreshAllowed());
	}

	@Test
	public void testEncodingAndDecodingWithStringAppIdMatches() {
		LoginTokenEncoderDecoderImpl encoder = new LoginTokenEncoderDecoderImpl(SIGNATURE);
		LoginTokenEncoderDecoderImpl decoder = new LoginTokenEncoderDecoderImpl((SignatureConfiguration) null);

		String invalidAppId = "INVALID_APP_ID";

		LoginTokenImpl loginToken = new LoginTokenImpl();
		loginToken.setApp(invalidAppId);
		loginToken.setPointerType(LoginPointerType.SYSTEM);
		loginToken.setExpiration(new Date(new Date().getTime() + (60 * 1000)));
		loginToken.setIssued(new Date());
		loginToken.setRefreshAllowed(true);

		String encodedToken = encoder.encodeLoginToken(loginToken);
		assertNotNull(encodedToken);

		LoginToken decodedToken = decoder.decodeLoginToken(encodedToken).getLoginToken();
		assertTrue(decodedToken.getApp().equals(invalidAppId), "Should atleast match...");

	}

	@Test
	public void testLoginPointerValueOf()
	{
		for (LoginPointerType type : ListUtility.toList(LoginPointerType.values())) {
			int code = type.code;
			LoginPointerType value = LoginPointerType.valueOf(code);
			assertTrue(value.equals(type), "Type should have matched valueOf.");
		}
	}

}
