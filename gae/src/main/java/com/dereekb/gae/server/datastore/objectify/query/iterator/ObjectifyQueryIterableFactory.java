package com.dereekb.gae.server.datastore.objectify.query.iterator;

import java.util.Map;

import com.dereekb.gae.server.datastore.models.query.iterator.IndexedModelQueryIterable;
import com.dereekb.gae.server.datastore.models.query.iterator.IndexedModelQueryIterableFactory;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.google.appengine.api.datastore.Cursor;
import com.googlecode.objectify.cmd.SimpleQuery;

/**
 * {@link IndexedModelQueryIterableFactory} extension that allows the use of {@link SimpleQuery}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ObjectifyQueryIterableFactory<T extends ObjectifyModel<T>> extends IndexedModelQueryIterableFactory<T> {

	public static final Integer MAX_ITERATION_LIMIT = 1000;

	public IndexedModelQueryIterable<T> makeIterable(SimpleQuery<T> query);

	public IndexedModelQueryIterable<T> makeIterable(SimpleQuery<T> query,
	                                              Cursor cursor);

	public IndexedModelQueryIterable<T> makeIterable(Cursor cursor);

	public IndexedModelQueryIterable<T> makeIterable(Map<String, String> parameters,
	                                              Cursor cursor);

}
