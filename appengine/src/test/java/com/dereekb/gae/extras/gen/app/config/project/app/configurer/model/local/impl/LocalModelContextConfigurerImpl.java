package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl;

import java.util.Map;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.SecuredQueryInitializerConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelApiControllerConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelChildrenRoleComponentConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelCrudConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelIterateControllerConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelRoleSetLoaderConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelSearchConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.utilities.task.impl.NoOpTask;

/**
 * {@link LocalModelContextConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class LocalModelContextConfigurerImpl
        implements LocalModelContextConfigurer {

	private LocalModelCrudConfigurer customLocalModelCrudConfigurer = new LocalModelCrudConfigurerImpl();
	private LocalModelSearchConfigurer customLocalModelSearchConfigurer = new NoOpLocalModelSearchConfigurerImpl();
	private LocalModelIterateControllerConfigurer customLocalModelIterateControllerConfigurer = new LocalModelIterateControllerConfigurerImpl();
	private LocalModelApiControllerConfigurer customLocalModelApiControllerConfigurer = new LocalModelApiControllerConfigurerImpl();
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

	public LocalModelSearchConfigurer getCustomLocalModelSearchConfigurer() {
		return this.customLocalModelSearchConfigurer;
	}

	public void setCustomLocalModelSearchConfigurer(LocalModelSearchConfigurer customLocalModelSearchConfigurer) {
		if (customLocalModelSearchConfigurer == null) {
			throw new IllegalArgumentException("customLocalModelSearchConfigurer cannot be null.");
		}

		this.customLocalModelSearchConfigurer = customLocalModelSearchConfigurer;
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

	public LocalModelApiControllerConfigurer getCustomLocalModelApiControllerConfigurer() {
		return this.customLocalModelApiControllerConfigurer;
	}

	public void setCustomLocalModelApiControllerConfigurer(LocalModelApiControllerConfigurer customLocalModelApiControllerConfigurer) {
		if (customLocalModelApiControllerConfigurer == null) {
			throw new IllegalArgumentException("customLocalModelApiControllerConfigurer cannot be null.");
		}

		this.customLocalModelApiControllerConfigurer = customLocalModelApiControllerConfigurer;
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

	public LocalModelEventListenerConfigurer getCustomLocalModelEventListenerConfigurer() {
		return this.customLocalModelEventListenerConfigurer;
	}

	public void setCustomLocalModelEventListenerConfigurer(LocalModelEventListenerConfigurer customLocalModelEventListenerConfigurer) {
		if (customLocalModelEventListenerConfigurer == null) {
			throw new IllegalArgumentException("customLocalModelEventListenerConfigurer cannot be null.");
		}

		this.customLocalModelEventListenerConfigurer = customLocalModelEventListenerConfigurer;
	}

	// MARK: CustomLocalModelContextConfigurer
	@Override
	public void configureSecuredQueryInitializer(AppConfiguration appConfig,
	                                             LocalModelConfiguration modelConfig,
	                                             String securedQueryInitializerDelegateId,
	                                             SpringBeansXMLBuilder builder) {
		this.securedQueryInitializerConfigurer.configureSecuredQueryInitializer(appConfig, modelConfig,
		        securedQueryInitializerDelegateId, builder);
	}

	// MARK: Custom Classes
	public static class TodoSecuredQueryInitializerConfigurerImpl
	        implements SecuredQueryInitializerConfigurer {

		@Override
		public void configureSecuredQueryInitializer(AppConfiguration appConfig,
		                                             LocalModelConfiguration modelConfig,
		                                             String securedQueryInitializerDelegateId,
		                                             SpringBeansXMLBuilder builder) {
			builder.comment("TODO: Complete Configuration");
			builder.bean(securedQueryInitializerDelegateId).beanClass(NoOpTask.class);
		}

	}

	@Override
	public boolean hasSearchComponents() {
		return this.customLocalModelSearchConfigurer.hasSearchComponents();
	}

	@Override
	public void configureSearchComponents(AppConfiguration appConfig,
	                                      LocalModelConfiguration modelConfig,
	                                      SpringBeansXMLBuilder builder) {
		this.customLocalModelSearchConfigurer.configureSearchComponents(appConfig, modelConfig, builder);
	}

	@Override
	public void configureCrudServiceComponents(AppConfiguration appConfig,
	                                           LocalModelConfiguration modelConfig,
	                                           SpringBeansXMLBuilder builder) {
		this.customLocalModelCrudConfigurer.configureCrudServiceComponents(appConfig, modelConfig, builder);
	}

	@Override
	public void configureApiEditController(AppConfiguration appConfig,
	                                       LocalModelConfiguration modelConfig,
	                                       SpringBeansXMLBuilder builder) {
		this.customLocalModelApiControllerConfigurer.configureApiEditController(appConfig, modelConfig, builder);
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
