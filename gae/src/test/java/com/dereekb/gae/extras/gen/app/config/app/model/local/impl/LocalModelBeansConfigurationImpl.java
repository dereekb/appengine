package com.dereekb.gae.extras.gen.app.config.app.model.local.impl;

import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.impl.AppModelBeansConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfiguration;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * {@link LocalModelBeansConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class LocalModelBeansConfigurationImpl extends AppModelBeansConfigurationImpl
        implements LocalModelBeansConfiguration {

	private String modelObjectifyEntryBeanId;
	private String modelRegistryId;
	private String modelSetterTaskBeanId;
	private String modelQueryInitializerBeanId;

	private String newModelFactoryBeanId;
	private String modelCrudServiceId;
	private String modelCreateServiceId;
	private String modelReadServiceId;
	private String modelUpdateServiceId;
	private String modelDeleteServiceId;
	private String modelInclusionReaderId;
	private String modelLinkModelAccessorId;
	private String modelLinkSystemBuilderEntryBeanId;

	private String modelStorerBeanId;
	private String modelUpdaterBeanId;
	private String modelConfiguredUpdaterBeanId;
	private String modelDeleterBeanId;
	private String modelScheduleCreateReviewBeanId;
	private String modelScheduleUpdateReviewBeanId;
	private String modelRoleSetLoaderBeanId;

	private String stringModelKeyConverter;
	private String modelQueryServiceId;
	private String modelEventServiceEntryBeanId;

	public LocalModelBeansConfigurationImpl(String modelType, ModelKeyType modelKeyType) {
		super(modelType, modelKeyType);
		String modelBeanPrefix = this.getModelBeanPrefix();

		this.setModelObjectifyEntryBeanId(modelBeanPrefix + "ObjectifyEntry");
		this.setModelRegistryId(modelBeanPrefix + "Registry");
		this.setModelSetterTaskBeanId(modelBeanPrefix + "SetterTask");
		this.setModelQueryInitializerBeanId(modelBeanPrefix + "QueryInitializer");
		this.setNewModelFactoryBeanId(modelBeanPrefix + "Factory");
		this.setModelCrudServiceId(modelBeanPrefix + "CrudService");
		this.setModelCreateServiceId(modelBeanPrefix + "CreateService");
		this.setModelReadServiceId(modelBeanPrefix + "ReadService");
		this.setModelUpdateServiceId(modelBeanPrefix + "UpdateService");
		this.setModelDeleteServiceId(modelBeanPrefix + "DeleteService");
		this.setModelInclusionReaderId(modelBeanPrefix + "InclusionReader");
		this.setModelLinkModelAccessorId(modelBeanPrefix + "LinkModelAccessor");
		this.setModelLinkSystemBuilderEntryBeanId(modelBeanPrefix + "LinkSystemBuilderEntry");
		this.setModelStorerBeanId(modelBeanPrefix + "Storer");
		this.setModelUpdaterBeanId(modelBeanPrefix + "Updater");
		this.setModelConfiguredUpdaterBeanId(modelBeanPrefix + "ConfiguredUpdater");
		this.setModelDeleterBeanId(modelBeanPrefix + "Deleter");
		this.setModelScheduleCreateReviewBeanId(modelBeanPrefix + "ScheduleCreateReview");
		this.setModelScheduleUpdateReviewBeanId(modelBeanPrefix + "ScheduleUpdateReview");
		this.setModelRoleSetLoaderBeanId(modelBeanPrefix + "ModelRoleSetLoader");
		this.setStringModelKeyConverter(AppBeansConfiguration.getModelKeyConverterBeanId(modelKeyType));
		this.setModelQueryServiceId(modelBeanPrefix + "QueryService");
		this.setModelEventServiceEntryBeanId(modelBeanPrefix + "ModelEventService");
	}

	@Override
	public String getModelObjectifyEntryBeanId() {
		return this.modelObjectifyEntryBeanId;
	}

	public void setModelObjectifyEntryBeanId(String modelObjectifyEntryBeanId) {
		if (modelObjectifyEntryBeanId == null) {
			throw new IllegalArgumentException("modelObjectifyEntryBeanId cannot be null.");
		}

		this.modelObjectifyEntryBeanId = modelObjectifyEntryBeanId;
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
	public String getModelSetterTaskBeanId() {
		return this.modelSetterTaskBeanId;
	}

	public void setModelSetterTaskBeanId(String modelSetterTaskBeanId) {
		if (modelSetterTaskBeanId == null) {
			throw new IllegalArgumentException("modelSetterTaskBeanId cannot be null.");
		}

		this.modelSetterTaskBeanId = modelSetterTaskBeanId;
	}

	@Override
	public String getModelQueryInitializerBeanId() {
		return this.modelQueryInitializerBeanId;
	}

	public void setModelQueryInitializerBeanId(String modelQueryInitializerBeanId) {
		if (modelQueryInitializerBeanId == null) {
			throw new IllegalArgumentException("modelQueryInitializerBeanId cannot be null.");
		}

		this.modelQueryInitializerBeanId = modelQueryInitializerBeanId;
	}

	@Override
	public String getNewModelFactoryBeanId() {
		return this.newModelFactoryBeanId;
	}

	public void setNewModelFactoryBeanId(String newModelFactoryBeanId) {
		if (newModelFactoryBeanId == null) {
			throw new IllegalArgumentException("newModelFactoryBeanId cannot be null.");
		}

		this.newModelFactoryBeanId = newModelFactoryBeanId;
	}

	@Override
	public String getModelCrudServiceId() {
		return this.modelCrudServiceId;
	}

	public void setModelCrudServiceId(String modelCrudServiceId) {
		if (modelCrudServiceId == null) {
			throw new IllegalArgumentException("modelCrudServiceId cannot be null.");
		}

		this.modelCrudServiceId = modelCrudServiceId;
	}

	@Override
	public String getModelCreateServiceId() {
		return this.modelCreateServiceId;
	}

	public void setModelCreateServiceId(String modelCreateServiceId) {
		if (modelCreateServiceId == null) {
			throw new IllegalArgumentException("modelCreateServiceId cannot be null.");
		}

		this.modelCreateServiceId = modelCreateServiceId;
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
	public String getModelUpdateServiceId() {
		return this.modelUpdateServiceId;
	}

	public void setModelUpdateServiceId(String modelUpdateServiceId) {
		if (modelUpdateServiceId == null) {
			throw new IllegalArgumentException("modelUpdateServiceId cannot be null.");
		}

		this.modelUpdateServiceId = modelUpdateServiceId;
	}

	@Override
	public String getModelDeleteServiceId() {
		return this.modelDeleteServiceId;
	}

	public void setModelDeleteServiceId(String modelDeleteServiceId) {
		if (modelDeleteServiceId == null) {
			throw new IllegalArgumentException("modelDeleteServiceId cannot be null.");
		}

		this.modelDeleteServiceId = modelDeleteServiceId;
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
	public String getModelLinkSystemBuilderEntryBeanId() {
		return this.modelLinkSystemBuilderEntryBeanId;
	}

	public void setModelLinkSystemBuilderEntryBeanId(String modelLinkSystemBuilderEntryBeanId) {
		if (modelLinkSystemBuilderEntryBeanId == null) {
			throw new IllegalArgumentException("modelLinkSystemBuilderEntryBeanId cannot be null.");
		}

		this.modelLinkSystemBuilderEntryBeanId = modelLinkSystemBuilderEntryBeanId;
	}

	@Override
	public String getModelStorerBeanId() {
		return this.modelStorerBeanId;
	}

	public void setModelStorerBeanId(String modelStorerBeanId) {
		if (modelStorerBeanId == null) {
			throw new IllegalArgumentException("modelStorerBeanId cannot be null.");
		}

		this.modelStorerBeanId = modelStorerBeanId;
	}

	@Override
	public String getModelUpdaterBeanId() {
		return this.modelUpdaterBeanId;
	}

	public void setModelUpdaterBeanId(String modelUpdaterBeanId) {
		if (modelUpdaterBeanId == null) {
			throw new IllegalArgumentException("modelUpdaterBeanId cannot be null.");
		}

		this.modelUpdaterBeanId = modelUpdaterBeanId;
	}

	@Override
	public String getModelConfiguredUpdaterBeanId() {
		return this.modelConfiguredUpdaterBeanId;
	}

	public void setModelConfiguredUpdaterBeanId(String modelConfiguredUpdaterBeanId) {
		if (modelConfiguredUpdaterBeanId == null) {
			throw new IllegalArgumentException("modelConfiguredUpdaterBeanId cannot be null.");
		}

		this.modelConfiguredUpdaterBeanId = modelConfiguredUpdaterBeanId;
	}

	@Override
	public String getModelDeleterBeanId() {
		return this.modelDeleterBeanId;
	}

	public void setModelDeleterBeanId(String modelDeleterBeanId) {
		if (modelDeleterBeanId == null) {
			throw new IllegalArgumentException("modelDeleterBeanId cannot be null.");
		}

		this.modelDeleterBeanId = modelDeleterBeanId;
	}

	@Override
	public String getModelScheduleCreateReviewBeanId() {
		return this.modelScheduleCreateReviewBeanId;
	}

	public void setModelScheduleCreateReviewBeanId(String modelScheduleCreateReviewBeanId) {
		if (modelScheduleCreateReviewBeanId == null) {
			throw new IllegalArgumentException("modelScheduleCreateReviewBeanId cannot be null.");
		}

		this.modelScheduleCreateReviewBeanId = modelScheduleCreateReviewBeanId;
	}

	@Override
	public String getModelScheduleUpdateReviewBeanId() {
		return this.modelScheduleUpdateReviewBeanId;
	}

	public void setModelScheduleUpdateReviewBeanId(String modelScheduleUpdateReviewBeanId) {
		if (modelScheduleUpdateReviewBeanId == null) {
			throw new IllegalArgumentException("modelScheduleUpdateReviewBeanId cannot be null.");
		}

		this.modelScheduleUpdateReviewBeanId = modelScheduleUpdateReviewBeanId;
	}

	@Override
	public String getModelRoleSetLoaderBeanId() {
		return this.modelRoleSetLoaderBeanId;
	}

	public void setModelRoleSetLoaderBeanId(String modelRoleSetLoaderBeanId) {
		if (modelRoleSetLoaderBeanId == null) {
			throw new IllegalArgumentException("modelRoleSetLoaderBeanId cannot be null.");
		}

		this.modelRoleSetLoaderBeanId = modelRoleSetLoaderBeanId;
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

	@Override
	public String getModelEventServiceEntryBeanId() {
		return this.modelEventServiceEntryBeanId;
	}

	public void setModelEventServiceEntryBeanId(String modelEventServiceEntryBeanId) {
		if (modelEventServiceEntryBeanId == null) {
			throw new IllegalArgumentException("modelEventServiceEntryBeanId cannot be null.");
		}

		this.modelEventServiceEntryBeanId = modelEventServiceEntryBeanId;
	}

}
