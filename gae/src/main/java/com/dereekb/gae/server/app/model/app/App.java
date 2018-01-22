package com.dereekb.gae.server.app.model.app;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.misc.owned.LoginOwnedModel;
import com.dereekb.gae.server.auth.security.app.AppLoginSecurityDetails;
import com.dereekb.gae.server.datastore.models.DatedDatabaseModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyModelKeyUtil;
import com.dereekb.gae.utilities.misc.keyed.utility.KeyedUtility;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfZero;

/**
 * Represents a registered app on the system.
 *
 * @author dereekb
 *
 */
@Cache
@Entity
public class App extends DatedDatabaseModel
        implements ObjectifyModel<App>, AppLoginSecurityDetails, LoginOwnedModel {

	private static final long serialVersionUID = 1L;

	public static final Integer DEFAULT_LEVEL = AppLoginSecurityLevel.APP.code;

	/**
	 * Database identifier.
	 */
	@Id
	private Long identifier;

	/**
	 * Application OAuth name.
	 */
	private String name;

	/**
	 * Generated app secret. Is generally a hexadecimal value.
	 * <p>
	 * Used for signing requests.
	 */
	private String secret;

	/**
	 * Security level.
	 */
	@Index
	@IgnoreSave({ IfZero.class })
	private Integer level = DEFAULT_LEVEL;

	/**
	 * Login
	 */
	private Key<Login> login;

	public App() {
		super();
	}

	public Long getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Integer getLevelCode() {
		return this.level;
	}

	public void setLevelCode(Integer level) {
		this.level = level;
	}

	public AppLoginSecurityLevel getLevel() {
		return AppLoginSecurityLevel.valueOf(this.level);
	}

	public void setLevel(AppLoginSecurityLevel level) {
		Integer levelCode = KeyedUtility.getCode(level);
		this.setLevelCode(levelCode);
	}

	// MARK: AppLoginSecurityDetails
	@Override
	public String getAppName() {
		return this.name;
	}

	@Override
	public String getAppSecret() {
		return this.secret;
	}

	@Override
	public AppLoginSecurityLevel getAppLoginSecurityLevel() {
		return AppLoginSecurityLevel.valueOf(this.level);
	}

	public Key<Login> getLogin() {
		return this.login;
	}

	public void setLogin(Key<Login> login) {
		this.login = login;
	}

	// Login Owner
	@Override
	public ModelKey getLoginOwnerKey() {
		return ObjectifyModelKeyUtil.readModelKey(this.login);
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
	public Key<App> getObjectifyKey() {
		return Key.create(App.class, this.identifier);
	}

	@Override
	public String toString() {
		return "App [identifier=" + this.identifier + ", name=" + this.name + ", secret=" + this.secret + ", level="
		        + this.level + ", login=" + this.login + ", date=" + this.date + "]";
	}

}
