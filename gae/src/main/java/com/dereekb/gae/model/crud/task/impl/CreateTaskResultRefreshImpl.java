package com.dereekb.gae.model.crud.task.impl;

import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.task.CreateTask;
import com.dereekb.gae.model.crud.task.config.CreateTaskConfig;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link CreateTask} implementation that wraps another, and reloads the
 * results.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class CreateTaskResultRefreshImpl<T extends UniqueModel>
        implements CreateTask<T> {

	private Getter<T> getter;
	private CreateTask<T> createTask;

	public CreateTaskResultRefreshImpl(Getter<T> getter, CreateTask<T> createTask) {
		this.setGetter(getter);
		this.setCreateTask(createTask);
	}

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("getter cannot be null.");
		}

		this.getter = getter;
	}

	public CreateTask<T> getCreateTask() {
		return this.createTask;
	}

	public void setCreateTask(CreateTask<T> createTask) {
		if (createTask == null) {
			throw new IllegalArgumentException("createTask cannot be null.");
		}

		this.createTask = createTask;
	}

	// MARK: CreateTask
	@Override
	public void doTask(Iterable<CreatePair<T>> input) throws FailedTaskException, AtomicOperationException {
		this.doTask(input, null);
	}

	@Override
	public void doTask(Iterable<CreatePair<T>> input,
	                   CreateTaskConfig configuration)
	        throws FailedTaskException,
	            AtomicOperationException {
		this.createTask.doTask(input, configuration);
		this.refreshResultModels(input);
	}

	private void refreshResultModels(Iterable<CreatePair<T>> input) {
		List<CreatePair<T>> pairs = CreatePair.pairsWithResults(input);

		Map<ModelKey, CreatePair<T>> pairsMap = ModelKey.makeModelKeyMap(pairs);
		List<T> models = this.getter.getWithKeys(pairsMap.keySet());

		for (T model : models) {
			CreatePair<T> pair = pairsMap.get(model.getModelKey());
			pair.setResult(model);
		}
	}

	@Override
	public String toString() {
		return "CreateTaskResultRefreshImpl [getter=" + this.getter + ", createTask=" + this.createTask + "]";
	}

}
