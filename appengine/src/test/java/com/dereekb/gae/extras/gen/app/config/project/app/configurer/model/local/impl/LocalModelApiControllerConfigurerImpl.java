package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelApiControllerConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.web.api.model.crud.controller.impl.EditModelControllerConversionDelegateImpl;
import com.dereekb.gae.web.api.model.crud.controller.impl.EditModelControllerDelegateImpl;

/**
 * Default {@link LocalModelApiControllerConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class LocalModelApiControllerConfigurerImpl
        implements LocalModelApiControllerConfigurer {

	// MARK: LocalModelApiControllerConfigurer
	@Override
	public final void configureApiEditController(AppConfiguration appConfig,
	                                             LocalModelConfiguration modelConfig,
	                                             SpringBeansXMLBuilder builder) {

		String editControllerBeanId = modelConfig.getModelBeanPrefix() + "EditController";

		if (modelConfig.hasCreateService() || modelConfig.hasUpdateService() || modelConfig.hasDeleteService()) {

			SpringBeansXMLBeanConstructorBuilder<SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder>> editControllerConstructorBuilder = builder
			        .bean(editControllerBeanId).beanClass(modelConfig.getModelEditControllerClass()).c()
			        .ref(modelConfig.getModelBeanPrefix() + "EditControllerDelegate")
			        .ref(modelConfig.getModelBeanPrefix() + "EditControllerConversionDelegate");

			this.configureAdditionalEditControllerConstructorArgs(appConfig, modelConfig, builder,
			        editControllerConstructorBuilder);

			builder.bean(modelConfig.getModelBeanPrefix() + "EditControllerDelegate")
			        .beanClass(EditModelControllerDelegateImpl.class).c()
			        .ref(modelConfig.getModelBeanPrefix() + "CrudService");

			builder.bean(modelConfig.getModelBeanPrefix() + "EditControllerConversionDelegate")
			        .beanClass(EditModelControllerConversionDelegateImpl.class).c()
			        .ref(modelConfig.getModelTypeBeanId()).ref(modelConfig.getStringModelKeyConverter())
			        .ref(modelConfig.getModelDataConverterBeanId());

		} else {
			builder.comment("This type has no edit controllers available.");
		}

	}

	protected void configureAdditionalEditControllerConstructorArgs(AppConfiguration appConfig,
	                                                                LocalModelConfiguration modelConfig,
	                                                                SpringBeansXMLBuilder builder,
	                                                                SpringBeansXMLBeanConstructorBuilder<SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder>> editControllerConstructorBuilder) {
		// Does nothing by default.
	}

}
