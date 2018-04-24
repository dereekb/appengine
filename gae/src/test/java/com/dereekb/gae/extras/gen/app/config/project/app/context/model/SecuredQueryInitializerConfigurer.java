package com.dereekb.gae.extras.gen.app.config.project.app.context.model;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;

/**
 * Used to configure the secured query initializer for a model.
 *
 * @author dereekb
 *
 */
public interface SecuredQueryInitializerConfigurer {

	public void configureSecuredQueryInitializer(AppConfiguration appConfig,
	                                             AppModelConfiguration modelConfig,
	                                             SpringBeansXMLBeanConstructorBuilder<?> beanConstructor);

}
