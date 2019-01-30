package com.dereekb.gae.test.utility.collection.tree;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

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
		Assert.assertNotNull(testTreeARoot);
		Assert.assertTrue(testTreeARoot.getChildren().size() == 1);

		MapTree<String> testTreeA = testTreeARoot.getChild("a");
		Assert.assertTrue(testTreeA.containsValue("b"));
		Assert.assertTrue(testTreeA.getChild("b").getChildrenValues().contains("c"));
		Assert.assertTrue(testTreeA.getChild("b").getChild("c").getChildren().isEmpty());
	}

	@Test
	public void testMapTreeBuilderArrayBuilding() {
		String testTreeAString = "a.[b,c,d]";

		MapTree<String> testTreeARoot = this.builder.decodeTree(testTreeAString);
		Assert.assertNotNull(testTreeARoot);
		Assert.assertTrue(testTreeARoot.getChildren().size() == 1);

		MapTree<String> testTreeA = testTreeARoot.getChild("a");
		Assert.assertTrue(testTreeA.containsValue("b"));
		Assert.assertTrue(testTreeA.containsValue("c"));
		Assert.assertTrue(testTreeA.containsValue("d"));

		String testTreeBString = "[b,c,d].e";

		MapTree<String> testTreeBRoot = this.builder.decodeTree(testTreeBString);
		Assert.assertNotNull(testTreeBRoot);
		Assert.assertTrue(testTreeBRoot.getChildren().size() == 3);

		Assert.assertTrue(testTreeBRoot.containsValue("b"));
		Assert.assertTrue(testTreeBRoot.containsValue("c"));
		Assert.assertTrue(testTreeBRoot.containsValue("d"));

		Assert.assertTrue(testTreeBRoot.getChild("b").containsValue("e"));
		Assert.assertTrue(testTreeBRoot.getChild("c").containsValue("e"));
		Assert.assertTrue(testTreeBRoot.getChild("d").containsValue("e"));

		String testTreeCString = "[a.1.2.3,b,c,d,e,f]";

		MapTree<String> testTreeCRoot = this.builder.decodeTree(testTreeCString);
		Assert.assertNotNull(testTreeCRoot);
		Assert.assertTrue(testTreeCRoot.getChildren().size() == 6);

		Assert.assertTrue(testTreeCRoot.containsValue("a"));
		Assert.assertTrue(testTreeCRoot.containsValue("b"));
		Assert.assertTrue(testTreeCRoot.containsValue("c"));
		Assert.assertTrue(testTreeCRoot.containsValue("d"));
		Assert.assertTrue(testTreeCRoot.containsValue("e"));
		Assert.assertTrue(testTreeCRoot.containsValue("f"));

		MapTree<String> testTreeCRootChildA = testTreeCRoot.getChild("a");
		Assert.assertTrue(testTreeCRootChildA.containsValue("1"));
		Assert.assertNotNull(testTreeCRootChildA.getChild("1").getChild("2").getChild("3"));

		Assert.assertNull(testTreeCRoot.getChild("b").getChild("1"));

		String testTreeDString = "[a.[b.[c.[d.[e.[f.[1,2,3]]]]]]]";

		MapTree<String> testTreeDRoot = this.builder.decodeTree(testTreeDString);
		Assert.assertNotNull(testTreeDRoot);
		Assert.assertTrue(testTreeDRoot.getChildren().size() == 1);

		Assert.assertTrue(testTreeDRoot.containsValue("a"));
		MapTree<String> fnode = testTreeDRoot.getChild("a").getChild("b").getChild("c").getChild("d").getChild("e")
		        .getChild("f");
		Assert.assertNotNull(fnode);
		Assert.assertNotNull(fnode.getChild("1"));
		Assert.assertNotNull(fnode.getChild("2"));
		Assert.assertNotNull(fnode.getChild("3"));
	}

	@Test
	public void testMoreArrays() {

		String input = "[play.[Nightlife, Shopping], explore.[Art,Parks], dine.[Casual,Groceries], stay.[Hotels, Apartments]]";

		DefaultMapTreeBuilder builder = new DefaultMapTreeBuilder();
		MapTree<MapTreeValue> tree = builder.decodeTree(input);

		Assert.assertTrue(DefaultMapTreeReader.containsChildrenWithValues(tree, "play", "explore", "dine", "stay"));

		MapTree<MapTreeValue> explore = DefaultMapTreeReader.getChild(tree, "explore");
		Assert.assertTrue(explore.childCount() == 2);
		Assert.assertNotNull(DefaultMapTreeReader.getChild(explore, "Art"));
		Assert.assertNotNull(DefaultMapTreeReader.getChild(explore, "Parks"));

		MapTree<MapTreeValue> play = DefaultMapTreeReader.getChild(tree, "play");
		Assert.assertTrue(play.childCount() == 2);
		Assert.assertNotNull(DefaultMapTreeReader.getChild(play, "Nightlife"));
		Assert.assertNotNull(DefaultMapTreeReader.getChild(play, "Shopping"));
	}

	@Test
	public void testArraysCombinations() {

		String input = "[[a11,a12],[a21,a22],[a31,a32]]";

		DefaultMapTreeBuilder builder = new DefaultMapTreeBuilder();
		MapTree<MapTreeValue> tree = builder.decodeTree(input);

		Assert.assertTrue(DefaultMapTreeReader.containsChildrenWithValues(tree, "a11", "a12", "a21", "a22", "a31",
		        "a32"));
	}

	@Test
	public void testStringCombination() {
		List<String> strings = new ArrayList<String>();
		strings.add("a");
		strings.add("[b");
		strings.add("c]");

		String combination = builder.combineTreeStrings(strings);
		Assert.assertTrue(combination.equals("[[a],[b],[c]]"));
	}

	@Test
	public void testMapTreeBuildComplexTree() {

		String complexTree = "visit.locations.[place(\"a\", \"b\"),series,destination].[create, read, update, delete, view].all";
		MapTree<String> complexTreeRoot = this.builder.decodeTree(complexTree);
		Assert.assertNotNull(complexTree);

		MapTree<String> locationsNode = complexTreeRoot.getChild("visit").getChild("locations");
		Assert.assertNotNull(locationsNode);
		Assert.assertTrue(locationsNode.getChildren().size() == 3);

		MapTree<String> destinationNode = locationsNode.getChild("destination");
		Assert.assertNotNull(destinationNode);
		Assert.assertTrue(destinationNode.getChildren().size() == 5);

		MapTree<String> readNode = destinationNode.getChild("read");
		Assert.assertNotNull(readNode);

		MapTree<String> spacedReadNode = destinationNode.getChild(" read");
		Assert.assertNull(spacedReadNode);
	}

	@Test
	public void testReadingBrokenInputs() {

		DefaultMapTreeBuilder builder = new DefaultMapTreeBuilder();
		String inputA = "[" + "place.place2.place3, " + "explore, " + "dine, " + "stay" + "]";

		Assert.assertNotNull(builder.decodeTree(inputA));

		String inputB = "[\nplace,\nexplore,\ndine,\nstay]";
		MapTree<MapTreeValue> inputBRoot = builder.decodeTree(inputB);

		Assert.assertTrue(inputBRoot.childCount() == 4);
		Assert.assertNotNull(inputBRoot.getChild(MapTreeValue.withValue("place")));
		Assert.assertNotNull(inputBRoot.getChild(MapTreeValue.withValue("explore")));
		Assert.assertNotNull(inputBRoot.getChild(MapTreeValue.withValue("dine")));
		Assert.assertNotNull(inputBRoot.getChild(MapTreeValue.withValue("stay")));
	}

	@Test
	public void testMapTreeBuildParameters() {

		DefaultMapTreeBuilder builder = new DefaultMapTreeBuilder();

		String complexTree = "visit.locations(a,\"b.c.d\",e)";
		MapTree<MapTreeValue> complexTreeRoot = builder.decodeTree(complexTree);
		Assert.assertNotNull(complexTree);

		MapTree<MapTreeValue> visitNode = complexTreeRoot.getChildren().get(0);
		Assert.assertNotNull(visitNode);

		MapTree<MapTreeValue> locationsNode = visitNode.getChildren().get(0);
		Assert.assertNotNull(locationsNode);

		MapTreeValue locationsNodeValue = locationsNode.getValue();
		Assert.assertNotNull(locationsNodeValue.getParameters());
		Assert.assertTrue(locationsNodeValue.getParameters().size() == 3);
		Assert.assertTrue(locationsNodeValue.getParameters().contains("a"));
		Assert.assertTrue(locationsNodeValue.getParameters().contains("b.c.d"));
		Assert.assertTrue(locationsNodeValue.getParameters().contains("e"));

	}

	@Test
	public void testMapTreeBuilderExceptions() {

		String testOpenLiteralString = "a.b.\"hasNoEnd";

		try {
			this.builder.decodeTree(testOpenLiteralString);
			Assert.fail();
		} catch (MapTreeBuilderOpenLiteralException e) {

		}

		String testOpenParameterString = "a.b.c(HasNoParameterEnd";

		try {
			this.builder.decodeTree(testOpenParameterString);
			Assert.fail();
		} catch (MapTreeBuilderOpenParametersException e) {

		}

		String testUnexpectedParameterString = "a.b.(ThisDoesn'tGoHere)";

		try {
			this.builder.decodeTree(testUnexpectedParameterString);
			Assert.fail();
		} catch (MapTreeBuilderUnexpectedParametersException e) {

		}

	}

}
