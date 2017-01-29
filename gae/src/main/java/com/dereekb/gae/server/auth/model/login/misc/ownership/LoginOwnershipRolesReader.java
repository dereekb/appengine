package com.dereekb.gae.server.auth.model.login.misc.ownership;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.security.ownership.impl.OwnershipRolesImpl;
import com.dereekb.gae.server.auth.security.ownership.source.OwnershipRolesReader;

/**
 * Login Ownership Key.
 * 
 * @author dereekb
 *
 */
public class LoginOwnershipRolesReader
        implements OwnershipRolesReader<Login> {

	public static final String DEFAULT_LOGIN_OWNER_FORMAT = "LO%s";

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

	@Override
	public OwnershipRolesImpl readRoles(Login input) {
		OwnershipRolesImpl roles = new OwnershipRolesImpl();
		Long identifier = input.getIdentifier();

		if (identifier != null) {
			String ownerId = this.makeOwnerId(identifier);
			roles.setOwnerId(ownerId);
		}

		return roles;
	}

	private String makeOwnerId(Long identifier) {
		return String.format(this.loginOwnerFormat, identifier);
	}

}
