package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Used for configuring the model role set loader for a local model.
 *
 * @author dereekb
 *
 */
public interface LocalModelRoleSetLoaderConfigurer {

	public void configureModelRoleSetLoaderComponents(AppConfiguration appConfig,
	                                                  LocalModelConfiguration modelConfig,
	                                                  SpringBeansXMLBuilder builder);

}
