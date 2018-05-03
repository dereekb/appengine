package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppLoginTokenSecurityConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link AppLoginTokenSecurityConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class AbstractAppLoginTokenSecurityConfigurerImpl
        implements AppLoginTokenSecurityConfigurer {

	// MARK: AppLoginTokenSecurityConfigurer
	@Override
	public void configureLoginTokenSecurityServiceComponents(AppConfiguration appConfig,
	                                        SpringBeansXMLBuilder builder) {

	}

}
