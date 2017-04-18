package com.dereekb.gae.model.crud.task.impl;

import java.util.List;

import com.dereekb.gae.model.crud.exception.AtomicFunctionException;
import com.dereekb.gae.model.crud.pairs.UpdatePair;
import com.dereekb.gae.model.crud.task.UpdateTask;
import com.dereekb.gae.model.crud.task.config.UpdateTaskConfig;
import com.dereekb.gae.model.crud.task.config.impl.UpdateTaskConfigImpl;
import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.task.IterableUpdateTask;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;
import com.dereekb.gae.web.api.util.attribute.exception.KeyedInvalidAttributeException;

/**
 * {@link UpdateTask} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class UpdateTaskImpl<T extends UniqueModel> extends AtomicTaskImpl<UpdatePair<T>, UpdateTaskConfig>
        implements UpdateTask<T> {

	private UpdateTaskDelegate<T> delegate;
	private IterableUpdateTask<T> updateTask;

	private TaskRequestSender<T> reviewTaskSender;

	public UpdateTaskImpl(UpdateTaskDelegate<T> delegate, IterableUpdateTask<T> updateTask, TaskRequestSender<T> sender)
	        throws IllegalArgumentException {
		this(delegate, updateTask);
		this.setReviewTaskSender(sender);
	}

	public UpdateTaskImpl(UpdateTaskDelegate<T> delegate, IterableUpdateTask<T> updateTask)
	        throws IllegalArgumentException {
		super(new UpdateTaskConfigImpl());
		this.setUpdateTask(updateTask);
		this.setDelegate(delegate);
	}

	public UpdateTaskDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(UpdateTaskDelegate<T> delegate) throws IllegalArgumentException {
		if (delegate == null) {
			throw new IllegalArgumentException("Delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	public IterableUpdateTask<T> getUpdateTask() {
		return this.updateTask;
	}

	public void setUpdateTask(IterableUpdateTask<T> updateTask) throws IllegalArgumentException {
		if (updateTask == null) {
			throw new IllegalArgumentException("UpdateTask cannot be null.");
		}

		this.updateTask = updateTask;
	}

	public TaskRequestSender<T> getReviewTaskSender() {
		return this.reviewTaskSender;
	}

	public void setReviewTaskSender(TaskRequestSender<T> reviewTaskSender) throws IllegalArgumentException {
		if (reviewTaskSender == null) {
			throw new IllegalArgumentException("ReviewTaskSender cannot be null.");
		}

		this.reviewTaskSender = reviewTaskSender;
	}

	// MARK: Update Task
	@Override
	public void doTask(Iterable<UpdatePair<T>> input,
	                   UpdateTaskConfig configuration) {
		super.doTask(input, configuration);

		List<UpdatePair<T>> pairs = UpdatePair.pairsWithResults(input);
		List<T> targets = UpdatePair.getSources(pairs);
		this.reviewUpdatedTargets(targets);
	}

	protected void reviewUpdatedTargets(List<T> targets) {
		this.updateTask.doUpdateTask(targets);

		if (this.reviewTaskSender != null) {
			this.reviewTaskSender.sendTasks(targets);
		}
	}

	@Override
	protected void usePair(UpdatePair<T> pair,
	                       UpdateTaskConfig config) {
		T template = pair.getTemplate();
		T target = pair.getTarget();

		try {
			this.delegate.updateTarget(target, template);
			pair.setSuccessful(true);
		} catch (InvalidAttributeException e) {
			pair.setFailureException(e);
			KeyedInvalidAttributeException keyedException = new KeyedInvalidAttributeException(template, e);
			throw new AtomicFunctionException(template, keyedException);
		}
	}

	@Override
	public String toString() {
		return "UpdateTaskImpl [updateTask=" + this.updateTask + ", delegate=" + this.delegate + ", defaultConfig="
		        + this.defaultConfig + "]";
	}

}
