package com.dereekb.gae.server.initialize.impl;

import java.util.List;
import java.util.logging.Level;

import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.AppLoginSecurityLevel;
import com.dereekb.gae.server.app.model.app.info.SystemAppInfo;
import com.dereekb.gae.server.app.model.app.search.query.AppQueryInitializer.ObjectifyAppQuery;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecretGenerator;
import com.dereekb.gae.server.auth.security.app.service.impl.AppLoginSecretGeneratorImpl;
import com.dereekb.gae.server.datastore.ForceGetterSetter;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyQueryService;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;

/**
 * {@link AbstractServerInitializeService} extension for root
 * servers.
 *
 * @author dereekb
 *
 */
public abstract class AbstractRootServerInitializeService extends AbstractServerInitializeService {

	private ObjectifyQueryService<App> appQueryService;
	private AppLoginSecretGenerator appLoginSecretGenerator = AppLoginSecretGeneratorImpl.SINGLETON;

	public AbstractRootServerInitializeService(SystemAppInfo appInfo,
	        ForceGetterSetter<App> appGetterSetter,
	        ObjectifyQueryService<App> appQueryService) {
		super(appInfo, appGetterSetter);
		this.setAppQueryService(appQueryService);
	}

	public ObjectifyQueryService<App> getAppQueryService() {
		return this.appQueryService;
	}

	public void setAppQueryService(ObjectifyQueryService<App> appQueryService) {
		if (appQueryService == null) {
			throw new IllegalArgumentException("appQueryService cannot be null.");
		}

		this.appQueryService = appQueryService;
	}

	public AppLoginSecretGenerator getAppLoginSecretGenerator() {
		return this.appLoginSecretGenerator;
	}

	public void setAppLoginSecretGenerator(AppLoginSecretGenerator appLoginSecretGenerator) {
		if (appLoginSecretGenerator == null) {
			throw new IllegalArgumentException("appLoginSecretGenerator cannot be null.");
		}

		this.appLoginSecretGenerator = appLoginSecretGenerator;
	}

	@Override
	public ForceGetterSetter<App> getAppGetterSetter() {
		return (ForceGetterSetter<App>) super.getAppGetterSetter();
	}

	public void setAppGetterSetter(ForceGetterSetter<App> appGetterSetter) {
		super.setAppGetterSetter(appGetterSetter);
	}

	// MARK: Create
	@Override
	protected final App firstTimeProductionSetup() {
		App app = this.makeRootAppForProduction();

		// Store App
		this.getAppGetterSetter().store(app);

		// Initialize App
		this.initializeServerForFirstSetup(app, true);

		return app;
	}

	@Override
	protected final App firstTimeDevelopmentSetup() {
		App app = this.makeRootAppForDevelopment();

		// Force Store App
		this.getAppGetterSetter().forceStore(app);

		// Initialize App
		this.initializeServerForFirstSetup(app, false);

		return app;
	}

	/**
	 * Called when {@link App} is first created on the root server, allowing any
	 * other one-time initialization to occur.
	 *
	 * @param app
	 * @param isProduction
	 */
	protected abstract void initializeServerForFirstSetup(App app,
	                                                      boolean isProduction);

	protected App makeRootAppForProduction() {
		App app = this.makeRootApp();

		// Clear Model Key in production
		app.setModelKey(null);

		return app;
	}

	protected App makeRootAppForDevelopment() {
		App app = this.makeRootApp();

		// Override and set development secret.
		app.setSecret(App.DEFAULT_DEVELOPMENT_SECRET);

		return app;
	}

	protected App makeRootApp() {

		// Create App
		App app = new App();
		app.copyFromSystemAppInfo(this.getAppInfo());

		// Set Security Level
		app.setLevel(AppLoginSecurityLevel.ROOT);

		// Generate Secret
		app.setSecret(this.appLoginSecretGenerator.generateSecret());

		// Don't initialize yet though.
		app.setInitialized(false);

		return app;
	}

	// MARK: Loading
	@Override
	protected App tryLoadThisApp() {
		App app = super.tryLoadThisApp();

		if (app != null) {
			app = this.tryFindThisApp();
		}

		return app;
	}

	/**
	 * Attempt to find the app using another method.
	 * <p>
	 * Is only used in production for the root app.
	 *
	 * @return {@link App}, or {@code null} if not found.
	 */
	@Override
	protected App tryFindThisApp() {
		ObjectifyQueryRequestBuilder<App> builder = this.appQueryService.makeQuery();

		ObjectifyAppQuery query = new ObjectifyAppQuery();
		query.setLevel(AppLoginSecurityLevel.ROOT);

		query.configure(builder);

		List<App> models = builder.buildExecutableQuery().queryModels();
		App app = null;

		if (models.size() > 1) {
			LOGGER.log(Level.SEVERE, "Returned more than 1 'root' level app when initializing. Resolve immediately.");
			models.clear();
		} else if (models.size() == 1) {
			app = models.get(0);
		}

		return app;
	}

}
