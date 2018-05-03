package com.dereekb.gae.extras.gen.app.config.app.services;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Used for configuring a service's login token security.
 * <p>
 * Implementations may leverage local resources, or remote ones.
 *
 * @author dereekb
 *
 */
public interface AppLoginTokenSecurityConfigurer {

	/**
	 * Configures the login token security.
	 *
	 * @param appConfig
	 * @param builder
	 */
	public void configureLoginTokenSecurityServiceComponents(AppConfiguration appConfig,
	                                        SpringBeansXMLBuilder builder);

}
