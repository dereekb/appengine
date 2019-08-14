package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Used to configure the secured query initializer for a model.
 *
 * @author dereekb
 *
 */
public interface SecuredQueryInitializerConfigurer {

	/**
	 * Configures a new bean with the input identifier.
	 * <p>
	 * A new bean should exist on the system that has the
	 * securedQueryInitializerDelegateId id.
	 *
	 * @param appConfig
	 * @param modelConfig
	 * @param securedQueryInitializerDelegateId
	 * @param builder
	 */
	public void configureSecuredQueryInitializer(AppConfiguration appConfig,
	                                             LocalModelConfiguration modelConfig,
	                                             String securedQueryInitializerDelegateId,
	                                             SpringBeansXMLBuilder builder);

}
