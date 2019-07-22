package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;

import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.JwtStringDencoder;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenDecoder;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoder;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.model.SignatureConfiguration;
import com.dereekb.gae.utilities.time.DateUtility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * Abstract {@link LoginTokenEncoder} and {@link LoginTokenDecoder}
 * implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractBasicLoginTokenImplEncoderDecoder<T extends LoginTokenImpl>
        implements LoginTokenEncoderDecoder<T> {

	public static final String REFRESH_KEY = "e";
	public static final String APP_KEY = "app";

	public static final Integer DEFAULT_EXPIRATION_MINUTES = 15;

	public static final SignatureAlgorithm DEFAULT_ALGORITHM = SignatureConfigurationImpl.DEFAULT_ALGORITHM;

	private JwtStringDencoder tokenStringDencoder;

	@Deprecated
	public AbstractBasicLoginTokenImplEncoderDecoder(String secret) {
		this(secret, DEFAULT_ALGORITHM);
	}

	@Deprecated
	public AbstractBasicLoginTokenImplEncoderDecoder(String secret, SignatureAlgorithm algorithm) {
		this(new SignatureConfigurationImpl(secret, algorithm));
	}

	public AbstractBasicLoginTokenImplEncoderDecoder() {
		this((SignatureConfiguration) null);
	}

	public AbstractBasicLoginTokenImplEncoderDecoder(SignatureConfiguration signature) {
		this.resetTokenStringDencoder(signature);
	}

	public JwtStringDencoder getTokenStringDencoder() {
		return this.tokenStringDencoder;
	}

	private void resetTokenStringDencoder(SignatureConfiguration signature) {
		if (signature == null) {
			this.tokenStringDencoder = UnsignedJwtStringDecoder.SINGLETON;
		} else {
			this.tokenStringDencoder = new JwtStringDencoderImpl(signature);
		}
	}

	// MARK: LoginTokenEncoder
	@Override
	public String encodeLoginToken(T loginToken) {
		Claims claims = this.buildClaims(loginToken);
		return this.encodeAndCompactClaims(claims);
	}

	protected final String encodeAndCompactClaims(Claims claims) {
		return this.tokenStringDencoder.encodeClaims(claims);
	}

	protected final Claims buildClaims(T loginToken) {
		Claims claims = Jwts.claims();

		claims.setSubject(loginToken.getSubject());
		claims.setIssuedAt(loginToken.getIssued());

		Date expiration = loginToken.getExpiration();

		if (expiration == null) {
			expiration = this.makeDefaultExpirationDate();
		}

		claims.setExpiration(expiration);

		String app = loginToken.getApp();
		if (app != null) {
			claims.put(APP_KEY, app);
		}

		if (loginToken.isRefreshAllowed()) {
			claims.put(REFRESH_KEY, true);
		}

		this.appendClaimsComponents(loginToken, claims);

		return claims;
	}

	protected abstract void appendClaimsComponents(T loginToken,
	                                               Claims claims);

	// MARK: LoginTokenDecoder
	@Override
	public DecodedLoginToken<T> decodeLoginToken(String token)
	        throws TokenExpiredException,
	            TokenUnauthorizedException {
		Claims claims = null;

		try {
			claims = this.parseClaims(token);
			return this.decodeLoginTokenFromClaims(token, claims);
		} catch (MissingClaimException | SignatureException | IncorrectClaimException e) {
			throw new TokenUnauthorizedException("Could not decode token.", e);
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException();
		}
	}

	@Override
	public final DecodedLoginTokenImpl<T> decodeLoginTokenFromClaims(Claims claims)
	        throws TokenExpiredException,
	            TokenUnauthorizedException {
		DecodedLoginTokenImpl<T> decodedToken = this.decodeLoginTokenFromClaims("", claims);
		String encoded = this.encodeLoginToken(decodedToken.getLoginToken());
		return new DecodedLoginTokenImpl<T>(encoded, decodedToken.getLoginToken(), claims);
	}

	@Override
	public final DecodedLoginTokenImpl<T> decodeLoginTokenFromClaims(String encodedToken,
	                                                                 Claims claims)
	        throws TokenExpiredException,
	            TokenUnauthorizedException {
		T loginToken = this.buildFromClaims(claims);
		return new DecodedLoginTokenImpl<T>(encodedToken, loginToken, claims);
	}

	protected final Claims parseClaims(String token) throws TokenExpiredException, TokenUnauthorizedException {
		return this.tokenStringDencoder.decodeTokenClaims(token);
	}

	protected T buildFromClaims(Claims claims) throws TokenUnauthorizedException {
		T loginToken = this.makeToken();
		this.initFromClaims(loginToken, claims);
		return loginToken;
	}

	protected void initFromClaims(T loginToken,
	                              Claims claims)
	        throws TokenUnauthorizedException {
		String subject = claims.getSubject();
		Date expiration = claims.getExpiration();
		Date issued = claims.getIssuedAt();

		String app = claims.get(APP_KEY, String.class);
		Boolean refreshAllowed = claims.get(REFRESH_KEY, Boolean.class);

		loginToken.setApp(app);
		loginToken.setSubject(subject);
		loginToken.setExpiration(expiration);
		loginToken.setIssued(issued);

		if (refreshAllowed != null) {
			loginToken.setRefreshAllowed(refreshAllowed);
		}
	}

	// MARK: Internal
	private Date makeDefaultExpirationDate() {
		Date date = DateUtility.getDateIn(DateUtility.timeInMinutes(DEFAULT_EXPIRATION_MINUTES));
		return date;
	}

}
