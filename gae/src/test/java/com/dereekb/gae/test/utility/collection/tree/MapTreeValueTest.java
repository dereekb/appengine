package com.dereekb.gae.test.utility.collection.tree;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.dereekb.gae.utilities.collections.tree.map.MapTree;
import com.dereekb.gae.utilities.collections.tree.map.builder.MapTreeValue;

public class MapTreeValueTest {

	@Test
	public void testWithMapTree() {
		MapTreeValue a = new MapTreeValue("a");
		MapTreeValue b = new MapTreeValue("b");

		MapTreeValue b2 = new MapTreeValue("b");

		MapTree<MapTreeValue> tree = new MapTree<MapTreeValue>();

		tree.put(a);
		tree.put(b);
		Assert.assertTrue(tree.childCount() == 2);

		MapTree<MapTreeValue> replacedB = tree.put(b2);
		Assert.assertTrue(tree.childCount() == 2);
		Assert.assertTrue(replacedB.getValue().equals(b));

		Assert.assertNotNull(tree.getChild(MapTreeValue.withValue("a")));
		Assert.assertNotNull(tree.getChild(MapTreeValue.withValue("b")));
	}

	@Test
	public void testToString() {
		MapTreeValue value = new MapTreeValue("value");

		String valueString = value.toString();
		Assert.assertTrue(valueString.equals("value()"));
	}

	@Test
	public void testParametersToString() {
		List<String> parameters = new ArrayList<String>();
		parameters.add("a");
		parameters.add("b");
		parameters.add("c");

		MapTreeValue value = new MapTreeValue("value", parameters);

		String valueString = value.toString();
		Assert.assertTrue(valueString.equals("value(\"a\",\"b\",\"c\")"));
	}

	@Test
	@Ignore
	public void testParametersToStringTime() {
		List<String> parameters = new ArrayList<String>();

		for (Integer i = 0; i < 1000; i += 1) {
			parameters.add(i.toString());
		}

		MapTreeValue value = new MapTreeValue("value", parameters);

		for (int i = 0; i < 10000; i += 1) {
			value.toString();
		}
	}

}
