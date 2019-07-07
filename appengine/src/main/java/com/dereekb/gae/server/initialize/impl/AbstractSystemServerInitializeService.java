package com.dereekb.gae.server.initialize.impl;

import java.util.logging.Level;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.extension.search.query.response.ClientModelQueryResponse;
import com.dereekb.gae.client.api.model.extension.search.query.services.ClientQueryService;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.app.info.SystemAppInfo;
import com.dereekb.gae.server.app.model.app.search.query.AppQuery;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.model.search.request.impl.SearchRequestImpl;

/**
 * {@link AbstractServerInitializeService} implementation.
 *
 * @author dereekb
 * @deprecated design problem where if we don't know what App this is, and this
 *             isn't the root login server, then client authentication is
 *             impossible.
 */
@Deprecated
public abstract class AbstractSystemServerInitializeService extends AbstractServerInitializeService {

	private ClientQueryService<App> appQueryService;

	public AbstractSystemServerInitializeService(SystemAppInfo appInfo,
	        GetterSetter<App> appGetterSetter,
	        ClientQueryService<App> appQueryService) {
		super(appInfo, appGetterSetter);
		this.setAppQueryService(appQueryService);
	}

	public ClientQueryService<App> getAppQueryService() {
		return this.appQueryService;
	}

	public void setAppQueryService(ClientQueryService<App> appQueryService) {
		if (appQueryService == null) {
			throw new IllegalArgumentException("appQueryService cannot be null.");
		}

		this.appQueryService = appQueryService;
	}

	// MARK: Internal
	@Override
	protected App tryLoadThisApp() {
		if (this.getAppInfo().getModelKey() != null) {
			return super.tryLoadThisApp();
		} else {
			return null;
		}
	}

	@Override
	protected App tryFindThisApp() {

		String systemKey = this.getAppInfo().getSystemKey();

		if (systemKey != null) {

			SearchRequestImpl request = new SearchRequestImpl();
			request.setKeysOnly(true);

			AppQuery query = new AppQuery();
			query.setSystemKey(systemKey);

			request.setSearchParameters(query);

			try {
				ClientModelQueryResponse<App> response = this.appQueryService.query(request);

				if (response.hasResults()) {
					ModelKey appKey = response.getKeyResults().iterator().next();
					return this.getAppGetterSetter().get(appKey);
				} else {
					LOGGER.log(Level.SEVERE, "System failed querying App.");
				}
			} catch (ClientRequestFailureException e) {
				LOGGER.log(Level.SEVERE, "System encountered an error while querying App.", e);
			}
		}

		return null;
	}

}
