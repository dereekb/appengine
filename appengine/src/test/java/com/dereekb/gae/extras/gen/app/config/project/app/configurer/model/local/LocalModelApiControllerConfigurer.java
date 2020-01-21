package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Configurer for the API components for a type.
 *
 * @author dereekb
 *
 */
public interface LocalModelApiControllerConfigurer {

	/**
	 * Configures the API Edit Controller for an app, if applicable.
	 */
	public void configureApiEditController(AppConfiguration appConfig,
	                                       LocalModelConfiguration modelConfig,
	                                       SpringBeansXMLBuilder builder);

}
