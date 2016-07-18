package com.dereekb.gae.model.crud.pairs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.exception.NullModelKeyException;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.pairs.ResultsPair;

/**
 * Defines a pair to process a read with.
 *
 * @author dereekb
 * @param <T>
 */
public class ReadPair<T extends UniqueModel> extends ResultsPair<ModelKey, T> {

	public ReadPair(ModelKey key) {
		super(key);
	}

	@Override
    public T getObject() {
		return this.object;
	}

	@Override
	public ModelKey getKey() {
		return this.key;
	}

	public static <T extends UniqueModel> List<ModelKey> keysFromPairs(Iterable<ReadPair<T>> pairs) {
		return ResultsPair.getSources(pairs);
	}

	/**
	 * @Deprecated Replaced by pairsKeyMap() function in ResultsPair.
	 * @param pairs
	 * @return
	 */
	@Deprecated
	public static <T extends UniqueModel> Map<ModelKey, ReadPair<T>> keysMapFromPairs(Iterable<ReadPair<T>> pairs) {
		return ResultsPair.pairsKeyMap(pairs);
	}

	public static <T extends UniqueModel> List<ReadPair<T>> createPairsForKeys(Iterable<ModelKey> keys)
	        throws NullModelKeyException {
		List<ReadPair<T>> pairs = new ArrayList<>();
		Set<ModelKey> keysSet = new HashSet<>();

		for (ModelKey key : keys) {
			if (key == null) {
				throw new NullModelKeyException("Encountered null key.");
			}

			if (keysSet.contains(key) == false) {
				ReadPair<T> pair = new ReadPair<>(key);
				pairs.add(pair);
				keysSet.add(key);
			}
		}

		return pairs;
	}

	/**
	 * @deprecated Replaced by better defined static functions in
	 *             {@link ResultsPair}. "Success" here is ambiguious, whereas
	 *             using the two static functions makes their calls more clear.
	 *
	 * @param pairs
	 * @param success
	 * @return
	 */
	@Deprecated
	public static <T extends UniqueModel> List<ModelKey> keysFromPairs(Iterable<ReadPair<T>> pairs,
	                                                                        boolean success) {
		List<ModelKey> keys = new ArrayList<>();

		for (ReadPair<T> pair : pairs) {
			if (pair.hasResult() == success) {
				ModelKey key = pair.getKey();
				keys.add(key);
			}
		}

		return keys;
	}

	/**
	 * @deprecated Replaced by successfulKeysMap() function defined in
	 *             {@link ResultsPair}.
	 *
	 * @param pairs
	 * @return
	 */
	@Deprecated
	public static <T extends UniqueModel> HashMapWithList<Boolean, ModelKey> successMapPairs(Iterable<ReadPair<T>> pairs) {
		return ResultsPair.successfulKeysMap(pairs);
	}

}