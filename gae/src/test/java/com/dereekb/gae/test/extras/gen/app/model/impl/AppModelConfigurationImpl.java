package com.dereekb.gae.test.extras.gen.app.model.impl;

import com.dereekb.gae.test.extras.gen.app.model.AppModelConfiguration;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link AppModelConfiguration} implementation.
 */
public class AppModelConfigurationImpl
        implements AppModelConfiguration {

	String modelType;
	Class<Object> modelClass;
	Class<Object> modelDataClass;

	String modelBeanPrefix;
	String modelTypeBeanId;
	String modelClassBeanId;
	String modelDtoBeanId;
	String modelDataConverterBeanId;

	public AppModelConfigurationImpl(Class<?> modelClass, Class<?> modelDataClass) {
		this(modelClass, modelDataClass, modelClass.getSimpleName());
	}

	@SuppressWarnings("unchecked")
	public AppModelConfigurationImpl(Class<?> modelClass, Class<?> modelDataClass, String modelType) {
		super();
		this.setModelClass((Class<Object>) modelClass);
		this.setModelDataClass((Class<Object>) modelDataClass);
		this.setModelType(modelType);

		this.resetBeans();
	}

	protected void resetBeans() {
		String modelBeanPrefix = StringUtility.firstLetterLowerCase(this.modelType);

		this.setModelBeanPrefix(modelBeanPrefix);
		this.setModelTypeBeanId(modelBeanPrefix + "Type");
		this.setModelClassBeanId(modelBeanPrefix + "Class");
		this.setModelDtoBeanId(modelBeanPrefix + "DtoClass");
		this.setModelDataConverterBeanId(modelBeanPrefix + "DataConverter");
	}

	@Override
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		if (modelType == null) {
			throw new IllegalArgumentException("modelType cannot be null.");
		}

		this.modelType = modelType;
	}

	@Override
	public Class<Object> getModelClass() {
		return this.modelClass;
	}

	public void setModelClass(Class<Object> modelClass) {
		if (modelClass == null) {
			throw new IllegalArgumentException("modelClass cannot be null.");
		}

		this.modelClass = modelClass;
	}

	@Override
	public Class<Object> getModelDataClass() {
		return this.modelDataClass;
	}

	public void setModelDataClass(Class<Object> modelDataClass) {
		if (modelDataClass == null) {
			throw new IllegalArgumentException("modelDataClass cannot be null.");
		}

		this.modelDataClass = modelDataClass;
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

}
