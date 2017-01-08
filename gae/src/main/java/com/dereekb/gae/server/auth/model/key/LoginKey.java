package com.dereekb.gae.server.auth.model.key;

import java.util.Date;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
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
import com.googlecode.objectify.condition.IfNull;

/**
 * API key model.
 * <p>
 * It is used for managing logging in via external systems and with a limited
 * set of permissions.
 *
 * @author dereekb
 */
@Cache
@Entity
public class LoginKey extends DatabaseModel
        implements ObjectifyModel<LoginKey> {

	private static final long serialVersionUID = 1L;

	public static final long MIN_EXPIRATION_TIME = 600000L;	// 10 Minutes

	@Id
	private Long identifier;

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
	 * Verificiation code.
	 */
	private String verification;

	/**
	 * Mask of permissions available to this API key.
	 */
	private Long mask;

	/**
	 * Creation date
	 */
	private Date created;

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
		return identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public Key<LoginPointer> getPointer() {
		return pointer;
	}

	public void setPointer(Key<LoginPointer> pointer) throws IllegalArgumentException {
		if (pointer == null) {
			throw new IllegalArgumentException("LoginPointer cannot be null.");
		}

		this.pointer = pointer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		if (verification == null) {
			throw new IllegalArgumentException("Verification cannot be null.");
		}

		this.verification = verification;
	}

	public Long getMask() {
		return mask;
	}

	public void setMask(Long mask) {
		this.mask = mask;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getExpiration() {
		return expiration;
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
		return Key.create(LoginKey.class, this.identifier);
	}

	@Override
	public String toString() {
		return "ApiLoginKey [identifier=" + identifier + ", pointer=" + pointer + ", name=" + name + ", verification="
		        + verification + ", mask=" + mask + ", created=" + created + ", expiration=" + expiration + "]";
	}

}
