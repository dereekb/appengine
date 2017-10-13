package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.ownership.OwnershipRoles;
import com.dereekb.gae.server.auth.security.ownership.OwnershipRolesUtility;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * {@link AbstractBasicLoginTokenImplEncoderDecoder} implementation for
 * {@link LoginTokenImpl}.
 *
 * @author dereekb
 * @see LoginTokenEncoderDecoderImpl for implementation.
 */
public abstract class AbstractLoginTokenImplEncoderDecoder<T extends LoginTokenImpl> extends AbstractBasicLoginTokenImplEncoderDecoder<T> {

	public static final String LOGIN_KEY = "lgn";
	public static final String LOGIN_POINTER_KEY = "ptr";
	public static final String LOGIN_POINTER_TYPE_KEY = "pt";
	public static final String OWNERSHIP_KEY = "o";

	@Deprecated
	public static final String ANONYMOUS_KEY = "anon";

	public static final String ROLES_KEY = "r";

	public AbstractLoginTokenImplEncoderDecoder(String secret) {
		super(secret);
	}

	public AbstractLoginTokenImplEncoderDecoder(String secret, SignatureAlgorithm algorithm) {
		super(secret, algorithm);
	}

	// MARK: LoginTokenEncoder
	@Override
	protected void appendClaimsComponents(T loginToken,
	                                      Claims claims) {
		claims.put(LOGIN_KEY, loginToken.getLoginId());
		claims.put(LOGIN_POINTER_KEY, loginToken.getLoginPointerId());
		claims.put(LOGIN_POINTER_TYPE_KEY, loginToken.getPointerType().getId());

		if (loginToken.getRoles() != LoginTokenImpl.DEFAULT_ROLES) {
			claims.put(ROLES_KEY, loginToken.getRoles());
		}

		String ownershipRoles = this.encodeOwnershipRoles(loginToken.getOwnershipRoles());

		if (ownershipRoles.isEmpty() == false) {
			claims.put(OWNERSHIP_KEY, ownershipRoles);
		}
	}

	@Override
	protected String encodeOwnershipRoles(OwnershipRoles ownershipRoles) {
		return OwnershipRolesUtility.encodeRoles(ownershipRoles);
	}

	// MARK: LoginTokenDecoder
	@Override
	protected void initFromClaims(T loginToken,
	                              Claims claims)
	        throws TokenUnauthorizedException {
		super.initFromClaims(loginToken, claims);

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

		if (type != null) {
			loginToken.setPointerType(LoginPointerType.valueOf(type));
		}

		Date expiration = loginToken.getExpiration();
		Date issued = loginToken.getIssued();

		// Must have expiration and issue times.
		if (expiration == null || issued == null) {
			throw new TokenUnauthorizedException("Invalid token.");
		}

		loginToken.setLogin(login);
		loginToken.setLoginPointer(loginPointer);
		loginToken.setRoles(roles);

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
