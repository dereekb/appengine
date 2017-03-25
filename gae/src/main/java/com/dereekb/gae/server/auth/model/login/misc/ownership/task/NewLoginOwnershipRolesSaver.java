package com.dereekb.gae.server.auth.model.login.misc.ownership.task;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.misc.ownership.LoginOwnershipRolesReader;
import com.dereekb.gae.server.datastore.Saver;
import com.dereekb.gae.server.datastore.Storer;
import com.dereekb.gae.server.datastore.exception.StoreKeyedEntityException;

/**
 * {@link Saver} implementation used for attaching ownership roles after
 * creating {@link Login} values.
 * 
 * @author dereekb
 *
 */
public class NewLoginOwnershipRolesSaver
        implements Storer<Login> {

	private Saver<Login> loginSaver;
	private LoginOwnershipRolesReader ownershipRolesReader;

	public NewLoginOwnershipRolesSaver(Saver<Login> loginSaver, LoginOwnershipRolesReader ownershipRolesReader)
	        throws IllegalArgumentException {
		this.setLoginSaver(loginSaver);
		this.setOwnershipRolesReader(ownershipRolesReader);
	}

	public Saver<Login> getLoginSaver() {
		return this.loginSaver;
	}

	public void setLoginSaver(Saver<Login> loginSaver) throws IllegalArgumentException {
		if (loginSaver == null) {
			throw new IllegalArgumentException("loginSaver cannot be null.");
		}

		this.loginSaver = loginSaver;
	}

	public LoginOwnershipRolesReader getOwnershipRolesReader() {
		return this.ownershipRolesReader;
	}

	public void setOwnershipRolesReader(LoginOwnershipRolesReader ownershipRolesReader)
	        throws IllegalArgumentException {
		if (ownershipRolesReader == null) {
			throw new IllegalArgumentException("ownershipRolesReader cannot be null.");
		}

		this.ownershipRolesReader = ownershipRolesReader;
	}

	// MARK: Storer
	@Override
	public void store(Login entity) throws StoreKeyedEntityException {
		this.loginSaver.store(entity);

		String ownerId = this.ownershipRolesReader.makeOwnerId(entity);
		entity.setOwnerId(ownerId);

		this.loginSaver.update(entity);
	}

	@Override
	public void store(Iterable<Login> entities) throws StoreKeyedEntityException {
		this.loginSaver.store(entities);

		for (Login login : entities) {
			String ownerId = this.ownershipRolesReader.makeOwnerId(login);
			login.setOwnerId(ownerId);
		}

		this.loginSaver.update(entities);
	}

	@Override
	public String toString() {
		return "NewLoginOwnershipRolesSaver [loginSaver=" + this.loginSaver + ", ownershipRolesReader="
		        + this.ownershipRolesReader + "]";
	}

}
