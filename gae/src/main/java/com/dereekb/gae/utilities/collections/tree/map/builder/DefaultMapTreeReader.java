package com.dereekb.gae.utilities.collections.tree.map.builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.utilities.collections.tree.map.MapTree;

/**
 * Helper class for reading a "default {@link MapTree}" that has {@link MapTreeValue} for values.
 * 
 * @author dereekb
 *
 */
public class DefaultMapTreeReader {

	public static List<String> getChildrenStringValues(MapTree<MapTreeValue> tree) {
		List<MapTree<MapTreeValue>> children = tree.getChildren();
		List<String> childrenValues = new ArrayList<String>();

		for (MapTree<MapTreeValue> child : children) {
			MapTreeValue value = child.getValue();
			String stringValue = value.getValue();
			childrenValues.add(stringValue);
		}

		return childrenValues;
	}

	/**
	 * Returns the child that corresponds to the string.
	 * 
	 * @param tree Tree to check for the child
	 * @param value String value of the child
	 * @return Returns the child that has the input string value, or null if none exists
	 * @throws NullPointerException Thrown if the tree or input value is null
	 */
	public static MapTree<MapTreeValue> getChild(MapTree<MapTreeValue> tree,
	                                             String value) throws NullPointerException {
		MapTreeValue valueForString = MapTreeValue.withValue(value);
		return tree.getChild(valueForString);
	}

	public static boolean containsChildrenWithValues(MapTree<MapTreeValue> tree,
	                                                 String... values) {

		boolean containsChildren = true;

		List<String> containedValues = DefaultMapTreeReader.getChildrenStringValues(tree);
		Set<String> set = new HashSet<String>(containedValues);

		for (String value : values) {
			containsChildren = set.contains(value);

			if (containsChildren == false) {
				break;
			}
		}

		return containsChildren;
	}

}
