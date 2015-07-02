package com.dereekb.gae.test.utility.collection.tree;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.utilities.collections.tree.map.MapTree;

public class MapTreeNodeTest {

	@Test
	public void testAdd() {
		MapTree<String> nodeA = new MapTree<String>("a");
		MapTree<String> nodeB = new MapTree<String>("b");

		nodeA.put(nodeB);
		Assert.assertTrue(nodeA.containsValue("b"));
		Assert.assertTrue(nodeA.getChildrenValues().contains("b"));
	}

	@Test
	public void testWildcard() {
		MapTree<Integer> nodeA = new MapTree<Integer>(1);
		MapTree<Integer> nodeB = new MapTree<Integer>(2);

		nodeA.put(3);
		nodeA.put(4);
		nodeA.put(5);

		Assert.assertFalse(nodeA.contains(nodeB));

		Assert.assertTrue(nodeA.childCount() == 3);
		nodeA.setWildcard(true);
		Assert.assertTrue(nodeA.isWildcard());
		Assert.assertTrue(nodeA.childCount() == 0);

		nodeA.contains(nodeB);
	}

	@Test
	public void testMerge() {
		MapTree<String> nodeA = new MapTree<String>("a");
		MapTree<String> nodeB = new MapTree<String>("b");
		nodeB.put("c");
		nodeB.put("d");
		nodeB.put("e");

		nodeA.merge(nodeB);

		Assert.assertTrue(nodeA.getChildrenValues().size() == 3);
		Assert.assertNotNull(nodeA.getChild("c"));
		Assert.assertNotNull(nodeA.getChild("d"));
		Assert.assertNotNull(nodeA.getChild("e"));

		MapTree<String> nodeC = new MapTree<String>("c");
		nodeC.put("f");
		nodeC.put("g");
		nodeC.put("h");

		MapTree<String> nodeAClone = nodeA.newTree();
		nodeAClone.mergeChild(nodeC);
		Assert.assertTrue(nodeAClone.getChild("c").childCount() == 3);
		Assert.assertNotNull(nodeAClone.getChild("c").getChild("f"));

		nodeA.getChild("c").merge(nodeC);
		Assert.assertTrue(nodeAClone.getChild("c").childCount() == 3);
		Assert.assertNotNull(nodeA.getChild("c").getChild("f"));
	}

	@Test
	public void testContains() {
		MapTree<String> nodeA = new MapTree<String>("a");
		MapTree<String> nodeB = new MapTree<String>("b");
		nodeB.put("c");
		nodeB.put("d");
		nodeB.put("e");

		// Trees should be similar to eachother
		Assert.assertTrue(nodeA.similarTo(nodeA));
		Assert.assertTrue(nodeB.similarTo(nodeB));

		// a does not exist in b.[c,d,e], and vice-versa.
		Assert.assertFalse(nodeA.contains(nodeB));
		Assert.assertFalse(nodeB.contains(nodeA));

		// Node B now contains a child 'a'.
		nodeB.put("a");
		Assert.assertTrue(nodeB.contains(nodeA));

		// Node B does not contain a child 'a.c'.
		nodeA.put("c");
		Assert.assertFalse(nodeB.contains(nodeA));

		// Node B contains all the children of node a, [c]
		Assert.assertTrue(nodeB.containsChildren(nodeA));
		Assert.assertFalse(nodeA.containsChildren(nodeB));
	}

	@Test
	public void testWildcardContains() {
		MapTree<String> nodeA = new MapTree<String>("*");
		nodeA.setWildcard(true);

		MapTree<String> nodeB = new MapTree<String>("b");
		nodeB.put("c");
		nodeB.put("d");
		nodeB.put("e");

		// Trees should be similar to eachother
		Assert.assertTrue(nodeA.similarTo(nodeA));
		Assert.assertTrue(nodeB.similarTo(nodeB));

		// a does not exist in b.[c,d,e], and vice-versa.
		Assert.assertTrue(nodeA.contains(nodeB));
		Assert.assertFalse(nodeB.contains(nodeA));

		// Node B now contains a child 'a'.
		nodeB.put("a");
		Assert.assertFalse(nodeB.contains(nodeA));

		// Node B does not contain a child 'a.c'.
		nodeA.put("c");
		Assert.assertFalse(nodeB.contains(nodeA));

		nodeB.setWildcard(true);
		Assert.assertTrue(nodeA.similarTo(nodeB));
		Assert.assertTrue(nodeB.similarTo(nodeA));
	}

}
