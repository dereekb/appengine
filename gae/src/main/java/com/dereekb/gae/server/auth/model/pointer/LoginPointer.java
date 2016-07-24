package com.dereekb.gae.server.auth.model.pointer;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.DatabaseModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfEmpty;

/**
 * Internal type used by {@link Login} to allow a flexible login system, and
 * specify different aliases.
 * <p>
 * The pointer is used for authentication purposes, and uses a user's email
 * address as the pointer key.
 *
 * @author dereekb
 */
@Cache
@Entity
public class LoginPointer extends DatabaseModel
        implements ObjectifyModel<LoginPointer> {

	private static final long serialVersionUID = 1L;

	/**
	 * Identifier for logging in.
	 *
	 * Is generally the username or an email address.
	 */
	@Id
	private String identifier;

	/**
	 * Identifier for the target {@link Login}.
	 */
	@Index
	private Key<Login> login;

	/**
	 * Pointer type.
	 */
	private Integer type;

	/**
	 * Password, if applicable.
	 */
	@IgnoreSave({ IfEmpty.class })
	private String password;

	/**
	 * Pointer data, if applicable.
	 */
	@IgnoreSave({ IfEmpty.class })
	private String data;

	/**
	 * Email address, if applicable.
	 */
	@IgnoreSave({ IfEmpty.class })
	private String email;

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
		ModelKey key = null;

		if (this.login != null) {
			Long id = this.login.getId();
			key = ModelKey.safe(id);
		}

		return key;
	}

	public void setLogin(Key<Login> loginKey) {
		this.login = loginKey;
	}

	public LoginPointerType getLoginPointerType() {
		return LoginPointerType.valueOf(this.type);
	}

	public void setLoginPointerType(LoginPointerType type) throws IllegalArgumentException {
		if (type == null) {
			this.setTypeId(null);
		} else {
			this.type = type.id;
		}
	}

	public Integer getTypeId() {
		return this.type;
	}

	public void setTypeId(Integer type) throws IllegalArgumentException {
		if (type == null) {
			throw new IllegalArgumentException("TypeId cannot be null.");
		}

		this.type = type;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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
