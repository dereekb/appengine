package com.dereekb.gae.server.app.model.app.info.impl;

import com.dereekb.gae.server.app.model.app.info.SystemAppInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.data.HashUtility;
import com.dereekb.gae.utilities.gae.impl.GoogleAppEngineContextualFactoryImpl;

/**
 * {@link GoogleAppEngineContextualFactoryImpl} extension for {@link SystemAppInfo}.
 *
 * @author dereekb
 *
 */
public class SystemAppInfoFactoryImpl extends GoogleAppEngineContextualFactoryImpl<SystemAppInfo> {

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
	public SystemAppInfo getDevelopmentSingleton() {
		SystemAppInfo testInfo = super.getDevelopmentSingleton();

		if (testInfo == null && this.overrideDevelopment) {
			SystemAppInfo appInfo = this.getProductionSingleton();

			/*
			 * Creates a hash of the app-key, which gives it a unique ID each
			 * time.
			 */
			Integer hash = Math.abs(HashUtility.simpleHash(appInfo.getAppName()));

			SystemAppInfoImpl info = new SystemAppInfoImpl(appInfo);
			info.setModelKey(new ModelKey(hash));

			return info;
		} else {
			return testInfo;
		}
	}

	@Override
	public SystemAppInfo getTestSingleton() {
		SystemAppInfo testInfo = super.getTestSingleton();

		if (testInfo == null) {
			SystemAppInfo appInfo = this.getProductionSingleton();

			SystemAppInfoImpl info = new SystemAppInfoImpl(appInfo);
			info.setModelKey(TEST_KEY);

			return info;
		} else {
			return testInfo;
		}
	}

	@Override
	public String toString() {
		return "SystemAppInfoFactoryImpl [isAssertNotNull()=" + this.isAssertNotNull() + ", getDefaultSource()="
		        + this.getDefaultSource() + ", getProductionSource()=" + this.getProductionSource()
		        + ", getDevelopmentSource()=" + this.getDevelopmentSource() + "]";
	}

}
