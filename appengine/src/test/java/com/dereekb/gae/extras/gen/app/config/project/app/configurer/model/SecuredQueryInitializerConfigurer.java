package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;

/**
 * Used to configure the secured query initializer for a model.
 *
 * @author dereekb
 *
 */
public interface SecuredQueryInitializerConfigurer {

	public void configureSecuredQueryInitializer(AppConfiguration appConfig,
	                                             LocalModelConfiguration modelConfig,
	                                             SpringBeansXMLBeanConstructorBuilder<?> beanConstructor);

}