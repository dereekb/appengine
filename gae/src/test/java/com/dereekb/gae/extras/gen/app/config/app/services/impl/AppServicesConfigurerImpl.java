package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.services.AppEventListenerConfigurer;
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
	private AppEventListenerConfigurer appEventListenerConfigurer;

	public AppServicesConfigurerImpl(AppLoginTokenSecurityConfigurer appLoginTokenSecurityConfigurer,
	        AppWebHookEventServiceConfigurer appWebHookEventServiceConfigurer,
	        AppEventListenerConfigurer appEventListenerConfigurer) {
		super();
		this.setAppLoginTokenSecurityConfigurer(appLoginTokenSecurityConfigurer);
		this.setAppWebHookEventServiceConfigurer(appWebHookEventServiceConfigurer);
		this.setAppEventListenerConfigurer(appEventListenerConfigurer);
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

	@Override
	public AppEventListenerConfigurer getAppEventListenerConfigurer() {
		return this.appEventListenerConfigurer;
	}

	public void setAppEventListenerConfigurer(AppEventListenerConfigurer appEventListenerConfigurer) {
		if (appEventListenerConfigurer == null) {
			throw new IllegalArgumentException("appEventListenerConfigurer cannot be null.");
		}

		this.appEventListenerConfigurer = appEventListenerConfigurer;
	}

}
