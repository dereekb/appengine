package com.dereekb.gae.model.crud.task.impl;

import java.util.List;

import com.dereekb.gae.model.crud.exception.AtomicFunctionException;
import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.task.CreateTask;
import com.dereekb.gae.model.crud.task.config.CreateTaskConfig;
import com.dereekb.gae.model.crud.task.config.impl.CreateTaskConfigImpl;
import com.dereekb.gae.model.crud.task.impl.delegate.CreateTaskDelegate;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.task.IterableStoreTask;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * {@link CreateTask} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class CreateTaskImpl<T extends UniqueModel> extends AtomicTaskImpl<CreatePair<T>, CreateTaskConfig>
        implements CreateTask<T> {

	private CreateTaskDelegate<T> delegate;
	private IterableStoreTask<T> storeTask;

	private TaskRequestSender<T> reviewTaskSender;

	public CreateTaskImpl(CreateTaskDelegate<T> delegate, IterableStoreTask<T> storeTask, TaskRequestSender<T> sender) {
		this(delegate, storeTask);
		this.setReviewTaskSender(sender);
	}

	public CreateTaskImpl(CreateTaskDelegate<T> delegate, IterableStoreTask<T> storeTask) {
		super(new CreateTaskConfigImpl());
		this.setSaveTask(storeTask);
		this.setDelegate(delegate);
	}

	public CreateTaskDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(CreateTaskDelegate<T> delegate) throws IllegalArgumentException {
		if (delegate == null) {
			throw new IllegalArgumentException("Delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	public IterableStoreTask<T> getSaveTask() {
		return this.storeTask;
	}

	public void setSaveTask(IterableStoreTask<T> storeTask) throws IllegalArgumentException {
		if (storeTask == null) {
			throw new IllegalArgumentException("SaveTask cannot be null.");
		}

		this.storeTask = storeTask;
	}

	public TaskRequestSender<T> getReviewTaskSender() {
		return this.reviewTaskSender;
	}

	public void setReviewTaskSender(TaskRequestSender<T> reviewTaskSender) {
		this.reviewTaskSender = reviewTaskSender;
	}

	// MARK: Create Task
	@Override
	public void doTask(Iterable<CreatePair<T>> input,
	                   CreateTaskConfig configuration)
	        throws AtomicOperationException {
		super.doTask(input, configuration);

		// Retrieve successful pairs
		List<T> results = CreatePair.getObjects(input);

		this.tryStoreResults(results);
		this.trySendReviewTasks(results);
	}

	private void tryStoreResults(List<T> results) throws RuntimeException {
		try {
			this.storeTask.doStoreTask(results);
		} catch (FailedTaskException e) {
			Throwable cause = e.getCause();

			if (cause != null) {
				// Will be a runtime exception
				throw (RuntimeException) cause;
			}
		}
	}

	private void trySendReviewTasks(List<T> results) {
		if (this.reviewTaskSender != null) {
			this.reviewTaskSender.sendTasks(results);
		}
	}

	@Override
	protected void usePair(CreatePair<T> pair,
	                       CreateTaskConfig config) {
		T source = pair.getSource();

		try {
			T result = this.delegate.createFromSource(source);
			pair.setResult(result);
		} catch (InvalidAttributeException e) {
			pair.setAttributeFailure(e);
			throw new AtomicFunctionException(source, e);
		}
	}

	// MARK: Internal

	@Override
	public String toString() {
		return "CreateTaskImpl [storeTask=" + this.storeTask + ", delegate=" + this.delegate + ", defaultConfig="
		        + this.defaultConfig + "]";
	}

}
