package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomLocalModelChildrenRoleComponentConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomLocalModelContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomLocalModelCrudConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomLocalModelIterateControllerConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomLocalModelRoleSetLoaderConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.SecuredQueryInitializerConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link CustomLocalModelContextConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class CustomLocalModelContextConfigurerImpl extends AbstractCustomModelContextConfigurer
        implements CustomLocalModelContextConfigurer {

	private CustomLocalModelCrudConfigurer customLocalModelCrudConfigurer = new CustomLocalModelCrudConfigurerImpl();
	private CustomLocalModelIterateControllerConfigurer customLocalModelIterateControllerConfigurer = new CustomLocalModelIterateControllerConfigurerImpl();
	private CustomLocalModelRoleSetLoaderConfigurer customLocalModelRoleSetLoaderConfigurer = new CustomLocalModelRoleSetLoaderConfigurerImpl();
	private CustomLocalModelChildrenRoleComponentConfigurer customLocalModelChildrenRoleComponentConfigurer = new CustomLocalModelChildrenRoleComponentConfigurerImpl();
	private SecuredQueryInitializerConfigurer securedQueryInitializerConfigurer = new TodoSecuredQueryInitializerConfigurerImpl();

	public CustomLocalModelCrudConfigurer getCustomLocalModelCrudConfigurer() {
		return this.customLocalModelCrudConfigurer;
	}

	public void setCustomLocalModelCrudConfigurer(CustomLocalModelCrudConfigurer customLocalModelCrudConfigurer) {
		if (customLocalModelCrudConfigurer == null) {
			throw new IllegalArgumentException("customLocalModelCrudConfigurer cannot be null.");
		}

		this.customLocalModelCrudConfigurer = customLocalModelCrudConfigurer;
	}

	public CustomLocalModelIterateControllerConfigurer getCustomLocalModelIterateControllerConfigurer() {
		return this.customLocalModelIterateControllerConfigurer;
	}

	public void setCustomLocalModelIterateControllerConfigurer(CustomLocalModelIterateControllerConfigurer customLocalModelIterateControllerConfigurer) {
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
	public void configureCrudServiceComponents(AppConfiguration appConfig,
	                                           AppModelConfiguration modelConfig,
	                                           SpringBeansXMLBuilder builder) {
		this.customLocalModelCrudConfigurer.configureCrudServiceComponents(appConfig, modelConfig, builder);
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
