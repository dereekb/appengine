package com.dereekb.gae.extras.gen.app.config.project.app;

import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

public interface AppBeansConfiguration {

	// App
	public String getAppInfoBeanId();

	public String getAppKeyBeanId();

	public String getAppNameBeanId();

	public String getAppIdBeanId();

	// Context
	public String getModelKeyTypeConverterId();

	public String getLinkServiceId();

	public String getTaskSchedulerId();

	public String getTaskQueueNameId();

	public String getSystemLoginTokenFactoryBeanId();

	// TaskQueue
	public String getEventServiceId();

	// Global / Utility
	public static final String STRING_LONG_MODEL_KEY_CONVERTER = "stringLongModelKeyConverter";
	public static final String STRING_NAME_MODEL_KEY_CONVERTER = "stringModelKeyConverter";

	public static String getModelKeyConverterBeanId(ModelKeyType modelKeyType) {
		return (modelKeyType == ModelKeyType.NUMBER) ? STRING_LONG_MODEL_KEY_CONVERTER
		        : STRING_NAME_MODEL_KEY_CONVERTER;
	}

}
