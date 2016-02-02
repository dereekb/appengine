package com.dereekb.gae.server.datastore.objectify.components.query.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.components.impl.ObjectifyModelDatastoreComponent;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyQueryService;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
import com.dereekb.gae.server.datastore.objectify.query.ConfiguredObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryBuilder;
import com.dereekb.gae.server.datastore.objectify.query.iterator.IterableObjectifyQuery;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;

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
	public IterableObjectifyQuery<T> makeIterableQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryResultIterator<T> queryIterator(ConfiguredObjectifyQuery<T> query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> queryModels(ConfiguredObjectifyQuery<T> query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Key<T>> queryKeys(ConfiguredObjectifyQuery<T> query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean modelsExist(Collection<Key<T>> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Key<T>> filterExistingModels(Collection<Key<T>> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectifyQueryBuilder<T> makeQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectifyQueryBuilder<T> makeQuery(Map<String, String> parameters) {
		// TODO Auto-generated method stub
		return null;
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
