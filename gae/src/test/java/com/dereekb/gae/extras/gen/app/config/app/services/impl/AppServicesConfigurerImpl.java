package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.services.AppEventServiceListenersConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppLoginTokenSecurityConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppModelKeyEventListenerConfigurer;
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
	private AppEventServiceListenersConfigurer appEventServiceListenersConfigurer;
	private AppWebHookEventServiceConfigurer appWebHookEventServiceConfigurer;
	private AppModelKeyEventListenerConfigurer appModelKeyEventListenerConfigurer;

	public AppServicesConfigurerImpl(AppLoginTokenSecurityConfigurer appLoginTokenSecurityConfigurer,
	        AppEventServiceListenersConfigurer appEventServiceListenersConfigurer,
	        AppWebHookEventServiceConfigurer appWebHookEventServiceConfigurer,
	        AppModelKeyEventListenerConfigurer appModelKeyEventListenerConfigurer) {
		super();
		this.setAppLoginTokenSecurityConfigurer(appLoginTokenSecurityConfigurer);
		this.setAppEventServiceListenersConfigurer(appEventServiceListenersConfigurer);
		this.setAppWebHookEventServiceConfigurer(appWebHookEventServiceConfigurer);
		this.setAppModelKeyEventListenerConfigurer(appModelKeyEventListenerConfigurer);
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
	public AppEventServiceListenersConfigurer getAppEventServiceListenersConfigurer() {
		return this.appEventServiceListenersConfigurer;
	}

	public void setAppEventServiceListenersConfigurer(AppEventServiceListenersConfigurer appEventServiceListenersConfigurer) {
		if (appEventServiceListenersConfigurer == null) {
			throw new IllegalArgumentException("appEventServiceListenersConfigurer cannot be null.");
		}

		this.appEventServiceListenersConfigurer = appEventServiceListenersConfigurer;
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
	public AppModelKeyEventListenerConfigurer getAppModelKeyEventListenerConfigurer() {
		return this.appModelKeyEventListenerConfigurer;
	}

	public void setAppModelKeyEventListenerConfigurer(AppModelKeyEventListenerConfigurer appModelKeyEventListenerConfigurer) {
		if (appModelKeyEventListenerConfigurer == null) {
			throw new IllegalArgumentException("appModelKeyEventListenerConfigurer cannot be null.");
		}

		this.appModelKeyEventListenerConfigurer = appModelKeyEventListenerConfigurer;
	}

}
