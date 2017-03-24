package com.dereekb.gae.server.auth.model.login.misc.ownership.task;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.misc.ownership.LoginOwnershipRolesReader;
import com.dereekb.gae.server.datastore.Updater;

/**
 * {@link Updater} implementation used for attaching ownership roles to created
 * {@link Login} values.
 * 
 * @author dereekb
 *
 */
public class NewLoginOwnershipRolesSaver
        implements Updater<Login> {

	private Updater<Login> loginSaver;
	private LoginOwnershipRolesReader ownershipRolesReader;

	public NewLoginOwnershipRolesSaver(Updater<Login> loginSaver, LoginOwnershipRolesReader ownershipRolesReader)
	        throws IllegalArgumentException {
		this.setLoginSaver(loginSaver);
		this.setOwnershipRolesReader(ownershipRolesReader);
	}

	public Updater<Login> getLoginSaver() {
		return this.loginSaver;
	}

	public void setLoginSaver(Updater<Login> loginSaver) throws IllegalArgumentException {
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

	// MARK: Saver
	@Override
	public void save(Login entity,
	                 boolean async) {
		this.loginSaver.save(entity, false);

		String ownerId = this.ownershipRolesReader.makeOwnerId(entity);
		entity.setOwnerId(ownerId);

		this.loginSaver.save(entity, async);
	}

	@Override
	public void save(Iterable<Login> entities,
	                 boolean async) {
		this.loginSaver.save(entities, false);

		for (Login login : entities) {
			String ownerId = this.ownershipRolesReader.makeOwnerId(login);
			login.setOwnerId(ownerId);
		}

		this.loginSaver.save(entities, async);
	}

}
