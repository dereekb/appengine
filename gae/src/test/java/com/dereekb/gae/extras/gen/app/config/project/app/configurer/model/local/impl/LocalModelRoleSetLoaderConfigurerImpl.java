package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelRoleSetLoaderConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLArrayBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.ModelRoleSetLoaderImpl;

/**
 * Default {@link LocalModelRoleSetLoaderConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class LocalModelRoleSetLoaderConfigurerImpl
        implements LocalModelRoleSetLoaderConfigurer {

	@Override
	public void configureModelRoleSetLoaderComponents(AppConfiguration appConfig,
	                                                  LocalModelConfiguration modelConfig,
	                                                  SpringBeansXMLBuilder builder) {
		boolean madeBuilder = false;

		try {
			this.makeRoleBuilderComponent(modelConfig, builder);
			madeBuilder = true;
		} catch (ClassNotFoundException e) {
			builder.comment("TODO: Complete Adding Role Builders");
		}

		this.makeRoleSetLoaderComponent(modelConfig, builder, madeBuilder);
	}

	protected void makeRoleSetLoaderComponent(LocalModelConfiguration modelConfig,
	                                          SpringBeansXMLBuilder builder,
	                                          boolean addDefaultBuilderRef) {

		SpringBeansXMLArrayBuilder<?> rolesArrayBuilder = this.makeRoleSetLoaderBuilder(modelConfig, builder).c()
		        .array();

		if (addDefaultBuilderRef) {
			String defaultModelRoleBuilderComponentName = getDefaultModelRoleBuilderComponentName(modelConfig);
			rolesArrayBuilder.ref(defaultModelRoleBuilderComponentName);
		} else {
			rolesArrayBuilder.getRawXMLBuilder().comment("TODO: Add Component Refs Here");
		}
	}

	protected SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> saferMakeRoleBuilderComponent(LocalModelConfiguration modelConfig,
	                                                                                         SpringBeansXMLBuilder builder)
	        throws RuntimeException {
		try {
			return this.makeRoleBuilderComponent(modelConfig, builder);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	protected SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> makeRoleBuilderComponent(LocalModelConfiguration modelConfig,
	                                                                                    SpringBeansXMLBuilder builder)
	        throws ClassNotFoundException {
		String defaultModelRoleBuilderComponentName = getDefaultModelRoleBuilderComponentName(modelConfig);
		Class<?> defaultModelRoleBuilderClass = this.getDefaultModelRoleBuilderClass(modelConfig);
		return builder.bean(defaultModelRoleBuilderComponentName).beanClass(defaultModelRoleBuilderClass);
	}

	protected Class<?> getDefaultModelRoleBuilderClass(LocalModelConfiguration modelConfig)
	        throws ClassNotFoundException {
		String defaultModelRoleBuilderClassPath = modelConfig.getBaseClassPath() + ".security."
		        + modelConfig.getBaseClassSimpleName() + "ModelRoleBuilderComponent";
		return Class.forName(defaultModelRoleBuilderClassPath);
	}

	protected String getDefaultModelRoleBuilderComponentName(LocalModelConfiguration modelConfig) {
		String defaultModelRoleBuilderComponentName = modelConfig.getModelBeanPrefix() + "ModelRoleBuilderComponent";
		return defaultModelRoleBuilderComponentName;
	}

	protected SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> makeRoleSetLoaderBuilder(LocalModelConfiguration modelConfig,
	                                                                                    SpringBeansXMLBuilder builder) {
		String modelRoleSetLoader = modelConfig.getModelRoleSetLoaderBeanId();
		return builder.bean(modelRoleSetLoader).beanClass(ModelRoleSetLoaderImpl.class);
	}

}
