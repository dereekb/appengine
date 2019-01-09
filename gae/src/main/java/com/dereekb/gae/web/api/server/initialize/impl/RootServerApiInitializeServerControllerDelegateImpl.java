package com.dereekb.gae.web.api.server.initialize.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.AppLoginSecurityLevel;
import com.dereekb.gae.server.app.model.app.info.AppInfo;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecretGeneratorImpl;
import com.dereekb.gae.server.datastore.ForceGetterSetter;

/**
 * {@link AbstractRootServerApiInitializeServerControllerDelegateImpl}
 * implementation that generates an admin login, and mails user accounts.
 *
 * @author dereekb
 *
 */
public class RootServerApiInitializeServerControllerDelegateImpl extends AbstractRootServerApiInitializeServerControllerDelegateImpl {

	private String adminEmail = null;
	private List<AppInfo> defaultSystemApps = Collections.emptyList();

	public RootServerApiInitializeServerControllerDelegateImpl(AppInfo appInfo,
	        ForceGetterSetter<App> appGetterSetter) {
		super(appInfo, appGetterSetter);
	}

	// MARK: AbstractRootServerApiInitializeServerControllerDelegateImpl
	@Override
	protected void initializeServerForFirstSetup(App app,
	                                             boolean isProduction) {
		this.initializeDefaultApps(isProduction);
	}

	@Override
	protected App tryUpdateApp(App app) {
		return app;
	}

	// MARK: Admin
	protected Login makeAdminLogin() {

		// TODO!

		return null;
	}

	// MARK: Apps
	protected void initializeDefaultApps(boolean isProduction) {
		List<App> defaultApps = this.makeDefaultApps(isProduction);

		this.getAppGetterSetter().store(defaultApps);

		// TODO: Send email to admin email address if specified, otherwise use system address?

	}

	protected List<App> makeDefaultApps(boolean isProduction) {
		List<App> apps = new ArrayList<App>();

		for (AppInfo appInfo : this.defaultSystemApps) {
			App app = this.makeDefaultAppFromAppInfo(appInfo, isProduction);
			apps.add(app);
		}

		return apps;
	}

	protected App makeDefaultAppFromAppInfo(AppInfo appInfo,
	                                        boolean isProduction) {
		App app = new App();

		app.setName(appInfo.getAppName());
		app.setAppServiceVersionInfo(appInfo.getAppServiceVersionInfo());
		app.setLevel(AppLoginSecurityLevel.SYSTEM);

		if (isProduction) {
			app.setSecret(AppLoginSecretGeneratorImpl.SINGLETON.generateSecret());
		} else {
			app.setSecret(App.DEFAULT_DEVELOPMENT_SECRET);
		}

		return app;
	}

}
