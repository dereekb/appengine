package com.dereekb.gae.utilities.misc.keyed.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.utilities.misc.keyed.Keyed;
import com.dereekb.gae.utilities.misc.keyed.exception.NullKeyException;

/**
 * Utility used with {@link Keyed} models.
 * 
 * @author dereekb
 *
 */
public class KeyedUtility {

	/**
	 * Safely merges of keyed models into one. Differs from
	 * {@link #merge(Collection, Collection)} by allowing collections to be
	 * null.
	 * 
	 * @param override
	 *            primary collection, which overrides those in the secondary
	 *            collection. May be {@code null}.
	 * @param input
	 *            secondary collection of input. May be {@code null}.
	 * @return {@link Collection} of merged items. Never {@code null}.
	 */
	public static <K, T extends Keyed<K>> Collection<? extends T> safeMerge(Collection<? extends T> override,
	                                                                        Collection<? extends T> input) {

		if (override != null && input != null) {
			return merge(override, input);
		} else if (input == null) {
			return override;
		} else if (override == null) {
			return input;
		} else {
			return Collections.emptyList();
		}
	}

	/**
	 * Merges two collections of keyed models into one.
	 * 
	 * @param override
	 *            primary collection, which overrides those in the secondary
	 *            collection. Never {@code null}.
	 * @param input
	 *            secondary collection of input. Never {@code null}.
	 * @return {@link Collection} of merged items. Never {@code null}.
	 */
	public static <K, T extends Keyed<K>> Collection<? extends T> merge(Collection<? extends T> override,
	                                                                    Collection<? extends T> input) {
		Map<K, T> primaryMap = toMap(override);
		Map<K, T> secondaryMap = toMap(input);

		secondaryMap.putAll(primaryMap);

		return secondaryMap.values();
	}

	/**
	 * Creates a map using keys from {@link Keyed} values.
	 * 
	 * @param models
	 *            Keyed values. Never {@code null}.
	 * @return {@link Map}. Never {@code null}.
	 */
	public static <K, T extends Keyed<K>> Map<K, T> toMap(Iterable<? extends T> models) {
		Map<K, T> map = new HashMap<>();

		for (T keyedValue : models) {
			K key = keyedValue.getKeyValue();

			if (key == null) {
				throw new NullKeyException();
			} else {
				map.put(key, keyedValue);
			}
		}

		return map;
	}

	/**
	 * Removes all parameters with the same key as the replacement, then adds a
	 * single instance of the replacement.
	 * 
	 * @param inputParameters
	 *            {@link Collection} of parameters. Can be {@code null}.
	 * @param replacement
	 *            Replacement value. Never {@code null}.
	 * @return {@link List} with the values replaced.
	 */
	public static <K, T extends Keyed<K>> List<T> replaceInCollection(Collection<? extends T> inputParameters,
	                                                                  T replacement) {
		List<T> newParameters = new ArrayList<T>();

		if (inputParameters != null) {
			K replacementKey = replacement.getKeyValue();

			for (T parameter : inputParameters) {
				K param = parameter.getKeyValue();

				// Filter out parameters with the same name.
				if (param.equals(replacementKey) == false) {
					newParameters.add(parameter);
				}
			}
		}

		newParameters.add(replacement);
		return newParameters;
	}

}
