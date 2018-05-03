package com.dereekb.gae.extras.gen.app.config.app.model.shared;

import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomModelContextConfigurer;
import com.dereekb.gae.model.extension.data.conversion.ModelDataConversionInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * Generic model configuration.
 *
 * @author dereekb
 *
 */
public interface AppModelConfiguration
        extends ModelDataConversionInfo<Object, Object>, AppModelCrudsConfiguration, AppModelBeansConfiguration {

	public Package getBaseClassPackage();

	public String getBaseClassPath();

	public String getBaseClassSimpleName();

	public AppModelCrudsConfiguration getCrudsConfiguration();

	public AppModelBeansConfiguration getBeansConfiguration();

	public ModelKeyType getModelKeyType();

	public Class<?> getModelDataBuilderClass();

	public Class<?> getModelDataReaderClass();

	/**
	 * Whether or not the model is local to the app.
	 */
	public boolean isLocalModel();

	// Custom Configuration
	/**
	 * Returns the custom configuration for configuring model contexts.
	 *
	 * @return {@link CustomModelContextConfigurer}. Never {@code null}.
	 */
	public CustomModelContextConfigurer getCustomModelContextConfigurer();

}
