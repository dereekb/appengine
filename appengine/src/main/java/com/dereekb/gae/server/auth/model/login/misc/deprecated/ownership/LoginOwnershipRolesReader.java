package com.dereekb.gae.server.auth.model.login.misc.ownership;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.security.deprecated.ownership.impl.OwnershipRolesImpl;
import com.dereekb.gae.server.auth.security.deprecated.ownership.source.OwnershipRolesReader;
import com.googlecode.objectify.Key;

/**
 * Login Ownership Key.
 *
 * @author dereekb
 *
 */
@Deprecated
public class LoginOwnershipRolesReader
        implements OwnershipRolesReader<Login> {

	public static final String DEFAULT_LOGIN_OWNER_FORMAT = "Lo%s";

	private String loginOwnerFormat;

	public LoginOwnershipRolesReader() {
		this(DEFAULT_LOGIN_OWNER_FORMAT);
	}

	public LoginOwnershipRolesReader(String loginOwnerFormat) throws IllegalArgumentException {
		this.setLoginOwnerFormat(loginOwnerFormat);
	}

	public String getLoginOwnerFormat() {
		return this.loginOwnerFormat;
	}

	public void setLoginOwnerFormat(String loginOwnerFormat) throws IllegalArgumentException {
		if (loginOwnerFormat == null) {
			throw new IllegalArgumentException("Format cannot be null.");
		}

		this.loginOwnerFormat = loginOwnerFormat;
	}

	// MARK: OwnershipRolesReader
	@Override
	public OwnershipRolesImpl readRoles(Login input) {
		return this.readRoles(input.getIdentifier());
	}

	public OwnershipRolesImpl readRoles(Key<Login> input) {
		Long identifier = null;

		if (input != null) {
			identifier = input.getId();
		}

		return this.readRoles(identifier);
	}

	protected OwnershipRolesImpl readRoles(Long identifier) {
		OwnershipRolesImpl roles = new OwnershipRolesImpl();

		if (identifier != null) {
			String ownerId = this.makeOwnerId(identifier);
			roles.setOwnerId(ownerId);
		}

		return roles;
	}

	/**
	 *
	 * @param login
	 *            {@link Login}. Never {@code null}.
	 * @return {@link String}. Never {@code null}.
	 */
	public String makeOwnerId(Login login) {
		return this.makeOwnerId(login.getIdentifier());
	}

	/**
	 *
	 * @param key
	 *            {@link Key}. Never {@code null}.
	 * @return {@link String}. Never {@code null}.
	 */
	public String makeOwnerId(Key<Login> key) {
		return this.makeOwnerId(key.getId());
	}

	/**
	 *
	 * @param identifier
	 *            {@link Long}. Never {@code null}.
	 * @return {@link String}. Never {@code null}.
	 */
	public String makeOwnerId(Long identifier) {
		return String.format(this.loginOwnerFormat, identifier);
	}

}
