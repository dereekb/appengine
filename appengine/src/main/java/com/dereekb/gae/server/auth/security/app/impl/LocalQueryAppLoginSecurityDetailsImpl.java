package com.dereekb.gae.server.auth.security.app.impl;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.AppLoginSecurityLevel;
import com.dereekb.gae.server.app.model.app.info.SystemAppInfo;
import com.dereekb.gae.server.app.model.app.search.query.AppQueryInitializer.ObjectifyAppQuery;
import com.dereekb.gae.server.auth.security.app.AppLoginSecurityDetails;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyQueryService;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;

/**
 * {@link AppLoginSecurityDetails} implementation that reads the secret and
 * related info using a {@link ObjectifyQueryService} for {@link App}.
 * <p>
 * This implementation has some benefits over the
 * {@link AppLoginSecurityDetailsImpl} that allows nodes to load directly from
 * the datastore the app's info.
 * <p>
 * The key caveat to this approach is the requirement of an
 * {@link ObjectifyQueryService}.
 *
 * @author dereekb
 *
 */
public class LocalQueryAppLoginSecurityDetailsImpl
        implements AppLoginSecurityDetails {

	private SystemAppInfo systemAppInfo;
	private ObjectifyQueryService<App> appQueryService;

	private transient App app;

	public LocalQueryAppLoginSecurityDetailsImpl(SystemAppInfo systemAppInfo,
	        ObjectifyQueryService<App> appQueryService) {
		super();
		this.setSystemAppInfo(systemAppInfo);
		this.setAppQueryService(appQueryService);
	}

	public SystemAppInfo getSystemAppInfo() {
		return this.systemAppInfo;
	}

	public void setSystemAppInfo(SystemAppInfo systemAppInfo) {
		if (systemAppInfo == null) {
			throw new IllegalArgumentException("systemAppInfo cannot be null.");
		}

		this.systemAppInfo = systemAppInfo;
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

	// MARK: AppLoginSecurityDetails
	@Override
	public String getAppName() {
		return this.getApp().getAppName();
	}

	@Override
	public ModelKey getModelKey() {
		return this.getApp().getModelKey();
	}

	@Override
	public ModelKey keyValue() {
		return this.getApp().getModelKey();
	}

	@Override
	public String getAppSecret() {
		return this.getApp().getAppSecret();
	}

	@Override
	public AppLoginSecurityLevel getAppLoginSecurityLevel() {
		return this.getApp().getAppLoginSecurityLevel();
	}

	// MARK: Internal
	private App getApp() throws UnavailableModelException {
		if (this.app == null) {
			this.app = this.loadApp();
		}

		return this.app;
	}

	private App loadApp() {
		ObjectifyQueryRequestBuilder<App> builder = this.appQueryService.makeQuery();

		ObjectifyAppQuery queryConfig = new ObjectifyAppQuery();
		queryConfig.setSystemKey(this.systemAppInfo.getSystemKey());
		queryConfig.configure(builder);

		App app;

		try {
			app = builder.buildExecutableQuery().queryModels().get(0);
		} catch (Exception e) {
			throw new UnavailableModelException(e);
		}

		return app;
	}

	@Override
	public String toString() {
		return "LocalQueryAppLoginSecurityDetailsImpl [systemAppInfo=" + this.systemAppInfo + ", appQueryService="
		        + this.appQueryService + "]";
	}

}
