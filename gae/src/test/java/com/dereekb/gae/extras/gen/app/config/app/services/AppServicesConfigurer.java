package com.dereekb.gae.extras.gen.app.config.app.services;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;

/**
 * Used for configuring important app services.
 *
 * @author dereekb
 *
 */
public interface AppServicesConfigurer {

	/**
	 *
	 */
	public void getLoginSecurityConfigurer();

	/**
	 *
	 */
	public void getWebHookServiceConfigurer();

	/**
	 *
	 */
	public void configureRemoteServices(AppConfiguration appConfig);

}
