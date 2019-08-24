package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.services.AppDebugApiConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppEventServiceListenersConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppLoginTokenSecurityConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppMailServiceConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppModelKeyEventListenerConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServerInitializationConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServicesConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppTaskSchedulerEnqueuerConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppWebHookEventServiceConfigurer;

/**
 * {@link AppServicesConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class AppServicesConfigurerImpl
        implements AppServicesConfigurer {

	private AppServerInitializationConfigurer appServerInitializationConfigurer;
	private AppLoginTokenSecurityConfigurer appLoginTokenSecurityConfigurer;
	private AppEventServiceListenersConfigurer appEventServiceListenersConfigurer;
	private AppWebHookEventServiceConfigurer appWebHookEventServiceConfigurer;
	private AppModelKeyEventListenerConfigurer appModelKeyEventListenerConfigurer;
	private AppMailServiceConfigurer appMailServiceConfigurer;
	private AppTaskSchedulerEnqueuerConfigurer appTaskSchedulerEnqueuerConfigurer = new AppTaskSchedulerEnqueuerConfigurerImpl();
	private AppDebugApiConfigurer appDebugConfigurer = null;

	public AppServicesConfigurerImpl(AppServerInitializationConfigurer appServerInitializationConfigurer,
	        AppLoginTokenSecurityConfigurer appLoginTokenSecurityConfigurer,
	        AppEventServiceListenersConfigurer appEventServiceListenersConfigurer,
	        AppWebHookEventServiceConfigurer appWebHookEventServiceConfigurer,
	        AppModelKeyEventListenerConfigurer appModelKeyEventListenerConfigurer,
	        AppMailServiceConfigurer appMailServiceConfigurer,
	        AppTaskSchedulerEnqueuerConfigurer appTaskSchedulerEnqueuerConfigurer) {
		this(appServerInitializationConfigurer, appLoginTokenSecurityConfigurer, appEventServiceListenersConfigurer,
		        appWebHookEventServiceConfigurer, appModelKeyEventListenerConfigurer, appMailServiceConfigurer);
		this.setAppTaskSchedulerEnqueuerConfigurer(appTaskSchedulerEnqueuerConfigurer);
	}

	public AppServicesConfigurerImpl(AppServerInitializationConfigurer appServerInitializationConfigurer,
	        AppLoginTokenSecurityConfigurer appLoginTokenSecurityConfigurer,
	        AppEventServiceListenersConfigurer appEventServiceListenersConfigurer,
	        AppWebHookEventServiceConfigurer appWebHookEventServiceConfigurer,
	        AppModelKeyEventListenerConfigurer appModelKeyEventListenerConfigurer,
	        AppMailServiceConfigurer appMailServiceConfigurer) {
		super();
		this.setAppServerInitializationConfigurer(appServerInitializationConfigurer);
		this.setAppLoginTokenSecurityConfigurer(appLoginTokenSecurityConfigurer);
		this.setAppEventServiceListenersConfigurer(appEventServiceListenersConfigurer);
		this.setAppWebHookEventServiceConfigurer(appWebHookEventServiceConfigurer);
		this.setAppModelKeyEventListenerConfigurer(appModelKeyEventListenerConfigurer);
		this.setAppMailServiceConfigurer(appMailServiceConfigurer);
	}

	@Override
	public AppServerInitializationConfigurer getAppServerInitializationConfigurer() {
		return this.appServerInitializationConfigurer;
	}

	public void setAppServerInitializationConfigurer(AppServerInitializationConfigurer appServerInitializationConfigurer) {
		this.appServerInitializationConfigurer = appServerInitializationConfigurer;
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
	public AppTaskSchedulerEnqueuerConfigurer getAppTaskSchedulerEnqueuerConfigurer() {
		return this.appTaskSchedulerEnqueuerConfigurer;
	}

	public void setAppTaskSchedulerEnqueuerConfigurer(AppTaskSchedulerEnqueuerConfigurer appTaskSchedulerEnqueuerConfigurer) {
		if (appTaskSchedulerEnqueuerConfigurer == null) {
			throw new IllegalArgumentException("appTaskSchedulerEnqueuerConfigurer cannot be null.");
		}

		this.appTaskSchedulerEnqueuerConfigurer = appTaskSchedulerEnqueuerConfigurer;
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

	@Override
	public AppMailServiceConfigurer getAppMailServiceConfigurer() {
		return this.appMailServiceConfigurer;
	}

	public void setAppMailServiceConfigurer(AppMailServiceConfigurer appMailServiceConfigurer) {
		if (appMailServiceConfigurer == null) {
			throw new IllegalArgumentException("appMailServiceConfigurer cannot be null.");
		}

		this.appMailServiceConfigurer = appMailServiceConfigurer;
	}

	public AppDebugApiConfigurer getAppDebugApiConfigurer() {
		return this.appDebugConfigurer;
	}

	public void setAppDebugConfigurer(AppDebugApiConfigurer appDebugConfigurer) {
		this.appDebugConfigurer = appDebugConfigurer;
	}

}
