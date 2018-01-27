package com.dereekb.gae.extras.gen.app.config.model;

import com.dereekb.gae.model.extension.data.conversion.ModelDataConversionInfo;

public interface AppModelConfiguration
        extends ModelDataConversionInfo<Object, Object>, AppModelBeansConfiguration {

	public AppModelBeansConfiguration getBeansConfiguration();

	public Class<?> getModelGeneratorClass();

}
