package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;

import com.dereekb.gae.server.auth.security.ownership.OwnershipRoles;
import com.dereekb.gae.server.auth.security.ownership.OwnershipRolesUtility;
import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
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
public abstract class AbstractLoginTokenEncoderDecoder
        implements LoginTokenEncoderDecoder {

	private static final SignatureAlgorithm DEFAULT_ALGORITHM = SignatureAlgorithm.HS256;

	private String secret;
	private SignatureAlgorithm algorithm;

	public AbstractLoginTokenEncoderDecoder(String secret) {
		this(secret, DEFAULT_ALGORITHM);
	}

	public AbstractLoginTokenEncoderDecoder(String secret, SignatureAlgorithm algorithm) {
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
	public String encodeLoginToken(LoginToken loginToken) {
		Claims claims = this.buildClaims(loginToken);
		return this.encodeAndCompactClaims(claims);
	}

	protected final String encodeAndCompactClaims(Claims claims) {
		JwtBuilder builder = Jwts.builder().signWith(this.algorithm, this.secret);
		return builder.setClaims(claims).compact();
	}

	protected Claims buildClaims(LoginToken loginToken) {
		Claims claims = Jwts.claims();

		claims.setSubject(loginToken.getSubject());
		claims.setIssuedAt(loginToken.getIssued());
		claims.setExpiration(loginToken.getExpiration());

		this.appendClaimsComponents(loginToken, claims);

		return claims;
	}

	protected abstract void appendClaimsComponents(LoginToken loginToken,
	                                               Claims claims);

	protected String encodeOwnershipRoles(OwnershipRoles ownershipRoles) {
		return OwnershipRolesUtility.encodeRoles(ownershipRoles);
	}

	// MARK: LoginTokenDecoder
	@Override
	public DecodedLoginToken decodeLoginToken(String token) throws TokenExpiredException, TokenUnauthorizedException {
		DecodedLoginToken loginToken = null;

		try {
			Claims claims = this.parseClaims(token);
			loginToken = this.buildFromClaims(token, claims);
		} catch (MissingClaimException | SignatureException | IncorrectClaimException e) {
			throw new TokenUnauthorizedException("Could not decode token.", e);
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException();
		}

		return loginToken;
	}

	protected final Claims parseClaims(String token) throws TokenExpiredException, TokenUnauthorizedException {
		JwtParser parsers = Jwts.parser().setSigningKey(this.secret);
		return parsers.parseClaimsJws(token).getBody();
	}

	protected DecodedLoginToken buildFromClaims(String token,
	                                            Claims claims)
	        throws TokenUnauthorizedException {
		DecodedLoginTokenImpl loginToken = new DecodedLoginTokenImpl(token);
		this.initFromClaims(loginToken, claims);
		return loginToken;
	}

	protected void initFromClaims(LoginTokenImpl loginToken,
	                              Claims claims)
	        throws TokenUnauthorizedException {
		String subject = claims.getSubject();
		Date expiration = claims.getExpiration();
		Date issued = claims.getIssuedAt();

		loginToken.setSubject(subject);
		loginToken.setExpiration(expiration);
		loginToken.setIssued(issued);
	}

}
