package com.dereekb.gae.model.crud.task.impl;

import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.crud.pairs.ReadPair;
import com.dereekb.gae.model.crud.task.ReadPairTask;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link Task} for reading models.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ReadPairTaskImpl<T extends UniqueModel>
        implements ReadPairTask<T> {

	private Getter<T> getter;

	public ReadPairTaskImpl() {}

	public ReadPairTaskImpl(Getter<T> getter) {
		this.getter = getter;
	}

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("Getter cannot be null.");
		}

		this.getter = getter;
	}

	@Override
	public void doTask(Iterable<ReadPair<T>> input) throws FailedTaskException {
		Map<ModelKey, ReadPair<T>> keysMap = ReadPair.pairsKeyMap(input);

		List<T> results = this.getter.getWithKeys(keysMap.keySet());

		for (T result : results) {
			ModelKey modelKey = result.getModelKey();
			ReadPair<T> pair = keysMap.get(modelKey);
			pair.setResult(result);
		}
	}

	@Override
	public String toString() {
		return "ReadPairTaskImpl [getter=" + this.getter + "]";
	}

}
