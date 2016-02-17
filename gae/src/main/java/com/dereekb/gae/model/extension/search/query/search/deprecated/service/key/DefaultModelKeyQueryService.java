package com.dereekb.gae.model.extension.search.query.search.service.key;

import java.util.List;

import com.dereekb.gae.model.extension.search.query.search.ObjectifyQueryFunction;
import com.dereekb.gae.model.extension.search.query.search.QueryPair;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * Default {@link ModelKeyQueryService} implementation.
 *
 * @author dereekb
 *
 * @param <Q>
 */
@Deprecated
public final class DefaultModelKeyQueryService<T extends ObjectifyModel<T>, Q>
        implements ModelKeyQueryService<Q> {

	private final Factory<ObjectifyQueryFunction<T, Q>> factory;

	public DefaultModelKeyQueryService(Factory<ObjectifyQueryFunction<T, Q>> factory) {
		this.factory = factory;
	}

	public Factory<ObjectifyQueryFunction<T, Q>> getFactory() {
		return this.factory;
	}

	@Override
	public List<ModelKey> queryKeys(Q query) {
		ObjectifyQueryFunction<T, Q> function = this.factory.make();
		QueryPair<Q> pair = new QueryPair<Q>(query);

		function.addObject(pair);
		function.run();

		List<ModelKey> keys = pair.getKeyResults();
		return keys;
	}

}
