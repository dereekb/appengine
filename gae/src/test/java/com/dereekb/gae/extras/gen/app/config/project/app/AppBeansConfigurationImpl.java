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

	public static final String OBJECTIFY_DATABASE_BEAN_ID = "objectifyDatabase";
	public static final String EVENT_SERVICE_BEAN_ID = "eventService";
	public static final String LINK_SERVICE_BEAN_ID = "linkService";
	public static final String TASK_SCHEDULER_BEAN_ID = "taskScheduler";
	public static final String TASK_QUEUE_NAME_BEAN_ID = "taskQueueName";
	public static final String MODEL_KEY_TYPE_CONVERTER_ID = "modelKeyTypeConverter";
	public static final String SYSTEM_LOGIN_TOKEN_FACTORY_BEAN_ID = "systemLoginTokenFactory";

	public static final String CRUD_READ_MODEL_ROLE_REF_BEAN_ID = "crudReadModelRole";
	public static final String CRUD_UPDATE_MODEL_ROLE_REF_BEAN_ID = "crudUpdateModelRole";
	public static final String CRUD_DELETE_MODEL_ROLE_REF_BEAN_ID = "crudDeleteModelRole";

	private String appInfoBeanId = APP_INFO_BEAN_ID;
	private String appKeyBeanId = APP_KEY_BEAN_ID;
	private String appNameBeanId = APP_NAME_BEAN_ID;
	private String appIdBeanId = APP_ID_BEAN_ID;

	private String objectifyDatabaseId = OBJECTIFY_DATABASE_BEAN_ID;
	private String eventServiceId = EVENT_SERVICE_BEAN_ID;
	private String linkServiceId = LINK_SERVICE_BEAN_ID;
	private String taskSchedulerId = TASK_SCHEDULER_BEAN_ID;
	private String taskQueueNameId = TASK_QUEUE_NAME_BEAN_ID;
	private String modelKeyTypeConverterId = MODEL_KEY_TYPE_CONVERTER_ID;
	private String SystemLoginTokenFactoryBeanId = SYSTEM_LOGIN_TOKEN_FACTORY_BEAN_ID;

	private String crudReadModelRoleRefBeanId = CRUD_READ_MODEL_ROLE_REF_BEAN_ID;
	private String crudUpdateModelRoleRefBeanId = CRUD_UPDATE_MODEL_ROLE_REF_BEAN_ID;
	private String crudDeleteModelRoleRefBeanId = CRUD_DELETE_MODEL_ROLE_REF_BEAN_ID;

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
	public String getObjectifyDatabaseId() {
		return this.objectifyDatabaseId;
	}

	public void setObjectifyDatabaseId(String objectifyDatabaseId) {
		if (objectifyDatabaseId == null) {
			throw new IllegalArgumentException("objectifyDatabaseId cannot be null.");
		}

		this.objectifyDatabaseId = objectifyDatabaseId;
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

	@Override
	public String getCrudReadModelRoleRefBeanId() {
		return this.crudReadModelRoleRefBeanId;
	}

	public void setCrudReadModelRoleRefBeanId(String crudReadModelRoleRefBeanId) {
		if (crudReadModelRoleRefBeanId == null) {
			throw new IllegalArgumentException("crudReadModelRoleRefBeanId cannot be null.");
		}

		this.crudReadModelRoleRefBeanId = crudReadModelRoleRefBeanId;
	}

	@Override
	public String getCrudUpdateModelRoleRefBeanId() {
		return this.crudUpdateModelRoleRefBeanId;
	}

	public void setCrudUpdateModelRoleRefBeanId(String crudUpdateModelRoleRefBeanId) {
		if (crudUpdateModelRoleRefBeanId == null) {
			throw new IllegalArgumentException("crudUpdateModelRoleRefBeanId cannot be null.");
		}

		this.crudUpdateModelRoleRefBeanId = crudUpdateModelRoleRefBeanId;
	}

	@Override
	public String getCrudDeleteModelRoleRefBeanId() {
		return this.crudDeleteModelRoleRefBeanId;
	}

	public void setCrudDeleteModelRoleRefBeanId(String crudDeleteModelRoleRefBeanId) {
		if (crudDeleteModelRoleRefBeanId == null) {
			throw new IllegalArgumentException("crudDeleteModelRoleRefBeanId cannot be null.");
		}

		this.crudDeleteModelRoleRefBeanId = crudDeleteModelRoleRefBeanId;
	}

}
