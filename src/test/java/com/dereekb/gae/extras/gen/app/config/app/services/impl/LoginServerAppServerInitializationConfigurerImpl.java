package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServerInitializationConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.app.model.app.info.AppInfo;
import com.dereekb.gae.web.api.server.initialize.impl.RootServerApiInitializeServerControllerDelegateImpl;

/**
 * Basic {@link AppServerInitializationConfigurer} implementation that uses a
 * {@link RootServerApiInitializeServerControllerDelegateImpl}.
 * <p>
 * Should only be used on the login/root server.
 */
public class LoginServerAppServerInitializationConfigurerImpl extends AbstractAppServerInitializationConfigurerImpl {

	/**
	 * Initial administrator of the app who should get the keys to the castle.
	 */
	private String adminEmail = null;

	/**
	 * Initial set of app infos that should be generated.
	 */
	private List<AppInfo> systemAppInfos;

	public String getAdminEmail() {
		return this.adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public List<AppInfo> getSystemAppInfos() {
		return this.systemAppInfos;
	}

	public void setSystemAppInfos(List<AppInfo> systemAppInfos) {
		if (systemAppInfos == null) {
			throw new IllegalArgumentException("systemAppInfos cannot be null.");
		}

		this.systemAppInfos = systemAppInfos;
	}

	// MARK: AbstractAppServerInitializationConfigurerImpl
	@Override
	protected void configureServerDelegateComponent(AppConfiguration appConfig,
	                                                SpringBeansXMLBuilder builder) {

		builder.bean(this.getDelegateBeanId()).beanClass(RootServerApiInitializeServerControllerDelegateImpl.class).c()
		        .ref(appConfig.getAppBeans().getAppInfoBeanId())
		        .ref("appRegistry")
		        .up()
		        .property("adminEmail").value(appConfig.getAppAdminEmail()).up()
		        .property("mailService").ref(appConfig.getAppBeans().getMailServiceBeanId()).up()
		        .property("passwordLoginService").ref(appConfig.getAppBeans().getUtilityBeans().getPasswordLoginServiceBeanId()).up()
		        .property("loginRegisterService").ref(appConfig.getAppBeans().getUtilityBeans().getLoginRegisterServiceBeanId());

		// TODO: Add beans for default app infos, etc.
		/*
		 * builder.bean("productionAppInfo").beanClass(AppInfoImpl.class).c().
		 * ref(appBeans.getAppKeyBeanId())
		 * .ref(appBeans.getAppNameBeanId()).ref(productionAppServiceInfoBeanId)
		 * ;
		 */

	}

}
