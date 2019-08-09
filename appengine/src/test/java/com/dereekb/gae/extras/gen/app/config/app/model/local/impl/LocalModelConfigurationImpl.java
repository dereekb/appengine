package com.dereekb.gae.extras.gen.app.config.app.model.local.impl;

import java.util.List;
import java.util.logging.Logger;

import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelCrudsConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.impl.AppModelConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.impl.LocalModelContextConfigurerImpl;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityKeyEnforcement;
import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyUtility;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.misc.path.PathUtility;

/**
 * {@link LocalModelConfiguration} implementation.
 */
public class LocalModelConfigurationImpl extends AppModelConfigurationImpl<LocalModelCrudsConfiguration, LocalModelBeansConfiguration, LocalModelContextConfigurer>
        implements LocalModelConfiguration, LocalModelCrudsConfiguration, LocalModelBeansConfiguration {

	private static final Logger LOGGER = Logger.getLogger(LocalModelConfigurationImpl.class.getName());

	private ObjectifyDatabaseEntityKeyEnforcement keyEnforcement;

	private Class<Object> modelDataBuilderClass;
	private Class<Object> modelDataReaderClass;

	private Class<Object> modelGeneratorClass;
	private Class<Object> modelLinkSystemBuilderEntryClass;

	private Class<Object> modelQueryClass;
	private Class<Object> modelQueryInitializerClass;

	private Class<Object> modelSecurityContextServiceEntryClass;

	private Class<Object> modelEditControllerClass;
	private Class<Object> modelOwnedModelQuerySecurityDelegateClass;

	public LocalModelConfigurationImpl(Class<?> modelClass) {
		this(modelClass, false);
	}

	public LocalModelConfigurationImpl(Class<?> modelClass, boolean isInternalModelOnly) {
		super(modelClass);
		this.setInternalModelOnly(isInternalModelOnly);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void inferClasses() {
		super.inferClasses();

		String baseClassPath = super.getBaseClassPath();
		String baseClassSimpleName = this.getBaseClassSimpleName();

		String dataClassName = baseClassPath + ".dto." + baseClassSimpleName + "Data";
		String generatorClassName = baseClassPath + ".generator." + baseClassSimpleName + "Generator";
		String linkSystemBuilderEntryClassName = baseClassPath + ".link." + baseClassSimpleName
		        + "LinkSystemBuilderEntry";
		String queryClassName = baseClassPath + ".search.query." + baseClassSimpleName + "Query";
		String queryInitializerClassName = queryClassName + "Initializer";

		String modelSecurityContextServiceEntry = baseClassPath + ".security." + baseClassSimpleName
		        + "SecurityContextServiceEntry";

		// Set Key Enforcement if set
		try {
			this.keyEnforcement = ObjectifyUtility.readObjectifyDatabaseEntityKeyEnforcement(this.getModelClass(), true);
		} catch (IllegalArgumentException e) {

			// Key enforcement is the default.
			this.keyEnforcement = ObjectifyDatabaseEntityKeyEnforcement.DEFAULT;
		}

		// Find Edit Controller Name
		List<String> packagePathComponents = PathUtility.getComponents(baseClassPath, "\\.");
		List<String> comPathComponents = packagePathComponents.subList(0, 3);
		String comPath = StringUtility.joinValues(".", comPathComponents);
		comPath = comPath + ".web.api.model.controllers";

		String editControllerClass = comPath + "." + baseClassSimpleName + "EditController";

		// Required Classes
		try {
			this.modelDataReaderClass = (Class<Object>) Class.forName(dataClassName + "Reader");
			this.modelDataBuilderClass = (Class<Object>) Class.forName(dataClassName + "Builder");

			this.modelGeneratorClass = (Class<Object>) Class.forName(generatorClassName);
			this.modelLinkSystemBuilderEntryClass = (Class<Object>) Class.forName(linkSystemBuilderEntryClassName);

			this.modelQueryClass = (Class<Object>) Class.forName(queryClassName);
			this.modelQueryInitializerClass = (Class<Object>) Class.forName(queryInitializerClassName);

			this.modelSecurityContextServiceEntryClass = (Class<Object>) Class
			        .forName(modelSecurityContextServiceEntry);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		// Optional Controller Class
		try {
			this.modelEditControllerClass = (Class<Object>) Class.forName(editControllerClass);
		} catch (ClassNotFoundException e) {
			LOGGER.warning("Could not find edit controller class: " + editControllerClass);
		}

		// Optional Classes
		try {
			String miscOwnedPath = baseClassPath + ".misc.owned";
			String ownedModelQuerySecurityDelegateClassName = miscOwnedPath + ".query.security." + baseClassSimpleName
			        + "OwnedModelQuerySecurityDelegate";
			this.modelOwnedModelQuerySecurityDelegateClass = (Class<Object>) Class
			        .forName(ownedModelQuerySecurityDelegateClassName);
		} catch (ClassNotFoundException e) {

		}
	}

	@Override
	protected LocalModelCrudsConfigurationImpl inferCrudsConfiguration() {
		return new LocalModelCrudsConfigurationImpl(this);
	}

	@Override
	protected LocalModelBeansConfigurationImpl makeModelBeansConfiguration() {
		return new LocalModelBeansConfigurationImpl(this.getModelType(), this.getModelKeyType());
	}

	@Override
	protected LocalModelContextConfigurer makeCustomModelContextConfigurer() {
		return new LocalModelContextConfigurerImpl();
	}

	public void setInternalModelOnly(boolean isInternalModelOnly,
	                                 boolean setIsReadOnly) {
		this.setInternalModelOnly(isInternalModelOnly);

		if (setIsReadOnly) {
			this.setIsReadOnly();
		}
	}

	/**
	 * Updates the CRUDs configuration to be read-only.
	 */
	public void setIsReadOnly() {
		this.setCrudsConfiguration(LocalModelCrudsConfigurationImpl.makeReadOnlyConfiguration(this));
	}

	@Override
	public ObjectifyDatabaseEntityKeyEnforcement getKeyEnforcement() {
		return this.keyEnforcement;
	}

	public void setKeyEnforcement(ObjectifyDatabaseEntityKeyEnforcement keyEnforcement) {
		if (keyEnforcement == null) {
			throw new IllegalArgumentException("keyEnforcement cannot be null.");
		}

		this.keyEnforcement = keyEnforcement;
	}

	@Override
	public Class<Object> getModelDataBuilderClass() {
		return this.modelDataBuilderClass;
	}

	@Override
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

	@Override
	public void setModelDataReaderClass(Class<Object> modelDataReaderClass) {
		if (modelDataReaderClass == null) {
			throw new IllegalArgumentException("modelDataReaderClass cannot be null.");
		}

		this.modelDataReaderClass = modelDataReaderClass;
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
	public Class<Object> getModelOwnedModelQuerySecurityDelegateClass() {
		return this.modelOwnedModelQuerySecurityDelegateClass;
	}

	public void setModelOwnedModelQuerySecurityDelegateClass(Class<Object> modelOwnedModelQuerySecurityDelegateClass) {
		this.modelOwnedModelQuerySecurityDelegateClass = modelOwnedModelQuerySecurityDelegateClass;
	}

	// MARK: AppModelBeansConfiguration
	@Override
	public String getModelObjectifyEntryBeanId() {
		return this.getBeansConfiguration().getModelObjectifyEntryBeanId();
	}

	@Override
	public String getModelRegistryId() {
		return this.getBeansConfiguration().getModelRegistryId();
	}

	@Override
	public String getModelKeyListAccessorFactoryId() {
		return this.getBeansConfiguration().getModelKeyListAccessorFactoryId();
	}

	@Override
	public String getModelInclusionReaderId() {
		return this.getBeansConfiguration().getModelInclusionReaderId();
	}

	@Override
	public String getModelLinkModelAccessorId() {
		return this.getBeansConfiguration().getModelLinkModelAccessorId();
	}

	@Override
	public String getStringModelKeyConverter() {
		return this.getBeansConfiguration().getStringModelKeyConverter();
	}

	@Override
	public String getModelCrudServiceId() {
		return this.getBeansConfiguration().getModelCrudServiceId();
	}

	@Override
	public String getModelCreateServiceId() {
		return this.getBeansConfiguration().getModelCreateServiceId();
	}

	@Override
	public String getModelReadServiceId() {
		return this.getBeansConfiguration().getModelReadServiceId();
	}

	@Override
	public String getModelUpdateServiceId() {
		return this.getBeansConfiguration().getModelUpdateServiceId();
	}

	@Override
	public String getModelDeleteServiceId() {
		return this.getBeansConfiguration().getModelDeleteServiceId();
	}

	@Override
	public String getModelQueryServiceId() {
		return this.getBeansConfiguration().getModelQueryServiceId();
	}

	@Override
	public String getModelLinkSystemBuilderEntryBeanId() {
		return this.getBeansConfiguration().getModelLinkSystemBuilderEntryBeanId();
	}

	@Override
	public String getModelSecurityContextServiceEntryBeanId() {
		return this.getBeansConfiguration().getModelSecurityContextServiceEntryBeanId();
	}

	@Override
	public String getNewModelFactoryBeanId() {
		return this.getBeansConfiguration().getNewModelFactoryBeanId();
	}

	@Override
	public String getModelStorerBeanId() {
		return this.getBeansConfiguration().getModelStorerBeanId();
	}

	@Override
	public String getModelUpdaterBeanId() {
		return this.getBeansConfiguration().getModelUpdaterBeanId();
	}

	@Override
	public String getModelDeleterBeanId() {
		return this.getBeansConfiguration().getModelDeleterBeanId();
	}

	@Override
	public String getModelScheduleCreateReviewBeanId() {
		return this.getBeansConfiguration().getModelScheduleCreateReviewBeanId();
	}

	@Override
	public String getModelScheduleUpdateReviewBeanId() {
		return this.getBeansConfiguration().getModelScheduleUpdateReviewBeanId();
	}

	@Override
	public String getModelRoleSetLoaderBeanId() {
		return this.getBeansConfiguration().getModelRoleSetLoaderBeanId();
	}

	@Override
	public String getModelQueryInitializerBeanId() {
		return this.getBeansConfiguration().getModelQueryInitializerBeanId();
	}

	@Override
	public String getModelConfiguredUpdaterBeanId() {
		return this.getBeansConfiguration().getModelConfiguredUpdaterBeanId();
	}

	@Override
	public String getModelSetterTaskBeanId() {
		return this.getBeansConfiguration().getModelSetterTaskBeanId();
	}

	@Override
	public String getModelEventServiceEntryBeanId() {
		return this.getBeansConfiguration().getModelEventServiceEntryBeanId();
	}

	@Override
	public boolean hasValidatedCreate() {
		return this.getCrudsConfiguration().hasValidatedCreate();
	}

	@Override
	public boolean hasCreateAttributeUpdater() {
		return this.getCrudsConfiguration().hasCreateAttributeUpdater();
	}

	@Override
	public Class<?> getModelCreateAttributeUpdaterClass() {
		return this.getCrudsConfiguration().getModelCreateAttributeUpdaterClass();
	}

	@Override
	public Class<?> getModelAttributeUpdaterClass() {
		return this.getCrudsConfiguration().getModelAttributeUpdaterClass();
	}

	@Override
	public boolean hasRelatedAttributeUtility() {
		return this.getCrudsConfiguration().hasRelatedAttributeUtility();
	}

	@Override
	public Class<?> getModelRelatedModelAttributeUtilityClass() {
		return this.getCrudsConfiguration().getModelRelatedModelAttributeUtilityClass();
	}

}
