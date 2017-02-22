package com.dereekb.gae.model.crud.task.impl;

import java.util.List;

import com.dereekb.gae.model.crud.exception.AtomicFunctionException;
import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.model.crud.task.CreateTask;
import com.dereekb.gae.model.crud.task.config.CreateTaskConfig;
import com.dereekb.gae.model.crud.task.config.impl.CreateTaskConfigImpl;
import com.dereekb.gae.model.crud.task.impl.delegate.CreateTaskDelegate;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.utilities.task.IterableTask;
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
	private IterableTask<T> saveTask;

	private TaskRequestSender<T> reviewTaskSender;

	public CreateTaskImpl(CreateTaskDelegate<T> delegate, IterableTask<T> saveTask, TaskRequestSender<T> sender) {
		this(delegate, saveTask);
		this.setReviewTaskSender(sender);
	}

	public CreateTaskImpl(CreateTaskDelegate<T> delegate, IterableTask<T> saveTask) {
		super(new CreateTaskConfigImpl());
		this.setSaveTask(saveTask);
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

	public IterableTask<T> getSaveTask() {
		return this.saveTask;
	}

	public void setSaveTask(IterableTask<T> saveTask) throws IllegalArgumentException {
		if (saveTask == null) {
			throw new IllegalArgumentException("SaveTask cannot be null.");
		}

		this.saveTask = saveTask;
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
	                   CreateTaskConfig configuration) {
		super.doTask(input, configuration);

		// Retrieve successful pairs
		List<T> results = CreatePair.getObjects(input);

		try {
			this.saveTask.doTask(results);
		} catch (FailedTaskException e) {
			Throwable cause = e.getCause();

			if (cause != null) {
				// Will be a runtime exception
				throw (RuntimeException) cause;
			}
		}

		if (this.reviewTaskSender != null) {
			this.reviewTaskSender.sendTasks(results);
		}
	}

	@Override
	public void usePair(CreatePair<T> pair,
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

	@Override
	public String toString() {
		return "CreateTaskImpl [saveTask=" + this.saveTask + ", delegate=" + this.delegate + ", defaultConfig="
		        + this.defaultConfig + "]";
	}

}
