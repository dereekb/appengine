package com.dereekb.gae.model.crud.task.impl;

import java.util.List;
import java.util.Map;

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

	public ReadTaskImpl() {}

	public ReadTaskImpl(Getter<T> getter) {
		this.getter = getter;
		this.defaultConfig = null;
	}

	public ReadTaskImpl(Getter<T> getter, ReadTaskConfig defaultConfig) {
		this.getter = getter;
		this.defaultConfig = defaultConfig;
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
		List<T> results = this.getter.getWithKeys(keysMap.keySet());

		for (T result : results) {
			ModelKey modelKey = result.getModelKey();
			ReadPair<T> pair = keysMap.get(modelKey);
			pair.setResult(result);
		}
	}

	@Override
	public String toString() {
		return "ReadTaskImpl [getter=" + this.getter + "]";
	}

}
