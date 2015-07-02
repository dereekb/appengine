package com.dereekb.gae.server.datastore.objectify.components;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifySimpleQueryFilter;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;

public class ObjectifyModelQuery<T extends ObjectifyModel<T>> extends ObjectifyModelDatastoreComponent<T>
        implements ObjectifyKeyedQuery<T> {

	public ObjectifyModelQuery(ObjectifyDatabase database, Class<T> type) {
		super(database, type);
	}

	@Override
	public Boolean modelsExist(Collection<Key<T>> keys) {
		List<Key<T>> existingModels = this.filterExistingModels(keys);
		return keys.containsAll(existingModels);
	}

	@Override
    public List<Key<T>> filterExistingModels(Collection<Key<T>> keys) {
		ObjectifyQuery<T> query = this.defaultQuery();
		ObjectifySimpleQueryFilter<T> keysFilter = new ObjectifySimpleQueryFilter<T>(keys);

		List<ObjectifySimpleQueryFilter<T>> filters = query.getSimpleQueryFilters();
		filters.add(keysFilter);

		List<Key<T>> existingModels = this.database.queryForKeys(query);
		return existingModels;
	}

	@Override
    public ObjectifyQuery<T> defaultQuery() {
		ObjectifyQuery<T> objectifyQuery = new ObjectifyQuery<T>(this.type);
		return objectifyQuery;
	}

	@Override
    public List<T> queryEntities(ObjectifyQuery<T> query) {
		List<T> results = this.database.queryForEntities(query);

		if (results == null) {
			results = Collections.emptyList();
		}

		return results;
	}

	@Override
    public List<Key<T>> queryKeys(ObjectifyQuery<T> query) {
		List<Key<T>> results = this.database.queryForKeys(query);

		if (results == null) {
			results = Collections.emptyList();
		}

		return results;
	}

	@Override
    public QueryResultIterator<T> queryIterator(ObjectifyQuery<T> query) {
		QueryResultIterator<T> results = this.database.queryForIterable(query);
		return results;
	}

}
