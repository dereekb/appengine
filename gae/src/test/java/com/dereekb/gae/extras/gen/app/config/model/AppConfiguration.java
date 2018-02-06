package com.dereekb.gae.extras.gen.app.config.model;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.project.app.AppBeansConfiguration;

public interface AppConfiguration {

	public Long getAppId();

	public String getAppName();

	public String getAppTaskQueueName();

	public AppBeansConfiguration getAppBeans();

	public List<AppModelConfigurationGroup> getModelConfigurations();

}
