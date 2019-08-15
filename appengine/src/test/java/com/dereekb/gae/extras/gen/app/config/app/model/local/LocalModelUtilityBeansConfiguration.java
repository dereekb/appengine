package com.dereekb.gae.extras.gen.app.config.app.model.local;

/**
 * Interface extension that exposes optional bean ids.
 *
 * @author dereekb
 *
 */
public interface LocalModelUtilityBeansConfiguration {

	// Search
	public String getTypedModelSearchServiceBeanId();

	public String getModelSearchFactoryBeanId();

	public String getModelSearchInitializerBeanId();

}
