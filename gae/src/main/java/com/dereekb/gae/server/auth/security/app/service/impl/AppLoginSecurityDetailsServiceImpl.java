package com.dereekb.gae.server.auth.security.app.service.impl;

import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.auth.security.app.AppLoginSecurityDetails;
import com.dereekb.gae.server.auth.security.app.exception.UnavailableAppLoginSecurityDetails;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityDetailsService;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link AppLoginSecurityDetailsService} implementation that retrieves objects
 * from a {@link Getter}.
 *
 * @author dereekb
 *
 */
public class AppLoginSecurityDetailsServiceImpl
        implements AppLoginSecurityDetailsService {

	private Getter<App> appGetter;

	public AppLoginSecurityDetailsServiceImpl(Getter<App> appGetter) {
		this.setAppGetter(appGetter);
	}

	public Getter<App> getAppGetter() {
		return this.appGetter;
	}

	public void setAppGetter(Getter<App> appGetter) {
		if (appGetter == null) {
			throw new IllegalArgumentException("appGetter cannot be null.");
		}

		this.appGetter = appGetter;
	}

	// MARK: AppLoginSecurityDetailsService
	@Override
	public AppLoginSecurityDetails getAppLoginDetails(ModelKey appId) {
		App app = this.appGetter.get(appId);

		if (app == null) {
			throw new UnavailableAppLoginSecurityDetails("The app '" + appId + "' is unavailable.");
		} else {
			return app;
		}
	}

	@Override
	public String toString() {
		return "AppLoginSecurityDetailsServiceImpl [appGetter=" + this.appGetter + "]";
	}

}
