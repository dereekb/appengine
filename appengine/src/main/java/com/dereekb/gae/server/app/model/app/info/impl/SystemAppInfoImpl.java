package com.dereekb.gae.server.app.model.app.info.impl;

import com.dereekb.gae.server.app.model.app.info.AppServiceVersionInfo;
import com.dereekb.gae.server.app.model.app.info.SystemAppInfo;

/**
 * {@link SystemAppInfo} implementation.
 *
 * @author dereekb
 *
 */
public class SystemAppInfoImpl extends AppInfoImpl
        implements SystemAppInfo {

	private String systemKey;

	public SystemAppInfoImpl(Long id, String appName, String systemKey, AppServiceVersionInfo appServiceVersionInfo) {
		super(id, appName, appServiceVersionInfo);
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
