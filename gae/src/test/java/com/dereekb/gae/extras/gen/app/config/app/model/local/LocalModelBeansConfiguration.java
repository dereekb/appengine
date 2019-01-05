package com.dereekb.gae.extras.gen.app.config.app.model.local;

import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelBeansConfiguration;

/**
 * {@link AppModelBeansConfiguration} interface extension for local models.
 *
 * @author dereekb
 *
 */
public interface LocalModelBeansConfiguration
        extends AppModelBeansConfiguration {

	public String getModelObjectifyEntryBeanId();

	public String getModelRegistryId();

	public String getModelSetterTaskBeanId();

	public String getModelQueryInitializerBeanId();

	public String getModelLinkSystemBuilderEntryBeanId();

	public String getNewModelFactoryBeanId();

	public String getModelCrudServiceId();

	public String getModelCreateServiceId();

	public String getModelReadServiceId();

	public String getModelUpdateServiceId();

	public String getModelDeleteServiceId();

	public String getModelQueryServiceId();

	public String getModelStorerBeanId();

	public String getModelUpdaterBeanId();

	public String getModelConfiguredUpdaterBeanId();

	public String getModelDeleterBeanId();

	public String getModelRoleSetLoaderBeanId();

	public String getModelScheduleCreateReviewBeanId();

	public String getModelScheduleUpdateReviewBeanId();

	public String getModelInclusionReaderId();

	public String getModelLinkModelAccessorId();

	public String getStringModelKeyConverter();

	public String getModelEventServiceEntryBeanId();

}
