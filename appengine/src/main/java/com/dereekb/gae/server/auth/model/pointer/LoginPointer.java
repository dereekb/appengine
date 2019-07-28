package com.dereekb.gae.server.auth.model.pointer;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.misc.owned.LoginOwnedModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyGenerationType;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.owner.OwnedDatabaseModel;
import com.dereekb.gae.server.datastore.objectify.MutableObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyModelKeyUtil;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfEmpty;
import com.googlecode.objectify.condition.IfFalse;
import com.googlecode.objectify.condition.IfNull;

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
@ModelKeyInfo(value = ModelKeyType.NAME, generation = ModelKeyGenerationType.UNIQUE_NAME)
public class LoginPointer extends OwnedDatabaseModel
	implements MutableObjectifyModel<LoginPointer>, LoginOwnedModel {

	private static final long serialVersionUID = 1L;

	/**
	 * While not actually set as the default, this value is used in development.
	 */
	public static final String DEFAULT_DEVELOPMENT_PASSWORD = "password";

	/**
	 * Identifier for logging in.
	 *
	 * Is generally the username or an email address.
	 */
	@Id
	private String identifier;

	/**
	 * Identifier for the target {@link Login}.
	 * <p>
	 * If {@code null}, not associated with any login yet.
	 */
	@Index
	private Key<Login> login;

	/**
	 * Pointer type.
	 */
	@Index
	private Integer type;

	/**
	 * Password, if applicable.
	 */
	@IgnoreSave({ IfEmpty.class })
	private String password;

	/**
	 * Arbitrary pointer data, if applicable.
	 */
	@IgnoreSave({ IfEmpty.class })
	private String data;

	/**
	 * Email address, if applicable.
	 */
	@Index
	@IgnoreSave({ IfEmpty.class })
	private String email;

	/**
	 * If the pointer's email has been verified.
	 */
	@IgnoreSave({ IfNull.class, IfFalse.class })
	private Boolean verified = false;

	public LoginPointer() {}

	public LoginPointer(String identifier) {
		this.setIdentifier(identifier);
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

	@Deprecated
	public ModelKey getLoginModelKey() {
		return this.getLoginOwnerKey();
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
			this.type = type.code;
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
		if (email != null) {
			email = email.toLowerCase();
		}

		this.email = email;
	}

	public Boolean getVerified() {
		return this.verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	// Unique Model
	@Override
	public ModelKey getModelKey() {
		return ModelKey.safe(this.identifier);
	}

	@Override
	public void setModelKey(ModelKey key) {
		this.identifier = ModelKey.strictReadName(key);
	}

	// Database Model
	@Override
	protected Object getDatabaseIdentifier() {
		return this.identifier;
	}

	// Objectify Model
	@Override
	public Key<LoginPointer> getObjectifyKey() {
		return ObjectifyKeyUtility.createKey(LoginPointer.class, this.identifier);
	}

	// Login Owned
	@Override
	public ModelKey getLoginOwnerKey() {
		return ObjectifyModelKeyUtil.readModelKey(this.login);
	}

	@Override
	public String toString() {
		return "LoginPointer [identifier=" + this.identifier + ", loginKey=" + this.login + "]";
	}

}
