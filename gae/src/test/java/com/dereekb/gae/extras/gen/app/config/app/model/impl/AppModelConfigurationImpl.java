package com.dereekb.gae.extras.gen.app.config.app.model.impl;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.model.AppModelBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelCrudsConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomModelContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.CustomLocalModelContextConfigurerImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.misc.path.PathUtility;

/**
 * {@link AppModelConfiguration} implementation.
 */
public class AppModelConfigurationImpl
        implements AppModelConfiguration, AppModelCrudsConfiguration, AppModelBeansConfiguration {

	private boolean localModel = true;
	private boolean localReadOnly = true;

	private boolean crudService = true;
	private boolean createService = true;
	private boolean validatedCreate = false;
	private boolean updateService = true;
	private boolean deleteService = true;

	private boolean iterateControllerEntry = false;

	private String modelType;
	private ModelKeyType modelKeyType;

	private final Package baseClassPackage;
	private final String baseClassPath;
	private final String baseClassSimpleName;

	private Class<Object> modelCreateAttributeUpdaterClass;
	private Class<Object> modelAttributeUpdaterClass;
	private Class<Object> modelRelatedModelAttributeUtilityClass;

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

	private Class<Object> modelOwnedModelQuerySecurityDelegateClass;

	private AppModelCrudsConfiguration crudsConfiguration;
	private AppModelBeansConfiguration beansConfiguration;

	private CustomModelContextConfigurer customModelContextConfigurer = new CustomLocalModelContextConfigurerImpl();

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

		this.baseClassPackage = this.modelClass.getPackage();
		this.baseClassPath = this.baseClassPackage.getName();
		this.baseClassSimpleName = this.modelClass.getSimpleName();

		this.inferClasses();
		this.resetBeans();
	}

	@SuppressWarnings("unchecked")
	private void inferClasses() {

		String baseClassPath = this.baseClassPath;
		String baseClassSimpleName = this.baseClassSimpleName;

		String dataClassName = baseClassPath + ".dto." + baseClassSimpleName + "Data";
		String generatorClassName = baseClassPath + ".generator." + baseClassSimpleName + "Generator";
		String linkSystemBuilderEntryClassName = baseClassPath + ".link." + baseClassSimpleName
		        + "LinkSystemBuilderEntry";
		String queryClassName = baseClassPath + ".search.query." + baseClassSimpleName + "Query";
		String queryInitializerClassName = queryClassName + "Initializer";

		String updateAttributeInitializerClassName = baseClassPath + ".crud." + baseClassSimpleName
		        + "AttributeUpdater";
		String createAttributeInitializerClassName = baseClassPath + ".crud." + baseClassSimpleName
		        + "CreateAttributeUpdater";

		String modelSecurityContextServiceEntry = baseClassPath + ".security." + baseClassSimpleName
		        + "SecurityContextServiceEntry";

		// Find Edit Controller Name
		List<String> packagePathComponents = PathUtility.getComponents(baseClassPath, "\\.");
		List<String> comPathComponents = packagePathComponents.subList(0, 3);
		String comPath = StringUtility.joinValues(".", comPathComponents);
		comPath = comPath + ".web.api.model.controllers";

		String editControllerClass = comPath + "." + baseClassSimpleName + "EditController";

		// Required Classes
		try {
			this.modelDataClass = (Class<Object>) Class.forName(dataClassName);
			this.modelDataReaderClass = (Class<Object>) Class.forName(dataClassName + "Reader");
			this.modelDataBuilderClass = (Class<Object>) Class.forName(dataClassName + "Builder");

			this.modelGeneratorClass = (Class<Object>) Class.forName(generatorClassName);
			this.modelLinkSystemBuilderEntryClass = (Class<Object>) Class.forName(linkSystemBuilderEntryClassName);

			this.modelQueryClass = (Class<Object>) Class.forName(queryClassName);
			this.modelQueryInitializerClass = (Class<Object>) Class.forName(queryInitializerClassName);

			this.modelAttributeUpdaterClass = (Class<Object>) Class.forName(updateAttributeInitializerClassName);
			this.modelSecurityContextServiceEntryClass = (Class<Object>) Class
			        .forName(modelSecurityContextServiceEntry);

			this.modelEditControllerClass = (Class<Object>) Class.forName(editControllerClass);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		// Optional Classes
		try {
			this.modelCreateAttributeUpdaterClass = (Class<Object>) Class.forName(createAttributeInitializerClassName);
		} catch (ClassNotFoundException e) {

		}

		try {
			String firstRelatedAttributeUtilityClassName = baseClassPath + ".crud." + baseClassSimpleName
			        + "RelatedModelAttributeUtility";
			this.modelRelatedModelAttributeUtilityClass = (Class<Object>) Class
			        .forName(firstRelatedAttributeUtilityClassName);
		} catch (ClassNotFoundException e) {
			// Try Checking "Shared"
			try {
				String sharedRelatedAttributeUtilityClassName = baseClassPath + ".shared.crud." + baseClassSimpleName
				        + "RelatedModelAttributeUtility";
				this.modelRelatedModelAttributeUtilityClass = (Class<Object>) Class
				        .forName(sharedRelatedAttributeUtilityClassName);
			} catch (ClassNotFoundException x) {

			}
		}

		try {
			String miscOwnedPath = baseClassPath + ".misc.owned";
			String ownedModelQuerySecurityDelegateClassName = miscOwnedPath + ".query.security." + baseClassSimpleName
			        + "OwnedModelQuerySecurityDelegate";
			this.modelOwnedModelQuerySecurityDelegateClass = (Class<Object>) Class
			        .forName(ownedModelQuerySecurityDelegateClassName);
		} catch (ClassNotFoundException e) {

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
	public boolean isOwnerModel() {
		return this.modelOwnedModelQuerySecurityDelegateClass != null;
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
	public AppModelCrudsConfiguration getCrudsConfiguration() {
		return this.crudsConfiguration;
	}

	public void setCrudsConfiguration(AppModelCrudsConfiguration crudsConfiguration) {
		if (crudsConfiguration == null) {
			throw new IllegalArgumentException("crudsConfiguration cannot be null.");
		}

		this.crudsConfiguration = crudsConfiguration;
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

	@Override
	public Class<Object> getModelOwnedModelQuerySecurityDelegateClass() {
		return this.modelOwnedModelQuerySecurityDelegateClass;
	}

	public void setModelOwnedModelQuerySecurityDelegateClass(Class<Object> modelOwnedModelQuerySecurityDelegateClass) {
		this.modelOwnedModelQuerySecurityDelegateClass = modelOwnedModelQuerySecurityDelegateClass;
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

	@Override
	public String getModelQueryInitializerBeanId() {
		return this.beansConfiguration.getModelQueryInitializerBeanId();
	}

	@Override
	public String getModelConfiguredUpdaterBeanId() {
		return this.beansConfiguration.getModelConfiguredUpdaterBeanId();
	}

	@Override
	public String getModelSetterTaskBeanId() {
		return this.beansConfiguration.getModelSetterTaskBeanId();
	}

	@Override
	public String getModelEventServiceEntryBeanId() {
		return this.beansConfiguration.getModelEventServiceEntryBeanId();
	}

	// AppModelCrudsConfiguration
	@Override
	public boolean hasCrudService() {
		return this.crudService;
	}

	public void setHasCrudService(boolean crudService) {
		this.crudService = crudService;
	}

	@Override
	public boolean hasCreateService() {
		return this.createService;
	}

	public void setHasCreateService(boolean createService) {
		this.createService = createService;
	}

	@Override
	public boolean hasUpdateService() {
		return this.updateService;
	}

	public void setHasUpdateService(boolean updateService) {
		this.updateService = updateService;
	}

	@Override
	public boolean hasDeleteService() {
		return this.deleteService;
	}

	public void setHasDeleteService(boolean deleteService) {
		this.deleteService = deleteService;
	}

	@Override
	public boolean hasValidatedCreate() {
		return this.validatedCreate;
	}

	public void setHasValidatedCreate(boolean validatedCreate) {
		this.validatedCreate = validatedCreate;
	}

	@Override
	public boolean hasCreateAttributeUpdater() {
		return this.modelCreateAttributeUpdaterClass != null;
	}

	@Override
	public Class<Object> getModelCreateAttributeUpdaterClass() {
		return this.modelCreateAttributeUpdaterClass;
	}

	public void setModelCreateAttributeUpdaterClass(Class<Object> modelCreateAttributeUpdaterClass) {
		this.modelCreateAttributeUpdaterClass = modelCreateAttributeUpdaterClass;
	}

	@Override
	public Class<Object> getModelAttributeUpdaterClass() {
		return this.modelAttributeUpdaterClass;
	}

	public void setModelAttributeUpdaterClass(Class<Object> modelAttributeUpdaterClass) {
		if (modelAttributeUpdaterClass == null) {
			throw new IllegalArgumentException("modelAttributeUpdaterClass cannot be null.");
		}

		this.modelAttributeUpdaterClass = modelAttributeUpdaterClass;
	}

	@Override
	public boolean hasRelatedAttributeUtility() {
		return this.modelRelatedModelAttributeUtilityClass != null;
	}

	@Override
	public Class<Object> getModelRelatedModelAttributeUtilityClass() {
		return this.modelRelatedModelAttributeUtilityClass;
	}

	public void setModelRelatedModelAttributeUtilityClass(Class<Object> modelRelatedModelAttributeUtilityClass) {
		this.modelRelatedModelAttributeUtilityClass = modelRelatedModelAttributeUtilityClass;
	}

	@Override
	public CustomModelContextConfigurer getCustomModelContextConfigurer() {
		return this.customModelContextConfigurer;
	}

	public void setCustomModelContextConfigurer(CustomModelContextConfigurer customModelContextConfigurer) {
		if (customModelContextConfigurer == null) {
			throw new IllegalArgumentException("customModelContextConfigurer cannot be null.");
		}

		this.customModelContextConfigurer = customModelContextConfigurer;
	}

}
