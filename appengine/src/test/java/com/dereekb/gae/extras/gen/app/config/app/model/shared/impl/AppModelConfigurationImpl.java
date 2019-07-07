package com.dereekb.gae.extras.gen.app.config.app.model.shared.impl;

import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelCrudsConfiguration;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * Abstract {@link AppModelConfiguration} implementation.
 *
 * @author dereekb
 *
 * @param <C>
 *            AppModelCrudsConfiguration type
 * @param <B>
 *            AppModelBeansConfiguration type
 */
public abstract class AppModelConfigurationImpl<C extends AppModelCrudsConfiguration, B extends AppModelBeansConfiguration, M>
        implements AppModelConfiguration {

	private boolean localModel;
	private boolean isInternalModelOnly = false;

	private String modelType;
	private ModelKeyType modelKeyType;

	private final Package baseClassPackage;
	private final String baseClassPath;
	private final String baseClassSimpleName;

	private Class<Object> modelClass;
	private Class<Object> modelDataClass;

	private Class<Object> modelDataBuilderClass;
	private Class<Object> modelDataReaderClass;

	private C crudsConfiguration;
	private B beansConfiguration;
	private M customModelContextConfigurer;

	public AppModelConfigurationImpl(Class<?> modelClass) {
		this(modelClass, ModelKey.readModelKeyType(modelClass), true);
	}

	public AppModelConfigurationImpl(Class<?> modelClass, boolean localModel) {
		this(modelClass, ModelKey.readModelKeyType(modelClass), localModel);
	}

	public AppModelConfigurationImpl(Class<?> modelClass, ModelKeyType modelKeyType) {
		this(modelClass, modelKeyType, true);
	}

	public AppModelConfigurationImpl(Class<?> modelClass, ModelKeyType modelKeyType, boolean localModel) {
		this(modelClass, modelKeyType, modelClass.getSimpleName(), localModel);
	}

	@SuppressWarnings("unchecked")
	public AppModelConfigurationImpl(Class<?> modelClass,
	        ModelKeyType modelKeyType,
	        String modelType,
	        boolean localModel) {
		super();
		this.setLocalModel(localModel);
		this.setModelClass((Class<Object>) modelClass);
		this.setModelType(modelType);
		this.setModelKeyType(modelKeyType);

		this.baseClassPackage = this.modelClass.getPackage();
		this.baseClassPath = this.baseClassPackage.getName();
		this.baseClassSimpleName = this.modelClass.getSimpleName();

		this.inferClasses();
		this.resetBeans();
	}

	@SuppressWarnings("unchecked")
	protected void inferClasses() {

		String baseClassPath = this.baseClassPath;
		String baseClassSimpleName = this.baseClassSimpleName;

		String dataClassName = baseClassPath + ".dto." + baseClassSimpleName + "Data";

		// Required Classes
		try {
			this.modelDataClass = (Class<Object>) Class.forName(dataClassName);
			this.modelDataReaderClass = (Class<Object>) Class.forName(dataClassName + "Reader");
			this.modelDataBuilderClass = (Class<Object>) Class.forName(dataClassName + "Builder");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		this.crudsConfiguration = this.inferCrudsConfiguration();
	}

	protected void resetBeans() {
		this.setBeansConfiguration(this.makeModelBeansConfiguration());
		this.setCustomModelContextConfigurer(this.makeCustomModelContextConfigurer());
	}

	protected abstract C inferCrudsConfiguration();

	protected abstract B makeModelBeansConfiguration();

	protected abstract M makeCustomModelContextConfigurer();

	// MARK: AppModelConfiguration
	@Override
	public Package getBaseClassPackage() {
		return this.baseClassPackage;
	}

	@Override
	public String getBaseClassPath() {
		return this.baseClassPath;
	}

	@Override
	public String getBaseClassSimpleName() {
		return this.baseClassSimpleName;
	}

	@Override
	public boolean isLocalModel() {
		return this.localModel;
	}

	public void setLocalModel(boolean localModel) {
		this.localModel = localModel;
	}

	@Override
	public boolean isInternalModelOnly() {
		return this.isInternalModelOnly;
	}

	public void setInternalModelOnly(boolean isInternalModelOnly) {
		this.isInternalModelOnly = isInternalModelOnly;
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
	public Class<Object> getModelDataBuilderClass() {
		return this.modelDataBuilderClass;
	}

	public void setModelDataBuilderClass(Class<Object> modelDataBuilderClass) {
		if (modelDataBuilderClass == null) {
			throw new IllegalArgumentException("modelDataBuilderClass cannot be null.");
		}

		this.modelDataBuilderClass = modelDataBuilderClass;
	}

	@Override
	public Class<Object> getModelDataReaderClass() {
		return this.modelDataReaderClass;
	}

	public void setModelDataReaderClass(Class<Object> modelDataReaderClass) {
		if (modelDataReaderClass == null) {
			throw new IllegalArgumentException("modelDataReaderClass cannot be null.");
		}

		this.modelDataReaderClass = modelDataReaderClass;
	}

	@Override
	public C getCrudsConfiguration() {
		return this.crudsConfiguration;
	}

	public void setCrudsConfiguration(C crudsConfiguration) {
		if (crudsConfiguration == null) {
			throw new IllegalArgumentException("crudsConfiguration cannot be null.");
		}

		this.crudsConfiguration = crudsConfiguration;
	}

	@Override
	public B getBeansConfiguration() {
		return this.beansConfiguration;
	}

	public void setBeansConfiguration(B beansConfiguration) {
		if (beansConfiguration == null) {
			throw new IllegalArgumentException("beansConfiguration cannot be null.");
		}

		this.beansConfiguration = beansConfiguration;
	}

	public M getCustomModelContextConfigurer() {
		return this.customModelContextConfigurer;
	}

	public void setCustomModelContextConfigurer(M customModelContextConfigurer) {
		if (customModelContextConfigurer == null) {
			throw new IllegalArgumentException("customModelContextConfigurer cannot be null.");
		}

		this.customModelContextConfigurer = customModelContextConfigurer;
	}

	@Override
	public boolean hasCrudService() {
		return this.crudsConfiguration.hasCrudService();
	}

	@Override
	public boolean hasCreateService() {
		return this.crudsConfiguration.hasCreateService();
	}

	@Override
	public boolean hasUpdateService() {
		return this.crudsConfiguration.hasUpdateService();
	}

	@Override
	public boolean hasDeleteService() {
		return this.crudsConfiguration.hasDeleteService();
	}

	@Override
	public String getModelBeanPrefix() {
		return this.beansConfiguration.getModelBeanPrefix();
	}

	@Override
	public String getModelTypeBeanId() {
		return this.beansConfiguration.getModelTypeBeanId();
	}

	@Override
	public String getModelIdTypeBeanId() {
		return this.beansConfiguration.getModelIdTypeBeanId();
	}

	@Override
	public String getModelClassBeanId() {
		return this.beansConfiguration.getModelClassBeanId();
	}

	@Override
	public String getModelDtoClassBeanId() {
		return this.beansConfiguration.getModelDtoClassBeanId();
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
	public String getModelGetterBeanId() {
		return this.beansConfiguration.getModelGetterBeanId();
	}

	@Override
	public String getModelKeyListAccessorFactoryId() {
		return this.beansConfiguration.getModelKeyListAccessorFactoryId();
	}

	@Override
	public String getModelSecurityContextServiceEntryBeanId() {
		return this.beansConfiguration.getModelSecurityContextServiceEntryBeanId();
	}

}
