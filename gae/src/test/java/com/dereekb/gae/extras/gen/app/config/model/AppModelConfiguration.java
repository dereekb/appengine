package com.dereekb.gae.extras.gen.app.config.model;

import com.dereekb.gae.model.extension.data.conversion.ModelDataConversionInfo;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

public interface AppModelConfiguration
        extends ModelDataConversionInfo<Object, Object>, AppModelBeansConfiguration {

	public AppModelBeansConfiguration getBeansConfiguration();

	public ModelKeyType getModelKeyType();

	public Class<?> getModelDataBuilderClass();

	public Class<?> getModelDataReaderClass();

	public Class<?> getModelGeneratorClass();

	public Class<?> getModelLinkSystemBuilderEntryClass();

	public Class<?> getModelQueryClass();

	public Class<?> getModelQueryInitializerClass();

	public Class<?> getModelEditControllerClass();

	public boolean hasIterateControllerEntry();

	/**
	 * Whether or not the model is local to the app.
	 */
	public boolean isLocalModel();

}
