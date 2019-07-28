package com.dereekb.gae.server.initialize.impl;

import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.AppLoginSecurityLevel;
import com.dereekb.gae.server.app.model.app.info.SystemAppInfo;
import com.dereekb.gae.server.datastore.ForceGetterSetter;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyQueryService;

/**
 * {@link AbstractRootServerInitializeService} implemention used in test cases
 * to initialize the server's {@link App} itself so App exists in the database
 * for tests.
 *
 * @author dereekb
 *
 */
public class TestRemoteServerInitializeService extends AbstractRootServerInitializeService {

	public TestRemoteServerInitializeService(SystemAppInfo appInfo,
	        ForceGetterSetter<App> appGetterSetter,
	        ObjectifyQueryService<App> appQueryService) {
		super(appInfo, appGetterSetter, appQueryService);
	}

	// MARK: AbstractRootServerInitializeService
	@Override
	protected App makeRootApp() {
		App app = super.makeRootApp();

		// Override the security level.
		app.setLevel(AppLoginSecurityLevel.SYSTEM);

		return app;
	}

	@Override
	protected void initializeServerForFirstSetup(App app,
	                                             boolean isProduction) {
		// Do nothing

		if (isProduction) {
			throw new RuntimeException("TestRemoteServerInitializeService should NOT be used for production.");
		}
	}

	@Override
	protected App tryUpdateApp(App app) {

		// Do nothing

		return app;
	}

}
