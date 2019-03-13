package com.dereekb.gae.test.utility.collection.tree;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.utilities.collections.tree.map.MapTree;
import com.dereekb.gae.utilities.collections.tree.map.builder.DefaultMapTreeBuilder;
import com.dereekb.gae.utilities.collections.tree.map.builder.DefaultMapTreeReader;
import com.dereekb.gae.utilities.collections.tree.map.builder.MapTreeBuilder;
import com.dereekb.gae.utilities.collections.tree.map.builder.MapTreeValue;
import com.dereekb.gae.utilities.collections.tree.map.delegates.StringMapTreeBuilderDelegate;
import com.dereekb.gae.utilities.collections.tree.map.exceptions.MapTreeBuilderOpenLiteralException;
import com.dereekb.gae.utilities.collections.tree.map.exceptions.MapTreeBuilderOpenParametersException;
import com.dereekb.gae.utilities.collections.tree.map.exceptions.MapTreeBuilderUnexpectedParametersException;

public class MapTreeBuilderTest {

	private MapTreeBuilder<String> builder = new MapTreeBuilder<String>(new StringMapTreeBuilderDelegate());

	@Test
	public void testMapTreeBuilderSimpleBuilding() {
		String testTreeAString = "a.b.c";

		MapTree<String> testTreeARoot = this.builder.decodeTree(testTreeAString);
		assertNotNull(testTreeARoot);
		assertTrue(testTreeARoot.getChildren().size() == 1);

		MapTree<String> testTreeA = testTreeARoot.getChild("a");
		assertTrue(testTreeA.containsValue("b"));
		assertTrue(testTreeA.getChild("b").getChildrenValues().contains("c"));
		assertTrue(testTreeA.getChild("b").getChild("c").getChildren().isEmpty());
	}

	@Test
	public void testMapTreeBuilderArrayBuilding() {
		String testTreeAString = "a.[b,c,d]";

		MapTree<String> testTreeARoot = this.builder.decodeTree(testTreeAString);
		assertNotNull(testTreeARoot);
		assertTrue(testTreeARoot.getChildren().size() == 1);

		MapTree<String> testTreeA = testTreeARoot.getChild("a");
		assertTrue(testTreeA.containsValue("b"));
		assertTrue(testTreeA.containsValue("c"));
		assertTrue(testTreeA.containsValue("d"));

		String testTreeBString = "[b,c,d].e";

		MapTree<String> testTreeBRoot = this.builder.decodeTree(testTreeBString);
		assertNotNull(testTreeBRoot);
		assertTrue(testTreeBRoot.getChildren().size() == 3);

		assertTrue(testTreeBRoot.containsValue("b"));
		assertTrue(testTreeBRoot.containsValue("c"));
		assertTrue(testTreeBRoot.containsValue("d"));

		assertTrue(testTreeBRoot.getChild("b").containsValue("e"));
		assertTrue(testTreeBRoot.getChild("c").containsValue("e"));
		assertTrue(testTreeBRoot.getChild("d").containsValue("e"));

		String testTreeCString = "[a.1.2.3,b,c,d,e,f]";

		MapTree<String> testTreeCRoot = this.builder.decodeTree(testTreeCString);
		assertNotNull(testTreeCRoot);
		assertTrue(testTreeCRoot.getChildren().size() == 6);

		assertTrue(testTreeCRoot.containsValue("a"));
		assertTrue(testTreeCRoot.containsValue("b"));
		assertTrue(testTreeCRoot.containsValue("c"));
		assertTrue(testTreeCRoot.containsValue("d"));
		assertTrue(testTreeCRoot.containsValue("e"));
		assertTrue(testTreeCRoot.containsValue("f"));

		MapTree<String> testTreeCRootChildA = testTreeCRoot.getChild("a");
		assertTrue(testTreeCRootChildA.containsValue("1"));
		assertNotNull(testTreeCRootChildA.getChild("1").getChild("2").getChild("3"));

		assertNull(testTreeCRoot.getChild("b").getChild("1"));

		String testTreeDString = "[a.[b.[c.[d.[e.[f.[1,2,3]]]]]]]";

		MapTree<String> testTreeDRoot = this.builder.decodeTree(testTreeDString);
		assertNotNull(testTreeDRoot);
		assertTrue(testTreeDRoot.getChildren().size() == 1);

		assertTrue(testTreeDRoot.containsValue("a"));
		MapTree<String> fnode = testTreeDRoot.getChild("a").getChild("b").getChild("c").getChild("d").getChild("e")
		        .getChild("f");
		assertNotNull(fnode);
		assertNotNull(fnode.getChild("1"));
		assertNotNull(fnode.getChild("2"));
		assertNotNull(fnode.getChild("3"));
	}

	@Test
	public void testMoreArrays() {

		String input = "[play.[Nightlife, Shopping], explore.[Art,Parks], dine.[Casual,Groceries], stay.[Hotels, Apartments]]";

		DefaultMapTreeBuilder builder = new DefaultMapTreeBuilder();
		MapTree<MapTreeValue> tree = builder.decodeTree(input);

		assertTrue(DefaultMapTreeReader.containsChildrenWithValues(tree, "play", "explore", "dine", "stay"));

		MapTree<MapTreeValue> explore = DefaultMapTreeReader.getChild(tree, "explore");
		assertTrue(explore.childCount() == 2);
		assertNotNull(DefaultMapTreeReader.getChild(explore, "Art"));
		assertNotNull(DefaultMapTreeReader.getChild(explore, "Parks"));

		MapTree<MapTreeValue> play = DefaultMapTreeReader.getChild(tree, "play");
		assertTrue(play.childCount() == 2);
		assertNotNull(DefaultMapTreeReader.getChild(play, "Nightlife"));
		assertNotNull(DefaultMapTreeReader.getChild(play, "Shopping"));
	}

	@Test
	public void testArraysCombinations() {

		String input = "[[a11,a12],[a21,a22],[a31,a32]]";

		DefaultMapTreeBuilder builder = new DefaultMapTreeBuilder();
		MapTree<MapTreeValue> tree = builder.decodeTree(input);

		assertTrue(DefaultMapTreeReader.containsChildrenWithValues(tree, "a11", "a12", "a21", "a22", "a31",
		        "a32"));
	}

	@Test
	public void testStringCombination() {
		List<String> strings = new ArrayList<String>();
		strings.add("a");
		strings.add("[b");
		strings.add("c]");

		String combination = builder.combineTreeStrings(strings);
		assertTrue(combination.equals("[[a],[b],[c]]"));
	}

	@Test
	public void testMapTreeBuildComplexTree() {

		String complexTree = "visit.locations.[place(\"a\", \"b\"),series,destination].[create, read, update, delete, view].all";
		MapTree<String> complexTreeRoot = this.builder.decodeTree(complexTree);
		assertNotNull(complexTree);

		MapTree<String> locationsNode = complexTreeRoot.getChild("visit").getChild("locations");
		assertNotNull(locationsNode);
		assertTrue(locationsNode.getChildren().size() == 3);

		MapTree<String> destinationNode = locationsNode.getChild("destination");
		assertNotNull(destinationNode);
		assertTrue(destinationNode.getChildren().size() == 5);

		MapTree<String> readNode = destinationNode.getChild("read");
		assertNotNull(readNode);

		MapTree<String> spacedReadNode = destinationNode.getChild(" read");
		assertNull(spacedReadNode);
	}

	@Test
	public void testReadingBrokenInputs() {

		DefaultMapTreeBuilder builder = new DefaultMapTreeBuilder();
		String inputA = "[" + "place.place2.place3, " + "explore, " + "dine, " + "stay" + "]";

		assertNotNull(builder.decodeTree(inputA));

		String inputB = "[\nplace,\nexplore,\ndine,\nstay]";
		MapTree<MapTreeValue> inputBRoot = builder.decodeTree(inputB);

		assertTrue(inputBRoot.childCount() == 4);
		assertNotNull(inputBRoot.getChild(MapTreeValue.withValue("place")));
		assertNotNull(inputBRoot.getChild(MapTreeValue.withValue("explore")));
		assertNotNull(inputBRoot.getChild(MapTreeValue.withValue("dine")));
		assertNotNull(inputBRoot.getChild(MapTreeValue.withValue("stay")));
	}

	@Test
	public void testMapTreeBuildParameters() {

		DefaultMapTreeBuilder builder = new DefaultMapTreeBuilder();

		String complexTree = "visit.locations(a,\"b.c.d\",e)";
		MapTree<MapTreeValue> complexTreeRoot = builder.decodeTree(complexTree);
		assertNotNull(complexTree);

		MapTree<MapTreeValue> visitNode = complexTreeRoot.getChildren().get(0);
		assertNotNull(visitNode);

		MapTree<MapTreeValue> locationsNode = visitNode.getChildren().get(0);
		assertNotNull(locationsNode);

		MapTreeValue locationsNodeValue = locationsNode.getValue();
		assertNotNull(locationsNodeValue.getParameters());
		assertTrue(locationsNodeValue.getParameters().size() == 3);
		assertTrue(locationsNodeValue.getParameters().contains("a"));
		assertTrue(locationsNodeValue.getParameters().contains("b.c.d"));
		assertTrue(locationsNodeValue.getParameters().contains("e"));

	}

	@Test
	public void testMapTreeBuilderExceptions() {

		String testOpenLiteralString = "a.b.\"hasNoEnd";

		try {
			this.builder.decodeTree(testOpenLiteralString);
			fail();
		} catch (MapTreeBuilderOpenLiteralException e) {

		}

		String testOpenParameterString = "a.b.c(HasNoParameterEnd";

		try {
			this.builder.decodeTree(testOpenParameterString);
			fail();
		} catch (MapTreeBuilderOpenParametersException e) {

		}

		String testUnexpectedParameterString = "a.b.(ThisDoesn'tGoHere)";

		try {
			this.builder.decodeTree(testUnexpectedParameterString);
			fail();
		} catch (MapTreeBuilderUnexpectedParametersException e) {

		}

	}

}
