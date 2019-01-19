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
	public static final String APP_SECRET_BEAN_ID = "serverAppSecret";

	public static final String OBJECTIFY_DATABASE_BEAN_ID = "objectifyDatabase";
	public static final String EVENT_SERVICE_BEAN_ID = "eventService";
	public static final String WEB_HOOK_EVENT_SUBMITTER_BEAN_ID = "webHookEventSubmitter";
	public static final String WEB_HOOK_EVENT_CONVERTER_BEAN_ID = "webHookEventConverter";
	public static final String LINK_SERVICE_BEAN_ID = "linkService";
	public static final String TASK_SCHEDULER_BEAN_ID = "taskScheduler";
	public static final String TASK_QUEUE_NAME_BEAN_ID = "taskQueueName";
	public static final String MODEL_KEY_TYPE_CONVERTER_ID = "modelKeyTypeConverter";
	public static final String SYSTEM_LOGIN_TOKEN_SERVICE_BEAN_ID = "systemLoginTokenService";
	public static final String SYSTEM_LOGIN_TOKEN_FACTORY_BEAN_ID = "systemLoginTokenFactory";
	public static final String MAIL_SERVICE_BEAN_ID = "mailService";

	public static final String CRUD_READ_MODEL_ROLE_REF_BEAN_ID = "crudReadModelRole";
	public static final String CRUD_UPDATE_MODEL_ROLE_REF_BEAN_ID = "crudUpdateModelRole";
	public static final String CRUD_DELETE_MODEL_ROLE_REF_BEAN_ID = "crudDeleteModelRole";
	public static final String ANONYMOUS_MODEL_ROLE_SET_CONTEXT_SERVICE_BEAN_ID = "anonymousModelRoleSetContextService";

	public static final String APP_LOGIN_SECURITY_SERVICE_BEAN_ID = "appLoginSecurityService";
	public static final String APP_LOGIN_SECURITY_VERIFIER_SERVICE_BEAN_ID = "appLoginSecurityVerifierService";
	public static final String APP_LOGIN_SECURITY_SIGNING_SERVICE_BEAN_ID = "appLoginSecuritySigningService";
	public static final String LOGIN_TOKEN_SERVICE_BEAN_ID = "loginTokenService";
	public static final String LOGIN_TOKEN_DECODER_BEAN_ID = "loginTokenDecoder";

	public static final String CLIENT_LOGIN_TOKEN_MODEL_CONTEXT_SERVICE_ENTRY_FACTORY_BEAN_ID = "clientLoginTokenModelContextServiceEntryFactory";
	public static final String PASSWORD_LOGIN_SERVICE_BEAN_ID = "passwordLoginService";
	public static final String LOGIN_REGISTER_SERVICE_BEAN_ID = "loginRegisterService";
	public static final String LOGIN_TOKEN_USER_DETAILS_BUILDER_BEAN_ID = "loginTokenUserDetailsBuilder";

	public static final String APP_DEVELOPMENT_PROXY_URL_BEAN_ID = "appDevelopmentProxyUrl";

	private String appInfoBeanId = APP_INFO_BEAN_ID;
	private String appKeyBeanId = APP_KEY_BEAN_ID;
	private String appNameBeanId = APP_NAME_BEAN_ID;
	private String appIdBeanId = APP_ID_BEAN_ID;
	private String appSecretBeanId = APP_SECRET_BEAN_ID;

	private String objectifyDatabaseId = OBJECTIFY_DATABASE_BEAN_ID;
	private String eventServiceId = EVENT_SERVICE_BEAN_ID;
	private String webHookEventSubmitterBeanId = WEB_HOOK_EVENT_SUBMITTER_BEAN_ID;
	private String webHookEventConverterBeanId = WEB_HOOK_EVENT_CONVERTER_BEAN_ID;
	private String linkServiceId = LINK_SERVICE_BEAN_ID;
	private String taskSchedulerId = TASK_SCHEDULER_BEAN_ID;
	private String taskQueueNameId = TASK_QUEUE_NAME_BEAN_ID;
	private String modelKeyTypeConverterId = MODEL_KEY_TYPE_CONVERTER_ID;
	private String systemLoginTokenServiceBeanId = SYSTEM_LOGIN_TOKEN_SERVICE_BEAN_ID;
	private String systemLoginTokenFactoryBeanId = SYSTEM_LOGIN_TOKEN_FACTORY_BEAN_ID;
	private String mailServiceBeanId = MAIL_SERVICE_BEAN_ID;
	private String anonymousModelRoleSetContextServiceBeanId = ANONYMOUS_MODEL_ROLE_SET_CONTEXT_SERVICE_BEAN_ID;

	private String appLoginSecurityServiceBeanId = APP_LOGIN_SECURITY_SERVICE_BEAN_ID;
	private String appLoginSecurityVerifierServiceBeanId = APP_LOGIN_SECURITY_VERIFIER_SERVICE_BEAN_ID;
	private String appLoginSecuritySigningServiceBeanId = APP_LOGIN_SECURITY_SIGNING_SERVICE_BEAN_ID;
	private String loginTokenServiceBeanId = LOGIN_TOKEN_SERVICE_BEAN_ID;
	private String loginTokenDecoderBeanId = LOGIN_TOKEN_DECODER_BEAN_ID;

	private String crudReadModelRoleRefBeanId = CRUD_READ_MODEL_ROLE_REF_BEAN_ID;
	private String crudUpdateModelRoleRefBeanId = CRUD_UPDATE_MODEL_ROLE_REF_BEAN_ID;
	private String crudDeleteModelRoleRefBeanId = CRUD_DELETE_MODEL_ROLE_REF_BEAN_ID;

	private String appDevelopmentProxyUrlBeanId = APP_DEVELOPMENT_PROXY_URL_BEAN_ID;

	private AppUtilityBeansConfiguration utilityBeansConfiguration = new AppUtilityBeansConfigurationImpl();

	public static class AppUtilityBeansConfigurationImpl
	        implements AppUtilityBeansConfiguration {

		private String clientLoginTokenModelContextServiceEntryFactoryBeanId = CLIENT_LOGIN_TOKEN_MODEL_CONTEXT_SERVICE_ENTRY_FACTORY_BEAN_ID;
		private String passwordLoginServiceBeanId = PASSWORD_LOGIN_SERVICE_BEAN_ID;
		private String loginRegisterServiceBeanId = LOGIN_REGISTER_SERVICE_BEAN_ID;
		private String loginTokenUserDetailsBuilderBeanId = LOGIN_TOKEN_USER_DETAILS_BUILDER_BEAN_ID;

		// MARK: AppUtilityBeansConfiguration
		@Override
		public String getClientLoginTokenModelContextServiceEntryFactoryBeanId() {
			return this.clientLoginTokenModelContextServiceEntryFactoryBeanId;
		}

		public void setClientLoginTokenModelContextServiceEntryFactoryBeanId(String clientLoginTokenModelContextServiceEntryFactoryBeanId) {
			if (clientLoginTokenModelContextServiceEntryFactoryBeanId == null) {
				throw new IllegalArgumentException(
				        "clientLoginTokenModelContextServiceEntryFactoryBeanId cannot be null.");
			}

			this.clientLoginTokenModelContextServiceEntryFactoryBeanId = clientLoginTokenModelContextServiceEntryFactoryBeanId;
		}

		@Override
		public String getPasswordLoginServiceBeanId() {
			return this.passwordLoginServiceBeanId;
		}

		public void setPasswordLoginServiceBeanId(String passwordLoginServiceBeanId) {
			if (passwordLoginServiceBeanId == null) {
				throw new IllegalArgumentException("passwordLoginServiceBeanId cannot be null.");
			}

			this.passwordLoginServiceBeanId = passwordLoginServiceBeanId;
		}

		@Override
		public String getLoginRegisterServiceBeanId() {
			return this.loginRegisterServiceBeanId;
		}

		public void setLoginRegisterServiceBeanId(String loginRegisterServiceBeanId) {
			if (loginRegisterServiceBeanId == null) {
				throw new IllegalArgumentException("loginRegisterServiceBeanId cannot be null.");
			}

			this.loginRegisterServiceBeanId = loginRegisterServiceBeanId;
		}

		@Override
		public String getLoginTokenUserDetailsBuilderBeanId() {
			return this.loginTokenUserDetailsBuilderBeanId;
		}

		public void setLoginTokenUserDetailsBuilderBeanId(String loginTokenUserDetailsBuilderBeanId) {
			if (loginTokenUserDetailsBuilderBeanId == null) {
				throw new IllegalArgumentException("loginTokenUserDetailsBuilderBeanId cannot be null.");
			}

			this.loginTokenUserDetailsBuilderBeanId = loginTokenUserDetailsBuilderBeanId;
		}

	}

	// MARK: AppBeansConfiguration
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
	public String getAppSecretBeanId() {
		return this.appSecretBeanId;
	}

	public void setAppSecretBeanId(String appSecretBeanId) {
		if (appSecretBeanId == null) {
			throw new IllegalArgumentException("appSecretBeanId cannot be null.");
		}

		this.appSecretBeanId = appSecretBeanId;
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
	public String getWebHookEventSubmitterBeanId() {
		return this.webHookEventSubmitterBeanId;
	}

	public void setWebHookEventSubmitterBeanId(String webHookEventSubmitterBeanId) {
		if (webHookEventSubmitterBeanId == null) {
			throw new IllegalArgumentException("webHookEventSubmitterBeanId cannot be null.");
		}

		this.webHookEventSubmitterBeanId = webHookEventSubmitterBeanId;
	}

	@Override
	public String getWebHookEventConverterBeanId() {
		return this.webHookEventConverterBeanId;
	}

	public void setWebHookEventConverterBeanId(String webHookEventConverterBeanId) {
		if (webHookEventConverterBeanId == null) {
			throw new IllegalArgumentException("webHookEventConverterBeanId cannot be null.");
		}

		this.webHookEventConverterBeanId = webHookEventConverterBeanId;
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
	public String getSystemLoginTokenServiceBeanId() {
		return this.systemLoginTokenServiceBeanId;
	}

	public void setSystemLoginTokenServiceBeanId(String systemLoginTokenServiceBeanId) {
		if (systemLoginTokenServiceBeanId == null) {
			throw new IllegalArgumentException("systemLoginTokenServiceBeanId cannot be null.");
		}

		this.systemLoginTokenServiceBeanId = systemLoginTokenServiceBeanId;
	}

	@Override
	public String getMailServiceBeanId() {
		return this.mailServiceBeanId;
	}

	public void setMailServiceBeanId(String mailServiceBeanId) {
		if (mailServiceBeanId == null) {
			throw new IllegalArgumentException("mailServiceBeanId cannot be null.");
		}

		this.mailServiceBeanId = mailServiceBeanId;
	}

	@Override
	public String getSystemLoginTokenFactoryBeanId() {
		return this.systemLoginTokenFactoryBeanId;
	}

	public void setSystemLoginTokenFactoryBeanId(String systemLoginTokenFactoryBeanId) {
		if (systemLoginTokenFactoryBeanId == null) {
			throw new IllegalArgumentException("systemLoginTokenFactoryBeanId cannot be null.");
		}

		this.systemLoginTokenFactoryBeanId = systemLoginTokenFactoryBeanId;
	}

	@Override
	public String getAnonymousModelRoleSetContextServiceBeanId() {
		return this.anonymousModelRoleSetContextServiceBeanId;
	}

	public void setAnonymousModelRoleSetContextServiceBeanId(String anonymousModelRoleSetContextServiceBeanId) {
		if (anonymousModelRoleSetContextServiceBeanId == null) {
			throw new IllegalArgumentException("anonymousModelRoleSetContextServiceBeanId cannot be null.");
		}

		this.anonymousModelRoleSetContextServiceBeanId = anonymousModelRoleSetContextServiceBeanId;
	}

	@Override
	public String getAppLoginSecurityServiceBeanId() {
		return this.appLoginSecurityServiceBeanId;
	}

	public void setAppLoginSecurityServiceBeanId(String appLoginSecurityServiceBeanId) {
		if (appLoginSecurityServiceBeanId == null) {
			throw new IllegalArgumentException("appLoginSecurityServiceBeanId cannot be null.");
		}

		this.appLoginSecurityServiceBeanId = appLoginSecurityServiceBeanId;
	}

	@Override
	public String getAppLoginSecurityVerifierServiceBeanId() {
		return this.appLoginSecurityVerifierServiceBeanId;
	}

	public void setAppLoginSecurityVerifierServiceBeanId(String appLoginSecurityVerifierServiceBeanId) {
		if (appLoginSecurityVerifierServiceBeanId == null) {
			throw new IllegalArgumentException("appLoginSecurityVerifierServiceBeanId cannot be null.");
		}

		this.appLoginSecurityVerifierServiceBeanId = appLoginSecurityVerifierServiceBeanId;
	}

	@Override
	public String getAppLoginSecuritySigningServiceBeanId() {
		return this.appLoginSecuritySigningServiceBeanId;
	}

	public void setAppLoginSecuritySigningServiceBeanId(String appLoginSecuritySigningServiceBeanId) {
		if (appLoginSecuritySigningServiceBeanId == null) {
			throw new IllegalArgumentException("appLoginSecuritySigningServiceBeanId cannot be null.");
		}

		this.appLoginSecuritySigningServiceBeanId = appLoginSecuritySigningServiceBeanId;
	}

	@Override
	public String getLoginTokenServiceBeanId() {
		return this.loginTokenServiceBeanId;
	}

	public void setLoginTokenServiceBeanId(String loginTokenServiceBeanId) {
		if (loginTokenServiceBeanId == null) {
			throw new IllegalArgumentException("loginTokenServiceBeanId cannot be null.");
		}

		this.loginTokenServiceBeanId = loginTokenServiceBeanId;
	}

	@Override
	public String getLoginTokenDecoderBeanId() {
		return this.loginTokenDecoderBeanId;
	}

	public void setLoginTokenDecoderBeanId(String loginTokenDecoderBeanId) {
		if (loginTokenDecoderBeanId == null) {
			throw new IllegalArgumentException("loginTokenDecoderBeanId cannot be null.");
		}

		this.loginTokenDecoderBeanId = loginTokenDecoderBeanId;
	}

	@Override
	public AppUtilityBeansConfiguration getUtilityBeans() {
		return this.getUtilityBeansConfiguration();
	}

	public AppUtilityBeansConfiguration getUtilityBeansConfiguration() {
		return this.utilityBeansConfiguration;
	}

	public void setUtilityBeansConfiguration(AppUtilityBeansConfiguration utilityBeansConfiguration) {
		if (utilityBeansConfiguration == null) {
			throw new IllegalArgumentException("utilityBeansConfiguration cannot be null.");
		}

		this.utilityBeansConfiguration = utilityBeansConfiguration;
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

	@Override
	public String getAppDevelopmentProxyUrlBeanId() {
		return this.appDevelopmentProxyUrlBeanId;
	}

	public void setAppDevelopmentProxyUrlBeanId(String appDevelopmentProxyUrlBeanId) {
		if (appDevelopmentProxyUrlBeanId == null) {
			throw new IllegalArgumentException("appDevelopmentProxyUrlBeanId cannot be null.");
		}

		this.appDevelopmentProxyUrlBeanId = appDevelopmentProxyUrlBeanId;
	}

}
