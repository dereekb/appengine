package com.dereekb.gae.server.auth.security.app.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.dereekb.gae.server.auth.security.app.service.AppLoginSecuritySigningService;
import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.impl.SignedEncodedLoginTokenImpl;

/**
 * {@link AppLoginSecuritySigningService} implementation.
 *
 * @author dereekb
 *
 */
public class AppLoginSecuritySigningServiceImpl
        implements AppLoginSecuritySigningService {

	public static final String DEFAULT_ALGORITHM = "HmacSHA256";

	private String hashAlg;

	public AppLoginSecuritySigningServiceImpl() {
		this(DEFAULT_ALGORITHM);
	}

	public AppLoginSecuritySigningServiceImpl(String hashAlg) {
		this.setHashAlg(hashAlg);
	}

	public static AppLoginSecuritySigningService HmacSHA256() {
		return new AppLoginSecuritySigningServiceImpl(DEFAULT_ALGORITHM);
	}

	public String getHashAlg() {
		return this.hashAlg;
	}

	public void setHashAlg(String hashAlg) {
		if (hashAlg == null) {
			throw new IllegalArgumentException("hashAlg cannot be null.");
		}

		this.hashAlg = hashAlg;
	}

	// MARK: AppLoginSecuritySigningService
	@Override
	public SignedEncodedLoginToken signToken(String secret,
	                                         String token)
	        throws IllegalArgumentException {
		String signature = this.hexSign(secret, token);
		return new SignedEncodedLoginTokenImpl(token, signature);
	}

	@Override
	public String hexSign(String secret,
	                      String token)
	        throws IllegalArgumentException {
		byte[] sig = this.byteSign(secret, token);
		return DatatypeConverter.printHexBinary(sig).toLowerCase();
	}

	@Override
	public byte[] byteSign(String secret,
	                       String token)
	        throws IllegalArgumentException {
		try {
			byte[] secretBytes = secret.getBytes("utf-8");
			SecretKeySpec keySpec = new SecretKeySpec(secretBytes, this.hashAlg);

			Mac mac = Mac.getInstance(this.hashAlg);
			mac.init(keySpec);

			byte[] accessTokenBytes = token.getBytes("utf-8");
			byte[] sig = mac.doFinal(accessTokenBytes);
			return sig;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RuntimeException("Signing service is not properly configured.", e);
		} catch (InvalidKeyException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public String toString() {
		return "AppLoginSecuritySigningServiceImpl [hashAlg=" + this.hashAlg + "]";
	}

}
