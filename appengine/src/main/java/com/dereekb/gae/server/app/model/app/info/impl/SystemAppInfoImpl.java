package com.dereekb.gae.server.app.model.app.info.impl;

import com.dereekb.gae.server.app.model.app.info.AppInfo;
import com.dereekb.gae.server.app.model.app.info.AppServiceVersionInfo;
import com.dereekb.gae.server.app.model.app.info.SystemAppInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link SystemAppInfo} implementation.
 *
 * @author dereekb
 *
 */
public class SystemAppInfoImpl extends AppInfoImpl
        implements SystemAppInfo {

	private String systemKey;

	public SystemAppInfoImpl(AppInfo appInfo) {
		super(appInfo);
		this.setSystemKey(null);
	}

	public SystemAppInfoImpl(Long modelKey,
	        String appName,
	        String systemKey,
	        String appProjectId,
	        String appVersion,
	        String appService) {
		this(new ModelKey(modelKey), appName, systemKey,
		        new AppServiceVersionInfoImpl(appProjectId, appVersion, appService));
	}

	public SystemAppInfoImpl(ModelKey modelKey,
	        String appName,
	        String systemKey,
	        AppServiceVersionInfo appServiceVersionInfo) {
		super(modelKey, appName, appServiceVersionInfo);
		this.setSystemKey(systemKey);
	}

	@Override
	public String getSystemKey() {
		return this.systemKey;
	}

	public void setSystemKey(String systemKey) {
		this.systemKey = systemKey;
	}

	@Override
	public String toString() {
		return "SystemAppInfoImpl [systemKey=" + this.systemKey + ", toString()=" + super.toString() + "]";
	}

}
