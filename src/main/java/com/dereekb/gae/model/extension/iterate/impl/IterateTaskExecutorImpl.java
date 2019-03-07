package com.dereekb.gae.model.extension.iterate.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.extension.iterate.IterateTaskExecutor;
import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.model.extension.iterate.exception.IterationLimitReachedException;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessorFactory;
import com.dereekb.gae.server.datastore.models.query.iterator.IndexedModelQueryIterable;
import com.dereekb.gae.server.datastore.models.query.iterator.IndexedModelQueryIterator;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.cursor.impl.ObjectifyCursor;
import com.dereekb.gae.server.datastore.objectify.query.iterator.ObjectifyQueryIterableFactory;
import com.dereekb.gae.utilities.collections.IteratorUtility;
import com.dereekb.gae.utilities.collections.batch.Batch;
import com.dereekb.gae.utilities.collections.batch.BatchBuilder;
import com.dereekb.gae.utilities.collections.batch.Partition;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.dereekb.gae.utilities.collections.iterator.limit.LimitedIterator;
import com.dereekb.gae.utilities.collections.iterator.limit.impl.LimitedIteratorImpl;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

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

	private int iteratorLimit = ObjectifyQueryIterableFactory.MAX_ITERATION_LIMIT;

	private ObjectifyQueryIterableFactory<T> query;
	private ModelKeyListAccessorFactory<T> accessorFactory;
	private Task<ModelKeyListAccessor<T>> task;

	private BatchBuilder batchBuilder;

	public IterateTaskExecutorImpl(ObjectifyQueryIterableFactory<T> query,
	        ModelKeyListAccessorFactory<T> accessorFactory,
	        Task<ModelKeyListAccessor<T>> task) {
		this.setQuery(query);
		this.setAccessorFactory(accessorFactory);
		this.setTask(task);
	}

	public int getIteratorLimit() {
		return this.iteratorLimit;
	}

	public void setIteratorLimit(int iteratorLimit) {
		this.iteratorLimit = iteratorLimit;
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

	public BatchBuilder getBatchBuilder() {
		return this.batchBuilder;
	}

	public void setBatchBuilder(BatchBuilder batchBuilder) {
		this.batchBuilder = batchBuilder;
	}

	public Task<ModelKeyListAccessor<T>> getTask() {
		return this.task;
	}

	public void setTask(Task<ModelKeyListAccessor<T>> task) {
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
		public void run() throws IterationLimitReachedException, FailedTaskException {

			// Initialize Iterator
			String cursorString = this.input.getStepCursor();
			ObjectifyCursor cursor = ObjectifyCursor.safe(cursorString);

			Map<String, String> parameters = this.input.getParameters();
			IndexedModelQueryIterable<T> iterable = IterateTaskExecutorImpl.this.query.makeIterable(parameters, cursor);
			IndexedModelQueryIterator<T> iterator = iterable.iterator();
			LimitedIterator<T> limitedIterator = new LimitedIteratorImpl<T>(iterator,
			        IterateTaskExecutorImpl.this.iteratorLimit);

			this.runTaskWithIterator(limitedIterator);

			// Schedule Continuation
			if (limitedIterator.hasReachedIteratorLimit()) {
				ResultsCursor endCursor = iterator.getEndCursor();
				String endCursorString = endCursor.getCursorString();
				throw new IterationLimitReachedException(endCursorString);
			}
		}

		private void runTaskWithIterator(Iterator<T> iterator) {
			if (IterateTaskExecutorImpl.this.batchBuilder != null) {
				Batch<T> batch = IterateTaskExecutorImpl.this.batchBuilder.makeBatch(iterator);

				for (Partition<T> partition : batch) {
					this.runTaskWithModels(partition.getPartitionElements());
				}
			} else {
				List<T> models = IteratorUtility.iteratorToList(iterator);
				this.runTaskWithModels(models);
			}
		}

		private void runTaskWithModels(Collection<T> models) {
			ModelKeyListAccessor<T> accessor = IterateTaskExecutorImpl.this.accessorFactory
			        .createAccessorWithModels(models);
			IterateTaskExecutorImpl.this.task.doTask(accessor);
		}

	}

}
