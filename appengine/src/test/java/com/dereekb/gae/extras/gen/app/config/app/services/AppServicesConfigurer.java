package com.dereekb.gae.extras.gen.app.config.app.services;

/**
 * Used for configuring important app services.
 *
 * @author dereekb
 *
 */
public interface AppServicesConfigurer {

	/**
	 * @return {@link AppServerInitializationConfigurer}, or {@code null} if no
	 *         initialization is necessary.
	 */
	public AppServerInitializationConfigurer getAppServerInitializationConfigurer();

	public AppLoginTokenSecurityConfigurer getAppLoginTokenSecurityConfigurer();

	public AppEventServiceListenersConfigurer getAppEventServiceListenersConfigurer();

	public AppModelKeyEventListenerConfigurer getAppModelKeyEventListenerConfigurer();

	public AppWebHookEventServiceConfigurer getAppWebHookEventServiceConfigurer();

}
