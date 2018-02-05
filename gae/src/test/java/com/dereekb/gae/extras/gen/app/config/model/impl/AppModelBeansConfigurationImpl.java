package com.dereekb.gae.extras.gen.app.config.model.impl;

import com.dereekb.gae.extras.gen.app.config.model.AppModelBeansConfiguration;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link AppModelBeansConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class AppModelBeansConfigurationImpl
        implements AppModelBeansConfiguration {

	private String modelBeanPrefix;
	private String modelTypeBeanId;
	private String modelClassBeanId;
	private String modelDtoBeanId;
	private String modelDataConverterBeanId;
	private String modelRegistryId;
	private String modelKeyListAccessorFactoryId;

	public AppModelBeansConfigurationImpl(String modelType) {
		String modelBeanPrefix = StringUtility.firstLetterLowerCase(modelType);

		this.setModelBeanPrefix(modelBeanPrefix);
		this.setModelTypeBeanId(modelBeanPrefix + "Type");
		this.setModelClassBeanId(modelBeanPrefix + "Class");
		this.setModelDtoBeanId(modelBeanPrefix + "DtoClass");
		this.setModelDataConverterBeanId(modelBeanPrefix + "DataConverter");
		this.setModelRegistryId(modelBeanPrefix + "Registry");
		this.setModelRegistryId(modelBeanPrefix + "KeyListAccessorFactory");
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

}
