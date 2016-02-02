package com.dereekb.gae.server.datastore.objectify.components.query.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.components.impl.ObjectifyModelDatastoreComponent;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyQueryService;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequest;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryResponse;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifySimpleQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyKeyInSetFilter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestBuilderImpl;
import com.dereekb.gae.server.datastore.objectify.query.iterator.ObjectifyQueryIterableFactory;
import com.dereekb.gae.server.datastore.objectify.query.iterator.impl.ObjectifyQueryIterableFactoryImpl;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterable;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.googlecode.objectify.cmd.SimpleQuery;

/**
 * {@link ObjectifyQueryService} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ObjectifyQueryServiceImpl<T extends ObjectifyModel<T>> extends ObjectifyModelDatastoreComponent<T>
        implements ObjectifyQueryService<T> {

	public ObjectifyQueryServiceImpl(ObjectifyDatabase database, Class<T> type) {
		super(database, type);
	}

	@Override
	public ObjectifyQueryIterableFactory<T> makeIterableQueryFactory() {
		return new ObjectifyQueryIterableFactoryImpl<T>(this);
	}

	// MARK: ObjectifyExistenceQueryService
	@Override
    public Boolean modelsExist(Collection<Key<T>> keys) {
		Set<Key<T>> existingModels = this.filterExistingModels(keys);
		return keys.containsAll(existingModels);
    }

	@Override
	public Set<Key<T>> filterExistingModels(Collection<Key<T>> keys) {
		ObjectifyQueryRequestBuilder<T> builder = this.makeQuery();
		ObjectifyKeyInSetFilter<T> keysFilter = new ObjectifyKeyInSetFilter<T>(keys);
		builder.addSimpleQueryFilter(keysFilter);
		builder.buildExecutableQuery();

		List<Key<T>> existing = this.query(builder).queryObjectifyKeys();
		return new HashSet<Key<T>>(existing);
    }

	@Override
    public ObjectifyQueryRequestBuilder<T> makeQuery() {
		return new ObjectifyQueryRequestBuilderImpl<T>(this);
    }

	@Override
    public ObjectifyQueryRequestBuilder<T> makeQuery(Map<String, String> parameters) {

		// TODO: Use builder initializer delegate

		return this.makeQuery();
    }

	@Override
	public ObjectifyQueryResponse<T> query(ObjectifyQueryRequest<T> request) {
		ObjectifyQueryRequestExecutor executor = new ObjectifyQueryRequestExecutor(request);
		return executor.query();
	}

	private Query<T> newQuery(boolean cache) {
		return this.database.makeQuery(this.type, cache);
	}

	private class ObjectifyQueryRequestExecutor {

		private final ObjectifyQueryRequest<T> request;

		public ObjectifyQueryRequestExecutor(ObjectifyQueryRequest<T> request) {
			this.request = request;
		}

		public ObjectifyQueryResponse<T> query() {
			boolean cache = this.request.allowCache();
			Query<T> query = ObjectifyQueryServiceImpl.this.newQuery(cache);

			query = this.applyLimit(query);
			query = this.applyCursor(query);

			Query<T> filteredQuery = this.applyFilters(query);
			filteredQuery = this.applyResultsOrdering(filteredQuery);

			SimpleQuery<T> simpleQuery = this.applySimpleQueryFilters(filteredQuery);
			ObjectifyQueryResponse<T> response = new ObjectifyQueryResponseImpl<T>(simpleQuery);
			return response;
		}

		private Query<T> applyFilters(Query<T> query) {
			Query<T> filteredQuery = query;

			for (ObjectifyQueryFilter filter : this.request.getQueryFilters()) {
				filteredQuery = filter.filter(filteredQuery);
			}

			return filteredQuery;
		}

		private SimpleQuery<T> applySimpleQueryFilters(Query<T> query) {
			SimpleQuery<T> filteredQuery = query;

			for (ObjectifySimpleQueryFilter<T> filter : this.request.getSimpleQueryFilters()) {
				filteredQuery = filter.filter(filteredQuery);
			}

			return filteredQuery;
		}

		private Query<T> applyLimit(Query<T> query) {
			Integer limit = this.request.getLimit();

			if (limit != null) {
				query = query.limit(limit);
			}

			return query;
		}

		private Query<T> applyCursor(Query<T> query) {
			Cursor cursor = this.request.getCursor();

			if (cursor != null) {
				query = query.startAt(cursor);
			}

			return query;
		}

		protected Query<T> applyResultsOrdering(Query<T> query) {
			Iterable<ObjectifyQueryOrdering> resultsOrdering = this.request.getResultsOrdering();

			if (resultsOrdering != null) {
				for (ObjectifyQueryOrdering ordering : resultsOrdering) {
					String orderingString = ordering.getOrderingString();
					query = query.order(orderingString);
				}
			}

			return query;
		}

	}

	private static class ObjectifyQueryResponseImpl<T extends ObjectifyModel<T>>
	        implements ObjectifyQueryResponse<T> {

		private final SimpleQuery<T> query;

		public ObjectifyQueryResponseImpl(SimpleQuery<T> query) {
			this.query = query;
		}

		@Override
		public List<T> queryModels() {
			List<T> results = this.query.list();

			if (results == null) {
				results = Collections.emptyList();
			}

			return results;
		}

		@Override
		public List<Key<T>> queryObjectifyKeys() {
			List<Key<T>> results = this.query.keys().list();

			if (results == null) {
				results = Collections.emptyList();
			}

			return results;
		}

		@Override
		public List<ModelKey> queryModelKeys() {
			List<Key<T>> objectifyKeys = this.queryObjectifyKeys();

			// TODO Convert keys.
			return null;
		}

		@Override
		public QueryResultIterable<T> queryModelsIterable() {
			return this.query.iterable();
		}

		@Override
		public QueryResultIterable<Key<T>> queryObjectifyKeyIterable() {
			return this.query.keys().iterable();
		}

	}

	/*
	// MARK: ObjectifyExistenceQueryService
	@Override
	public Boolean modelsExist(Collection<Key<T>> keys) {
		List<Key<T>> existingModels = this.filterExistingModels(keys);
		return keys.containsAll(existingModels);
	}

	@Override
    public List<Key<T>> filterExistingModels(Collection<Key<T>> keys) {
		ConfiguredObjectifyQuery<T> query = this.defaultQuery();
		ObjectifyKeyInSetFilter<T> keysFilter = new ObjectifyKeyInSetFilter<T>(keys);

		List<ObjectifyKeyInSetFilter<T>> filters = query.getSimpleQueryFilters();
		filters.add(keysFilter);

		List<Key<T>> existingModels = this.database.queryForKeys(query);
		return existingModels;
	}

	// MARK: ObjectifyQueryBuilder
	@Override
	public IterableObjectifyQuery<T> makeIterableQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> queryModels(ConfiguredObjectifyQuery<T> query) {
		List<T> results = this.database.queryForEntities(query);

		if (results == null) {
			results = Collections.emptyList();
		}

		return results;
	}

	@Override
    public List<Key<T>> queryKeys(ConfiguredObjectifyQuery<T> query) {
		List<Key<T>> results = this.database.queryForKeys(query);

		if (results == null) {
			results = Collections.emptyList();
		}

		return results;
	}

	@Override
    public QueryResultIterator<T> queryIterator(ConfiguredObjectifyQuery<T> query) {
		QueryResultIterator<T> results = this.database.queryForIterable(query);
		return results;
	}
	*/

}
