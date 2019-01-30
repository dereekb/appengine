package com.dereekb.gae.server.app.model.app.info.impl;

import com.dereekb.gae.server.app.model.app.info.AppDomainInfo;

/**
 * {@link AppDomainInfo} implementation.
 *
 * @author dereekb
 *
 */
public class AppDomainInfoImpl
        implements AppDomainInfo {

	private String prefix = "https://";
	private String appDomain;

	public AppDomainInfoImpl(String appDomain) {
		this.setAppDomain(appDomain);
	}

	// MARK: AppDomainInfo
	@Override
	public String getAppDomain() {
		return this.appDomain;
	}

	public void setAppDomain(String appDomain) {
		if (appDomain == null) {
			throw new IllegalArgumentException("appDomain cannot be null.");
		}

		this.appDomain = appDomain;
	}

	@Override
	public String getAppDomainUrl() {
		return this.prefix + this.appDomain;
	}

	@Override
	public String toString() {
		return "AppDomainInfoImpl [prefix=" + this.prefix + ", appDomain=" + this.appDomain + "]";
	}

}
