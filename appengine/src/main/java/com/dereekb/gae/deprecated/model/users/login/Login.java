package com.thevisitcompany.gae.deprecated.model.users.login;

import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfDefault;
import com.googlecode.objectify.condition.IfEmpty;
import com.googlecode.objectify.condition.IfNull;
import com.googlecode.objectify.condition.IfTrue;
import com.thevisitcompany.gae.deprecated.model.users.UsersModel;
import com.thevisitcompany.gae.deprecated.model.users.account.Account;
import com.thevisitcompany.gae.deprecated.model.users.user.User;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.IfNoRoles;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.IfUserOnly;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.PermissionAuthority;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.Permissions;

/**
 * A
 * 
 * @author dereekb
 * 
 */
@Entity
@Cache
public final class Login extends UsersModel<Login>
        implements PermissionAuthority {

	private static final long serialVersionUID = 1L;

	@Index
	private String email;

	@IgnoreSave(IfNull.class)
	private Key<User> user;

	@IgnoreSave(IfEmpty.class)
	private Set<Key<Account>> accounts = new HashSet<Key<Account>>();

	@IgnoreSave({ IfNoRoles.class, IfUserOnly.class })
	private Permissions permissions = new Permissions();

	@IgnoreSave(IfDefault.class)
	private LoginSettings settings = new LoginSettings();

	@IgnoreSave(IfTrue.class)
	private Boolean active = true;

	public Login() {}

	public Login(Long id) {
		super(id);
	}

	/**
	 * Pre-registration constructor.
	 */
	public Login(String nickname, String email) {
		this.email = email;
		this.active = true;
	}

	/**
	 * Post-registration constructor. This is used once the user has completed registration.
	 */
	public Login(Login newUser) {
		super(newUser.getId());
		this.email = newUser.getEmail();
		this.active = true;
	}

	@Override
	public Key<Login> getKey() {
		return Key.create(Login.class, this.id);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Key<User> getUser() {
		return user;
	}

	public void setUser(Key<User> user) {
		this.user = user;
	}

	public Set<Key<Account>> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Key<Account>> accounts) {
		this.accounts = accounts;
	}

	public Permissions getPermissions() {
		return this.permissions;
	}

	public void setPermissions(Permissions permissions) {
		this.permissions = permissions;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public LoginSettings getSettings() {
		return settings;
	}

	public void setSettings(LoginSettings settings) {
		this.settings = settings;
	}

	@Override
	public String toString() {
		return "Login [email=" + email + ", id=" + id + "]";
	}

	@Override
    public boolean modelEquals(Login object) {
	    // TODO Auto-generated method stub
	    return false;
    }

}
