package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.ownership.OwnershipRoles;
import com.dereekb.gae.server.auth.security.ownership.OwnershipRolesUtility;
import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenDecoder;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoder;

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
 * {@link LoginTokenEncoder} and {@link LoginTokenDecoder}
 *
 * @author dereekb
 *
 */
public class LoginTokenEncoderDecoderImpl
        implements LoginTokenEncoder, LoginTokenDecoder {

	private static final String LOGIN_KEY = "lgn";
	private static final String LOGIN_POINTER_KEY = "ptr";
	private static final String LOGIN_POINTER_TYPE_KEY = "pt";
	private static final String OWNERSHIP_KEY = "o";
	private static final String ANONYMOUS_KEY = "anon";
	private static final String ROLES_KEY = "r";

	private static final SignatureAlgorithm DEFAULT_ALGORITHM = SignatureAlgorithm.HS256;

	private String secret;
	private SignatureAlgorithm algorithm;

	public LoginTokenEncoderDecoderImpl(String secret) {
		this(secret, null);
	}

	public LoginTokenEncoderDecoderImpl(String secret, SignatureAlgorithm algorithm) {
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
			algorithm = DEFAULT_ALGORITHM;
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

		claims.put(LOGIN_KEY, loginToken.getLoginId());
		claims.put(LOGIN_POINTER_KEY, loginToken.getLoginPointerId());
		claims.put(LOGIN_POINTER_TYPE_KEY, loginToken.getPointerType().getId());
		claims.put(ROLES_KEY, loginToken.getRoles());
		claims.put(OWNERSHIP_KEY, this.encodeOwnershipRoles(loginToken.getOwnershipRoles()));

		if (loginToken.isAnonymous()) {
			claims.put(ANONYMOUS_KEY, 1);
		}

		return claims;
	}

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

		Number loginNumber = claims.get(LOGIN_KEY, Number.class);
		Long login = null;

		if (loginNumber != null) {
			login = loginNumber.longValue();
		}

		String loginPointer = claims.get(LOGIN_POINTER_KEY, String.class);
		Number rolesNumber = claims.get(ROLES_KEY, Number.class);
		Number typeNumber = claims.get(LOGIN_POINTER_TYPE_KEY, Number.class);
		Long roles = null;
		Integer type = null;

		if (rolesNumber != null) {
			roles = rolesNumber.longValue();
		}

		if (typeNumber != null) {
			type = typeNumber.intValue();
		}

		String subject = claims.getSubject();

		boolean anonymous = false;

		if (claims.containsKey(ANONYMOUS_KEY)) {
			anonymous = (claims.get(ANONYMOUS_KEY, Number.class).intValue() == 1);
		}

		Date expiration = claims.getExpiration();
		Date issued = claims.getIssuedAt();

		// Login might not always be present.
		if (expiration == null || issued == null) {
			if (anonymous == false && loginPointer == null) {
				throw new TokenUnauthorizedException("Invalid token.");
			}
		}

		loginToken.setLogin(login);
		loginToken.setLoginPointer(loginPointer);
		loginToken.setRoles(roles);

		if (type != null) {
			loginToken.setPointerType(LoginPointerType.valueOf(type));
		}

		loginToken.setSubject(subject);
		loginToken.setAnonymous(anonymous);
		loginToken.setExpiration(expiration);
		loginToken.setIssued(issued);

		String encodedOwnershipRoles = claims.get(OWNERSHIP_KEY, String.class);

		if (encodedOwnershipRoles != null) {
			OwnershipRoles ownershipRoles = this.decodeOwnershipRoles(encodedOwnershipRoles);
			loginToken.setOwnershipRoles(ownershipRoles);
		}

	}

	protected OwnershipRoles decodeOwnershipRoles(String encodedOwnershipRoles) {
		return OwnershipRolesUtility.decodeRoles(encodedOwnershipRoles);
	}

}
