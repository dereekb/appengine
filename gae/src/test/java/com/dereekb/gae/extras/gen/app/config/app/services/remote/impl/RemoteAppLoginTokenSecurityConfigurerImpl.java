package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppLoginTokenSecurityConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link AppLoginTokenSecurityConfigurer} implementation for a remote login
 * service.
 *
 * @author dereekb
 *
 */
public class RemoteAppLoginTokenSecurityConfigurerImpl
        implements AppLoginTokenSecurityConfigurer {

	@Override
	public void configureLoginTokenSecurityServiceComponents(AppConfiguration appConfig,
	                                        SpringBeansXMLBuilder builder) {

		// TODO: For Test, use local dencoder.
		// TODO: For Dev, send requests to the dev login service.
		// TODO: For Prod, send requests to the prod login service.

	}

}

