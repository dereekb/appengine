package com.dereekb.gae.extras.gen.app.config.model;

import com.dereekb.gae.model.extension.data.conversion.ModelDataConversionInfo;

public interface AppModelConfiguration
        extends ModelDataConversionInfo<Object, Object>, AppModelBeansConfiguration {

	public AppModelBeansConfiguration getBeansConfiguration();

	public Class<?> getModelGeneratorClass();

	/**
	 * Whether or not the model is local to the app.
	 */
	public boolean isLocalModel();

	public boolean hasIterateControllerEntry();

}
