package com.dereekb.gae.model.extension.search.query.search;

import java.util.List;

import com.dereekb.gae.model.extension.search.query.search.components.ModelQueryConverter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedQuery;
import com.dereekb.gae.server.datastore.objectify.keys.ObjectifyKeyReader;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQuery;
import com.dereekb.gae.utilities.function.staged.StagedFunction;
import com.dereekb.gae.utilities.function.staged.filter.FilteredStagedFunction;
import com.googlecode.objectify.Key;

/**
 * {@link StagedFunction} used for retrieving {@link ModelKey} instances from
 * Objectify, using input {@link QueryPair} instances.
 *
 * @author dereekb
 *
 * @param <Q>
 *            Query type.
 */
public final class ObjectifyQueryFunction<T, Q> extends FilteredStagedFunction<Q, QueryPair<Q>> {

	private final ModelQueryConverter<T, Q> converter;
	private final ObjectifyKeyedQuery<T> objectifyQuery;
	private final ObjectifyKeyReader<T, ModelKey> keyReader;

	public ObjectifyQueryFunction(ModelQueryConverter<T, Q> converter,
	        ObjectifyKeyedQuery<T> objectifyQuery,
	        ObjectifyKeyReader<T, ModelKey> keyReader) {
		this.converter = converter;
		this.objectifyQuery = objectifyQuery;
		this.keyReader = keyReader;
	}

	@Override
	protected void doFunction() {
		Iterable<QueryPair<Q>> pairs = this.getWorkingObjects();

		for (QueryPair<Q> pair : pairs) {
			Q query = pair.getQuery();
			ObjectifyQuery<T> objectifyQuery = this.converter.convertQuery(query);
			List<Key<T>> keys = this.objectifyQuery.queryKeys(objectifyQuery);
			List<ModelKey> modelKeys = this.keyReader.readKeys(keys);
			pair.setKeyResults(modelKeys);
		}
	}

	public ModelQueryConverter<T, Q> getConverter() {
		return this.converter;
	}

	public ObjectifyKeyedQuery<T> getObjectifyQuery() {
		return this.objectifyQuery;
	}

	public ObjectifyKeyReader<T, ModelKey> getKeyReader() {
		return this.keyReader;
	}

}
