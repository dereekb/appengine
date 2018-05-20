package com.dereekb.gae.extras.gen.app.config.app.services;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.auth.security.system.SystemLoginTokenFactory;

/**
 * Used for configuring the {@link SystemLoginTokenFactory}.
 *
 * @author dereekb
 *
 */
public interface SystemLoginTokenFactoryConfigurer {

	// Login Token System Factory
	public void configureSystemLoginTokenFactory(AppConfiguration appConfig,
	                                             SpringBeansXMLBuilder beanBuilder);

}
