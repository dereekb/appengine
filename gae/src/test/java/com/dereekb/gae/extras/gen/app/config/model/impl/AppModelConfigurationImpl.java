package com.dereekb.gae.extras.gen.app.config.model.impl;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.model.AppModelBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.misc.path.PathUtility;

/**
 * {@link AppModelConfiguration} implementation.
 */
public class AppModelConfigurationImpl
        implements AppModelConfiguration, AppModelBeansConfiguration {

	private boolean localModel = true;
	private boolean localReadOnly = true;

	private boolean iterateControllerEntry = true;

	private String modelType;
	private ModelKeyType modelKeyType;

	private Class<Object> modelClass;
	private Class<Object> modelDataClass;

	private Class<Object> modelDataBuilderClass;
	private Class<Object> modelDataReaderClass;

	private Class<Object> modelGeneratorClass;
	private Class<Object> modelLinkSystemBuilderEntryClass;

	private Class<Object> modelQueryClass;
	private Class<Object> modelQueryInitializerClass;

	private Class<Object> modelSecurityContextServiceEntryClass;

	private Class<Object> modelEditControllerClass;

	private AppModelBeansConfiguration beansConfiguration;

	public AppModelConfigurationImpl(Class<?> modelClass) {
		this(modelClass, ModelKey.readModelKeyType(modelClass));
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

		this.inferClasses();
		this.resetBeans();
	}

	@SuppressWarnings("unchecked")
	private void inferClasses() {

		Package baseClassPackage = this.modelClass.getPackage();
		String baseClassPath = baseClassPackage.getName();
		String baseClassSimpleName = this.modelClass.getSimpleName();

		String dataClassName = baseClassPath + ".dto." + baseClassSimpleName + "Data";
		String generatorClassName = baseClassPath + ".generator." + baseClassSimpleName + "Generator";
		String linkSystemBuilderEntryClassName = baseClassPath + ".link." + baseClassSimpleName
		        + "LinkSystemBuilderEntry";
		String queryClassName = baseClassPath + ".search.query." + baseClassSimpleName + "Query";
		String queryInitializerClassName = queryClassName + "Initializer";

		// Find Edit Controller Name
		List<String> packagePathComponents = PathUtility.getComponents(baseClassPath, "\\.");
		List<String> comPathComponents = packagePathComponents.subList(0, 3);
		String comPath = StringUtility.joinValues(".", comPathComponents);
		comPath = comPath + ".web.api.model.controllers";

		String editControllerClass = comPath + "." + baseClassSimpleName + "EditController";

		try {
			this.modelDataClass = (Class<Object>) Class.forName(dataClassName);
			this.modelDataReaderClass = (Class<Object>) Class.forName(dataClassName + "Reader");
			this.modelDataBuilderClass = (Class<Object>) Class.forName(dataClassName + "Builder");

			this.modelGeneratorClass = (Class<Object>) Class.forName(generatorClassName);
			this.modelLinkSystemBuilderEntryClass = (Class<Object>) Class.forName(linkSystemBuilderEntryClassName);

			this.modelQueryClass = (Class<Object>) Class.forName(queryClassName);
			this.modelQueryInitializerClass = (Class<Object>) Class.forName(queryInitializerClassName);

			this.modelSecurityContextServiceEntryClass = (Class<Object>) Class.forName(queryInitializerClassName);

			this.modelEditControllerClass = (Class<Object>) Class.forName(editControllerClass);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

	}

	protected void resetBeans() {
		this.setBeansConfiguration(new AppModelBeansConfigurationImpl(this.modelType, this.modelKeyType));
	}

	@Override
	public boolean isLocalModel() {
		return this.localModel;
	}

	public void setLocalModel(boolean localModel) {
		this.localModel = localModel;
	}

	@Override
	public boolean isLocalReadOnly() {
		return this.localReadOnly;
	}

	public void setLocalReadOnly(boolean localReadOnly) {
		this.localReadOnly = localReadOnly;
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

	@Override
	public Class<Object> getModelLinkSystemBuilderEntryClass() {
		return this.modelLinkSystemBuilderEntryClass;
	}

	public void setModelLinkSystemBuilderEntryClass(Class<Object> modelLinkSystemBuilderEntryClass) {
		if (modelLinkSystemBuilderEntryClass == null) {
			throw new IllegalArgumentException("modelLinkSystemBuilderEntryClass cannot be null.");
		}

		this.modelLinkSystemBuilderEntryClass = modelLinkSystemBuilderEntryClass;
	}

	@Override
	public Class<Object> getModelQueryClass() {
		return this.modelQueryClass;
	}

	public void setModelQueryClass(Class<Object> modelQueryClass) {
		if (modelQueryClass == null) {
			throw new IllegalArgumentException("modelQueryClass cannot be null.");
		}

		this.modelQueryClass = modelQueryClass;
	}

	@Override
	public Class<Object> getModelQueryInitializerClass() {
		return this.modelQueryInitializerClass;
	}

	public void setModelQueryInitializerClass(Class<Object> modelQueryInitializerClass) {
		if (modelQueryInitializerClass == null) {
			throw new IllegalArgumentException("modelQueryInitializerClass cannot be null.");
		}

		this.modelQueryInitializerClass = modelQueryInitializerClass;
	}

	@Override
	public Class<Object> getModelSecurityContextServiceEntryClass() {
		return this.modelSecurityContextServiceEntryClass;
	}

	public void setModelSecurityContextServiceEntryClass(Class<Object> modelSecurityContextServiceEntryClass) {
		if (modelSecurityContextServiceEntryClass == null) {
			throw new IllegalArgumentException("modelSecurityContextServiceEntryClass cannot be null.");
		}

		this.modelSecurityContextServiceEntryClass = modelSecurityContextServiceEntryClass;
	}

	@Override
	public Class<Object> getModelEditControllerClass() {
		return this.modelEditControllerClass;
	}

	public void setModelEditControllerClass(Class<Object> modelEditControllerClass) {
		if (modelEditControllerClass == null) {
			throw new IllegalArgumentException("modelEditControllerClass cannot be null.");
		}

		this.modelEditControllerClass = modelEditControllerClass;
	}

	@Override
	public boolean hasIterateControllerEntry() {
		return this.iterateControllerEntry;
	}

	public void setIterateControllerEntry(boolean iterateControllerEntry) {
		this.iterateControllerEntry = iterateControllerEntry;
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
	public String getModelObjectifyEntryBeanId() {
		return this.beansConfiguration.getModelObjectifyEntryBeanId();
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

	@Override
	public String getModelKeyListAccessorFactoryId() {
		return this.beansConfiguration.getModelKeyListAccessorFactoryId();
	}

	@Override
	public String getModelInclusionReaderId() {
		return this.beansConfiguration.getModelInclusionReaderId();
	}

	@Override
	public String getModelLinkModelAccessorId() {
		return this.beansConfiguration.getModelLinkModelAccessorId();
	}

	@Override
	public String getStringModelKeyConverter() {
		return this.beansConfiguration.getStringModelKeyConverter();
	}

	@Override
	public String getModelCrudServiceId() {
		return this.beansConfiguration.getModelCrudServiceId();
	}

	@Override
	public String getModelCreateServiceId() {
		return this.beansConfiguration.getModelCreateServiceId();
	}

	@Override
	public String getModelReadServiceId() {
		return this.beansConfiguration.getModelReadServiceId();
	}

	@Override
	public String getModelUpdateServiceId() {
		return this.beansConfiguration.getModelUpdateServiceId();
	}

	@Override
	public String getModelDeleteServiceId() {
		return this.beansConfiguration.getModelDeleteServiceId();
	}

	@Override
	public String getModelQueryServiceId() {
		return this.beansConfiguration.getModelQueryServiceId();
	}

	@Override
	public String getModelLinkSystemBuilderEntryBeanId() {
		return this.beansConfiguration.getModelLinkSystemBuilderEntryBeanId();
	}

	@Override
	public String getModelSecurityContextServiceEntryBeanId() {
		return this.beansConfiguration.getModelSecurityContextServiceEntryBeanId();
	}

	@Override
	public String getNewModelFactoryBeanId() {
		return this.beansConfiguration.getNewModelFactoryBeanId();
	}

	@Override
	public String getModelStorerBeanId() {
		return this.beansConfiguration.getModelStorerBeanId();
	}

	@Override
	public String getModelUpdaterBeanId() {
		return this.beansConfiguration.getModelUpdaterBeanId();
	}

	@Override
	public String getModelDeleterBeanId() {
		return this.beansConfiguration.getModelDeleterBeanId();
	}

	@Override
	public String getModelScheduleCreateReviewBeanId() {
		return this.beansConfiguration.getModelScheduleCreateReviewBeanId();
	}

	@Override
	public String getModelScheduleUpdateReviewBeanId() {
		return this.beansConfiguration.getModelScheduleUpdateReviewBeanId();
	}

	@Override
	public String getModelRoleSetLoaderBeanId() {
		return this.beansConfiguration.getModelRoleSetLoaderBeanId();
	}

}
