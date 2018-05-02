package com.dereekb.gae.extras.gen.app.config.app.services;

/**
 * Used for configuring important app services.
 *
 * @author dereekb
 *
 */
public interface AppServicesConfigurer {

	public AppLoginTokenSecurityConfigurer getAppLoginTokenSecurityConfigurer();

	public AppWebHookEventServiceConfigurer getAppWebHookEventServiceConfigurer();

}
