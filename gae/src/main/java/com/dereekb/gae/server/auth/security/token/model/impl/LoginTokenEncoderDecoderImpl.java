package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
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
	private static final String ANONYMOUS_KEY = "anon";
	private static final String ROLES_KEY = "roles";

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

		JwtBuilder builder = Jwts.builder().signWith(this.algorithm, this.secret);
		Claims claims = this.buildClaims(loginToken);

		return builder.setClaims(claims).compact();
	}

	private Claims buildClaims(LoginToken loginToken) {
		Claims claims = Jwts.claims();

		claims.setSubject(loginToken.getSubject());
		claims.setIssuedAt(loginToken.getIssued());
		claims.setExpiration(loginToken.getExpiration());

		claims.put(LOGIN_KEY, loginToken.getLoginId());
		claims.put(LOGIN_POINTER_KEY, loginToken.getLoginPointerId());
		claims.put(LOGIN_POINTER_TYPE_KEY, loginToken.getPointerType().getId());
		claims.put(ROLES_KEY, loginToken.getRoles());

		if (loginToken.isAnonymous()) {
			claims.put(ANONYMOUS_KEY, 1);
		}

		return claims;
	}

	// MARK: LoginTokenDecoder
	@Override
	public LoginToken decodeLoginToken(String token) throws TokenUnauthorizedException {
		LoginTokenImpl loginToken = null;

		try {
			JwtParser parsers = Jwts.parser().setSigningKey(this.secret);
			Claims claims = parsers.parseClaimsJws(token).getBody();
			loginToken = this.buildFromClaims(claims);
		} catch (MissingClaimException | SignatureException | IncorrectClaimException e) {
			throw new TokenUnauthorizedException("Could not decode token.", e);
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException();
		}

		return loginToken;
	}

	private LoginTokenImpl buildFromClaims(Claims claims) throws TokenUnauthorizedException {
		LoginTokenImpl loginToken = new LoginTokenImpl();

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

		return loginToken;
	}

}
