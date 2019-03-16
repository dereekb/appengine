package com.dereekb.gae.test.utility.collection.tree;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.collections.tree.map.MapTree;

public class MapTreeNodeTest {

	@Test
	public void testAdd() {
		MapTree<String> nodeA = new MapTree<String>("a");
		MapTree<String> nodeB = new MapTree<String>("b");

		nodeA.put(nodeB);
		assertTrue(nodeA.containsValue("b"));
		assertTrue(nodeA.getChildrenValues().contains("b"));
	}

	@Test
	public void testWildcard() {
		MapTree<Integer> nodeA = new MapTree<Integer>(1);
		MapTree<Integer> nodeB = new MapTree<Integer>(2);

		nodeA.put(3);
		nodeA.put(4);
		nodeA.put(5);

		assertFalse(nodeA.contains(nodeB));

		assertTrue(nodeA.childCount() == 3);
		nodeA.setWildcard(true);
		assertTrue(nodeA.isWildcard());
		assertTrue(nodeA.childCount() == 0);

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

		assertTrue(nodeA.getChildrenValues().size() == 3);
		assertNotNull(nodeA.getChild("c"));
		assertNotNull(nodeA.getChild("d"));
		assertNotNull(nodeA.getChild("e"));

		MapTree<String> nodeC = new MapTree<String>("c");
		nodeC.put("f");
		nodeC.put("g");
		nodeC.put("h");

		MapTree<String> nodeAClone = nodeA.newTree();
		nodeAClone.mergeChild(nodeC);
		assertTrue(nodeAClone.getChild("c").childCount() == 3);
		assertNotNull(nodeAClone.getChild("c").getChild("f"));

		nodeA.getChild("c").merge(nodeC);
		assertTrue(nodeAClone.getChild("c").childCount() == 3);
		assertNotNull(nodeA.getChild("c").getChild("f"));
	}

	@Test
	public void testContains() {
		MapTree<String> nodeA = new MapTree<String>("a");
		MapTree<String> nodeB = new MapTree<String>("b");
		nodeB.put("c");
		nodeB.put("d");
		nodeB.put("e");

		// Trees should be similar to eachother
		assertTrue(nodeA.similarTo(nodeA));
		assertTrue(nodeB.similarTo(nodeB));

		// a does not exist in b.[c,d,e], and vice-versa.
		assertFalse(nodeA.contains(nodeB));
		assertFalse(nodeB.contains(nodeA));

		// Node B now contains a child 'a'.
		nodeB.put("a");
		assertTrue(nodeB.contains(nodeA));

		// Node B does not contain a child 'a.c'.
		nodeA.put("c");
		assertFalse(nodeB.contains(nodeA));

		// Node B contains all the children of node a, [c]
		assertTrue(nodeB.containsChildren(nodeA));
		assertFalse(nodeA.containsChildren(nodeB));
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
		assertTrue(nodeA.similarTo(nodeA));
		assertTrue(nodeB.similarTo(nodeB));

		// a does not exist in b.[c,d,e], and vice-versa.
		assertTrue(nodeA.contains(nodeB));
		assertFalse(nodeB.contains(nodeA));

		// Node B now contains a child 'a'.
		nodeB.put("a");
		assertFalse(nodeB.contains(nodeA));

		// Node B does not contain a child 'a.c'.
		nodeA.put("c");
		assertFalse(nodeB.contains(nodeA));

		nodeB.setWildcard(true);
		assertTrue(nodeA.similarTo(nodeB));
		assertTrue(nodeB.similarTo(nodeA));
	}

}
