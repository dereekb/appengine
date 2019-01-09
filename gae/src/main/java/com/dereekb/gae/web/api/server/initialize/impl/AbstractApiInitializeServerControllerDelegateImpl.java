package com.dereekb.gae.web.api.server.initialize.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.info.AppInfo;
import com.dereekb.gae.server.app.model.app.info.AppServiceVersionInfoUtility;
import com.dereekb.gae.server.app.model.app.info.exception.AppInequalityException;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.utilities.gae.GoogleAppEngineUtility;
import com.dereekb.gae.web.api.server.initialize.ApiInitializeServerControllerDelegate;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;

/**
 * Abstract {@link ApiInitializeServerControllerDelegate} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractApiInitializeServerControllerDelegateImpl
        implements ApiInitializeServerControllerDelegate {

	protected static final Logger LOGGER = Logger
	        .getLogger(AbstractApiInitializeServerControllerDelegateImpl.class.getName());

	/**
	 * App Info for the current app.
	 */
	private AppInfo appInfo;
	private GetterSetter<App> appGetterSetter;

	public AbstractApiInitializeServerControllerDelegateImpl(AppInfo appInfo, GetterSetter<App> appGetterSetter) {
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

	// MARK: ApiInitializeServerControllerDelegate
	@Override
	public ApiResponse initialize() throws AppInequalityException {
		this.tryInitializeApp();
		return new ApiResponseImpl(true);
	}

	protected final App tryInitializeApp() throws AppInequalityException {
		boolean created = false;
		App app = null;

		// Load/Create App
		try {
			app = this.loadThisApp();
		} catch (UnavailableModelException e) {
			if (GoogleAppEngineUtility.isProductionEnvironment()) {
				app = this.firstTimeProductionSetup();
			} else {
				app = this.firstTimeDevelopmentSetup();
			}

			created = true;
		}

		// Update If Not Created

		// TODO: Update this, since this assertion and update don't
		// particularly make sense if we're updating on version changes.


		if (!created) {
			/*
			try {
				this.assertIsExpectedApp(app);
			} catch (AppInequalityException e) {
				LOGGER.severe("App service is not properly configured for the right app. Check configuration.");
				throw e;
			}

			app = this.tryUpdateApp(app);
			*/
		}

		return app;
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
