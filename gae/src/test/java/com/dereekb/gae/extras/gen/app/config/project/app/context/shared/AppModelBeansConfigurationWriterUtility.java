package com.dereekb.gae.extras.gen.app.config.project.app.context.shared;

import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.model.extension.data.conversion.impl.TypedBidirectionalConverterImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * Utility for writing shared app model beans configurations necessary for application configuration.
 *
 * @author dereekb
 *
 */
public class AppModelBeansConfigurationWriterUtility {

	private AppModelConfiguration modelConfig;

	public AppModelBeansConfigurationWriterUtility(AppModelConfiguration modelConfig) {
		super();
		this.modelConfig = modelConfig;
	}

	public AppModelBeansConfiguration getModelConfig() {
		return this.modelConfig;
	}

	public void setModelConfig(AppModelConfiguration modelConfig) {
		if (modelConfig == null) {
			throw new IllegalArgumentException("modelConfig cannot be null.");
		}

		this.modelConfig = modelConfig;
	}

	// MARK: Configs
	public void insertDataConversionBeans(SpringBeansXMLBuilder builder) {

		builder.comment("Dto");

		String dataBuilderId = this.modelConfig.getModelBeanPrefix() + "DataBuilder";
		String dataReaderId = this.modelConfig.getModelBeanPrefix() + "DataReader";

		builder.bean(dataBuilderId).beanClass(this.modelConfig.getModelDataBuilderClass());
		builder.bean(dataReaderId).beanClass(this.modelConfig.getModelDataReaderClass());

		builder.bean(this.modelConfig.getModelBeanPrefix() + "DataConverter")
		        .beanClass(TypedBidirectionalConverterImpl.class).c().ref(dataBuilderId).ref(dataReaderId)
		        .ref(this.modelConfig.getModelTypeBeanId()).ref(this.modelConfig.getModelBeanPrefix() + "Class")
		        .ref(this.modelConfig.getModelBeanPrefix() + "DtoClass");

	}

	public void insertModelTypeInformation(SpringBeansXMLBuilder builder) {

		builder.comment("Type");

		builder.stringBean(this.modelConfig.getModelTypeBeanId(), this.modelConfig.getModelType());
		builder.bean(this.modelConfig.getModelClassBeanId()).beanClass(Class.class).factoryMethod("forName").c()
		        .value(this.modelConfig.getModelClass().getCanonicalName());
		builder.bean(this.modelConfig.getModelDtoClassBeanId()).beanClass(Class.class).factoryMethod("forName").c()
		        .value(this.modelConfig.getModelDataClass().getCanonicalName());
		builder.bean(this.modelConfig.getModelIdTypeBeanId()).beanClass(ModelKeyType.class).factoryMethod("valueOf").c()
		        .value(this.modelConfig.getModelKeyType().name());

	}

}
