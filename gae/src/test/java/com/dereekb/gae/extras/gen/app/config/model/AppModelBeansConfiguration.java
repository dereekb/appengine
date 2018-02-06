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

	public String getModelClassBeanId();

	public String getModelDtoBeanId();

	public String getModelDataConverterBeanId();

	public String getModelRegistryId();

	/**
	 * Generally returns the registry, but for remote models it will return the
	 * factory.
	 */
	public String getModelKeyListAccessorFactoryId();

	public String getModelReadServiceId();

	public String getModelQueryServiceId();

	public String getModelInclusionReaderId();

	public String getModelLinkModelAccessorId();

	public String getStringModelKeyConverter();

}
