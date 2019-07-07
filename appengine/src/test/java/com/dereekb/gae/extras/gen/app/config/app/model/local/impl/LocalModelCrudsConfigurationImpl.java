package com.dereekb.gae.extras.gen.app.config.app.model.local.impl;

import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelCrudsConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.impl.AppModelCrudsConfigurationImpl;

/**
 * {@link LocalModelCrudsConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class LocalModelCrudsConfigurationImpl extends AppModelCrudsConfigurationImpl
        implements LocalModelCrudsConfiguration {

	private boolean validatedCreate = false;

	private Class<Object> modelCreateAttributeUpdaterClass;
	private Class<Object> modelAttributeUpdaterClass;
	private Class<Object> modelRelatedModelAttributeUtilityClass;

	public LocalModelCrudsConfigurationImpl(AppModelConfiguration configuration) {
		super();
		this.inferClasses(configuration);
	}

	public static LocalModelCrudsConfigurationImpl makeReadOnlyConfiguration(AppModelConfiguration configuration)
	{
		LocalModelCrudsConfigurationImpl config = new LocalModelCrudsConfigurationImpl(configuration);
		config.setIsReadOnly();
		return config;
	}

	@SuppressWarnings("unchecked")
	private void inferClasses(AppModelConfiguration configuration) {

		String baseClassPath = configuration.getBaseClassPath();
		String baseClassSimpleName = configuration.getBaseClassSimpleName();

		String updateAttributeInitializerClassName = baseClassPath + ".crud." + baseClassSimpleName
		        + "AttributeUpdater";
		String createAttributeInitializerClassName = baseClassPath + ".crud." + baseClassSimpleName
		        + "CreateAttributeUpdater";

		// Required Classes
		try {
			this.modelAttributeUpdaterClass = (Class<Object>) Class.forName(updateAttributeInitializerClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		// Optional Classses
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
	}

	// MARK: LocalModelCrudsConfiguration
	@Override
	public boolean hasValidatedCreate() {
		return this.validatedCreate;
	}

	public void setValidatedCreate(boolean validatedCreate) {
		this.validatedCreate = validatedCreate;
	}

	@Override
	public boolean hasCreateAttributeUpdater() {
		return this.modelCreateAttributeUpdaterClass != null;
	}

	@Override
	public boolean hasRelatedAttributeUtility() {
		return this.modelRelatedModelAttributeUtilityClass != null;
	}

	@Override
	public Class<Object> getModelCreateAttributeUpdaterClass() {
		return this.modelCreateAttributeUpdaterClass;
	}

	public void setModelCreateAttributeUpdaterClass(Class<Object> modelCreateAttributeUpdaterClass) {
		if (modelCreateAttributeUpdaterClass == null) {
			throw new IllegalArgumentException("modelCreateAttributeUpdaterClass cannot be null.");
		}

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
	public Class<Object> getModelRelatedModelAttributeUtilityClass() {
		return this.modelRelatedModelAttributeUtilityClass;
	}

	public void setModelRelatedModelAttributeUtilityClass(Class<Object> modelRelatedModelAttributeUtilityClass) {
		if (modelRelatedModelAttributeUtilityClass == null) {
			throw new IllegalArgumentException("modelRelatedModelAttributeUtilityClass cannot be null.");
		}

		this.modelRelatedModelAttributeUtilityClass = modelRelatedModelAttributeUtilityClass;
	}

}
