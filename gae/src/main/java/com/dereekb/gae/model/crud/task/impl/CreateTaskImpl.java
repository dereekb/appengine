package com.dereekb.gae.model.crud.task.impl;

import java.util.List;

import com.dereekb.gae.model.crud.exception.AtomicFunctionException;
import com.dereekb.gae.model.crud.exception.InvalidTemplateException;
import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.model.crud.task.CreateTask;
import com.dereekb.gae.model.crud.task.config.CreateTaskConfig;
import com.dereekb.gae.model.crud.task.config.impl.CreateTaskConfigImpl;
import com.dereekb.gae.model.crud.task.impl.delegate.CreateTaskDelegate;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.utilities.task.IterableTask;

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
		this.saveTask = saveTask;
		this.delegate = delegate;
	}

	public CreateTaskDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(CreateTaskDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public IterableTask<T> getSaveTask() {
		return this.saveTask;
	}

	public void setSaveTask(IterableTask<T> saveTask) {
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
		this.saveTask.doTask(results);

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
		} catch (InvalidTemplateException e) {
			pair.flagFailure();
			throw new AtomicFunctionException(source, e);
		}
	}

	@Override
	public String toString() {
		return "CreateTaskImpl [saveTask=" + this.saveTask + ", delegate=" + this.delegate + ", defaultConfig="
		        + this.defaultConfig + "]";
	}

}
