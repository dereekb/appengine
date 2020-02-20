package com.dereekb.gae.utilities.misc.keyed.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.data.ValueUtility;
import com.dereekb.gae.utilities.misc.keyed.IndexCoded;
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
			K key = keyedValue.keyValue();

			if (key == null) {
				throw new NullKeyException();
			} else {
				map.put(key, keyedValue);
			}
		}

		return map;
	}

	/**
	 * Creates a map using keys from {@link Keyed} values.
	 *
	 * @param models
	 *            Keyed values. Never {@code null}.
	 * @return {@link Map}. Never {@code null}.
	 */
	public static <K, T extends Keyed<K>> HashMapWithList<K, T> toHashMapWithList(Iterable<? extends T> models) {
		HashMapWithList<K, T> map = new HashMapWithList<>();

		for (T keyedValue : models) {
			K key = keyedValue.keyValue();

			if (key == null) {
				throw new NullKeyException();
			} else {
				map.add(key, keyedValue);
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
			K replacementKey = replacement.keyValue();

			for (T parameter : inputParameters) {
				K param = parameter.keyValue();

				// Filter out parameters with the same name.
				if (param.equals(replacementKey) == false) {
					newParameters.add(parameter);
				}
			}
		}

		newParameters.add(replacement);
		return newParameters;
	}

	/**
	 * Reads the code from a {@link IndexCoded} value, and defaults to another
	 * value if {@code null}.
	 *
	 * @param coded
	 *            {@link IndexCoded}. May be {@code null}.
	 * @param defaultCode
	 *            {@link Integer} default. May be {@code null}.
	 * @return {@link Integer}. May be {@code null}.
	 */
	public static <T extends IndexCoded> Integer getCode(IndexCoded coded,
	                                                     Integer defaultCode) {
		Integer code = getCode(coded);
		return ValueUtility.defaultTo(code, defaultCode);
	}

	/**
	 * Reads the code from a {@link IndexCoded} value.
	 *
	 * @param coded
	 *            {@link IndexCoded}. May be {@code null}.
	 * @return {@link Integer}. May be {@code null}.
	 */
	public static <T extends IndexCoded> Integer getCode(IndexCoded coded) {
		Integer code = null;

		if (coded != null) {
			code = coded.getCode();
		}

		return code;
	}

	/**
	 * Makes a map using the input {@link Class} enum type.
	 *
	 * @param values
	 *            {@link Iterable}. Never {@code null}.
	 * @return {@link Map}. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             thrown if the input type is not an enum.
	 */
	public static <T extends IndexCoded> Map<Integer, ? extends T> makeCodedMap(Class<T> codedType)
	        throws IllegalArgumentException {
		List<T> enumValues = readEnumValues(codedType);
		return makeCodedMap(enumValues);
	}

	/**
	 * Makes a map using the input {@link IndexCoded} values.
	 *
	 * @param values
	 *            {@link Iterable}. Never {@code null}.
	 * @return {@link Map}. Never {@code null}.
	 */
	public static <T extends IndexCoded> Map<Integer, ? extends T> makeCodedMap(Iterable<? extends T> values) {
		Map<Integer, T> map = new HashMap<Integer, T>();

		for (T value : values) {
			map.put(value.getCode(), value);
		}

		return map;
	}

	/**
	 * Makes a list of codes using the input {@link IndexCoded} values.
	 *
	 * @param values
	 *            {@link Iterable}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public static <T extends IndexCoded> List<Integer> makeCodeList(Iterable<? extends T> values) {
		List<Integer> codes = new ArrayList<Integer>();

		for (T value : values) {
			codes.add(value.getCode());
		}

		return codes;
	}

	/**
	 * Makes a list of codes using the input {@link IndexCoded} values and enum
	 * class/type.
	 *
	 * @param codes
	 *            {@link Iterable} of codes to map to values.
	 * @param codedType
	 *            {@link Class} of the enum. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             thrown if the input type is not an enum.
	 */
	public static <T extends IndexCoded> List<T> makeValuesListFromCodes(Iterable<Integer> codes,
	                                                                     Class<T> codedType)
	        throws IllegalArgumentException {
		List<T> enumValues = readEnumValues(codedType);
		Map<Integer, ? extends T> map = KeyedUtility.makeCodedMap(enumValues);
		return makeValuesListFromCodes(codes, map);
	}

	/**
	 * Makes a list of codes using the input {@link Map} of codes to values.
	 * Values that do not exist in the map or are null are ignored/filtered out.
	 *
	 * @param codes
	 *            {@link Iterable} of codes to map to values.
	 * @param map
	 *            {@link Map}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public static <T extends IndexCoded> List<T> makeValuesListFromCodes(Iterable<Integer> codes,
	                                                                     Map<Integer, ? extends T> map) {
		List<T> values = new ArrayList<T>();

		for (Integer code : codes) {
			T value = map.get(code);

			if (value != null) {
				values.add(value);
			}
		}

		return values;
	}

	/**
	 * Reads the list of coded values from an enum.
	 *
	 * @param codedType
	 *            {@link Class}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             thrown if the input type is not an enum.
	 */
	public static <T extends IndexCoded> List<T> readEnumValues(Class<T> codedType) throws IllegalArgumentException {
		T[] constants = codedType.getEnumConstants();

		if (codedType.isEnum() == false) {
			throw new IllegalArgumentException("Is not an emum.");
		}

		List<T> enumValues = ListUtility.toList(constants);
		return enumValues;
	}

}
