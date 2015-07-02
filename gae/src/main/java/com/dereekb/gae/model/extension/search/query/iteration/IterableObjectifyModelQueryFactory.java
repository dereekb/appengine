package com.dereekb.gae.model.extension.search.query.iteration;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

/**
 * Factory for generating {@link IterableObjectifyModelQuery} instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            {@link ObjectifyModel}
 */
public class IterableObjectifyModelQueryFactory<T extends ObjectifyModel<T>>
        implements Factory<IterableObjectifyModelQuery<T>> {

	/**
	 * How many objects to retrieve from the database at once. Is not a factor
	 * of how many items will be queried total, but how they are batched
	 * together.
	 */
	private Integer queryLimit = IterableObjectifyModelQuery.MAX_QUERY_LIMIT;
	private ObjectifyRegistry<T> registry;
	private Factory<ObjectifyQueryInitializer<T>> initializerFactory;

	@Override
	public IterableObjectifyModelQuery<T> make() throws FactoryMakeFailureException {
		IterableObjectifyModelQuery<T> iterator = new IterableObjectifyModelQuery<T>();

		if (this.queryLimit != null) {
			iterator.setQueryLimit(this.queryLimit);
		}

		if (this.registry != null) {
			iterator.setRegistry(this.registry);
		}

		if (this.initializerFactory != null) {
			ObjectifyQueryInitializer<T> initializer = this.initializerFactory.make();
			iterator.setInitializer(initializer);
		}

		return iterator;
	}

	public Integer getQueryLimit() {
		return this.queryLimit;
	}

	public void setQueryLimit(Integer limit) {
		this.queryLimit = limit;
	}

	public ObjectifyRegistry<T> getRegistry() {
		return this.registry;
	}

	public void setRegistry(ObjectifyRegistry<T> registry) {
		this.registry = registry;
	}

	public Factory<ObjectifyQueryInitializer<T>> getInitializerFactory() {
		return this.initializerFactory;
	}

	public void setInitializerFactory(
			Factory<ObjectifyQueryInitializer<T>> initializerFactory) {
		this.initializerFactory = initializerFactory;
	}


}
