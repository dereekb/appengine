package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;

import com.dereekb.gae.server.auth.security.ownership.OwnershipRoles;
import com.dereekb.gae.server.auth.security.ownership.OwnershipRolesUtility;
import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenDecoder;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoder;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoderDecoder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
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

	private static final SignatureAlgorithm DEFAULT_ALGORITHM = SignatureAlgorithm.HS256;

	private String secret;
	private SignatureAlgorithm algorithm;

	public AbstractBasicLoginTokenImplEncoderDecoder(String secret) {
		this(secret, DEFAULT_ALGORITHM);
	}

	public AbstractBasicLoginTokenImplEncoderDecoder(String secret, SignatureAlgorithm algorithm) {
		this.setSecret(secret);
		this.setAlgorithm(algorithm);
	}

	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) throws IllegalArgumentException {
		if (secret == null || secret.isEmpty()) {
			throw new IllegalArgumentException("Secret cannot be null or empty.");
		}

		this.secret = secret;
	}

	public SignatureAlgorithm getAlgorithm() {
		return this.algorithm;
	}

	public void setAlgorithm(SignatureAlgorithm algorithm) {
		if (algorithm == null) {
			throw new IllegalArgumentException("Algorithm cannot be null.");
		}

		this.algorithm = algorithm;
	}

	// MARK: LoginTokenEncoder
	@Override
	public String encodeLoginToken(T loginToken) {
		Claims claims = this.buildClaims(loginToken);
		return this.encodeAndCompactClaims(claims);
	}

	protected final String encodeAndCompactClaims(Claims claims) {
		JwtBuilder builder = Jwts.builder().signWith(this.algorithm, this.secret);
		return builder.setClaims(claims).compact();
	}

	protected final Claims buildClaims(T loginToken) {
		Claims claims = Jwts.claims();

		claims.setSubject(loginToken.getSubject());
		claims.setIssuedAt(loginToken.getIssued());
		claims.setExpiration(loginToken.getExpiration());

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

	protected String encodeOwnershipRoles(OwnershipRoles ownershipRoles) {
		return OwnershipRolesUtility.encodeRoles(ownershipRoles);
	}

	// MARK: LoginTokenDecoder
	@Override
	public DecodedLoginToken<T> decodeLoginToken(String token)
	        throws TokenExpiredException,
	            TokenUnauthorizedException {
		T loginToken = null;

		try {
			Claims claims = this.parseClaims(token);
			loginToken = this.buildFromClaims(token, claims);
		} catch (MissingClaimException | SignatureException | IncorrectClaimException e) {
			throw new TokenUnauthorizedException("Could not decode token.", e);
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException();
		}

		return new DecodedLoginTokenImpl<T>(token, loginToken);
	}

	protected final Claims parseClaims(String token) throws TokenExpiredException, TokenUnauthorizedException {
		JwtParser parsers = Jwts.parser().setSigningKey(this.secret);
		return parsers.parseClaimsJws(token).getBody();
	}

	protected T buildFromClaims(String token,
	                            Claims claims)
	        throws TokenUnauthorizedException {
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

}
