package com.dereekb.gae.extras.gen.app.config.model.impl;

import com.dereekb.gae.extras.gen.app.config.model.AppModelBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfiguration;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link AppModelBeansConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class AppModelBeansConfigurationImpl
        implements AppModelBeansConfiguration {

	private ModelKeyType modelKeyType;

	private String modelBeanPrefix;
	private String modelTypeBeanId;
	private String modelClassBeanId;
	private String modelDtoBeanId;
	private String modelDataConverterBeanId;
	private String modelRegistryId;
	private String modelKeyListAccessorFactoryId;

	private String modelReadServiceId;
	private String modelInclusionReaderId;
	private String modelLinkModelAccessorId;
	private String stringModelKeyConverter;
	private String modelQueryServiceId;

	public AppModelBeansConfigurationImpl(String modelType, ModelKeyType modelKeyType) {
		String modelBeanPrefix = StringUtility.firstLetterLowerCase(modelType);

		this.setModelKeyType(modelKeyType);
		this.setModelBeanPrefix(modelBeanPrefix);
		this.setModelTypeBeanId(modelBeanPrefix + "Type");
		this.setModelClassBeanId(modelBeanPrefix + "Class");
		this.setModelDtoBeanId(modelBeanPrefix + "DtoClass");
		this.setModelDataConverterBeanId(modelBeanPrefix + "DataConverter");
		this.setModelRegistryId(modelBeanPrefix + "Registry");
		this.setModelKeyListAccessorFactoryId(modelBeanPrefix + "KeyListAccessorFactory");
		this.setModelReadServiceId(modelBeanPrefix + "ReadService");
		this.setModelInclusionReaderId(modelBeanPrefix + "InclusionReader");
		this.setModelLinkModelAccessorId(modelBeanPrefix + "LinkModelAccessor");
		this.setStringModelKeyConverter(AppBeansConfiguration.getModelKeyConverterBeanId(modelKeyType));
		this.setModelQueryServiceId(modelBeanPrefix + "QueryService");
	}

	public ModelKeyType getModelKeyType() {
		return this.modelKeyType;
	}

	public void setModelKeyType(ModelKeyType modelKeyType) {
		if (modelKeyType == null) {
			throw new IllegalArgumentException("modelKeyType cannot be null.");
		}

		this.modelKeyType = modelKeyType;
	}

	@Override
	public String getModelBeanPrefix() {
		return this.modelBeanPrefix;
	}

	public void setModelBeanPrefix(String modelBeanPrefix) {
		if (modelBeanPrefix == null) {
			throw new IllegalArgumentException("modelBeanPrefix cannot be null.");
		}

		this.modelBeanPrefix = modelBeanPrefix;
	}

	@Override
	public String getModelTypeBeanId() {
		return this.modelTypeBeanId;
	}

	public void setModelTypeBeanId(String modelTypeBeanId) {
		if (modelTypeBeanId == null) {
			throw new IllegalArgumentException("modelTypeBeanId cannot be null.");
		}

		this.modelTypeBeanId = modelTypeBeanId;
	}

	@Override
	public String getModelClassBeanId() {
		return this.modelClassBeanId;
	}

	public void setModelClassBeanId(String modelClassBeanId) {
		if (modelClassBeanId == null) {
			throw new IllegalArgumentException("modelClassBeanId cannot be null.");
		}

		this.modelClassBeanId = modelClassBeanId;
	}

	@Override
	public String getModelDtoBeanId() {
		return this.modelDtoBeanId;
	}

	public void setModelDtoBeanId(String modelDtoBeanId) {
		if (modelDtoBeanId == null) {
			throw new IllegalArgumentException("modelDtoBeanId cannot be null.");
		}

		this.modelDtoBeanId = modelDtoBeanId;
	}

	@Override
	public String getModelDataConverterBeanId() {
		return this.modelDataConverterBeanId;
	}

	public void setModelDataConverterBeanId(String modelDataConverterBeanId) {
		if (modelDataConverterBeanId == null) {
			throw new IllegalArgumentException("modelDataConverterBeanId cannot be null.");
		}

		this.modelDataConverterBeanId = modelDataConverterBeanId;
	}

	@Override
	public String getModelRegistryId() {
		return this.modelRegistryId;
	}

	public void setModelRegistryId(String modelRegistryId) {
		if (modelRegistryId == null) {
			throw new IllegalArgumentException("modelRegistryId cannot be null.");
		}

		this.modelRegistryId = modelRegistryId;
	}

	@Override
	public String getModelKeyListAccessorFactoryId() {
		return this.modelKeyListAccessorFactoryId;
	}

	public void setModelKeyListAccessorFactoryId(String modelKeyListAccessorFactoryId) {
		if (modelKeyListAccessorFactoryId == null) {
			throw new IllegalArgumentException("modelKeyListAccessorFactoryId cannot be null.");
		}

		this.modelKeyListAccessorFactoryId = modelKeyListAccessorFactoryId;
	}

	@Override
	public String getModelReadServiceId() {
		return this.modelReadServiceId;
	}

	public void setModelReadServiceId(String modelReadServiceId) {
		if (modelReadServiceId == null) {
			throw new IllegalArgumentException("modelReadServiceId cannot be null.");
		}

		this.modelReadServiceId = modelReadServiceId;
	}

	@Override
	public String getModelInclusionReaderId() {
		return this.modelInclusionReaderId;
	}

	public void setModelInclusionReaderId(String modelInclusionReaderId) {
		if (modelInclusionReaderId == null) {
			throw new IllegalArgumentException("modelInclusionReaderId cannot be null.");
		}

		this.modelInclusionReaderId = modelInclusionReaderId;
	}

	@Override
	public String getModelLinkModelAccessorId() {
		return this.modelLinkModelAccessorId;
	}

	public void setModelLinkModelAccessorId(String modelLinkModelAccessorId) {
		if (modelLinkModelAccessorId == null) {
			throw new IllegalArgumentException("modelLinkModelAccessorId cannot be null.");
		}

		this.modelLinkModelAccessorId = modelLinkModelAccessorId;
	}

	@Override
	public String getStringModelKeyConverter() {
		return this.stringModelKeyConverter;
	}

	public void setStringModelKeyConverter(String stringModelKeyConverter) {
		if (stringModelKeyConverter == null) {
			throw new IllegalArgumentException("stringModelKeyConverter cannot be null.");
		}

		this.stringModelKeyConverter = stringModelKeyConverter;
	}

	@Override
	public String getModelQueryServiceId() {
		return this.modelQueryServiceId;
	}

	public void setModelQueryServiceId(String modelQueryServiceId) {
		if (modelQueryServiceId == null) {
			throw new IllegalArgumentException("modelQueryServiceId cannot be null.");
		}

		this.modelQueryServiceId = modelQueryServiceId;
	}

}
