package com.dereekb.gae.server.auth.model.key;

import java.util.Date;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.misc.owned.LoginOwnedModel;
import com.dereekb.gae.server.auth.model.login.shared.LoginRelatedModel;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyGenerationType;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.models.owner.OwnedDatabaseModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyModelKeyUtil;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfEmpty;
import com.googlecode.objectify.condition.IfNull;

/**
 * OAuth/API key model.
 * <p>
 * It is used for managing logging in via external systems and with a limited
 * set of permissions.
 *
 * @author dereekb
 */
@Cache
@Entity
@ModelKeyInfo(value = ModelKeyType.NUMBER, generation = ModelKeyGenerationType.AUTOMATIC)
public class LoginKey extends OwnedDatabaseModel
        implements LoginRelatedModel, ObjectifyModel<LoginKey>, LoginOwnedModel {

	private static final long serialVersionUID = 1L;

	public static final long MIN_EXPIRATION_TIME = 600000L;	// 10 Minutes

	@Id
	private Long identifier;

	/**
	 * Login for this key.
	 */
	@Index
	private Key<Login> login;

	/**
	 * API {@link LoginPointer} associated with this key.
	 */
	@Index
	private Key<LoginPointer> pointer;

	/**
	 * (Optional) Name attached to this image.
	 */
	@IgnoreSave({ IfEmpty.class })
	private String name;

	/**
	 * Verification code.
	 */
	private String verification;

	/**
	 * Mask of permissions available to this API key.
	 */
	private Long mask;

	/**
	 * Creation date
	 */
	@Index
	private Date date;

	/**
	 * Expiration time in milliseconds. Can be {@code null} if it never expires.
	 */
	@IgnoreSave({ IfNull.class })
	private Long expiration;

	public LoginKey() {}

	public LoginKey(Long identifier) {
		this.identifier = identifier;
	}

	public Long getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	@Override
	public Key<Login> getLogin() {
		return this.login;
	}

	public void setLogin(Key<Login> login) {
		this.login = login;
	}

	public Key<LoginPointer> getLoginPointer() {
		return this.pointer;
	}

	public ModelKey getPointerModelKey() {
		return ModelKey.safe(this.pointer.getName());
	}

	public void setLoginPointer(Key<LoginPointer> pointer) throws IllegalArgumentException {
		if (pointer == null) {
			throw new IllegalArgumentException("LoginPointer cannot be null.");
		}

		this.pointer = pointer;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVerification() {
		return this.verification;
	}

	public void setVerification(String verification) {
		if (verification == null) {
			throw new IllegalArgumentException("Verification cannot be null.");
		}

		this.verification = verification;
	}

	public Long getMask() {
		return this.mask;
	}

	public void setMask(Long mask) {
		this.mask = mask;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getExpiration() {
		return this.expiration;
	}

	public void setExpiration(Long expiration) {
		if (expiration != null) {
			expiration = Math.max(expiration, MIN_EXPIRATION_TIME);
		}

		this.expiration = expiration;
	}

	// Unique Model
	@Override
	public ModelKey getModelKey() {
		return ModelKey.safe(this.identifier);
	}

	@Override
	public void setModelKey(ModelKey key) {
		this.identifier = ModelKey.readIdentifier(key);
	}

	// Database Model
	@Override
	protected Object getDatabaseIdentifier() {
		return this.identifier;
	}

	// Objectify Model
	@Override
	public Key<LoginKey> getObjectifyKey() {
		return ObjectifyKeyUtility.createKey(LoginKey.class, this.identifier);
	}

	// Login Owned
	@Override
	public ModelKey getLoginOwnerKey() {
		return ObjectifyModelKeyUtil.readModelKey(this.login);
	}

	@Override
	public String toString() {
		return "ApiLoginKey [identifier=" + this.identifier + ", pointer=" + this.pointer + ", name=" + this.name
		        + ", verification=" + this.verification + ", mask=" + this.mask + ", date=" + this.date
		        + ", expiration=" + this.expiration + "]";
	}

}
