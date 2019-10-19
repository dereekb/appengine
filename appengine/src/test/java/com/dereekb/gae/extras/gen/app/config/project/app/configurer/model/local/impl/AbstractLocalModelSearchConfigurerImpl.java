package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelSearchConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.model.extension.search.document.service.TypedModelSearchService;
import com.dereekb.gae.model.extension.search.document.service.impl.TypedModelSearchServiceImpl;

/**
 * Abstract {@link LocalModelSearchConfigurer} implementation that configures a
 * {@link TypedModelSearchService} and
 *
 * @author dereekb
 *
 */
public abstract class AbstractLocalModelSearchConfigurerImpl
        implements LocalModelSearchConfigurer {

	private String defaultIndex;

	public AbstractLocalModelSearchConfigurerImpl() {}

	public AbstractLocalModelSearchConfigurerImpl(String defaultIndex) {
		super();
		this.setDefaultIndex(defaultIndex);
	}

	public String getDefaultIndex() {
		return this.defaultIndex;
	}

	public void setDefaultIndex(String defaultIndex) {
		this.defaultIndex = defaultIndex;
	}

	// MARK: LocalModelSearchConfigurer
	@Override
	public boolean hasSearchComponents() {
		return true;
	}

	@Override
	public void configureSearchComponents(AppConfiguration appConfig,
	                                      LocalModelConfiguration modelConfig,
	                                      SpringBeansXMLBuilder builder) {
		this.configureModelSearchFactory(appConfig, modelConfig, builder);
		this.configureTypedModelSearchService(appConfig, modelConfig, builder);
		this.configureModelSearchInitializer(appConfig, modelConfig, builder);
	}

	protected void configureModelSearchFactory(AppConfiguration appConfig,
	                                           LocalModelConfiguration modelConfig,
	                                           SpringBeansXMLBuilder builder) {
		// By Default attempt to use the configured Model Query Initializer
		builder.alias(modelConfig.getModelQueryInitializerBeanId(),
		        modelConfig.getUtilityBeans().getModelSearchFactoryBeanId());
	}

	protected void configureTypedModelSearchService(AppConfiguration appConfig,
	                                                LocalModelConfiguration modelConfig,
	                                                SpringBeansXMLBuilder builder) {
		SpringBeansXMLBeanConstructorBuilder<SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder>> beanBuilder = builder
		        .bean(modelConfig.getUtilityBeans().getTypedModelSearchServiceBeanId())
		        .beanClass(TypedModelSearchServiceImpl.class).c().ref(modelConfig.getModelTypeBeanId())
		        .ref(modelConfig.getModelGetterBeanId())
		        .ref(appConfig.getAppBeans().getUtilityBeans().getModelSearchServiceBeanId())
		        .ref(modelConfig.getUtilityBeans().getModelSearchInitializerBeanId());

		if (this.defaultIndex != null) {
			beanBuilder.value(this.defaultIndex);
		}
	}

	protected abstract void configureModelSearchInitializer(AppConfiguration appConfig,
	                                                        LocalModelConfiguration modelConfig,
	                                                        SpringBeansXMLBuilder builder);

}
