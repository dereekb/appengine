package com.dereekb.gae.server.auth.security.app.service.impl;

import com.dereekb.gae.server.app.model.login.AppLogin;
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

	private Getter<AppLogin> appLoginGetter;

	public AppLoginSecurityDetailsServiceImpl(Getter<AppLogin> appLoginGetter) {
		this.setAppLoginGetter(appLoginGetter);
	}

	public Getter<AppLogin> getAppLoginGetter() {
		return this.appLoginGetter;
	}

	public void setAppLoginGetter(Getter<AppLogin> appLoginGetter) {
		if (appLoginGetter == null) {
			throw new IllegalArgumentException("appLoginGetter cannot be null.");
		}

		this.appLoginGetter = appLoginGetter;
	}

	// MARK: AppLoginSecurityDetailsService
	@Override
	public AppLoginSecurityDetails getAppLoginDetails(ModelKey appId) {
		AppLogin login = this.appLoginGetter.get(appId);

		if (login == null) {
			throw new UnavailableAppLoginSecurityDetails();
		} else {
			return login;
		}
	}

	@Override
	public String toString() {
		return "AppLoginSecurityDetailsServiceImpl [appLoginGetter=" + this.appLoginGetter + "]";
	}

}
