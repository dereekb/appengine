package com.dereekb.gae.extras.gen.app.config.app.model.shared;

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

	/**
	 * Whether or not this model is an internal server model only.
	 * <p>
	 * Currently this means that the model is a read-only model.
	 */
	public boolean isInternalModelOnly();

	/**
	 * Whether or not this model is an system-only model that has no outside
	 * access of any sort and does not require security checks.
	 * <p>
	 * Similar to {@link #isInternalModelOnly()} but additional components are
	 * disabled/skipped from generation, such as security.
	 */
	public boolean isSystemModelOnly();

}
