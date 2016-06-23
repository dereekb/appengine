package com.dereekb.gae.server.auth.model.login;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.model.extension.search.document.search.SearchableDatabaseModel;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.condition.IfEmpty;

/**
 * Default login model.
 * <p>
 * It is used to contain system login information and roles.
 *
 * @author dereekb
 */
@Cache
@Entity
public final class Login extends SearchableDatabaseModel
        implements ObjectifyModel<Login> {

	private static final long serialVersionUID = 1L;

	/**
	 * Database identifier.
	 */
	@Id
	private Long identifier;

	/**
	 * Creation date of the login.
	 */
	private Date date = new Date();

	/**
	 * Indexed login type.
	 */
	private Integer type = 0;

	/**
	 * A set of identifiers that correspond to different role types.
	 */
	@IgnoreSave({ IfEmpty.class })
	private Set<Integer> roles;

	/**
	 * Pointers for this login.
	 *
	 * Each pointer should reference this {@link Login}.
	 *
	 * This allows multiple different pointers to have access to the same login,
	 * if such functionality is required by the system.
	 */
	private Set<Key<LoginPointer>> pointers;

	public Login() {}

	public Login(Long identifier) {
		this.identifier = identifier;
	}

	public Long getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Set<Integer> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Integer> roles) {
		if (roles == null) {
			roles = new HashSet<Integer>();
		}

		this.roles = roles;
	}

	public Set<Key<LoginPointer>> getPointers() {
		return this.pointers;
	}

	public void setPointers(Set<Key<LoginPointer>> pointers) {
		if (pointers == null) {
			pointers = new HashSet<Key<LoginPointer>>();
		}

		this.pointers = pointers;
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
	public Key<Login> getObjectifyKey() {
		return Key.create(Login.class, this.identifier);
	}

	@Override
	public String toString() {
		return "Login [identifier=" + this.identifier + ", date=" + this.date + ", type=" + this.type + ", roles="
		        + this.roles + ", pointers=" + this.pointers + "]";
	}

}
