package com.dereekb.gae.server.auth.model.login;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.model.extension.search.document.search.SearchableDatabaseModel;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.roles.EncodedRolesBearer;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfDefault;
import com.googlecode.objectify.condition.IfEmpty;
import com.googlecode.objectify.condition.IfNotDefault;

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
        implements ObjectifyModel<Login>, EncodedRolesBearer {

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
	 * Whether or not this login is a root-level login.
	 */
	@IgnoreSave({ IfDefault.class })
	private boolean root = true;

	/**
	 * Login group identifier
	 */
	@Index({ IfNotDefault.class })
	@IgnoreSave({ IfDefault.class })
	private Integer group = 0;

	/**
	 * A {@link Long} that encodes all roles.
	 * <p>
	 * The roles identifiers will differ between systems.
	 */
	@IgnoreSave({ IfDefault.class })
	private Long roles = 0L;

	/**
	 * Pointers for this login.
	 *
	 * Each pointer should reference this {@link Login}.
	 *
	 * This allows multiple different pointers to have access to the same login,
	 * if such functionality is required by the system.
	 */
	private Set<Key<LoginPointer>> pointers = new HashSet<Key<LoginPointer>>();

	/**
	 * Parent Logins that have access to this login.
	 */
	@IgnoreSave({ IfEmpty.class })
	private Set<Key<Login>> parents = new HashSet<Key<Login>>();

	/**
	 * Children logins that this login has access to.
	 */
	@IgnoreSave({ IfEmpty.class })
	private Set<Key<Login>> children = new HashSet<Key<Login>>();

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

	public boolean isRoot() {
		return this.root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public Integer getGroup() {
		return this.group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public Long getRoles() {
		return this.roles;
	}

	@Override
	public Long getEncodedRoles() {
		return this.roles;
	}

	public void setRoles(Long roles) {
		if (roles == null) {
			roles = 0L;
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

	public Set<Key<Login>> getParents() {
		return this.parents;
	}

	public void setParents(Set<Key<Login>> parents) {
		if (parents == null) {
			parents = new HashSet<Key<Login>>();
		}

		this.parents = parents;
	}

	public Set<Key<Login>> getChildren() {
		return this.children;
	}

	public void setChildren(Set<Key<Login>> children) {
		if (children == null) {
			children = new HashSet<Key<Login>>();
		}

		this.children = children;
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
		return "Login [identifier=" + this.identifier + ", date=" + this.date + ", group=" + this.group + ", roles="
		        + this.roles + ", pointers=" + this.pointers + "]";
	}

}
