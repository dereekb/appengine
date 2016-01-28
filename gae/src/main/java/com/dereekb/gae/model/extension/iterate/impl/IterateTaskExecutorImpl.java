package com.dereekb.gae.model.extension.iterate.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.iterate.IterateTaskExecutor;
import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.model.extension.iterate.exception.IterationLimitReachedException;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessorFactory;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.iterator.IterableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.iterator.ObjectifyQueryIterable;
import com.dereekb.gae.utilities.collections.iterator.limit.impl.LimitedIterableImpl;
import com.dereekb.gae.utilities.task.IterableTask;
import com.google.appengine.api.datastore.Cursor;

/**
 * {@link IterateTaskExecutor} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class IterateTaskExecutorImpl<T extends ObjectifyModel<T>>
        implements IterateTaskExecutor<T> {

	private int iteratorLimit = IterableObjectifyQuery.MAX_LIMIT;

	private IterableObjectifyQuery<T> query;
	private ModelKeyListAccessorFactory<T> accessorFactory;

	private IterableTask<T> task;

	public IterateTaskExecutorImpl(IterableObjectifyQuery<T> query,
	        ModelKeyListAccessorFactory<T> accessorFactory,
	        IterableTask<T> task) {
		this.query = query;
		this.accessorFactory = accessorFactory;
		this.task = task;
	}

	public int getIteratorLimit() {
		return this.iteratorLimit;
	}

	public void setIteratorLimit(int iteratorLimit) {
		this.iteratorLimit = iteratorLimit;
	}

	public IterableObjectifyQuery<T> getQuery() {
		return this.query;
	}

	public void setQuery(IterableObjectifyQuery<T> query) {
		this.query = query;
	}

	public ModelKeyListAccessorFactory<T> getAccessorFactory() {
		return this.accessorFactory;
	}

	public void setAccessorFactory(ModelKeyListAccessorFactory<T> accessorFactory) {
		this.accessorFactory = accessorFactory;
	}

	public IterableTask<T> getTask() {
		return this.task;
	}

	public void setTask(IterableTask<T> task) {
		this.task = task;
	}

	@Override
	public void executeTask(IterateTaskInput input) throws IterationLimitReachedException {
		Instance instance = new Instance(input);
		instance.run();
	}

	// MARK: Task
	private class Instance {

		private final IterateTaskInput input;

		public Instance(IterateTaskInput input) {
			this.input = input;
		}

		/**
		 * Performs the iteration.
		 */
		public void run() throws IterationLimitReachedException {

			// Initialize Iterator
			String cursorString = this.input.getStepCursor();
			Cursor cursor = null;

			if (cursorString != null) {
				cursor = Cursor.fromWebSafeString(cursorString);
			}

			Map<String, String> parameters = this.input.getParameters();
			ObjectifyQueryIterable<T> iterable = IterateTaskExecutorImpl.this.query.makeIterable(cursor, parameters);

			// Run Iterator
			LimitedIterableImpl<T> limitedIterable = new LimitedIterableImpl<T>(iterable,
			        IterateTaskExecutorImpl.this.iteratorLimit);

			IterateTaskExecutorImpl.this.task.doTask(limitedIterable);

			// TODO: Batch without using the limited iterable, and keep access
			// to the ObjectifyQuery in order to throw the exception properly.

			/*
			// Schedule Continuation
			if (limitedIterator.getIndex() >= IterateTask.this.iteratorLimit) {
				cursor = iterator.getEndCursor();
				IterateTask.this.continuation.continueTask(this.input, cursor);
			}
			*/
		}

	}

}
