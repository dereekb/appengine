package com.dereekb.gae.server.app.model.app.info.impl;

import com.dereekb.gae.server.app.model.app.info.AppInfo;
import com.dereekb.gae.server.app.model.app.info.AppServiceVersionInfo;
import com.dereekb.gae.server.datastore.models.impl.UniqueModelImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link AppInfo} implementation.
 *
 * @author dereekb
 *
 */
public class AppInfoImpl extends UniqueModelImpl
        implements AppInfo {

	private String appName;
	private AppServiceVersionInfo appServiceVersionInfo;

	public AppInfoImpl(AppInfo appInfo) {
		this(appInfo.getModelKey(), appInfo.getAppName(), appInfo.getAppServiceVersionInfo());
	}

	public AppInfoImpl(Long id, String appName) {
		this(id, appName, null);
	}

	public AppInfoImpl(Long id, String appName, AppServiceVersionInfo appServiceVersionInfo) {
		this(new ModelKey(id), appName, appServiceVersionInfo);
	}

	public AppInfoImpl(ModelKey modelKey, String appName) {
		this(modelKey, appName, null);
	}

	public AppInfoImpl(ModelKey modelKey, String appName, AppServiceVersionInfo appServiceVersionInfo) {
		super(modelKey);
		this.setAppName(appName);
		this.setAppServiceVersionInfo(appServiceVersionInfo);
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
	public AppServiceVersionInfo getAppServiceVersionInfo() {
		return this.appServiceVersionInfo;
	}

	public void setAppServiceVersionInfo(AppServiceVersionInfo appServiceVersionInfo) {
		this.appServiceVersionInfo = appServiceVersionInfo;
	}

	@Override
	public String toString() {
		return "AppInfoImpl [appName=" + this.appName + ", appServiceVersionInfo=" + this.appServiceVersionInfo
		        + ", modelKey=" + this.modelKey + "]";
	}

}
