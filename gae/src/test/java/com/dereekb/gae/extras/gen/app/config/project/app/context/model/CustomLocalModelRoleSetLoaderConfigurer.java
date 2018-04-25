package com.dereekb.gae.extras.gen.app.config.project.app.context.model;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Used for configuring the model role set loader for a local model.
 *
 * @author dereekb
 *
 */
public interface CustomLocalModelRoleSetLoaderConfigurer {

	public void configureModelRoleSetLoaderComponents(AppConfiguration appConfig,
	                                                  AppModelConfiguration modelConfig,
	                                                  SpringBeansXMLBuilder builder);

}
