package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Used for configuring the CRUD services for the local model.
 *
 * @author dereekb
 *
 */
public interface LocalModelCrudConfigurer {

	public void configureCrudServiceComponents(AppConfiguration appConfig,
	                                           LocalModelConfiguration modelConfig,
	                                           SpringBeansXMLBuilder builder);

}
