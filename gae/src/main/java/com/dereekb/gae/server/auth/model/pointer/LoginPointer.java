package com.dereekb.gae.server.auth.model.pointer;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.DatabaseModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Internal type used by {@link Login} to allow a flexible login system, and
 * specify different aliases.
 *
 * The pointer is used for authentication purposes, and uses a user's email
 * address as the pointer key.
 *
 * @author dereekb
 */
public class LoginPointer extends DatabaseModel
        implements ObjectifyModel<LoginPointer> {

	private static final long serialVersionUID = 1L;

	/**
	 * Identifier for logging in.
	 *
	 * Is generally an email address.
	 */
	@Id
	private String identifier;

	/**
	 * Identifier for the target {@link Login}.
	 */
	@Index
	private Key<Login> login;

	public LoginPointer() {}

	public LoginPointer(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Key<Login> getLogin() {
		return this.login;
	}

	public ModelKey getLoginModelKey() {
		Long id = this.login.getId();
		return new ModelKey(id);
	}

	public void setLogin(Key<Login> loginKey) {
		this.login = loginKey;
	}

	// Unique Model
	@Override
	public ModelKey getModelKey() {
		return new ModelKey(this.identifier);
	}

	public void setModelKey(ModelKey key) {
		this.identifier = ModelKey.readName(key);
	}

	// Database Model
	@Override
	protected Object getDatabaseIdentifier() {
		return this.identifier;
	}

	// Objectify Model
	@Override
	public Key<LoginPointer> getObjectifyKey() {
		return Key.create(LoginPointer.class, this.identifier);
	}

	@Override
	public String toString() {
		return "LoginPointer [identifier=" + this.identifier + ", loginKey=" + this.login + "]";
	}

}
