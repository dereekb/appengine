package com.dereekb.gae.server.auth.security.token.model.impl;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.ownership.OwnershipRoles;
import com.dereekb.gae.server.auth.security.ownership.source.OwnershipRolesReader;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Abstract extension of {@link AbstractLoginTokenBuilder} that will load the
 * {@link Login} and initialize the {@link LoginToken} with it.
 *
 * @author dereekb
 *
 * @param <T>
 *            token type
 *
 * @see AbstractUserLoginTokenBuilder for extension that also include user data.
 */
public abstract class ExtendedAbstractLoginTokenBuilder<T extends LoginTokenImpl> extends AbstractLoginTokenBuilder<T> {

	private Getter<Login> loginGetter;

	@Deprecated
	private OwnershipRolesReader<Login> ownershipRolesReader;

	public ExtendedAbstractLoginTokenBuilder(Getter<Login> loginGetter) throws IllegalArgumentException {
		this.setLoginGetter(loginGetter);
	}

	@Deprecated
	public ExtendedAbstractLoginTokenBuilder(Getter<Login> loginGetter,
	        OwnershipRolesReader<Login> ownershipRolesReader) throws IllegalArgumentException {
		this.setLoginGetter(loginGetter);
		this.setOwnershipRolesReader(ownershipRolesReader);
	}

	public Getter<Login> getLoginGetter() {
		return this.loginGetter;
	}

	public void setLoginGetter(Getter<Login> loginGetter) throws IllegalArgumentException {
		if (loginGetter == null) {
			throw new IllegalArgumentException("LoginGetter cannot be null.");
		}

		this.loginGetter = loginGetter;
	}

	@Deprecated
	public OwnershipRolesReader<Login> getOwnershipRolesReader() {
		return this.ownershipRolesReader;
	}

	@Deprecated
	public void setOwnershipRolesReader(OwnershipRolesReader<Login> ownershipRolesReader)
	        throws IllegalArgumentException {
		if (ownershipRolesReader == null) {
			throw new IllegalArgumentException("OwnershipRolesReader cannot be null.");
		}

		this.ownershipRolesReader = ownershipRolesReader;
	}

	// MARK: LoginTokenBuilder
	@Override
	protected void initLoginToken(T loginToken,
	                              LoginPointer pointer) {

		String pointerId = pointer.getIdentifier();
		loginToken.setLoginPointer(pointerId);
		loginToken.setPointerType(pointer.getLoginPointerType());

		ModelKey loginKey = pointer.getLoginOwnerKey();
		this.initLoginTokenWithLogin(loginToken, loginKey);
	}

	protected void initLoginTokenWithLogin(T loginToken,
	                                       ModelKey loginKey) {
		if (loginKey != null) {
			Login login = this.loginGetter.get(loginKey);

			if (login != null) {
				this.initLoginTokenWithLogin(loginToken, login);
			}
		}
	}

	protected void initLoginTokenWithLogin(T loginToken,
	                                       Login login) {
		loginToken.setLogin(login.getIdentifier());

		OwnershipRoles ownershipRoles = this.ownershipRolesReader.readRoles(login);
		Long roles = login.getRoles();

		loginToken.setRoles(roles);
		loginToken.setOwnershipRoles(ownershipRoles);
	}

}
