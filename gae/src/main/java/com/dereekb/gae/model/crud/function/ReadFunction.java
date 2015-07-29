package com.dereekb.gae.model.crud.function;

import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.crud.pairs.ReadPair;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.function.staged.filter.FilteredStagedFunction;

/**
 * Read Function that reads models from the datastore.
 *
 * Uses a Getter by default
 *
 * @author dereekb
 *
 * @param <T>
 */
@Deprecated
public class ReadFunction<T extends UniqueModel> extends FilteredStagedFunction<T, ReadPair<T>> {

	private final Getter<T> getter;

	public ReadFunction(Getter<T> getter) throws IllegalArgumentException {
		if (getter == null) {
			throw new IllegalArgumentException("Getter cannot be null.");
		}

		this.getter = getter;
	}

	public final Getter<T> getGetter() {
		return this.getter;
	}

	/**
	 * Adds the given key to the set of objects as a new ReadPair.
	 *
	 * @param key
	 */
	public void addIdentifier(ModelKey key) {
		ReadPair<T> readPair = new ReadPair<T>(key);
		this.addObject(readPair);
	}

	/**
	 * Adds the collection of given keys to the set of objects as ReadPair(s).
	 *
	 * @param key
	 */
	public void addIdentifiers(Iterable<ModelKey> keys) {
		for (ModelKey key : keys) {
			this.addIdentifier(key);
		}
	}

	@Override
	protected void doFunction() {
		Iterable<ReadPair<T>> pairs = this.getWorkingObjects();
		this.read(pairs);
	}

	protected void read(Iterable<ReadPair<T>> pairs) {
		Map<ModelKey, ReadPair<T>> keysMap = ReadPair.pairsKeyMap(pairs);

		List<T> results = this.readModels(keysMap.keySet());

		for (T result : results) {
			ModelKey modelKey = result.getModelKey();
			ReadPair<T> pair = keysMap.get(modelKey);
			pair.setResult(result);
		}
	}

	protected List<T> readModels(Iterable<ModelKey> keys) {
		List<T> models = this.getter.getWithKeys(keys);
		return models;
	}

}
