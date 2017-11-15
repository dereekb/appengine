package com.dereekb.gae.model.crud.task.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.crud.pairs.ReadPair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationExceptionReason;
import com.dereekb.gae.model.crud.task.ReadTask;
import com.dereekb.gae.model.crud.task.config.AtomicTaskConfig;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.pairs.impl.ResultsPair;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link ReadTask} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ReadTaskImpl<T extends UniqueModel>
        implements ReadTask<T> {

	private Getter<T> getter;

	public ReadTaskImpl(Getter<T> getter) throws IllegalArgumentException {
		this.setGetter(getter);
	}

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) throws IllegalArgumentException {
		if (getter == null) {
			throw new IllegalArgumentException("Getter cannot be null.");
		}

		this.getter = getter;
	}

	// MARK: ReadTask
	@Override
	public void doTask(Iterable<ReadPair<T>> input) {
		Map<ModelKey, ReadPair<T>> keysMap = ReadPair.pairsKeyMap(input);
		List<T> results = this.loadModels(keysMap.keySet());

		for (T result : results) {
			ModelKey modelKey = result.getModelKey();
			ReadPair<T> pair = keysMap.get(modelKey);
			pair.setResult(result);
		}
	}

	@Override
	public void doTask(Iterable<ReadPair<T>> input,
	                   AtomicTaskConfig configuration)
	        throws FailedTaskException {
		try {
			this.doReadTask(input, configuration.isAtomic());
		} catch (RuntimeException e) {
			throw new FailedTaskException(e);
		}
	}

	@Override
	public void doReadTask(Iterable<ReadPair<T>> input,
	                       boolean atomic)
	        throws AtomicOperationException,
	            FailedTaskException {
		this.doTask(input);

		if (atomic) {
			List<ReadPair<T>> errorPairs = ResultsPair.pairsWithoutResults(input);
			List<ModelKey> errorKeys = ReadPair.getKeys(errorPairs);

			if (errorKeys.size() > 0) {
				throw new AtomicOperationException(errorKeys, AtomicOperationExceptionReason.UNAVAILABLE);
			}
		}
	}

	protected List<T> loadModels(Set<ModelKey> set) {
		return this.getter.getWithKeys(set);
	}

	@Override
	public String toString() {
		return "ReadTaskImpl [getter=" + this.getter + "]";
	}

}
