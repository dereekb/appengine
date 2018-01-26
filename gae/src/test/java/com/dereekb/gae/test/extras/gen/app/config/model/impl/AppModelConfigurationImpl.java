package com.dereekb.gae.test.extras.gen.app.config.model.impl;

import com.dereekb.gae.test.extras.gen.app.config.model.AppModelBeansConfiguration;
import com.dereekb.gae.test.extras.gen.app.config.model.AppModelConfiguration;

/**
 * {@link AppModelConfiguration} implementation.
 */
public class AppModelConfigurationImpl
        implements AppModelConfiguration, AppModelBeansConfiguration {

	private String modelType;
	private Class<Object> modelClass;
	private Class<Object> modelDataClass;

	private Class<Object> modelGeneratorClass;

	private AppModelBeansConfiguration beansConfiguration;

	public AppModelConfigurationImpl(Class<?> modelClass) {
		this(modelClass, modelClass.getSimpleName());
	}

	@SuppressWarnings("unchecked")
	public AppModelConfigurationImpl(Class<?> modelClass, String modelType) {
		super();
		this.setModelClass((Class<Object>) modelClass);
		this.setModelType(modelType);

		this.inferClasses();
		this.resetBeans();
	}

	@SuppressWarnings("unchecked")
	private void inferClasses() {

		String baseClassName = this.modelClass.getName();
		String baseClassPath = this.modelClass.getPackage().getName();
		String baseClassSimpleName = this.modelClass.getSimpleName();

		String dataClassName = baseClassPath + ".dto." + baseClassSimpleName + "Data";
		String generatorClassName = baseClassPath + ".generator." + baseClassSimpleName + "Generator";

		try {
			this.modelDataClass = (Class<Object>) Class.forName(dataClassName);
			this.modelGeneratorClass = (Class<Object>) Class.forName(generatorClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

	}

	protected void resetBeans() {
		this.setBeansConfiguration(new AppModelBeansConfigurationImpl(this.modelType));
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
	public AppModelBeansConfiguration getBeansConfiguration() {
		return this.beansConfiguration;
	}

	public void setBeansConfiguration(AppModelBeansConfiguration beansConfiguration) {
		if (beansConfiguration == null) {
			throw new IllegalArgumentException("beansConfiguration cannot be null.");
		}

		this.beansConfiguration = beansConfiguration;
	}

	@Override
	public Class<?> getModelGeneratorClass() {
		return this.modelGeneratorClass;
	}

	public void setModelGeneratorClass(Class<Object> modelGeneratorClass) {
		if (modelGeneratorClass == null) {
			throw new IllegalArgumentException("modelGeneratorClass cannot be null.");
		}

		this.modelGeneratorClass = modelGeneratorClass;
	}

	// MARK: AppModelBeansConfiguration
	@Override
	public String getModelBeanPrefix() {
		return this.beansConfiguration.getModelBeanPrefix();
	}

	@Override
	public String getModelTypeBeanId() {
		return this.beansConfiguration.getModelTypeBeanId();
	}

	@Override
	public String getModelClassBeanId() {
		return this.beansConfiguration.getModelClassBeanId();
	}

	@Override
	public String getModelDtoBeanId() {
		return this.beansConfiguration.getModelDtoBeanId();
	}

	@Override
	public String getModelDataConverterBeanId() {
		return this.beansConfiguration.getModelDataConverterBeanId();
	}

	@Override
	public String getModelRegistryId() {
		return this.beansConfiguration.getModelRegistryId();
	}

}
