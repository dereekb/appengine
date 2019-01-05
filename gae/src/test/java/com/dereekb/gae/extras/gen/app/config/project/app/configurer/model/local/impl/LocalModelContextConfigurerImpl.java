package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl;

import java.util.Map;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.SecuredQueryInitializerConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelChildrenRoleComponentConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelCrudConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelIterateControllerConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelRoleSetLoaderConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link LocalModelContextConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class LocalModelContextConfigurerImpl
        implements LocalModelContextConfigurer {

	private LocalModelCrudConfigurer customLocalModelCrudConfigurer = new LocalModelCrudConfigurerImpl();
	private LocalModelIterateControllerConfigurer customLocalModelIterateControllerConfigurer = new LocalModelIterateControllerConfigurerImpl();
	private LocalModelRoleSetLoaderConfigurer customLocalModelRoleSetLoaderConfigurer = new LocalModelRoleSetLoaderConfigurerImpl();
	private LocalModelChildrenRoleComponentConfigurer customLocalModelChildrenRoleComponentConfigurer = new LocalModelChildrenRoleComponentConfigurerImpl();
	private LocalModelEventListenerConfigurer customLocalModelEventListenerConfigurer = new NoOpCustomLocalModelEventListenerConfigurerImpl();
	private SecuredQueryInitializerConfigurer securedQueryInitializerConfigurer = new TodoSecuredQueryInitializerConfigurerImpl();

	public LocalModelCrudConfigurer getCustomLocalModelCrudConfigurer() {
		return this.customLocalModelCrudConfigurer;
	}

	public void setCustomLocalModelCrudConfigurer(LocalModelCrudConfigurer customLocalModelCrudConfigurer) {
		if (customLocalModelCrudConfigurer == null) {
			throw new IllegalArgumentException("customLocalModelCrudConfigurer cannot be null.");
		}

		this.customLocalModelCrudConfigurer = customLocalModelCrudConfigurer;
	}

	public LocalModelIterateControllerConfigurer getCustomLocalModelIterateControllerConfigurer() {
		return this.customLocalModelIterateControllerConfigurer;
	}

	public void setCustomLocalModelIterateControllerConfigurer(LocalModelIterateControllerConfigurer customLocalModelIterateControllerConfigurer) {
		if (customLocalModelIterateControllerConfigurer == null) {
			throw new IllegalArgumentException("customLocalModelIterateControllerConfigurer cannot be null.");
		}

		this.customLocalModelIterateControllerConfigurer = customLocalModelIterateControllerConfigurer;
	}

	public SecuredQueryInitializerConfigurer getSecuredQueryInitializerConfigurer() {
		return this.securedQueryInitializerConfigurer;
	}

	public void setSecuredQueryInitializerConfigurer(SecuredQueryInitializerConfigurer securedQueryInitializerConfigurer) {
		if (securedQueryInitializerConfigurer == null) {
			throw new IllegalArgumentException("securedQueryInitializerConfigurer cannot be null.");
		}

		this.securedQueryInitializerConfigurer = securedQueryInitializerConfigurer;
	}

	public LocalModelRoleSetLoaderConfigurer getCustomLocalModelRoleSetLoaderConfigurer() {
		return this.customLocalModelRoleSetLoaderConfigurer;
	}

	public void setCustomLocalModelRoleSetLoaderConfigurer(LocalModelRoleSetLoaderConfigurer customLocalModelRoleSetLoaderConfigurer) {
		if (customLocalModelRoleSetLoaderConfigurer == null) {
			throw new IllegalArgumentException("customLocalModelRoleSetLoaderConfigurer cannot be null.");
		}

		this.customLocalModelRoleSetLoaderConfigurer = customLocalModelRoleSetLoaderConfigurer;
	}

	public LocalModelChildrenRoleComponentConfigurer getCustomLocalModelChildrenRoleComponentConfigurer() {
		return this.customLocalModelChildrenRoleComponentConfigurer;
	}

	public void setCustomLocalModelChildrenRoleComponentConfigurer(LocalModelChildrenRoleComponentConfigurer customLocalModelChildrenRoleComponentConfigurer) {
		if (customLocalModelChildrenRoleComponentConfigurer == null) {
			throw new IllegalArgumentException("customLocalModelChildrenRoleComponentConfigurer cannot be null.");
		}

		this.customLocalModelChildrenRoleComponentConfigurer = customLocalModelChildrenRoleComponentConfigurer;
	}

	// MARK: CustomLocalModelContextConfigurer
	@Override
	public void configureSecuredQueryInitializer(AppConfiguration appConfig,
	                                             LocalModelConfiguration modelConfig,
	                                             SpringBeansXMLBeanConstructorBuilder<?> beanConstructor) {
		this.securedQueryInitializerConfigurer.configureSecuredQueryInitializer(appConfig, modelConfig,
		        beanConstructor);
	}

	// MARK: Custom Classes
	public static class TodoSecuredQueryInitializerConfigurerImpl
	        implements SecuredQueryInitializerConfigurer {

		@Override
		public void configureSecuredQueryInitializer(AppConfiguration appConfig,
		                                             LocalModelConfiguration modelConfig,
		                                             SpringBeansXMLBeanConstructorBuilder<?> beanConstructor) {
			beanConstructor.nextArgBuilder().comment("TODO: Complete Configuration");
		}

	}

	@Override
	public void configureCrudServiceComponents(AppConfiguration appConfig,
	                                           LocalModelConfiguration modelConfig,
	                                           SpringBeansXMLBuilder builder) {
		this.customLocalModelCrudConfigurer.configureCrudServiceComponents(appConfig, modelConfig, builder);
	}

	@Override
	public void configureModelRoleSetLoaderComponents(AppConfiguration appConfig,
	                                                  LocalModelConfiguration modelConfig,
	                                                  SpringBeansXMLBuilder builder) {
		this.customLocalModelRoleSetLoaderConfigurer.configureModelRoleSetLoaderComponents(appConfig, modelConfig,
		        builder);
	}

	@Override
	public void configureModelChildrenRoleComponents(AppConfiguration appConfig,
	                                                 LocalModelConfiguration modelConfig,
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
	                                            LocalModelConfiguration modelConfig,
	                                            SpringBeansXMLBuilder builder) {
		this.customLocalModelIterateControllerConfigurer.configureIterateControllerTasks(appConfig, modelConfig,
		        builder);
	}

	@Override
	public Map<String, String> configureEventListenerEntries(AppConfiguration appConfiguration,
	                                                         LocalModelConfiguration modelConfig,
	                                                         SpringBeansXMLBuilder builder) {
		return this.customLocalModelEventListenerConfigurer.configureEventListenerEntries(appConfiguration, modelConfig,
		        builder);
	}

}
