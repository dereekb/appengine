package com.dereekb.gae.model.crud.task.impl;

import java.util.List;

import com.dereekb.gae.model.crud.exception.AtomicFunctionException;
import com.dereekb.gae.model.crud.exception.AttributeFailureException;
import com.dereekb.gae.model.crud.pairs.UpdatePair;
import com.dereekb.gae.model.crud.task.UpdateTask;
import com.dereekb.gae.model.crud.task.config.UpdateTaskConfig;
import com.dereekb.gae.model.crud.task.config.impl.UpdateTaskConfigImpl;
import com.dereekb.gae.model.crud.task.impl.delegate.UpdateTaskDelegate;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.task.IterableTask;

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
	private IterableTask<T> saveTask;

	public UpdateTaskImpl(UpdateTaskDelegate<T> delegate, IterableTask<T> saveTask) {
		super(new UpdateTaskConfigImpl());
		this.saveTask = saveTask;
		this.delegate = delegate;
	}

	public UpdateTaskDelegate<T> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(UpdateTaskDelegate<T> delegate) {
		this.delegate = delegate;
	}

	public IterableTask<T> getSaveTask() {
		return this.saveTask;
	}

	public void setSaveTask(IterableTask<T> saveTask) {
		this.saveTask = saveTask;
	}

	// MARK: Update Task
	@Override
	public void doTask(Iterable<UpdatePair<T>> input,
	                   UpdateTaskConfig configuration) {
		super.doTask(input, configuration);

		List<UpdatePair<T>> pairs = UpdatePair.pairsWithResults(input);
		List<T> targets = UpdatePair.getSources(pairs);
		this.saveTask.doTask(targets);
	}

	@Override
	public void usePair(UpdatePair<T> pair,
	                    UpdateTaskConfig config) {
		T template = pair.getTemplate();
		T target = pair.getTarget();

		try {
			this.delegate.updateTarget(target, template);
			pair.setSuccessful(true);
		} catch (AttributeFailureException e) {
			pair.setFailureException(e);
			throw new AtomicFunctionException(template, e);
		}
	}

	@Override
	public String toString() {
		return "UpdateTaskImpl [saveTask=" + this.saveTask + ", delegate=" + this.delegate + ", defaultConfig="
		        + this.defaultConfig + "]";
	}

}
