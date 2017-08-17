package com.dereekb.gae.test.model.extension.link;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dereekb.gae.model.crud.services.components.impl.ReadServiceImpl;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.ModelReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.crud.task.ReadTask;
import com.dereekb.gae.model.crud.task.impl.ReadTaskImpl;
import com.dereekb.gae.model.extension.links.system.components.LinkModelInfo;
import com.dereekb.gae.model.extension.links.system.mutable.FullLinkModelAccessor;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLink;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeResult;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModel;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkModelAccessorPair;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkSystemBuilderEntry;
import com.dereekb.gae.model.extension.links.system.mutable.impl.MutableLinkChangeImpl;
import com.dereekb.gae.model.extension.links.system.mutable.impl.MutableLinkSystemBuilderImpl;
import com.dereekb.gae.model.extension.links.system.readonly.LinkModelAccessor;
import com.dereekb.gae.model.extension.links.system.readonly.LinkSystem;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabase;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntity;
import com.dereekb.gae.server.datastore.objectify.core.ObjectifyDatabaseEntityDefinition;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseEntityDefinitionImpl;
import com.dereekb.gae.server.datastore.objectify.core.impl.ObjectifyDatabaseImpl;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.test.model.extension.generator.impl.TestModelGeneratorImpl;
import com.dereekb.gae.test.model.extension.link.model.TestLinkModelA;
import com.dereekb.gae.test.model.extension.link.model.TestLinkModelAGenerator;
import com.dereekb.gae.test.model.extension.link.model.TestLinkModelALinkSystemBuilderEntry;
import com.dereekb.gae.test.model.extension.link.model.TestLinkModelB;
import com.dereekb.gae.test.model.extension.link.model.TestLinkModelBGenerator;
import com.dereekb.gae.test.model.extension.link.model.TestLinkModelBLinkSystemBuilderEntry;
import com.dereekb.gae.test.spring.CoreServiceTestingContext;

public class LinkSystemTests extends CoreServiceTestingContext {

	private LinkSystemCreationInfo testSystem;

	@Before
	public void initSystem() {
		this.testSystem = makeTestLinkSystem();
	}

	public static LinkSystemCreationInfo makeTestLinkSystem() {

		ObjectifyDatabaseImpl database = makeDatabase();

		// MARK: Model A
		ObjectifyDatabaseEntity<TestLinkModelA> aEntity = database.getDatabaseEntity(TestLinkModelA.class);
		ReadTask<TestLinkModelA> aReadTask = new ReadTaskImpl<TestLinkModelA>(aEntity);
		ReadServiceImpl<TestLinkModelA> readServiceA = new ReadServiceImpl<TestLinkModelA>(aReadTask);

		TestLinkModelALinkSystemBuilderEntry aLinkSystemEntry = new TestLinkModelALinkSystemBuilderEntry(readServiceA);

		// Links
		Map<String, String> aBiLinks = new HashMap<String, String>();

		aBiLinks.put(TestLinkModelALinkSystemBuilderEntry.PRIMARY_LINK_NAME,
		        TestLinkModelALinkSystemBuilderEntry.A_CHILDREN_LINK_NAME);
		aBiLinks.put(TestLinkModelALinkSystemBuilderEntry.A_CHILDREN_LINK_NAME,
		        TestLinkModelALinkSystemBuilderEntry.PRIMARY_LINK_NAME);
		aBiLinks.put(TestLinkModelALinkSystemBuilderEntry.B_CHILDREN_LINK_NAME,
		        TestLinkModelBLinkSystemBuilderEntry.PARENTS_LINK_NAME);
		aBiLinks.put(TestLinkModelALinkSystemBuilderEntry.THIRD_LINK_NAME,
		        TestLinkModelBLinkSystemBuilderEntry.MAIN_LINK_NAME);

		aLinkSystemEntry.setBidirectionalLinkNames(aBiLinks);

		// MARK: Model B
		ObjectifyDatabaseEntity<TestLinkModelB> bEntity = database.getDatabaseEntity(TestLinkModelB.class);
		ReadTask<TestLinkModelB> bReadTask = new ReadTaskImpl<TestLinkModelB>(bEntity);
		ReadServiceImpl<TestLinkModelB> readServiceB = new ReadServiceImpl<TestLinkModelB>(bReadTask);

		TestLinkModelBLinkSystemBuilderEntry bLinkSystemEntry = new TestLinkModelBLinkSystemBuilderEntry(readServiceB);

		// Links
		Map<String, String> bBiLinks = new HashMap<String, String>();

		bBiLinks.put(TestLinkModelBLinkSystemBuilderEntry.PARENTS_LINK_NAME,
		        TestLinkModelALinkSystemBuilderEntry.B_CHILDREN_LINK_NAME);

		bBiLinks.put(TestLinkModelBLinkSystemBuilderEntry.MAIN_LINK_NAME,
		        TestLinkModelALinkSystemBuilderEntry.THIRD_LINK_NAME);

		bLinkSystemEntry.setBidirectionalLinkNames(bBiLinks);

		// MARK: Builder
		Map<String, MutableLinkSystemBuilderEntry> entries = new HashMap<String, MutableLinkSystemBuilderEntry>();

		entries.put(TestLinkModelA.MODEL_ENTITY_NAME, aLinkSystemEntry);
		entries.put(TestLinkModelB.MODEL_ENTITY_NAME, bLinkSystemEntry);

		MutableLinkSystemBuilderImpl builder = new MutableLinkSystemBuilderImpl(entries);

		FullLinkModelAccessor<TestLinkModelA> aAccessor = builder.makeAccessor(aLinkSystemEntry);
		FullLinkModelAccessor<TestLinkModelB> bAccessor = builder.makeAccessor(bLinkSystemEntry);

		// MARK: System
		Map<String, LinkModelAccessor> accessors = new HashMap<String, LinkModelAccessor>();

		accessors.put(TestLinkModelA.MODEL_ENTITY_NAME, aAccessor);
		accessors.put(TestLinkModelB.MODEL_ENTITY_NAME, bAccessor);

		LinkSystem linkSystem = builder.makeLinkSystem(accessors);

		return new LinkSystemCreationInfo(database, aEntity, bEntity, aAccessor, bAccessor, linkSystem);
	}

	public static class LinkSystemCreationInfo {

		public final ObjectifyDatabase database;
		public final ObjectifyDatabaseEntity<TestLinkModelA> aEntity;
		public final ObjectifyDatabaseEntity<TestLinkModelB> bEntity;

		public final LinkSystem linkSystem;
		public final FullLinkModelAccessor<TestLinkModelA> aAccessor;
		public final FullLinkModelAccessor<TestLinkModelB> bAccessor;

		public final TestModelGenerator<TestLinkModelA> aEntityGenerator;
		public final TestModelGenerator<TestLinkModelB> bEntityGenerator;

		public LinkSystemCreationInfo(ObjectifyDatabase database,
		        ObjectifyDatabaseEntity<TestLinkModelA> aEntity,
		        ObjectifyDatabaseEntity<TestLinkModelB> bEntity,
		        FullLinkModelAccessor<TestLinkModelA> aAccessor,
		        FullLinkModelAccessor<TestLinkModelB> bAccessor,
		        LinkSystem linkSystem) {
			super();
			this.database = database;
			this.aEntity = aEntity;
			this.bEntity = bEntity;
			this.linkSystem = linkSystem;

			this.aAccessor = aAccessor;
			this.bAccessor = bAccessor;

			this.aEntityGenerator = new TestModelGeneratorImpl<TestLinkModelA>(aEntity, new TestLinkModelAGenerator());
			this.bEntityGenerator = new TestModelGeneratorImpl<TestLinkModelB>(bEntity, new TestLinkModelBGenerator());
		}

	}

	public static ObjectifyDatabaseImpl makeDatabase() {

		ObjectifyDatabaseEntityDefinitionImpl testModelADefinition = new ObjectifyDatabaseEntityDefinitionImpl(
		        TestLinkModelA.MODEL_ENTITY_NAME, TestLinkModelA.class, ModelKeyType.NUMBER);
		ObjectifyDatabaseEntityDefinitionImpl testModelBDefinition = new ObjectifyDatabaseEntityDefinitionImpl(
		        TestLinkModelB.MODEL_ENTITY_NAME, TestLinkModelB.class, ModelKeyType.NAME);

		List<ObjectifyDatabaseEntityDefinition> definitions = new ArrayList<ObjectifyDatabaseEntityDefinition>();
		definitions.add(testModelADefinition);
		definitions.add(testModelBDefinition);

		ObjectifyDatabaseImpl database = new ObjectifyDatabaseImpl(definitions);
		return database;
	}

	// MARK: Tests
	/**
	 * Tests that the link system is properly setup.
	 */
	@Test
	public void testSystemAndLinks() {
		LinkSystem linkSystem = this.testSystem.linkSystem;

		Set<String> types = linkSystem.getAvailableSetTypes();
		Assert.assertTrue(types.size() == 2);

		for (String type : types) {
			LinkModelInfo info = linkSystem.loadLinkModelInfo(type);
			Assert.assertTrue(info != null);

			LinkModelAccessor accessor = linkSystem.loadAccessor(type);
			Assert.assertTrue(accessor != null);

			Set<String> linkNames = info.getLinkNames();
			Assert.assertFalse(linkNames.isEmpty());
		}
	}

	@Test
	public void testLinkingAndUnlinking() {

		TestLinkModelA a = this.testSystem.aEntityGenerator.generate();
		List<TestLinkModelB> children = this.testSystem.bEntityGenerator.generate(5);

		Assert.assertTrue(a.getbChildKeys().isEmpty());

		ReadRequest aReadRequest = new ModelReadRequest(a);
		ReadResponse<? extends MutableLinkModelAccessorPair<TestLinkModelA>> aMutableReadResponse = this.testSystem.aAccessor
		        .readMutableLinkModels(aReadRequest);

		MutableLinkModel aMutableLinkModel = aMutableReadResponse.getModels().iterator().next().getMutableLinkModel();
		MutableLink childLink = aMutableLinkModel.getLink(TestLinkModelALinkSystemBuilderEntry.B_CHILDREN_LINK_NAME);

		// Link Children
		List<ModelKey> childrenKeys = ModelKey.readModelKeys(children);
		MutableLinkChange linkBChildrenToAChange = MutableLinkChangeImpl.add(childrenKeys);

		MutableLinkChangeResult linkChangeResult = childLink.modifyKeys(linkBChildrenToAChange);
		Set<ModelKey> modified = linkChangeResult.getModified();
		Set<ModelKey> redundant = linkChangeResult.getRedundant();

		Assert.assertTrue(modified.containsAll(childrenKeys));
		Assert.assertTrue(redundant.isEmpty());

		// Link Again to Test Redundancy
		linkChangeResult = childLink.modifyKeys(linkBChildrenToAChange);

		modified = linkChangeResult.getModified();
		redundant = linkChangeResult.getRedundant();

		Assert.assertTrue(modified.isEmpty());
		Assert.assertTrue(redundant.containsAll(childrenKeys));

		// Unlink Children
		MutableLinkChange unlinkBChildrenToAChange = MutableLinkChangeImpl.remove(childrenKeys);

		linkChangeResult = childLink.modifyKeys(unlinkBChildrenToAChange);

		modified = linkChangeResult.getModified();
		redundant = linkChangeResult.getRedundant();

		Assert.assertTrue(modified.containsAll(childrenKeys));
		Assert.assertTrue(redundant.isEmpty());

		// Unlink Again to Test Redundancy
		linkChangeResult = childLink.modifyKeys(unlinkBChildrenToAChange);

		modified = linkChangeResult.getModified();
		redundant = linkChangeResult.getRedundant();

		Assert.assertTrue(modified.isEmpty());
		Assert.assertTrue(redundant.containsAll(childrenKeys));

		// Set Children
		MutableLinkChange setBChildrenToAChange = MutableLinkChangeImpl.set(childrenKeys);

		linkChangeResult = childLink.modifyKeys(setBChildrenToAChange);
		modified = linkChangeResult.getModified();
		redundant = linkChangeResult.getRedundant();

		Assert.assertTrue(modified.containsAll(childrenKeys));
		Assert.assertTrue(redundant.isEmpty());
		
		// Clear Children
		MutableLinkChange clearBChildren = MutableLinkChangeImpl.clear();

		linkChangeResult = childLink.modifyKeys(clearBChildren);
		modified = linkChangeResult.getModified();
		redundant = linkChangeResult.getRedundant();

		Assert.assertTrue(modified.containsAll(childrenKeys));
		Assert.assertTrue(redundant.isEmpty());
		
	}

}
