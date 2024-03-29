package com.dereekb.gae.server.app.model.app;

import com.dereekb.gae.server.app.model.app.info.AppConfigInfo;
import com.dereekb.gae.server.app.model.app.info.AppInfo;
import com.dereekb.gae.server.app.model.app.info.AppServiceVersionInfo;
import com.dereekb.gae.server.app.model.app.info.AppVersion;
import com.dereekb.gae.server.app.model.app.info.SystemAppInfo;
import com.dereekb.gae.server.app.model.app.info.impl.AppServiceVersionInfoImpl;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.misc.owned.LoginOwnedModel;
import com.dereekb.gae.server.auth.security.app.AppLoginSecurityDetails;
import com.dereekb.gae.server.datastore.models.DatedDatabaseModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyGenerationType;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyModelKeyUtil;
import com.dereekb.gae.utilities.gae.GoogleAppEngineUtility;
import com.dereekb.gae.utilities.misc.keyed.utility.KeyedUtility;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfEmpty;
import com.googlecode.objectify.condition.IfFalse;
import com.googlecode.objectify.condition.IfZero;

/**
 * Represents a registered app on the system.
 *
 * @author dereekb
 *
 */
@Cache
@Entity
@ModelKeyInfo(value = ModelKeyType.NUMBER, generation = ModelKeyGenerationType.AUTOMATIC)
public class App extends DatedDatabaseModel
        implements ObjectifyModel<App>, AppConfigInfo, AppLoginSecurityDetails, LoginOwnedModel {

	private static final long serialVersionUID = 1L;

	public static final Integer DEFAULT_LEVEL = AppLoginSecurityLevel.APP.code;

	/**
	 * While not actually set as the default, this value is used in development.
	 */
	public static final String DEFAULT_DEVELOPMENT_SECRET = "SECRET";

	/**
	 * Database identifier.
	 */
	@Id
	private Long identifier;

	/**
	 * System identifier that is indexed and used for querying.
	 *
	 * This should only be used for system applications.
	 */
	@Index
	@IgnoreSave({ IfEmpty.class })
	private String systemKey;

	/**
	 * Application name.
	 */
	private String name;

	/**
	 * Base server url. Used by various other components when resolving the
	 * path.
	 */
	private String server;

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

	// MARK: Internal Variables
	@IgnoreSave({ IfEmpty.class })
	private String app;

	@IgnoreSave({ IfEmpty.class })
	private String service;

	@IgnoreSave({ IfEmpty.class })
	private String version;

	@IgnoreSave({ IfFalse.class })
	private boolean initialized = false;

	public App() {
		super();
	}

	public Long getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public String getSystemKey() {
		return this.systemKey;
	}

	public void setSystemKey(String systemKey) {
		this.systemKey = systemKey;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServer() {
		return this.server;
	}

	public void setServer(String server) {
		this.server = server;
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

	public String getApp() {
		return this.app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public AppVersion getAppVersion() {
		try {
			return GoogleAppEngineUtility.decodeAppVersion(this.getVersion());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public void setAppVersion(AppVersion version) {
		this.setVersion(GoogleAppEngineUtility.encodeAppVersion(version));
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isInitialized() {
		return this.initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	// MARK: AppInfo
	public void copyFromSystemAppInfo(SystemAppInfo appInfo) {
		this.copyFromAppInfo(appInfo);
		this.setSystemKey(appInfo.getSystemKey());
	}

	/**
	 * Copies all information from the {@link AppInfo}.
	 *
	 * @param appInfo
	 *            {@link AppInfo}. Never {@code null}.
	 */
	public void copyFromAppInfo(AppInfo appInfo) {
		this.setName(appInfo.getAppName());
		this.setModelKey(appInfo.getModelKey());
		this.setAppServiceVersionInfo(appInfo.getAppServiceVersionInfo());
	}

	@Override
	public AppServiceVersionInfo getAppServiceVersionInfo() {
		try {
			return new AppServiceVersionInfoImpl(this.app, this.service, this.getAppVersion());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public void setAppServiceVersionInfo(AppServiceVersionInfo info) {
		if (info != null) {
			this.setApp(info.getAppProjectId());
			this.setService(info.getAppService());
			this.setAppVersion(info.getAppVersion());
		} else {
			this.setApp(null);
			this.setService(null);
			this.setAppVersion(null);
		}
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
		return ObjectifyKeyUtility.createKey(App.class, this.identifier);
	}

	@Override
	public String toString() {
		return "App [identifier=" + this.identifier + ", name=" + this.name + ", secret=" + this.secret + ", level="
		        + this.level + ", login=" + this.login + ", app=" + this.app + ", service=" + this.service
		        + ", version=" + this.version + ", initialized=" + this.initialized + ", date=" + this.date + "]";
	}

}
