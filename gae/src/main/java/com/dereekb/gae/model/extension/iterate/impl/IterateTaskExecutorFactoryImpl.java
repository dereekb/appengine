package com.dereekb.gae.model.extension.iterate.impl;

import com.dereekb.gae.model.extension.iterate.IterateTaskExecutor;
import com.dereekb.gae.model.extension.iterate.IterateTaskExecutorFactory;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessorFactory;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.iterator.ObjectifyQueryIterableFactory;
import com.dereekb.gae.utilities.task.Task;

/**
 * {@link IterateTaskExecutorFactory} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class IterateTaskExecutorFactoryImpl<T extends ObjectifyModel<T>>
        implements IterateTaskExecutorFactory<T> {

	private ObjectifyQueryIterableFactory<T> query;
	private ModelKeyListAccessorFactory<T> accessorFactory;

	public IterateTaskExecutorFactoryImpl(ObjectifyRegistry<T> registry) {
		this(registry.makeIterableQueryFactory(), registry);
	}

	public IterateTaskExecutorFactoryImpl(ObjectifyQueryIterableFactory<T> query,
	        ModelKeyListAccessorFactory<T> accessorFactory) {
		this.query = query;
		this.accessorFactory = accessorFactory;
	}

	public ObjectifyQueryIterableFactory<T> getQuery() {
		return this.query;
	}

	public void setQuery(ObjectifyQueryIterableFactory<T> query) {
		this.query = query;
	}

	public ModelKeyListAccessorFactory<T> getAccessorFactory() {
		return this.accessorFactory;
	}

	public void setAccessorFactory(ModelKeyListAccessorFactory<T> accessorFactory) {
		this.accessorFactory = accessorFactory;
	}

	// MARK: IterateTaskExecutorFactory
	@Override
	public IterateTaskExecutor<T> makeExecutor(Task<ModelKeyListAccessor<T>> task) {
		return new IterateTaskExecutorImpl<T>(this.query, this.accessorFactory, task);
	}

	@Override
	public String toString() {
		return "IterateTaskExecutorFactoryImpl [query=" + this.query + ", accessorFactory=" + this.accessorFactory
		        + "]";
	}

}
