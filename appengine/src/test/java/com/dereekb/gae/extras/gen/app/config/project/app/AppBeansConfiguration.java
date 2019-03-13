package com.dereekb.gae.extras.gen.app.config.project.app;

import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * App-wide beans configuration.
 *
 * @author dereekb
 *
 */
public interface AppBeansConfiguration {

	// App
	public String getAppInfoBeanId();

	public String getAppKeyBeanId();

	public String getAppNameBeanId();

	public String getAppIdBeanId();

	public String getAppSecretBeanId();

	// Context
	public String getObjectifyInitializerId();

	public String getObjectifyDatabaseId();

	public String getModelKeyTypeConverterId();

	public String getLinkServiceId();

	public String getTaskSchedulerId();

	public String getTaskQueueNameId();

	public String getSystemLoginTokenServiceBeanId();

	public String getSystemLoginTokenFactoryBeanId();

	public String getCrudReadModelRoleRefBeanId();

	public String getCrudUpdateModelRoleRefBeanId();

	public String getCrudDeleteModelRoleRefBeanId();

	public String getAnonymousModelRoleSetContextServiceBeanId();

	public String getAppLoginSecurityServiceBeanId();

	public String getAppLoginSecurityVerifierServiceBeanId();

	public String getAppLoginSecuritySigningServiceBeanId();

	public String getLoginTokenServiceBeanId();

	public String getLoginTokenDecoderBeanId();

	public String getMailServiceBeanId();

	// Utility
	public AppUtilityBeansConfiguration getUtilityBeans();

	// TaskQueue
	public String getEventServiceId();

	public String getWebHookEventSubmitterBeanId();

	public String getWebHookEventConverterBeanId();

	// Global / Utility
	public static final String STRING_LONG_MODEL_KEY_CONVERTER = "stringLongModelKeyConverter";
	public static final String STRING_NAME_MODEL_KEY_CONVERTER = "stringModelKeyConverter";

	public static String getModelKeyConverterBeanId(ModelKeyType modelKeyType) {
		return (modelKeyType == ModelKeyType.NUMBER) ? STRING_LONG_MODEL_KEY_CONVERTER
		        : STRING_NAME_MODEL_KEY_CONVERTER;
	}

	// Development
	public String getAppDevelopmentProxyUrlBeanId();

}