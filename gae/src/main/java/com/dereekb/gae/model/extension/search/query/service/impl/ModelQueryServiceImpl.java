package com.dereekb.gae.model.extension.search.query.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.extension.search.query.service.ModelQueryRequest;
import com.dereekb.gae.model.extension.search.query.service.ModelQueryResponse;
import com.dereekb.gae.model.extension.search.query.service.ModelQueryService;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyUtility;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.utilities.model.search.exception.NoSearchCursorException;
import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;
import com.googlecode.objectify.Key;

/**
 * {@link ModelQueryService} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelQueryServiceImpl<T extends ObjectifyModel<T>>
        implements ModelQueryService<T> {

	private ObjectifyRegistry<T> registry;
	private ObjectifyQueryRequestLimitedBuilderInitializer initializer;

	public ModelQueryServiceImpl(ObjectifyRegistry<T> registry) {
		this(registry, null);
	}

	public ModelQueryServiceImpl(ObjectifyRegistry<T> registry,
	        ObjectifyQueryRequestLimitedBuilderInitializer initializer) {
		this.setRegistry(registry);
		this.setInitializer(initializer);
	}

	public ObjectifyRegistry<T> getRegistry() {
		return this.registry;
	}

	public void setRegistry(ObjectifyRegistry<T> registry) {
		this.registry = registry;
	}

	public ObjectifyQueryRequestLimitedBuilderInitializer getInitializer() {
		return this.initializer;
	}

	public void setInitializer(ObjectifyQueryRequestLimitedBuilderInitializer initializer) {
		this.initializer = initializer;
	}

	// MARK: ModelQueryService
	@Override
	public ModelQueryResponse<T> queryModels(ModelQueryRequest request) throws IllegalQueryArgumentException {
		Map<String, String> parameters = request.getParameters();
		ObjectifyQueryRequestBuilder<T> builder;

		// Used the custom initializer if available.
		if (this.initializer != null) {
			builder = this.registry.makeQuery();
			this.initializer.initalizeBuilder(builder, parameters);
			this.initializer.overrideOptions(builder, request);
		} else {
			builder = this.registry.makeQuery(parameters);
			builder.setOptions(request);
		}

		ExecutableObjectifyQuery<T> query = builder.buildExecutableQuery();
		return new ModelQueryResponseImpl(request.isKeysOnly(), query);
	}

	/**
	 * {@link ModelQueryResponse} implementation that lazy-loads the response
	 * components.
	 *
	 * @author dereekb
	 *
	 */
	private class ModelQueryResponseImpl
	        implements ModelQueryResponse<T> {

		private final boolean keysQuery;
		private final ExecutableObjectifyQuery<T> query;

		// Lazy
		private List<T> models;
		private List<ModelKey> keys;
		private List<Key<T>> objectifyKeys;

		public ModelQueryResponseImpl(boolean keysQuery, ExecutableObjectifyQuery<T> query) {
			this.keysQuery = keysQuery;
			this.query = query;
		}

		// MARK: ModelQueryResponse
		@Override
		public boolean isKeysOnlyResponse() {
			return this.keysQuery;
		}

		@Override
		public Collection<T> getModelResults() {
			if (this.models == null) {
				if (this.keysQuery == false) {
					this.models = this.query.queryModels();
				} else {
					this.models = ModelQueryServiceImpl.this.registry
					        .getWithObjectifyKeys(this.getObjectifyKeyResults());
				}
			}

			return this.models;
		}

		@Override
		public List<ModelKey> getKeyResults() {
			if (this.keys == null) {
				List<Key<T>> keys = this.getObjectifyKeyResults();
				this.keys = ModelQueryServiceImpl.this.registry.getObjectifyKeyConverter().convertTo(keys);
			}

			return this.keys;
		}

		@Override
		public List<Key<T>> getObjectifyKeyResults() {
			if (this.objectifyKeys == null) {
				if (this.keysQuery) {
					this.objectifyKeys = this.query.queryObjectifyKeys();
				} else {
					this.objectifyKeys = ObjectifyUtility.readKeys(this.getModelResults());
				}
			}

			return this.objectifyKeys;
		}

		@Override
		public String getSearchCursor() {
			try {
				return this.query.getCursor().toWebSafeString();
			} catch (NoSearchCursorException e) {
				return null;
			}
		}

	}

	@Override
	public String toString() {
		return "ModelQueryServiceImpl [registry=" + this.registry + "]";
	}

}
