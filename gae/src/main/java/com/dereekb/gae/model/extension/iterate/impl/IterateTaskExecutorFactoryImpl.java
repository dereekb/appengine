package com.dereekb.gae.model.extension.iterate.impl;

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

	private ObjectifyQueryIterableFactory<T> queryFactory;
	private ModelKeyListAccessorFactory<T> accessorFactory;

	public IterateTaskExecutorFactoryImpl(ObjectifyRegistry<T> registry) throws IllegalArgumentException {
		this(registry.makeIterableQueryFactory(), registry);
	}

	public IterateTaskExecutorFactoryImpl(ObjectifyQueryIterableFactory<T> queryFactory,
	        ModelKeyListAccessorFactory<T> accessorFactory) throws IllegalArgumentException {
		this.setQueryFactory(queryFactory);
		this.setAccessorFactory(accessorFactory);
	}

	public ObjectifyQueryIterableFactory<T> getQueryFactory() {
		return this.queryFactory;
	}

	public void setQueryFactory(ObjectifyQueryIterableFactory<T> queryFactory) throws IllegalArgumentException {
		if (queryFactory == null) {
			throw new IllegalArgumentException("QueryFactory cannot be null.");
		}

		this.queryFactory = queryFactory;
	}

	public ModelKeyListAccessorFactory<T> getAccessorFactory() {
		return this.accessorFactory;
	}

	public void setAccessorFactory(ModelKeyListAccessorFactory<T> accessorFactory) throws IllegalArgumentException {
		if (accessorFactory == null) {
			throw new IllegalArgumentException("AccessorFactory cannot be null.");
		}

		this.accessorFactory = accessorFactory;
	}

	// MARK: IterateTaskExecutorFactory
	@Override
	public IterateTaskExecutorImpl<T> makeExecutor(Task<ModelKeyListAccessor<T>> task) {
		return new IterateTaskExecutorImpl<T>(this.queryFactory, this.accessorFactory, task);
	}

	@Override
	public String toString() {
		return "IterateTaskExecutorFactoryImpl [query=" + this.queryFactory + ", accessorFactory="
		        + this.accessorFactory + "]";
	}

}
