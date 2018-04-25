package com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.CustomLocalModelChildrenRoleComponentConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.CustomLocalModelContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.CustomLocalModelIterateControllerConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.CustomLocalModelRoleSetLoaderConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.SecuredQueryInitializerConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link CustomLocalModelContextConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class CustomLocalModelContextConfigurerImpl
        implements CustomLocalModelContextConfigurer {

	private CustomLocalModelIterateControllerConfigurer customLocalModelIterateControllerConfigurer = new CustomLocalModelIterateControllerConfigurerImpl();
	private CustomLocalModelRoleSetLoaderConfigurer customLocalModelRoleSetLoaderConfigurer = new CustomLocalModelRoleSetLoaderConfigurerImpl();
	private CustomLocalModelChildrenRoleComponentConfigurer customLocalModelChildrenRoleComponentConfigurer = new CustomLocalModelChildrenRoleComponentConfigurerImpl();
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

	public CustomLocalModelRoleSetLoaderConfigurer getCustomLocalModelRoleSetLoaderConfigurer() {
		return this.customLocalModelRoleSetLoaderConfigurer;
	}

	public void setCustomLocalModelRoleSetLoaderConfigurer(CustomLocalModelRoleSetLoaderConfigurer customLocalModelRoleSetLoaderConfigurer) {
		if (customLocalModelRoleSetLoaderConfigurer == null) {
			throw new IllegalArgumentException("customLocalModelRoleSetLoaderConfigurer cannot be null.");
		}

		this.customLocalModelRoleSetLoaderConfigurer = customLocalModelRoleSetLoaderConfigurer;
	}

	public CustomLocalModelChildrenRoleComponentConfigurer getCustomLocalModelChildrenRoleComponentConfigurer() {
		return this.customLocalModelChildrenRoleComponentConfigurer;
	}

	public void setCustomLocalModelChildrenRoleComponentConfigurer(CustomLocalModelChildrenRoleComponentConfigurer customLocalModelChildrenRoleComponentConfigurer) {
		if (customLocalModelChildrenRoleComponentConfigurer == null) {
			throw new IllegalArgumentException("customLocalModelChildrenRoleComponentConfigurer cannot be null.");
		}

		this.customLocalModelChildrenRoleComponentConfigurer = customLocalModelChildrenRoleComponentConfigurer;
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

	@Override
	public void configureModelRoleSetLoaderComponents(AppConfiguration appConfig,
	                                                  AppModelConfiguration modelConfig,
	                                                  SpringBeansXMLBuilder builder) {
		this.customLocalModelRoleSetLoaderConfigurer.configureModelRoleSetLoaderComponents(appConfig, modelConfig,
		        builder);
	}

	@Override
	public void configureModelChildrenRoleComponents(AppConfiguration appConfig,
	                                                 AppModelConfiguration modelConfig,
	                                                 SpringBeansXMLBuilder builder) {
		this.customLocalModelChildrenRoleComponentConfigurer.configureModelChildrenRoleComponents(appConfig,
		        modelConfig, builder);
	}

	@Override
	public boolean hasIterateControllerEntry() {
		return this.customLocalModelIterateControllerConfigurer.hasIterateControllerEntry();
	}

	@Override
	public void configureIterateControllerTasks(AppConfiguration appConfig,
	                                            AppModelConfiguration modelConfig,
	                                            SpringBeansXMLBuilder builder) {
		this.customLocalModelIterateControllerConfigurer.configureIterateControllerTasks(appConfig, modelConfig,
		        builder);
	}

}
