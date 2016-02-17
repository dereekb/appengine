package com.dereekb.gae.web.taskqueue.controller.extension.iterate.old.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.iterator.IterableObjectifyQuery;
import com.dereekb.gae.utilities.collections.iterator.limit.impl.LimitedIteratorImpl;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.iteration.ConfiguredIterationTask;
import com.dereekb.gae.utilities.task.iteration.exception.IncompleteTaskIterationException;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.deprecated.request.IterateTaskContinuation;
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
@Deprecated
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

	public IterateTask() {}

	public IterateTask(IterableObjectifyQuery<T> query) {
		this.query = query;
	}

	public IterateTaskContinuation getContinuation() {
		return this.continuation;
	}

	public void setContinuation(IterateTaskContinuation continuation) {
		this.continuation = continuation;
	}

	public Factory<ConfiguredIterationTask<T, IterateTaskInput>> getTaskFactory() {
		return this.taskFactory;
	}

	public void setTaskFactory(Factory<ConfiguredIterationTask<T, IterateTaskInput>> taskFactory) {
		this.taskFactory = taskFactory;
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
			IterableObjectifyQuery<T>.Instance iterator = IterateTask.this.query.makeIterable(cursor);

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
