package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelSearchConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.model.extension.search.document.components.impl.TaskedModelSearchInitializerImpl;

/**
 * {@link LocalModelSearchConfigurer} implementation that uses the configured
 * search task implementation.
 *
 * @author dereekb
 *
 */
public class TaskedModelSearchInitializerLocalModelSearchConfigurerImpl extends AbstractLocalModelSearchConfigurerImpl
        implements LocalModelSearchConfigurer {

	public TaskedModelSearchInitializerLocalModelSearchConfigurerImpl() {
		super();
	}

	public TaskedModelSearchInitializerLocalModelSearchConfigurerImpl(String defaultIndex) {
		super(defaultIndex);
	}

	// MARK: AbstractLocalModelSearchConfigurerImpl
	@Override
	protected void configureModelSearchInitializer(AppConfiguration appConfig,
	                                               LocalModelConfiguration modelConfig,
	                                               SpringBeansXMLBuilder builder) {
		String beanId = modelConfig.getUtilityBeans().getModelSearchInitializerBeanId();

		builder.bean(beanId).beanClass(TaskedModelSearchInitializerImpl.class).c()
		        .ref(modelConfig.getUtilityBeans().getModelSearchFactoryBeanId())
		        .ref(modelConfig.getSecuredModelQueryInitializerDelegateBeanId());

	}

}
