package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.model.context.encoded.EncodedLoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.ownership.OwnershipRoles;
import com.dereekb.gae.server.auth.security.ownership.OwnershipRolesUtility;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.utilities.data.NumberUtility;
import com.dereekb.gae.utilities.data.StringUtility;

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
	public static final String OBJECT_CONTEXT_KEY = "oc";

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

		// Encode Object Context
		EncodedLoginTokenModelContextSet contextMap = loginToken.getEncodedModelContextSet();

		if (contextMap != null) {
			Set<Integer> contextMapKeys = contextMap.getEncodedModelContextTypes();
			Set<Integer> nonEmptyKeys = new HashSet<Integer>();

			for (Integer contextMapKey : contextMapKeys) {
				String key = OBJECT_CONTEXT_KEY + contextMapKey;
				String value = contextMap.getEncodedModelTypeContext(contextMapKey);

				if (StringUtility.isEmptyString(value) == false) {
					claims.put(key, value);
					nonEmptyKeys.add(contextMapKey);
				}
			}

			if (nonEmptyKeys.isEmpty() == false) {
				claims.put(OBJECT_CONTEXT_KEY, StringUtility.joinValues(nonEmptyKeys));
			}
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

		// Decode Object Context
		String objectContexts = claims.get(OBJECT_CONTEXT_KEY, String.class);

		if (objectContexts != null) {
			List<Integer> keys = NumberUtility.integersFromString(objectContexts);

			Map<Integer, String> contextMap = new HashMap<Integer, String>();

			for (Integer key : keys) {
				String claimsKey = OBJECT_CONTEXT_KEY + key;
				String encodedObjectContexts = claims.get(claimsKey, String.class);

				if (StringUtility.isEmptyString(encodedObjectContexts)) {
					throw new TokenUnauthorizedException("Invalid token at object context: " + key);
				} else {
					contextMap.put(key, encodedObjectContexts);
				}
			}
		}
	}

	protected OwnershipRoles decodeOwnershipRoles(String encodedOwnershipRoles) {
		return OwnershipRolesUtility.decodeRoles(encodedOwnershipRoles);
	}

}
