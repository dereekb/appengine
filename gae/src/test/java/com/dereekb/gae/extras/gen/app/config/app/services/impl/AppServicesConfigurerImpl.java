package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.services.AppLoginTokenSecurityConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServicesConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppWebHookEventServiceConfigurer;

/**
 * {@link AppServicesConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class AppServicesConfigurerImpl
        implements AppServicesConfigurer {

	private AppLoginTokenSecurityConfigurer appLoginTokenSecurityConfigurer;
	private AppWebHookEventServiceConfigurer appWebHookEventServiceConfigurer;

	public AppServicesConfigurerImpl(AppLoginTokenSecurityConfigurer appLoginTokenSecurityConfigurer,
	        AppWebHookEventServiceConfigurer appWebHookEventServiceConfigurer) {
		super();
		this.setAppLoginTokenSecurityConfigurer(appLoginTokenSecurityConfigurer);
		this.setAppWebHookEventServiceConfigurer(appWebHookEventServiceConfigurer);
	}

	@Override
	public AppLoginTokenSecurityConfigurer getAppLoginTokenSecurityConfigurer() {
		return this.appLoginTokenSecurityConfigurer;
	}

	public void setAppLoginTokenSecurityConfigurer(AppLoginTokenSecurityConfigurer appLoginTokenSecurityConfigurer) {
		if (appLoginTokenSecurityConfigurer == null) {
			throw new IllegalArgumentException("appLoginTokenSecurityConfigurer cannot be null.");
		}

		this.appLoginTokenSecurityConfigurer = appLoginTokenSecurityConfigurer;
	}

	@Override
	public AppWebHookEventServiceConfigurer getAppWebHookEventServiceConfigurer() {
		return this.appWebHookEventServiceConfigurer;
	}

	public void setAppWebHookEventServiceConfigurer(AppWebHookEventServiceConfigurer appWebHookEventServiceConfigurer) {
		if (appWebHookEventServiceConfigurer == null) {
			throw new IllegalArgumentException("appWebHookEventServiceConfigurer cannot be null.");
		}

		this.appWebHookEventServiceConfigurer = appWebHookEventServiceConfigurer;
	}

}
