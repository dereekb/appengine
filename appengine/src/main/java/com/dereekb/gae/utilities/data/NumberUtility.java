package com.dereekb.gae.utilities.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NumberUtility {

	public static List<Integer> integersFromString(String string) {
		Collection<String> strings = StringUtility.separateValues(string);
		return integersFromStrings(strings);
	}

	public static List<Integer> integersFromString(String string, String regex) {
		Collection<String> strings = StringUtility.separateValues(string, regex);
		return integersFromStrings(strings);
	}

	public static List<Integer> integersFromStrings(Iterable<String> strings) {
		List<Integer> integers = new ArrayList<Integer>();

		for (String string : strings) {
			Integer integer = new Integer(string);
			integers.add(integer);
		}

		return integers;
	}

}
