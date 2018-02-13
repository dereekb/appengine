package com.dereekb.gae.extras.gen.app.config.project.app;

/**
 * {@link AppBeansConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class AppBeansConfigurationImpl
        implements AppBeansConfiguration {

	public static final String APP_INFO_BEAN_ID = "serverAppInfo";
	public static final String APP_KEY_BEAN_ID = "serverAppKey";
	public static final String APP_NAME_BEAN_ID = "serverAppName";
	public static final String APP_ID_BEAN_ID = "serverAppId";

	public static final String EVENT_SERVICE_BEAN_ID = "eventService";
	public static final String LINK_SERVICE_BEAN_ID = "linkService";
	public static final String TASK_SCHEDULER_BEAN_ID = "taskScheduler";
	public static final String TASK_QUEUE_NAME_BEAN_ID = "taskQueueName";
	public static final String MODEL_KEY_TYPE_CONVERTER_ID = "modelKeyTypeConverter";
	public static final String SYSTEM_LOGIN_TOKEN_FACTORY_BEAN_ID = "systemLoginTokenFactory";

	private String appInfoBeanId = APP_INFO_BEAN_ID;
	private String appKeyBeanId = APP_KEY_BEAN_ID;
	private String appNameBeanId = APP_NAME_BEAN_ID;
	private String appIdBeanId = APP_ID_BEAN_ID;

	private String eventServiceId = EVENT_SERVICE_BEAN_ID;
	private String linkServiceId = LINK_SERVICE_BEAN_ID;
	private String taskSchedulerId = TASK_SCHEDULER_BEAN_ID;
	private String taskQueueNameId = TASK_QUEUE_NAME_BEAN_ID;
	private String modelKeyTypeConverterId = MODEL_KEY_TYPE_CONVERTER_ID;
	private String SystemLoginTokenFactoryBeanId = SYSTEM_LOGIN_TOKEN_FACTORY_BEAN_ID;

	@Override
	public String getAppInfoBeanId() {
		return this.appInfoBeanId;
	}

	public void setAppInfoBeanId(String appInfoBeanId) {
		if (appInfoBeanId == null) {
			throw new IllegalArgumentException("appInfoBeanId cannot be null.");
		}

		this.appInfoBeanId = appInfoBeanId;
	}

	@Override
	public String getAppKeyBeanId() {
		return this.appKeyBeanId;
	}

	public void setAppKeyBeanId(String appKeyBeanId) {
		if (appKeyBeanId == null) {
			throw new IllegalArgumentException("appKeyBeanId cannot be null.");
		}

		this.appKeyBeanId = appKeyBeanId;
	}

	@Override
	public String getAppNameBeanId() {
		return this.appNameBeanId;
	}

	public void setAppNameBeanId(String appNameBeanId) {
		if (appNameBeanId == null) {
			throw new IllegalArgumentException("appNameBeanId cannot be null.");
		}

		this.appNameBeanId = appNameBeanId;
	}

	@Override
	public String getAppIdBeanId() {
		return this.appIdBeanId;
	}

	public void setAppIdBeanId(String appIdBeanId) {
		if (appIdBeanId == null) {
			throw new IllegalArgumentException("appIdBeanId cannot be null.");
		}

		this.appIdBeanId = appIdBeanId;
	}

	@Override
	public String getEventServiceId() {
		return this.eventServiceId;
	}

	public void setEventServiceId(String eventServiceId) {
		if (eventServiceId == null) {
			throw new IllegalArgumentException("eventServiceId cannot be null.");
		}

		this.eventServiceId = eventServiceId;
	}

	@Override
	public String getLinkServiceId() {
		return this.linkServiceId;
	}

	public void setLinkServiceId(String linkServiceId) {
		if (linkServiceId == null) {
			throw new IllegalArgumentException("linkServiceId cannot be null.");
		}

		this.linkServiceId = linkServiceId;
	}

	@Override
	public String getTaskSchedulerId() {
		return this.taskSchedulerId;
	}

	public void setTaskSchedulerId(String taskSchedulerId) {
		if (taskSchedulerId == null) {
			throw new IllegalArgumentException("taskSchedulerId cannot be null.");
		}

		this.taskSchedulerId = taskSchedulerId;
	}

	@Override
	public String getTaskQueueNameId() {
		return this.taskQueueNameId;
	}

	public void setTaskQueueNameId(String taskQueueNameId) {
		if (taskQueueNameId == null) {
			throw new IllegalArgumentException("taskQueueNameId cannot be null.");
		}

		this.taskQueueNameId = taskQueueNameId;
	}

	@Override
	public String getModelKeyTypeConverterId() {
		return this.modelKeyTypeConverterId;
	}

	public void setModelKeyTypeConverterId(String modelKeyTypeConverterId) {
		if (modelKeyTypeConverterId == null) {
			throw new IllegalArgumentException("modelKeyTypeConverterId cannot be null.");
		}

		this.modelKeyTypeConverterId = modelKeyTypeConverterId;
	}

	@Override
	public String getSystemLoginTokenFactoryBeanId() {
		return this.SystemLoginTokenFactoryBeanId;
	}

	public void setSystemLoginTokenFactoryBeanId(String systemLoginTokenFactoryBeanId) {
		if (systemLoginTokenFactoryBeanId == null) {
			throw new IllegalArgumentException("systemLoginTokenFactoryBeanId cannot be null.");
		}

		this.SystemLoginTokenFactoryBeanId = systemLoginTokenFactoryBeanId;
	}

}
