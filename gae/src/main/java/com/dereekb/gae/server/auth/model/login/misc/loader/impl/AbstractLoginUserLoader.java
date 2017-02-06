package com.dereekb.gae.server.auth.model.login.misc.loader.impl;

import java.util.List;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.misc.loader.LoginUserLoader;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyQueryService;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestOptionsImpl;

/**
 * Abstract {@link LoginUserLoader} implementation that uses an
 * {@link ObjectifyQueryService} to find the required model.
 * 
 * @author dereekb
 *
 * @param <T>
 */
public abstract class AbstractLoginUserLoader<T extends ObjectifyModel<T>>
        implements LoginUserLoader<T> {

	private ObjectifyQueryService<T> queryService;

	public AbstractLoginUserLoader(ObjectifyQueryService<T> queryService) {
		this.setQueryService(queryService);
	}

	public ObjectifyQueryService<T> getQueryService() {
		return this.queryService;
	}

	public void setQueryService(ObjectifyQueryService<T> queryService) throws IllegalArgumentException {
		if (queryService == null) {
			throw new IllegalArgumentException("QueryService cannot be null.");
		}

		this.queryService = queryService;
	}

	// MARK: LoginUserLoader
	@Override
	public T loadUserForLogin(Login login) {

		if (login == null) {
			throw new IllegalArgumentException("Login cannot be null.");
		}

		return this.loadUserForLogin(login.getModelKey());
	}

	@Override
	public T loadUserForLogin(ModelKey loginKey) {
		if (loginKey == null) {
			throw new IllegalArgumentException("Login Key cannot be null.");
		}

		ConfigurableObjectifyQueryRequestConfigurer configurer = this.buildQueryConfigForLoginKey(loginKey);
		ObjectifyQueryRequestBuilder<T> builder = this.queryService.makeQuery();
		ObjectifyQueryRequestOptions options = new ObjectifyQueryRequestOptionsImpl(1);

		builder.setOptions(options);

		configurer.configure(builder);

		ExecutableObjectifyQuery<T> query = builder.buildExecutableQuery();
		List<T> models = query.queryModels();

		if (models.isEmpty()) {
			throw new UnavailableModelException("User for login was unavailable.");
		}

		return models.get(0);
	}

	protected abstract ConfigurableObjectifyQueryRequestConfigurer buildQueryConfigForLoginKey(ModelKey loginKey);

}
