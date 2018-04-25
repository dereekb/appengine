package com.dereekb.gae.extras.gen.app.config.project.app.context.model.impl;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.CustomLocalModelRoleSetLoaderConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLArrayBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.ModelRoleSetLoaderImpl;

/**
 * Default {@link CustomLocalModelRoleSetLoaderConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class CustomLocalModelRoleSetLoaderConfigurerImpl
        implements CustomLocalModelRoleSetLoaderConfigurer {

	@Override
	public void configureModelRoleSetLoaderComponents(AppConfiguration appConfig,
	                                                  AppModelConfiguration modelConfig,
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

	protected void makeRoleSetLoaderComponent(AppModelConfiguration modelConfig,
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

	protected SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> saferMakeRoleBuilderComponent(AppModelConfiguration modelConfig,
	                                                                                         SpringBeansXMLBuilder builder)
	        throws RuntimeException {
		try {
			return this.makeRoleBuilderComponent(modelConfig, builder);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	protected SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> makeRoleBuilderComponent(AppModelConfiguration modelConfig,
	                                                                                    SpringBeansXMLBuilder builder)
	        throws ClassNotFoundException {
		String defaultModelRoleBuilderComponentName = getDefaultModelRoleBuilderComponentName(modelConfig);
		Class<?> defaultModelRoleBuilderClass = this.getDefaultModelRoleBuilderClass(modelConfig);
		return builder.bean(defaultModelRoleBuilderComponentName).beanClass(defaultModelRoleBuilderClass);
	}

	protected Class<?> getDefaultModelRoleBuilderClass(AppModelConfiguration modelConfig)
	        throws ClassNotFoundException {
		String defaultModelRoleBuilderClassPath = modelConfig.getBaseClassPath() + ".security."
		        + modelConfig.getBaseClassSimpleName() + "ModelRoleBuilderComponent";
		return Class.forName(defaultModelRoleBuilderClassPath);
	}

	protected String getDefaultModelRoleBuilderComponentName(AppModelConfiguration modelConfig) {
		String defaultModelRoleBuilderComponentName = modelConfig.getModelBeanPrefix() + "ModelRoleBuilderComponent";
		return defaultModelRoleBuilderComponentName;
	}

	protected SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> makeRoleSetLoaderBuilder(AppModelConfiguration modelConfig,
	                                                                                    SpringBeansXMLBuilder builder) {
		String modelRoleSetLoader = modelConfig.getModelRoleSetLoaderBeanId();
		return builder.bean(modelRoleSetLoader).beanClass(ModelRoleSetLoaderImpl.class);
	}

}
