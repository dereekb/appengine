package com.dereekb.gae.model.extension.taskqueue.task.iterate;

import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.extension.taskqueue.api.CustomTask;
import com.dereekb.gae.model.extension.taskqueue.api.CustomTaskInfo;
import com.dereekb.gae.server.datastore.models.query.IterableModelQuery;
import com.dereekb.gae.server.datastore.models.query.ModelQueryIterator;
import com.dereekb.gae.utilities.collections.batch.BatchGenerator;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;
import com.dereekb.gae.web.api.taskqueue.controller.extension.iterate.IterateTask;
import com.google.appengine.api.datastore.Cursor;

/**
 * Task that iterates over models, building batches of models, and a subtask
 * function.
 *
 * Has a maximum amount of models it iterates over before scheduling itself with
 * the models cursor forward.
 *
 * @author dereekb
 * @deprecated replaced by {@link IterateTask}
 */
@Deprecated
public class IterateModelsTask<T>
        implements CustomTask, Factory<CustomTask> {

	public static final Integer DEFAULT_LIMIT = 1000;
	public static final String CURSOR_PARAM = "cursor";

	/**
	 * Parameter of the cursor string.
	 */
	private String cursorParam = CURSOR_PARAM;

	/**
	 * The limit on the number of models to iterate over before scheduling a
	 * continuation.
	 */
	private Integer limit = DEFAULT_LIMIT;

	private Factory<IterableModelQuery<T>> iteratorFactory;
	private Factory<IterateModelsSubtask<T>> subtaskFactory;
	private IterateModelsTaskContinueDelegate continuationDelegate;
	private BatchGenerator<T> batchGenerator = new BatchGenerator<T>();

	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getBatchSize() {
		return this.batchGenerator.getBatchSize();
	}

	public void setBatchSize(Integer batchSize) {
		this.batchGenerator.setBatchSize(batchSize);
	}

	@Override
	public void doTask(CustomTaskInfo request) {
		CustomTask task = this.make();
		task.doTask(request);
	}

	@Override
	public IterateModelsTaskInstance make() throws FactoryMakeFailureException {
		if (this.iteratorFactory == null || this.subtaskFactory == null) {
			throw new FactoryMakeFailureException("Missing required factory.");
		}

		if (this.continuationDelegate == null) {
			throw new FactoryMakeFailureException("No continue delegate specified.");
		}

		IterableModelQuery<T> iterator = this.iteratorFactory.make();
		IterateModelsSubtask<T> subtask = this.subtaskFactory.make();
		IterateModelsTaskInstance instance = new IterateModelsTaskInstance(iterator, subtask);
		return instance;
	}

	/**
	 * Single-use task instance.
	 *
	 * @author dereekb
	 */
	private class IterateModelsTaskInstance
	        implements CustomTask {

		private Integer current = 0;

		private CustomTaskInfo request;

		private final IterableModelQuery<T> iterable;
		private final IterateModelsSubtask<T> task;

		public IterateModelsTaskInstance(IterableModelQuery<T> iterable, IterateModelsSubtask<T> task) {
			this.iterable = iterable;
			this.task = task;
		}

		@Override
		public void doTask(CustomTaskInfo request) {
			this.request = request;
			this.init();

			ModelQueryIterator<T> iterator = this.iterable.make();

			this.task.initTask(request);

			while (iterator.hasNext() && this.current < IterateModelsTask.this.limit) {
				List<T> batch = IterateModelsTask.this.batchGenerator.createBatch(iterator);
				this.task.useModelBatch(batch);
				this.current += batch.size();
			}

			this.task.endTask();

			if (this.current >= IterateModelsTask.this.limit) {
				this.scheduleContinuation(iterator);
			}
		}

		/**
		 * Initializes this {@link IterateModelsTaskInstance} by updating
		 * {@link #iterable} with custom parameters and a cursor value, if it
		 * exists.
		 */
		private void init() {
			if (this.request == null) {
				throw new RuntimeException("No request set for IterateModelsTaskInstance.");
			}

			Map<String, String> parameters = this.request.getTaskParameters();
			this.iterable.setCustomParameters(parameters);

			String cursorString = parameters.get(IterateModelsTask.this.cursorParam);
			if (cursorString != null) {
				Cursor cursor = Cursor.fromWebSafeString(cursorString);
				this.iterable.setStartCursor(cursor);
			}
		}

		/**
		 * Called to schedule the completion of another section of the task in
		 * the future.
		 *
		 * @param iterator
		 */
		private void scheduleContinuation(ModelQueryIterator<T> iterator) {
			Cursor cursor = iterator.getEndCursor();
			String cursorString = cursor.toWebSafeString();
			IterateModelsTask.this.continuationDelegate.continueIteration(this.request, cursorString);
		}
	}

}
