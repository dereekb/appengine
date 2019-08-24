package com.dereekb.gae.server.datastore.models.query.iterator;

import java.util.Map;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.dereekb.gae.utilities.misc.parameters.Parameters;

/**
 * Used for generating {@link IndexedModelQueryIterable} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface IndexedModelQueryIterableFactory<T extends UniqueModel> {

	public IndexedModelQueryIterable<T> makeIterable();

	public IndexedModelQueryIterable<T> makeIterable(ResultsCursor cursor);

	public IndexedModelQueryIterable<T> makeIterable(Parameters parameters);

	public IndexedModelQueryIterable<T> makeIterable(Map<String, String> parameters);

	public IndexedModelQueryIterable<T> makeIterable(Parameters parameters,
	                                                 ResultsCursor cursor);

	public IndexedModelQueryIterable<T> makeIterable(Map<String, String> parameters,
	                                                 ResultsCursor cursor);

}
