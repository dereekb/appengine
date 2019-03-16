package com.dereekb.gae.server.auth.security.app.impl;

import com.dereekb.gae.server.app.model.app.AppLoginSecurityLevel;
import com.dereekb.gae.server.app.model.app.info.AppInfo;
import com.dereekb.gae.server.auth.security.app.AppLoginSecurityDetails;
import com.dereekb.gae.server.datastore.models.impl.UniqueModelImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link AppLoginSecurityDetails} implementation.
 *
 * @author dereekb
 *
 */
public class AppLoginSecurityDetailsImpl extends UniqueModelImpl
        implements AppLoginSecurityDetails {

	public String appName;
	public String appSecret;
	public AppLoginSecurityLevel appLoginSecurityLevel;

	public AppLoginSecurityDetailsImpl(AppInfo info,
	        String appSecret) {
		this(info, appSecret, AppLoginSecurityLevel.SYSTEM);
	}

	public AppLoginSecurityDetailsImpl(AppInfo info,
	        String appSecret,
	        AppLoginSecurityLevel appLoginSecurityLevel) {
		this(info.getModelKey(), info.getAppName(), appSecret, appLoginSecurityLevel);
	}

	public AppLoginSecurityDetailsImpl(ModelKey modelKey,
	        String appName,
	        String appSecret,
	        AppLoginSecurityLevel appLoginSecurityLevel) {
		super(modelKey);
		this.setAppName(appName);
		this.setAppSecret(appSecret);
		this.setAppLoginSecurityLevel(appLoginSecurityLevel);
	}

	@Override
	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		if (appName == null) {
			throw new IllegalArgumentException("appName cannot be null.");
		}

		this.appName = appName;
	}

	@Override
	public String getAppSecret() {
		return this.appSecret;
	}

	public void setAppSecret(String appSecret) {
		if (appSecret == null) {
			throw new IllegalArgumentException("appSecret cannot be null.");
		}

		this.appSecret = appSecret;
	}

	@Override
	public AppLoginSecurityLevel getAppLoginSecurityLevel() {
		return this.appLoginSecurityLevel;
	}

	public void setAppLoginSecurityLevel(AppLoginSecurityLevel appLoginSecurityLevel) {
		if (appLoginSecurityLevel == null) {
			throw new IllegalArgumentException("appLoginSecurityLevel cannot be null.");
		}

		this.appLoginSecurityLevel = appLoginSecurityLevel;
	}

	@Override
	public String toString() {
		return "AppLoginSecurityDetailsImpl [appName=" + this.appName + ", appSecret=" + this.appSecret
		        + ", appLoginSecurityLevel=" + this.appLoginSecurityLevel + ", modelKey=" + this.modelKey + "]";
	}

}
