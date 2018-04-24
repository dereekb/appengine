package com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.CustomLocalModelContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.SecuredQueryInitializerConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;

/**
 * {@link CustomLocalModelContextConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class CustomLocalModelContextConfigurerImpl
        implements CustomLocalModelContextConfigurer {

	private SecuredQueryInitializerConfigurer securedQueryInitializerConfigurer = new TodoSecuredQueryInitializerConfigurerImpl();

	public SecuredQueryInitializerConfigurer getSecuredQueryInitializerConfigurer() {
		return this.securedQueryInitializerConfigurer;
	}

	public void setSecuredQueryInitializerConfigurer(SecuredQueryInitializerConfigurer securedQueryInitializerConfigurer) {
		if (securedQueryInitializerConfigurer == null) {
			throw new IllegalArgumentException("securedQueryInitializerConfigurer cannot be null.");
		}

		this.securedQueryInitializerConfigurer = securedQueryInitializerConfigurer;
	}

	// MARK: CustomLocalModelContextConfigurer
	@Override
	public void configureSecuredQueryInitializer(AppConfiguration appConfig,
	                                             AppModelConfiguration modelConfig,
	                                             SpringBeansXMLBeanConstructorBuilder<?> beanConstructor) {
		this.securedQueryInitializerConfigurer.configureSecuredQueryInitializer(appConfig, modelConfig,
		        beanConstructor);
	}

	// MARK: Custom Classes
	public static class TodoSecuredQueryInitializerConfigurerImpl
	        implements SecuredQueryInitializerConfigurer {

		@Override
		public void configureSecuredQueryInitializer(AppConfiguration appConfig,
		                                             AppModelConfiguration modelConfig,
		                                             SpringBeansXMLBeanConstructorBuilder<?> beanConstructor) {
			beanConstructor.nextArgBuilder().comment("TODO: Complete Configuration");
		}

	}

}
