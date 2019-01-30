package com.dereekb.gae.server.app.model.app.info.impl;

import com.dereekb.gae.server.app.model.app.info.AppInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.data.HashUtility;
import com.dereekb.gae.utilities.gae.impl.GoogleAppEngineContextualFactoryImpl;

/**
 * {@link GoogleAppEngineContextualFactoryImpl} extension for {@link AppInfo}.
 *
 * @author dereekb
 *
 */
public class AppInfoFactoryImpl extends GoogleAppEngineContextualFactoryImpl<AppInfo> {

	private static final ModelKey TEST_KEY = new ModelKey(1L);

	private boolean overrideDevelopment = true;

	public boolean isOverrideDevelopment() {
		return this.overrideDevelopment;
	}

	public void setOverrideDevelopment(boolean overrideDevelopment) {
		this.overrideDevelopment = overrideDevelopment;
	}

	// MARK: Override
	@Override
	public AppInfo getDevelopmentSingleton() {
		AppInfo testInfo = super.getDevelopmentSingleton();

		if (testInfo == null && this.overrideDevelopment) {
			AppInfo appInfo = this.getProductionSingleton();

			/*
			 * Creates a hash of the app-key, which gives it a unique ID each
			 * time.
			 */
			Integer hash = HashUtility.simpleHash(appInfo.getAppName());

			AppInfoImpl info = new AppInfoImpl(appInfo);
			info.setModelKey(new ModelKey(hash));

			return info;
		} else {
			return testInfo;
		}
	}

	@Override
	public AppInfo getTestSingleton() {
		AppInfo testInfo = super.getTestSingleton();

		if (testInfo == null) {
			AppInfo appInfo = this.getProductionSingleton();

			AppInfoImpl info = new AppInfoImpl(appInfo);
			info.setModelKey(TEST_KEY);

			return info;
		} else {
			return testInfo;
		}
	}

	@Override
	public String toString() {
		return "AppInfoFactoryImpl [isAssertNotNull()=" + this.isAssertNotNull() + ", getDefaultSource()="
		        + this.getDefaultSource() + ", getProductionSource()=" + this.getProductionSource()
		        + ", getDevelopmentSource()=" + this.getDevelopmentSource() + "]";
	}

}
