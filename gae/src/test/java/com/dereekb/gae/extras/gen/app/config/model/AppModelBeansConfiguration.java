package com.dereekb.gae.extras.gen.app.config.model;

public interface AppModelBeansConfiguration {

	/**
	 * Returns the bean model name/prefix, which is usually
	 * {{@link #getModelType()} with the first character to lowercase.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getModelBeanPrefix();

	public String getModelTypeBeanId();

	public String getModelIdTypeBeanId();

	public String getModelClassBeanId();

	public String getModelDtoClassBeanId();

	public String getModelObjectifyEntryBeanId();

	public String getModelDtoBeanId();

	public String getModelDataConverterBeanId();

	public String getModelRegistryId();

	public String getModelSetterTaskBeanId();

	public String getModelQueryInitializerBeanId();

	public String getModelLinkSystemBuilderEntryBeanId();

	/**
	 * Generally returns the registry, but for remote models it will return the
	 * factory.
	 */
	public String getModelKeyListAccessorFactoryId();

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

	public String getModelSecurityContextServiceEntryBeanId();

}
