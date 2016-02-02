package com.dereekb.gae.model.extension.search.query.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.search.query.search.components.ModelQueryConverter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyQueryService;
import com.dereekb.gae.server.datastore.objectify.keys.ObjectifyKeyReader;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequest;
import com.dereekb.gae.utilities.function.staged.StagedFunction;
import com.dereekb.gae.utilities.function.staged.filter.FilteredStagedFunction;

/**
 * {@link StagedFunction} used for retrieving {@link ModelKey} instances from
 * Objectify, using input {@link QueryPair} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <Q>
 *            query type
 */
@Deprecated
public class ObjectifyQueryFunction<T extends ObjectifyModel<T>, Q> extends FilteredStagedFunction<Q, QueryPair<Q>> {

	private ModelQueryConverter<T, Q> converter;
	private ObjectifyQueryService<T> objectifyQuery;
	private ObjectifyKeyReader<T, ModelKey> keyReader;

	public ObjectifyQueryFunction(ModelQueryConverter<T, Q> converter,
	        ObjectifyQueryService<T> objectifyQuery,
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
			ObjectifyQueryRequest<T> objectifyQuery = this.converter.convertQuery(query);
			Set<ModelKey> keys = this.objectifyQuery.query(objectifyQuery).queryModelKeys();
			List<ModelKey> modelKeys = new ArrayList<ModelKey>(keys);
			pair.setKeyResults(modelKeys);
		}
	}

	public ModelQueryConverter<T, Q> getConverter() {
		return this.converter;
	}

	public ObjectifyQueryService<T> getObjectifyQuery() {
		return this.objectifyQuery;
	}

	public ObjectifyKeyReader<T, ModelKey> getKeyReader() {
		return this.keyReader;
	}

}
