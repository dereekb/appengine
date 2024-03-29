package com.dereekb.gae.extras.gen.app.config.app.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.services.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServicesConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.local.LoginTokenAppSecurityBeansConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfigurationImpl;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link AppConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class AppConfigurationImpl
        implements AppConfiguration {

	private Long appId = 1L;
	private String appName = "app";
	private String appTaskQueueName = "app";
	private String appSecret = App.DEFAULT_DEVELOPMENT_SECRET;
	private String appSystemKey = null;
	private String appAdminEmail = "dereekb@gmail.com";

	private String appDomain = "dereekb.com";
	private String appDevelopmentProxyUrl = "http://gae-nginx:8080";
	private String appDevelopmentServerHostUrl = "http://localhost:4400";

	private AppServicesConfigurer appServicesConfigurer;
	private AppServiceConfigurationInfo appServiceConfigurationInfo = new AppServiceConfigurationInfoImpl("app", "app", "v1");

	private boolean isRootServer = false;
	private boolean isLoginServer = true;

	private AppBeansConfiguration appBeans = new AppBeansConfigurationImpl();
	private AppSecurityBeansConfigurer appSecurityBeansConfigurer = new LoginTokenAppSecurityBeansConfigurerImpl();

	private List<LocalModelConfigurationGroup> localModelConfigurations;
	private List<RemoteServiceConfiguration> remoteServices = Collections.emptyList();

	public AppConfigurationImpl(AppServiceConfigurationInfo appServiceConfigurationInfo,
	        AppServicesConfigurer appServicesConfigurer) {
		this(appServiceConfigurationInfo, appServicesConfigurer, Collections.emptyList());
	}

	public AppConfigurationImpl(AppServiceConfigurationInfo appServiceConfigurationInfo,
	        AppServicesConfigurer appServicesConfigurer,
	        List<LocalModelConfigurationGroup> localModelConfigurations) {
		this.setAppServiceConfigurationInfo(appServiceConfigurationInfo);
		this.setAppServicesConfigurer(appServicesConfigurer);
		this.setLocalModelConfigurations(localModelConfigurations);
	}

	// MARK: AppConfiguration
	@Override
	public Long getAppId() {
		return this.appId;
	}

	public void setAppId(Long appId) {
		if (appId == null || appId == 0L) {
			throw new IllegalArgumentException("appId cannot be null or 0.");
		}

		this.appId = appId;
	}

	@Override
	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		if (appName == null) {
			throw new IllegalArgumentException("appName cannot be null.");
		}

		this.appName = appName;
	}

	@Override
	public String getAppTaskQueueName() {
		return this.appTaskQueueName;
	}

	public void setAppTaskQueueName(String appTaskQueueName) {
		if (appTaskQueueName == null) {
			throw new IllegalArgumentException("appTaskQueueName cannot be null.");
		}

		this.appTaskQueueName = appTaskQueueName;
	}

	@Override
	public String getAppSecret() {
		return this.appSecret;
	}

	public void setAppSecret(String appSecret) {
		if (appSecret == null) {
			throw new IllegalArgumentException("appSecret cannot be null.");
		}

		this.appSecret = appSecret;
	}

	@Override
	public String getAppSystemKey() {
		return this.appSystemKey;
	}

	public void setAppSystemKey(String appSystemKey) {
		if (appSystemKey == null) {
			throw new IllegalArgumentException("appSystemKey cannot be null.");
		}

		this.appSystemKey = appSystemKey;
	}

	@Override
	public String getAppAdminEmail() {
		return this.appAdminEmail;
	}

	public void setAppAdminEmail(String appAdminEmail) {
		if (appAdminEmail == null) {
			throw new IllegalArgumentException("appAdminEmail cannot be null.");
		}

		this.appAdminEmail = appAdminEmail;
	}

	@Override
	public String getAppDomain() {
		return this.appDomain;
	}

	public void setAppDomain(String appDomain) {
		if (appDomain == null) {
			throw new IllegalArgumentException("appDomain cannot be null.");
		}

		this.appDomain = appDomain;
	}

	@Override
	public String getAppDevelopmentProxyUrl() {
		return this.appDevelopmentProxyUrl;
	}

	public void setAppDevelopmentProxyUrl(String appDevelopmentProxyUrl) {
		if (appDevelopmentProxyUrl == null) {
			throw new IllegalArgumentException("appDevelopmentProxyUrl cannot be null.");
		}

		this.appDevelopmentProxyUrl = appDevelopmentProxyUrl;
	}

	@Override
	public String getAppDevelopmentServerHostUrl() {
		return this.appDevelopmentServerHostUrl;
	}

	public void setAppDevelopmentServerHostUrl(String appDevelopmentServerHostUrl) {
		if (appDevelopmentServerHostUrl == null) {
			throw new IllegalArgumentException("appDevelopmentServerHostUrl cannot be null.");
		}

		this.appDevelopmentServerHostUrl = appDevelopmentServerHostUrl;
	}

	@Override
	public AppServicesConfigurer getAppServicesConfigurer() {
		return this.appServicesConfigurer;
	}

	public void setAppServicesConfigurer(AppServicesConfigurer appServicesConfigurer) {
		if (appServicesConfigurer == null) {
			throw new IllegalArgumentException("appServicesConfigurer cannot be null.");
		}

		this.appServicesConfigurer = appServicesConfigurer;
	}

	@Override
	public AppServiceConfigurationInfo getAppServiceConfigurationInfo() {
		return this.appServiceConfigurationInfo;
	}

	public void setAppServiceConfigurationInfo(AppServiceConfigurationInfo appServiceConfigurationInfo) {
		if (appServiceConfigurationInfo == null) {
			throw new IllegalArgumentException("appServiceConfigurationInfo cannot be null.");
		}

		this.appServiceConfigurationInfo = appServiceConfigurationInfo;
	}

	@Override
	public boolean isRootServer() {
		return this.isRootServer;
	}

	public void setIsRootServer(boolean isRootServer) {
		this.isRootServer = isRootServer;
	}

	@Override
	public boolean isLoginServer() {
		return this.isLoginServer;
	}

	public void setIsLoginServer(boolean isLoginServer) {
		this.isLoginServer = isLoginServer;
	}

	@Override
	public AppBeansConfiguration getAppBeans() {
		return this.appBeans;
	}

	public void setAppBeans(AppBeansConfiguration appBeans) {
		if (appBeans == null) {
			throw new IllegalArgumentException("appBeans cannot be null.");
		}

		this.appBeans = appBeans;
	}

	@Override
	public AppSecurityBeansConfigurer getAppSecurityBeansConfigurer() {
		return this.appSecurityBeansConfigurer;
	}

	public void setAppSecurityBeansConfigurer(AppSecurityBeansConfigurer appSecurityBeansConfigurer) {
		if (appSecurityBeansConfigurer == null) {
			throw new IllegalArgumentException("appSecurityBeansConfigurer cannot be null.");
		}

		this.appSecurityBeansConfigurer = appSecurityBeansConfigurer;
	}

	@Override
	public boolean hasRemoteServices() {
		return !this.remoteServices.isEmpty();
	}

	@Override
	public List<RemoteServiceConfiguration> getRemoteServices() {
		return this.remoteServices;
	}

	public void setRemoteServices(RemoteServiceConfiguration... remoteServices) {
		this.setRemoteServices(ListUtility.toList(remoteServices));
	}

	public void setRemoteServices(List<RemoteServiceConfiguration> remoteServices) {
		if (remoteServices == null) {
			throw new IllegalArgumentException("remoteServices cannot be null.");
		}

		this.remoteServices = remoteServices;
	}

	@Override
	public List<? extends AppModelConfigurationGroup> getModelConfigurations() {
		List<AppModelConfigurationGroup> models = ListUtility.copy(this.getLocalModelConfigurations());

		models.addAll(this.getRemoteModelConfigurations());

		return models;
	}

	@Override
	public List<RemoteModelConfigurationGroup> getRemoteModelConfigurations() {
		List<RemoteModelConfigurationGroup> models = new ArrayList<RemoteModelConfigurationGroup>();

		for (RemoteServiceConfiguration service : this.remoteServices) {
			models.addAll(service.getServiceModelConfigurations());
		}

		return models;
	}

	@Override
	public List<LocalModelConfigurationGroup> getLocalModelConfigurations() {
		return this.localModelConfigurations;
	}

	public void setLocalModelConfigurations(List<LocalModelConfigurationGroup> localModelConfigurations) {
		if (localModelConfigurations == null) {
			throw new IllegalArgumentException("localModelConfigurations cannot be null.");
		}

		this.localModelConfigurations = localModelConfigurations;
	}

	@Override
	public boolean hasNotificationServices() {
		return this.getAppServicesConfigurer().getAppUserNotificationServiceConfigurer() != null;
	}

}
