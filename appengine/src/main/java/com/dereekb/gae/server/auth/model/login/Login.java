package com.dereekb.gae.server.auth.model.login;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedDatabaseModel;
import com.dereekb.gae.server.auth.security.roles.EncodedRolesBearer;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyGenerationType;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.MutableObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfDefault;
import com.googlecode.objectify.condition.IfEmpty;
import com.googlecode.objectify.condition.IfFalse;
import com.googlecode.objectify.condition.IfNotDefault;
import com.googlecode.objectify.condition.IfNull;
import com.googlecode.objectify.condition.IfTrue;

/**
 * Default login model.
 * <p>
 * It is used to contain system login information and roles.
 *
 * @author dereekb
 */
@Cache
@Entity
@ModelKeyInfo(value = ModelKeyType.NUMBER, generation = ModelKeyGenerationType.AUTOMATIC)
public final class Login extends DescribedDatabaseModel
        implements MutableObjectifyModel<Login>, EncodedRolesBearer {

	private static final long serialVersionUID = 1L;

	public static final Long DEFAULT_ROLES = 0L;

	/**
	 * Database identifier.
	 */
	@Id
	private Long identifier;

	/**
	 * Creation date of the login.
	 */
	@Index
	private Date date = new Date();

	/**
	 * Authentication reset time. Used to invalidate tokens that have been
	 * created before this timer.
	 */
	private Date authReset = new Date();

	/**
	 * Whether or not this login is a root-level login.
	 */
	@IgnoreSave({ IfDefault.class })
	private Boolean root = true;

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
	 * <p>
	 * Should never be null.
	 */
	@Index({ IfNotDefault.class })
	@IgnoreSave({ IfDefault.class, IfNull.class })
	private Long roles = DEFAULT_ROLES;

	/**
	 * Whether or not the login is disabled.
	 */
	@IgnoreSave({ IfNull.class, IfFalse.class })
	private Boolean disabled = false;

	/**
	 * Reason why the login was disabled, if applicable.
	 */
	@IgnoreSave({ IfEmpty.class })
	private String disabledReason;

	/**
	 * Whether or not the disabling has been synchronized.
	 * <p>
	 * {@code true} means it is synchronized as not disabled. {@code false}
	 * means it is synchronized as disabled.
	 */
	@IgnoreSave({ IfNull.class, IfTrue.class })
	private Boolean disabledSyncState = true;

	/**
	 * Parent Logins that have access to this login.
	 */
	@Deprecated
	@IgnoreSave({ IfEmpty.class })
	private Set<Key<Login>> parents = new HashSet<Key<Login>>();

	/**
	 * Children logins that this login has access to.
	 */
	@Deprecated
	@IgnoreSave({ IfEmpty.class })
	private Set<Key<Login>> children = new HashSet<Key<Login>>();

	// TODO: Consider adding model key string for the User model type to reduce
	// amount of querying required

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

	public Date getAuthReset() {
		return this.authReset;
	}

	public void setAuthReset(Date authReset) {
		this.authReset = authReset;
	}

	public Boolean isRoot() {
		return this.root;
	}

	public void setRoot(Boolean root) {
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

	public Boolean getDisabled() {
		return this.disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public String getDisabledReason() {
		return this.disabledReason;
	}

	public void setDisabledReason(String disabledReason) {
		this.disabledReason = disabledReason;
	}

	public Boolean getDisabledSyncState() {
		return this.disabledSyncState;
	}

	public void setDisabledSyncState(Boolean disabledSyncState) {
		this.disabledSyncState = disabledSyncState;
	}

	/**
	 * The login is properly synchronized when the disabled state is the
	 * opposite of done.
	 */
	public boolean isSynchronized() {
		return this.getDisabledSyncState() != this.getDisabled();
	}

	public void setSynchronized() {
		this.disabledSyncState = !this.disabled;
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
	public Key<Login> getObjectifyKey() {
		return ObjectifyKeyUtility.createKey(Login.class, this.identifier);
	}

	@Override
	public String toString() {
		return "Login [identifier=" + this.identifier + ", date=" + this.date + ", group=" + this.group + ", roles="
		        + this.roles + "]";
	}

}
