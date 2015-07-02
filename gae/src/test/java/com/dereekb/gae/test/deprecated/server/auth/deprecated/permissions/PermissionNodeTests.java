package com.thevisitcompany.gae.test.depr.server.auth.deprecated.permissions;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.thevisitcompany.gae.server.auth.deprecated.permissions.components.PermissionNode;
import com.thevisitcompany.gae.server.auth.deprecated.permissions.components.PermissionNodeBuilder;

@RunWith(JUnit4.class)
public class PermissionNodeTests {

	@Test
	public void testNodes() {

		PermissionNode baseNode = new PermissionNode("base");
		String baseNodeString = baseNode.toString();

		assertNotNull(baseNodeString);
		assertTrue(baseNodeString.equals("base"));
	}

	@Test
	public void testNodeParentString() {

		PermissionNode baseNode = new PermissionNode("base");
		String baseNodeString = baseNode.toString();

		assertNotNull(baseNodeString);
		assertTrue(baseNodeString.equals("base"));

		PermissionNode subNodeA = new PermissionNode("subNodeA", baseNode);
		String subNodeAString = subNodeA.toString();

		assertNotNull(subNodeAString);
		assertTrue(subNodeAString.equals("base.subNodeA"));
		
		PermissionNode subNodeB = new PermissionNode("subNodeB", subNodeA);
		String subNodeBString = subNodeB.toString();

		assertNotNull(subNodeBString);
		assertTrue(subNodeBString.equals("base.subNodeA.subNodeB"));
	}

	@Test
	public void testNodeBuilder() {

		PermissionNodeBuilder nodeBuilder = new PermissionNodeBuilder("base");
		nodeBuilder.addToRoot("more-base");
		
		PermissionNode moreBaseNode = nodeBuilder.build();
		assertNotNull(moreBaseNode);
		assertTrue(moreBaseNode.toString().equals("more-base"));
		
		nodeBuilder.addNodes("base-b", "base-c");
		PermissionNode multipleBaseNode = nodeBuilder.build();
		assertTrue(multipleBaseNode.size() == 4);
		
		Set<String> multipleBaseNodeKeys = multipleBaseNode.allStrings();
		assertTrue(multipleBaseNodeKeys.size() == 4);
		assertTrue(multipleBaseNodeKeys.contains("more-base"));
		assertTrue(multipleBaseNodeKeys.contains("more-base.base"));
		assertTrue(multipleBaseNodeKeys.contains("more-base.base-b"));
		assertTrue(multipleBaseNodeKeys.contains("more-base.base-c"));
		
		nodeBuilder.clearSubNodes();
		PermissionNode clearedNode = nodeBuilder.build();
		assertTrue(clearedNode.allStrings().size() == 1);

		nodeBuilder.addNodes(multipleBaseNode);
		PermissionNode multipleBaseNodeB = nodeBuilder.build();
		final int expectedMultipleBaseNodeBSize = 5;
		assertTrue(multipleBaseNodeB.size() == expectedMultipleBaseNodeBSize);
		
		//These additions should end up only overriding but not being added to the set.
		nodeBuilder.addNodes(multipleBaseNodeB);
		nodeBuilder.addNodes(multipleBaseNodeB);
		
		PermissionNode multipleBaseNodeC = nodeBuilder.build();
		final int expectedMultipleBaseNodeCSize = expectedMultipleBaseNodeBSize;
		assertTrue(multipleBaseNodeC.size() == expectedMultipleBaseNodeCSize);
		
		nodeBuilder.rename("rename");
		PermissionNode renamedNode = nodeBuilder.build();
		assertTrue(renamedNode.toString() == "rename");
		int size = renamedNode.size();
		assertTrue(size == expectedMultipleBaseNodeCSize);
	}

	@Test
	public void testPermissionBuilding() {
		
		//Create following permissions:
		/*
		 * visit.login
		 * visit.login.login
		 * visit.login.edit
		 * 
		 * visit.models.location.place.create								//Can create new models.
		 * 
		 * visit.models.location.place.read									//Can read models
		 * visit.models.location.place.read.visibility.public			//Can read public places.
		 * visit.models.location.place.read.visibility.private		//Can read private places.
		 * visit.models.location.place.read.ownership.unowned //Can read any unowned place.
		 * visit.models.location.place.read.ownership.owned		//Can read any owned place.
		 * visit.models.location.place.read.ownership.self			//Can read places that are owned by self.
		 * 
		 * visit.models.location.place.edit.visiblility...
		 * visit.models.location.place.edit.ownership...
		 * 
		 * visit.models.location.place.delete.force
		 * visit.models.location.place.delete.published...
		 * visit.models.location.place.delete.ownership...
		 * 
		 * visit.model.location.place.search.datastore.visibility.public
		 * visit.model.location.place.search.datastore.visibility.private
		 * visit.model.location.place.search.datastore.published.published
		 * visit.model.location.place.search.datastore.published.unpublished
		 * visit.model.location.place.search.documents...
		 * 
		 * visit.model.location.destination...
		 * visit.model.location.series...
		 * 
		 */


		PermissionNodeBuilder visibilityBuilder = new PermissionNodeBuilder("visibility");
		visibilityBuilder.addNodes("public", "private");
		PermissionNode visibilityNode = visibilityBuilder.build();
		
		PermissionNodeBuilder ownershipBuilder = new PermissionNodeBuilder("ownership");
		ownershipBuilder.addNodes("unowned", "self", "owned");
		PermissionNode ownershipNode = ownershipBuilder.build();

		PermissionNodeBuilder publishedBuilder = new PermissionNodeBuilder("published");
		publishedBuilder.addNodes("public", "private");
		PermissionNode publishedNode = publishedBuilder.build();
		
		PermissionNode createNode = new PermissionNode("create");

		PermissionNodeBuilder readNodeBuilder = new PermissionNodeBuilder("read");
		readNodeBuilder.addNodes(visibilityNode);
		readNodeBuilder.addNodes(ownershipNode);
		PermissionNode readNode = readNodeBuilder.build();

		PermissionNodeBuilder editNodeBuilder = new PermissionNodeBuilder("edit");
		editNodeBuilder.addNodes(visibilityNode);
		editNodeBuilder.addNodes(ownershipNode);
		PermissionNode editNode = editNodeBuilder.build();

		PermissionNodeBuilder deleteNodeBuilder = new PermissionNodeBuilder("delete");
		deleteNodeBuilder.addNodes("force");
		deleteNodeBuilder.addNodes(publishedNode);
		deleteNodeBuilder.addNodes(ownershipNode);
		PermissionNode deleteNode = deleteNodeBuilder.build();

		PermissionNodeBuilder searchTypeNodeBuilder = new PermissionNodeBuilder("documents");
		searchTypeNodeBuilder.addNodes(publishedNode, visibilityNode);
		PermissionNode searchDocumentsNode = searchTypeNodeBuilder.build();
		
		searchTypeNodeBuilder.rename("datastore");
		PermissionNode searchDatastoreNode = searchTypeNodeBuilder.build();
		
		PermissionNodeBuilder searchNodeBuilder = new PermissionNodeBuilder("search");
		searchNodeBuilder.addNodes(searchDocumentsNode, searchDatastoreNode);
		
		PermissionNode searchNode = searchNodeBuilder.build();
		
		PermissionNodeBuilder modelBuilder = new PermissionNodeBuilder("model");
		modelBuilder.addNodes(createNode, readNode, editNode, deleteNode, searchNode);

		PermissionNodeBuilder locationModelBuilder = modelBuilder.cloneBuilder();
		locationModelBuilder.rename("place");
		locationModelBuilder.addToRoot("location");
		locationModelBuilder.addToRoot("model");
		locationModelBuilder.addToRoot("visit");
		
		PermissionNode visitPlacePermissions = locationModelBuilder.build();
		Set<String> placePermissionSet = visitPlacePermissions.allStrings();
		assertNotNull(visitPlacePermissions);
		assertFalse(placePermissionSet.isEmpty());

		assertTrue(visitPlacePermissions.containsPermission("visit.model.location.place.delete.force"));
	}

	@Test
	public void testPermissionChecking() {

		PermissionNodeBuilder nodeBuilder = new PermissionNodeBuilder("*");
		nodeBuilder.addToRoot("d");
		nodeBuilder.addToRoot("c");
		nodeBuilder.addToRoot("b");
		nodeBuilder.addToRoot("a");
		
		PermissionNode node = nodeBuilder.build();
		assertTrue(node.containsPermission("a.b.c"));
		assertFalse(node.containsPermission("a.b.*"));
		assertTrue(node.containsPermission("a.b.c.d.*"));
		assertTrue(node.containsPermission("a.b.c.d.e.f.g.h.i.j.k"));

		assertTrue(node.satisfiesPermission("a.*"));
		assertFalse(node.satisfiesPermission("a.test"));
		assertTrue(node.satisfiesPermission("a.*.test"));
	}
		
}
