package com.dereekb.gae.utilities.data;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.google.common.base.Joiner;

public class StringUtility {

	private static final String DEFAULT_SEPARATOR = ",";

	// MARK: Strins
	/**
	 * Converts a list of values to their {@link #toString()} value.
	 * 
	 * @param values
	 *            {@link List}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public static <T> List<String> toStringList(List<T> values) {
		List<String> stringList = new ArrayList<String>();

		for (T value : values) {
			stringList.add(value.toString());
		}

		return stringList;
	}

	// MARK: Join
	public static String joinValues(Object... objects) throws IllegalArgumentException {
		return joinValues(DEFAULT_SEPARATOR, objects);
	}

	public static String joinValues(Iterable<?> objects) throws IllegalArgumentException {
		return joinValues(DEFAULT_SEPARATOR, objects);
	}

	public static String joinValues(String separator,
	                                Object... objects)
	        throws IllegalArgumentException {
		List<Object> list = ListUtility.toList(objects);
		return joinValues(separator, list);
	}

	public static String joinValues(String separator,
	                                Iterable<?> objects)
	        throws IllegalArgumentException {
		if (separator == null) {
			throw new IllegalArgumentException("Separator cannot be null.");
		}

		Joiner joiner = Joiner.on(separator).skipNulls();
		return joiner.join(objects);
	}

	public static int lengthOfStrings(Iterable<String> strings) {
		int length = 0;

		for (String string : strings) {
			length += string.length();
		}

		return length;
	}

	public static String createStringWithLength(int length) {
		String string = "";

		int r = length % 10;

		if (length > 10) {
			int c = (length - r) / 10;
			string = copyString("XXXXXXXXXX", c);
		}

		if (r > 0) {
			string += copyString("X", r);
		}

		return string;
	}

	public static String copyString(String copy,
	                                int count) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < count; i += 1) {
			builder.append(copy);
		}

		return builder.toString();
	}

}
