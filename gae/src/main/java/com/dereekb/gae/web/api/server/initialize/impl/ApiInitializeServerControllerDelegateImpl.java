package com.dereekb.gae.web.api.server.initialize.impl;

import java.util.logging.Logger;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.info.AppInfo;
import com.dereekb.gae.server.app.model.app.info.AppServiceVersionInfo;
import com.dereekb.gae.server.app.model.app.info.AppServiceVersionInfoUtility;
import com.dereekb.gae.server.app.model.app.info.exception.AppInequalityException;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecretGeneratorImpl;
import com.dereekb.gae.server.datastore.ForceGetterSetter;
import com.dereekb.gae.utilities.gae.GoogleAppEngineUtility;
import com.dereekb.gae.web.api.server.initialize.ApiInitializeServerControllerDelegate;
import com.dereekb.gae.web.api.shared.response.ApiResponse;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseImpl;
import com.dereekb.gae.web.taskqueue.server.task.TaskQueueTaskController;
import com.dereekb.gae.web.taskqueue.server.task.impl.lifecycle.apps.AllocateAppsTask;

/**
 * {@link ApiInitializeServerControllerDelegate} that initializes the server's
 * {@link App}.
 * <p>
 * The App should already exist in order to prevent IDs from being reused in
 * subsequent create requests.
 *
 * @author dereekb
 *
 * @see AllocateAppsTask for allocating apps.
 */
public class ApiInitializeServerControllerDelegateImpl
        implements ApiInitializeServerControllerDelegate {

	private static final Logger LOGGER = Logger.getLogger(TaskQueueTaskController.class.getName());

	private AppInfo appInfo;
	private ForceGetterSetter<App> appGetterSetter;

	public ApiInitializeServerControllerDelegateImpl(AppInfo appInfo, ForceGetterSetter<App> appGetterSetter) {
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

	public ForceGetterSetter<App> getAppGetterSetter() {
		return this.appGetterSetter;
	}

	public void setAppGetterSetter(ForceGetterSetter<App> appGetterSetter) {
		if (appGetterSetter == null) {
			throw new IllegalArgumentException("appGetterSetter cannot be null.");
		}

		this.appGetterSetter = appGetterSetter;
	}

	// MARK: Initialize
	@Override
	public ApiResponse initialize() throws AppInequalityException {
		this.initializeApp();
		return new ApiResponseImpl(true);
	}

	protected App initializeApp() throws AppInequalityException {
		App app = this.loadTargetApp();

		if (app.isInitialized()) {
			// Verify the app is configured properly.
			try {
				AppServiceVersionInfoUtility.assertEquivalentApp(app, this.appInfo);
			} catch (AppInequalityException e) {
				LOGGER.severe("App service is not properly configured for the right app. Check configuration.");
				throw e;
			}
		} else {
			this.initializeApp(app);
		}

		return app;
	}

	// MARK: Loading
	protected App loadTargetApp() {
		if (GoogleAppEngineUtility.isProductionEnvironment()) {
			try {
				return this.loadAppForProduction();
			} catch (UnavailableModelException e) {
				LOGGER.severe("Specified app could not be loaded. Does it exist?");
				throw e;
			}
		} else {
			return this.createAppForDevelopment();
		}
	}

	protected App loadAppForProduction() throws UnavailableModelException {
		App app = this.appGetterSetter.get(this.appInfo.getModelKey());

		if (app == null) {
			throw new UnavailableModelException("The specified app does not exist.");
		}

		return app;
	}

	protected App createAppForDevelopment() {
		App app = null;

		try {
			// Try Load App
			app = this.loadAppForProduction();
		} catch (UnavailableModelException e) {

			// Create App
			app = new App();
			app.setModelKey(this.appInfo.getModelKey());

			// Generate Secret
			app.setSecret(AppLoginSecretGeneratorImpl.SINGLETON.generateSecret());

			// Don't initialize yet though.
			app.setInitialized(false);

			this.appGetterSetter.forceStore(app);
		}

		return app;
	}

	// MARK: Initializing
	protected App initializeApp(App app) {

		AppServiceVersionInfo info = GoogleAppEngineUtility.getApplicationInfo();

		// Set Name / Info
		app.setName(this.appInfo.getAppName());
		app.setAppServiceVersionInfo(info);

		// Set Initialized
		app.setInitialized(true);

		return app;
	}

	@Override
	public String toString() {
		return "ApiInitializeServerControllerDelegateImpl [appInfo=" + this.appInfo + ", appGetterSetter="
		        + this.appGetterSetter + "]";
	}

}
