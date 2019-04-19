package com.dereekb.gae.server.initialize.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.info.AppInfo;
import com.dereekb.gae.server.app.model.app.info.AppServiceVersionInfoUtility;
import com.dereekb.gae.server.app.model.app.info.exception.AppInequalityException;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.initialize.ServerInitializeService;
import com.dereekb.gae.utilities.gae.GoogleAppEngineUtility;
import com.dereekb.gae.utilities.spring.initializer.SpringInitializerFunction;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;

/**
 * Abstract {@link ApiInitializeServerControllerDelegate} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractServerInitializeService
        implements ServerInitializeService, SpringInitializerFunction {

	protected static final Logger LOGGER = Logger.getLogger(AbstractServerInitializeService.class.getName());

	/**
	 * App Info for the current app.
	 */
	private AppInfo appInfo;
	private GetterSetter<App> appGetterSetter;

	public AbstractServerInitializeService(AppInfo appInfo, GetterSetter<App> appGetterSetter) {
		this.setAppInfo(appInfo);
		this.setAppGetterSetter(appGetterSetter);
	}

	public AppInfo getAppInfo() {
		return this.appInfo;
	}

	public void setAppInfo(AppInfo appInfo) {
		if (appInfo == null) {
			throw new IllegalArgumentException("appInfo cannot be null.");
		}

		this.appInfo = appInfo;
	}

	public GetterSetter<App> getAppGetterSetter() {
		return this.appGetterSetter;
	}

	public void setAppGetterSetter(GetterSetter<App> appGetterSetter) {
		if (appGetterSetter == null) {
			throw new IllegalArgumentException("appGetterSetter cannot be null.");
		}

		this.appGetterSetter = appGetterSetter;
	}

	// MARK: Initialize
	@Override
	public void initializeServer() throws Exception {
		this.tryInitializeApp();
	}

	// MARK: SpringInitializerFunction
	@Override
	public void initializeApplication() throws Exception {
		this.tryInitializeApp();
	}

	protected final App tryInitializeApp() throws AppInequalityException {
		// Run within the ObjectifyService directly to ensure it takes place within a context.
		App app = ObjectifyService.run(new InitWork());
		return app;
	}

	protected class InitWork implements Work<App> {

		@Override
		public App run() {
			boolean created = false;
			App app = null;

			// Load/Create App
			try {
				app = loadThisApp();
			} catch (UnavailableModelException e) {
				if (GoogleAppEngineUtility.isProductionEnvironment()) {
					app = firstTimeProductionSetup();
				} else {
					app = firstTimeDevelopmentSetup();
				}

				created = true;
			}

			// Update If Not Created

			// TODO: Update this, since this assertion and update don't
			// particularly make sense if we're updating on version changes.

			if (!created) {
				/*
				 * try {
				 * this.assertIsExpectedApp(app);
				 * } catch (AppInequalityException e) {
				 * LOGGER.
				 * severe("App service is not properly configured for the right app. Check configuration."
				 * );
				 * throw e;
				 * }
				 *
				 * app = this.tryUpdateApp(app);
				 */
			}

			return app;
		}

	}

	protected void assertIsExpectedApp(App app) throws AppInequalityException {
		AppServiceVersionInfoUtility.assertEquivalentApp(app, this.appInfo);
	}

	// MARK: Production
	protected abstract App firstTimeProductionSetup();

	protected abstract App firstTimeDevelopmentSetup();

	/**
	 * Function that allows any updates to occur to the current app.
	 *
	 * @param app
	 * @return
	 */
	protected abstract App tryUpdateApp(App app);

	// MARK: Internal
	protected final App loadThisApp() throws UnavailableModelException {
		App app = this.tryLoadThisApp();

		if (GoogleAppEngineUtility.isProductionEnvironment()) {
			app = this.tryFindThisApp();

			if (app != null) {
				LOGGER.log(Level.WARNING, "Found app using find. Be sure to update the app to use the app ID soon.");
			}
		}

		if (app == null) {
			throw new UnavailableModelException("The specified app does not exist.");
		}

		return app;
	}

	protected App tryLoadThisApp() {
		return this.appGetterSetter.get(this.appInfo.getModelKey());
	}

	/**
	 * Attempt to find the app using another method.
	 * <p>
	 * Is only used in production for the root app.
	 *
	 * @return {@link App}, or {@code null} if not found.
	 */
	protected App tryFindThisApp() {
		return null;
	}

}
