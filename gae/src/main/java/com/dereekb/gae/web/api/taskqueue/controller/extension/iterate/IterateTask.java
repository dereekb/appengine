package com.dereekb.gae.web.api.taskqueue.controller.extension.iterate;

import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.iterator.IterableObjectifyQuery;
import com.dereekb.gae.utilities.collections.iterator.limit.impl.LimitedIteratorImpl;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.iteration.ConfiguredIterationTask;
import com.dereekb.gae.utilities.task.iteration.exception.IncompleteTaskIterationException;
import com.dereekb.gae.web.api.taskqueue.controller.extension.iterate.request.IterateTaskContinuation;
import com.google.appengine.api.datastore.Cursor;

/**
 * {@link Task} for iterating over {@link ObjectifyModel} models using a
 * {@link IterableObjectifyQuery}.
 * <p>
 * The iterated values are used by a {@link ConfiguredIterationTask} instance.
 *
 * @author dereekb
 *
 * @param <T>
 *            Model type.
 */
public class IterateTask<T extends ObjectifyModel<T>>
        implements Task<IterateTaskInput> {

	/**
	 * Query iterator limit.
	 */
	private int iteratorLimit = IterableObjectifyQuery.MAX_LIMIT;

	/**
	 * Query to use for iterating.
	 */
	private IterableObjectifyQuery<T> query;

	/**
	 * Delegate for continuation.
	 */
	private IterateTaskContinuation continuation;

	/**
	 * {@link Factory} of the task to perform.
	 */
	private Factory<ConfiguredIterationTask<T, IterateTaskInput>> taskFactory;

	public IterateTask() {}

	public IterateTask(IterableObjectifyQuery<T> query) {
		this.query = query;
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

	// MARK: Task
	@Override
	public void doTask(IterateTaskInput input) {
		IterateInstance instance = new IterateInstance(input);
		instance.run();
	}

	private class IterateInstance {

		private final IterateTaskInput input;

		private final ConfiguredIterationTask<T, IterateTaskInput> task;

		public IterateInstance(IterateTaskInput input) {
			this.input = input;
			this.task = IterateTask.this.taskFactory.make();
		}

		/**
		 * Performs the iteration.
		 */
		public void run() throws IncompleteTaskIterationException {

			// Initialize Iterator
			Cursor cursor = this.input.getQueryCursor();
			IterableObjectifyQuery<T>.IteratorInstance iterator = IterateTask.this.query.iteratorWithCursor(cursor);

			Map<String, String> parameters = this.input.getParameters();
			iterator.setParameters(parameters);

			// Run Iterator
			LimitedIteratorImpl<T> limitedIterator = new LimitedIteratorImpl<T>(iterator,
			        IterateTask.this.iteratorLimit);
			limitedIterator.setIteratorLimit(IterateTask.this.iteratorLimit);

			this.task.doTask(limitedIterator, this.input);

			// Schedule Continuation
			if (limitedIterator.getIndex() >= IterateTask.this.iteratorLimit) {
				cursor = iterator.getEndCursor();
				IterateTask.this.continuation.continueTask(this.input, cursor);
			}
		}

	}

}
