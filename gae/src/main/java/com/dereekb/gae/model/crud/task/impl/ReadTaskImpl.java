package com.dereekb.gae.model.crud.task.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.crud.pairs.ReadPair;
import com.dereekb.gae.model.crud.task.ReadTask;
import com.dereekb.gae.model.crud.task.config.ReadTaskConfig;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

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
	private ReadTaskConfig defaultConfig;

	public ReadTaskImpl(Getter<T> getter) throws IllegalArgumentException {
		this(getter, null);
	}

	public ReadTaskImpl(Getter<T> getter, ReadTaskConfig defaultConfig) throws IllegalArgumentException {
		this.setGetter(getter);
		this.setDefaultConfig(defaultConfig);
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

	public ReadTaskConfig getDefaultConfig() {
		return this.defaultConfig;
	}

	public void setDefaultConfig(ReadTaskConfig defaultConfig) {
		this.defaultConfig = defaultConfig;
	}

	// MARK: ReadTask
	@Override
	public void doTask(Iterable<ReadPair<T>> input) {
		this.doTask(input, this.defaultConfig);
	}

	@Override
	public void doTask(Iterable<ReadPair<T>> input,
	                   ReadTaskConfig configuration) {
		Map<ModelKey, ReadPair<T>> keysMap = ReadPair.pairsKeyMap(input);
		List<T> results = this.loadModels(keysMap.keySet());

		for (T result : results) {
			ModelKey modelKey = result.getModelKey();
			ReadPair<T> pair = keysMap.get(modelKey);
			pair.setResult(result);
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
