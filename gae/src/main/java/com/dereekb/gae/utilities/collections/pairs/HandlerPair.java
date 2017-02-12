package com.dereekb.gae.utilities.collections.pairs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.utilities.misc.keyed.Keyed;

/**
 * Basic tuple.
 *
 * @author dereekb
 *
 * @param <L>
 *            Key value
 * @param <R>
 *            Object value
 */
public class HandlerPair<L, R>
        implements Keyed<L> {

	protected L key;
	protected R object;

	public HandlerPair(L key, R object) {
		this.key = key;
		this.object = object;
	}

	public L getKey() {
		return this.key;
	}

	public R getObject() {
		return this.object;
	}

	// MARK: AlwaysKeyed
	@Override
	public L keyValue() {
		return this.getKey();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.key == null) ? 0 : this.key.hashCode());
		result = prime * result + ((this.object == null) ? 0 : this.object.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "HandlerPair [key=" + this.key + ", object=" + this.object + "]";
	}

	public static <L, R> List<L> getKeys(Iterable<? extends HandlerPair<L, R>> pairs) {
		List<L> keys = new ArrayList<L>();

		for (HandlerPair<L, R> pair : pairs) {
			L key = pair.getKey();

			if (key != null) {
				keys.add(key);
			}
		}

		return keys;
	}

	public static <L, R> List<R> getObjects(Iterable<? extends HandlerPair<L, R>> pairs) {
		List<R> results = new ArrayList<R>();

		for (HandlerPair<L, R> pair : pairs) {
			R result = pair.getObject();

			if (result != null) {
				results.add(result);
			}
		}

		return results;
	}

	public static <L, R> Integer countNullObjects(Iterable<? extends HandlerPair<L, R>> pairs) {
		Integer nullCount = 0;

		for (HandlerPair<L, R> pair : pairs) {
			R result = pair.getObject();

			if (result == null) {
				nullCount += 1;
			}
		}

		return nullCount;
	}

	public static <L, R, T extends HandlerPair<L, R>> Map<L, T> getObjectsMap(Iterable<T> pairs) {
		Map<L, T> map = new HashMap<L, T>();

		for (T pair : pairs) {
			L key = pair.getKey();
			map.put(key, pair);
		}

		return map;
	}

	public static <L, T extends HandlerPair<L, ?>> List<T> filterRepeatingKeys(Iterable<? extends T> pairs) {
		Set<L> keysUsed = new HashSet<L>();

		List<T> filtered = new ArrayList<T>();

		for (T pair : pairs) {
			L key = pair.getKey();

			if (keysUsed.contains(key) == false) {
				filtered.add(pair);
				keysUsed.add(key);
			}
		}

		return filtered;
	}

}
