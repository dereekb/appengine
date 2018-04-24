package com.dereekb.gae.extras.gen.app.config.model;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfiguration;

public interface AppConfiguration {

	public Long getAppId();

	public String getAppName();

	public String getAppServiceName();

	public String getAppTaskQueueName();

	public String getAppVersion();

	public AppBeansConfiguration getAppBeans();

	public AppSecurityBeansConfigurer getAppSecurityBeansConfigurer();

	public List<AppModelConfigurationGroup> getModelConfigurations();

	public boolean isLoginServer();

}
