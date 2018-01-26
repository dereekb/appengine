package com.dereekb.gae.utilities.data;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.google.common.base.Joiner;

public class StringUtility {

	public static final String DEFAULT_SEPARATOR = ",";

	// MARK: Strings
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

	// Split
	public static String splitAndSnip(String string,
	                                  String splitter) {
		return splitAndSnip(string, splitter, 1);
	}

	public static String splitAndSnip(String string,
	                                  String splitter,
	                                  int cut) {
		String joiner = splitter;

		switch (joiner) {
			case ".":
				splitter = "\\.";
				break;
			default:
				break;
		}

		return splitAndSnip(string, splitter, joiner, cut);
	}

	public static String splitAndSnip(String string,
	                                  String splitter,
	                                  String joiner,
	                                  int cut) {
		List<String> split = separateValues(splitter, string);

		if (cut >= split.size()) {
			return "";
		} else {
			List<String> pieces = split.subList(0, split.size() - cut);
			return joinValues(joiner, pieces);
		}
	}

	/**
	 * Separates the values within the input string.
	 *
	 * @param value
	 * @return
	 */
	public static List<String> separateValues(String value) {
		return separateValues(DEFAULT_SEPARATOR, value);
	}

	/**
	 * Separates the values within the input string.
	 *
	 * @param value
	 * @return {@link List}. Never {@code null}.
	 */
	public static List<String> separateValues(String regex,
	                                          String value) {
		if (regex == null) {
			throw new IllegalArgumentException();
		}

		if (!StringUtility.isEmptyString(value)) {
			return ListUtility.toList(value.split(regex));
		} else {
			return Collections.emptyList();
		}
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

	public static boolean isEmptyString(String value) {
		return (value == null || value.isEmpty());
	}

	public static String tryToString(Object value) {
		if (value != null) {
			return value.toString();
		} else {
			return null;
		}
	}

	public static String generateRandomHex(int numchars) {
		Random r = new Random();
		return generateRandomHexString(numchars, r);
	}

	public static String generateSecureRandomHexString(int numchars) {
		SecureRandom r = new SecureRandom();
		return generateRandomHexString(numchars, r);
	}

	public static String generateRandomHexString(int numchars,
	                                             Random r) {
		StringBuffer sb = new StringBuffer();
		while (sb.length() < numchars) {
			sb.append(Integer.toHexString(r.nextInt()));
		}

		return sb.toString().substring(0, numchars);
	}

	public static String safeFirstLetterLowerCase(String string) {
		if (isEmptyString(string)) {
			return string;
		}

		return firstLetterLowerCase(string);
	}

	public static String firstLetterLowerCase(String string) {
		char c[] = string.toCharArray();
		c[0] = Character.toLowerCase(c[0]);
		return new String(c);
	}

	/**
	 * Makes the string start with the prefix if it doesn't already start with
	 * it.
	 *
	 * @param prefix
	 *            Prefix
	 * @param string
	 *            String
	 * @return {@link
	 */
	public static String startWith(String prefix,
	                               String string) {
		if (string.startsWith(prefix)) {
			return string;
		} else {
			return prefix + string;
		}
	}

}
