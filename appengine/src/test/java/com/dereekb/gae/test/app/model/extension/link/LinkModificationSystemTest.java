package com.dereekb.gae.test.app.model.extension.link;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemChangesResult;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemDelegate;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemEntry;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.exception.failure.LinkModificationFailedException;
import com.dereekb.gae.model.extension.links.system.modification.exception.internal.LinkModificationSystemInstanceAlreadyRunException;
import com.dereekb.gae.model.extension.links.system.modification.exception.internal.UnexpectedLinkModificationSystemChangeException;
import com.dereekb.gae.model.extension.links.system.modification.exception.request.ConflictingLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.request.IllegalLinkChangeLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.request.InvalidLinkModificationSystemRequestException;
import com.dereekb.gae.model.extension.links.system.modification.exception.request.TooManyChangeKeysException;
import com.dereekb.gae.model.extension.links.system.modification.impl.LinkModificationSystemDelegateImpl;
import com.dereekb.gae.model.extension.links.system.modification.impl.LinkModificationSystemEntryImpl;
import com.dereekb.gae.model.extension.links.system.modification.impl.LinkModificationSystemImpl;
import com.dereekb.gae.model.extension.links.system.modification.impl.LinkModificationSystemInstanceOptionsImpl;
import com.dereekb.gae.model.extension.links.system.modification.impl.LinkModificationSystemRequestImpl;
import com.dereekb.gae.model.extension.links.system.modification.impl.ReusableLinkModificationSystemRequestBuilder;
import com.dereekb.gae.model.extension.links.system.modification.impl.ReusableLinkModificationSystemRequestBuilder.RequestBuilderGroup;
import com.dereekb.gae.model.extension.links.system.modification.impl.ReusableLinkModificationSystemRequestBuilder.RequestBuilderItem;
import com.dereekb.gae.model.extension.links.system.modification.utility.LinkModificationSystemUtility;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;
import com.dereekb.gae.model.extension.links.system.mutable.impl.MutableLinkChangeImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.test.app.mock.context.AbstractAppContextOnlyTestingContext;
import com.dereekb.gae.test.app.model.extension.link.LinkSystemTest.LinkSystemCreationInfo;
import com.dereekb.gae.test.app.model.extension.link.model.TestLinkModelA;
import com.dereekb.gae.test.app.model.extension.link.model.TestLinkModelALinkSystemBuilderEntry;
import com.dereekb.gae.test.app.model.extension.link.model.TestLinkModelB;
import com.dereekb.gae.test.app.model.extension.link.model.TestLinkModelBLinkSystemBuilderEntry;
import com.dereekb.gae.test.server.datastore.objectify.TestObjectifyInitializerImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.list.SetUtility;

/**
 * Unit tests for {@link LinkModificationSystemImpl}.
 *
 * @author dereekb
 *
 */
public class LinkModificationSystemTest extends AbstractAppContextOnlyTestingContext {

	private LinkModificationSystemInfo testSystem;

	@Override
	@BeforeEach
	public void setUpAppServices() {
		super.setUpAppServices();
		this.testSystem = makeLinkModificationSystem(this.testObjectifyInitializer);
	}

	public static LinkModificationSystemInfo makeLinkModificationSystem(TestObjectifyInitializerImpl testObjectifyInitializer) {
		LinkSystemCreationInfo testLinkSystem = LinkSystemTest.makeTestLinkSystem(testObjectifyInitializer);

		LinkModificationSystemEntryImpl<TestLinkModelA> aEntry = new LinkModificationSystemEntryImpl<TestLinkModelA>(testLinkSystem.aAccessor, testLinkSystem.aEntity, new TaskRequestSender<TestLinkModelA>() {

			@Override
			public void sendTask(TestLinkModelA input) throws SubmitTaskException {}

			@Override
			public void sendTasks(Iterable<TestLinkModelA> input) throws SubmitTaskException {}

		});

		LinkModificationSystemEntryImpl<TestLinkModelB> bEntry = new LinkModificationSystemEntryImpl<TestLinkModelB>(testLinkSystem.bAccessor, testLinkSystem.bEntity, new TaskRequestSender<TestLinkModelB>() {

			@Override
			public void sendTask(TestLinkModelB input) throws SubmitTaskException {}

			@Override
			public void sendTasks(Iterable<TestLinkModelB> input) throws SubmitTaskException {}

		});

		Map<String, LinkModificationSystemEntry> entriesMap = new HashMap<String, LinkModificationSystemEntry>();

		entriesMap.put(TestLinkModelA.MODEL_ENTITY_NAME, aEntry);
		entriesMap.put(TestLinkModelB.MODEL_ENTITY_NAME, bEntry);

		LinkModificationSystemDelegate delegate = new LinkModificationSystemDelegateImpl(entriesMap);
		LinkModificationSystemImpl linkModificationSystem = new LinkModificationSystemImpl(testLinkSystem.linkSystem, delegate);
		return new LinkModificationSystemInfo(testLinkSystem, linkModificationSystem);
	}

	public static class LinkModificationSystemInfo {

		public final LinkSystemCreationInfo testLinkSystem;
		public final LinkModificationSystemImpl linkModificationSystem;

		public LinkModificationSystemInfo(LinkSystemCreationInfo testLinkSystem,
		        LinkModificationSystemImpl linkModificationSystem) {
			super();
			this.testLinkSystem = testLinkSystem;
			this.linkModificationSystem = linkModificationSystem;
		}

	}

	// MARK: Tests
	/**
	 * Tests linking and unlinking the many to many relation.
	 */
	@Test
	public void testBidirectionalManyToManyLinkingAndUnlinking() throws UnavailableLinkException, UnavailableLinkModelException, ConflictingLinkModificationSystemRequestException, InvalidLinkModificationSystemRequestException, LinkModificationSystemInstanceAlreadyRunException, UnexpectedLinkModificationSystemChangeException {

		LinkSystemCreationInfo linkSystemInfo = this.testSystem.testLinkSystem;

		// Generate Models
		TestLinkModelA a = linkSystemInfo.aEntityGenerator.generate();
		List<TestLinkModelB> children = linkSystemInfo.bEntityGenerator.generate(5);

		// Link Models
		ModelKey aModelKey = a.getModelKey();
		List<ModelKey> childrenKeys = ModelKey.readModelKeys(children);
		MutableLinkChange linkBChildrenToAChange = MutableLinkChangeImpl.add(childrenKeys);

		LinkModificationSystemRequest linkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.B_CHILDREN_LINK_NAME, linkBChildrenToAChange);

		LinkModificationSystemInstance instance = this.testSystem.linkModificationSystem.makeInstance();
		instance.queueRequest(linkRequest);

		@SuppressWarnings("unused")
		LinkModificationSystemChangesResult result = instance.applyChanges();

		// Assert models were linked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		children = this.testSystem.testLinkSystem.bEntity.get(children);

		assertFalse(a.getbChildKeys().isEmpty());
		assertTrue(a.getbChildKeys().containsAll(childrenKeys));

		for (TestLinkModelB child : children) {
			assertTrue(child.getParentKeys().contains(aModelKey));
		}

		// Unlink Models
		MutableLinkChange unlinkBChildrenToAChange = MutableLinkChangeImpl.remove(childrenKeys);

		LinkModificationSystemRequest unlinkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.B_CHILDREN_LINK_NAME, unlinkBChildrenToAChange);

		instance = this.testSystem.linkModificationSystem.makeInstance();
		instance.queueRequest(unlinkRequest);

		result = instance.applyChanges();

		// Assert models were unlinked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		children = this.testSystem.testLinkSystem.bEntity.get(children);

		assertTrue(a.getbChildKeys().isEmpty());

		for (TestLinkModelB child : children) {
			assertTrue(child.getParentKeys().isEmpty());
		}

		// Link From Opposite Side
		MutableLinkChange linkAChangeToBChildren = MutableLinkChangeImpl.add(ListUtility.wrap(a.getModelKey()));

		instance = this.testSystem.linkModificationSystem.makeInstance();

		for (TestLinkModelB child : children) {
			ModelKey childModelKey = child.getModelKey();
			LinkModificationSystemRequest linkParentRequest = new LinkModificationSystemRequestImpl(TestLinkModelB.MODEL_ENTITY_NAME, childModelKey, TestLinkModelBLinkSystemBuilderEntry.PARENTS_LINK_NAME, linkAChangeToBChildren);
			instance.queueRequest(linkParentRequest);
		}

		result = instance.applyChanges();

		// Assert models were linked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		children = this.testSystem.testLinkSystem.bEntity.get(children);

		assertFalse(a.getbChildKeys().isEmpty());
		assertTrue(a.getbChildKeys().containsAll(childrenKeys));

		for (TestLinkModelB child : children) {
			assertTrue(child.getParentKeys().contains(aModelKey));
		}

		// Unlink Models using clear
		MutableLinkChange clearBChildrenFromAChange = MutableLinkChangeImpl.clear();

		LinkModificationSystemRequest clearRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.B_CHILDREN_LINK_NAME, clearBChildrenFromAChange);

		instance = this.testSystem.linkModificationSystem.makeInstance();
		instance.queueRequest(clearRequest);

		result = instance.applyChanges();

		// Assert models were unlinked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		children = this.testSystem.testLinkSystem.bEntity.get(children);

		assertTrue(a.getbChildKeys().isEmpty());

		for (TestLinkModelB child : children) {
			assertTrue(child.getParentKeys().isEmpty());
		}

	}

	/**
	 * Tests linking and unlinking a one to many relation.
	 */
	@Test
	public void testBidirectionalOneToManyLinkingAndUnlinking() throws UnavailableLinkException, UnavailableLinkModelException, ConflictingLinkModificationSystemRequestException, InvalidLinkModificationSystemRequestException, LinkModificationSystemInstanceAlreadyRunException, UnexpectedLinkModificationSystemChangeException {

		LinkSystemCreationInfo linkSystemInfo = this.testSystem.testLinkSystem;

		// Generate Models
		TestLinkModelA a = linkSystemInfo.aEntityGenerator.generate();
		List<TestLinkModelA> children = linkSystemInfo.aEntityGenerator.generate(5);

		// Link Models
		ModelKey aModelKey = a.getModelKey();
		List<ModelKey> childrenKeys = ModelKey.readModelKeys(children);
		MutableLinkChange linkAChildrenToAChange = MutableLinkChangeImpl.add(childrenKeys);

		LinkModificationSystemRequest linkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.A_CHILDREN_LINK_NAME, linkAChildrenToAChange);

		LinkModificationSystemInstance instance = this.testSystem.linkModificationSystem.makeInstance();
		instance.queueRequest(linkRequest);

		@SuppressWarnings("unused")
		LinkModificationSystemChangesResult result = instance.applyChanges();

		// Assert models were linked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		children = this.testSystem.testLinkSystem.aEntity.get(children);

		assertFalse(a.getaChildKeys().isEmpty());
		assertTrue(a.getaChildKeys().containsAll(childrenKeys));

		for (TestLinkModelA child : children) {
			assertTrue(child.getPrimaryKey().equals(a.getModelKey()));
		}

		// Unlink Models
		MutableLinkChange unlinkAChildrenToAChange = MutableLinkChangeImpl.remove(childrenKeys);

		LinkModificationSystemRequest unlinkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.A_CHILDREN_LINK_NAME, unlinkAChildrenToAChange);

		instance = this.testSystem.linkModificationSystem.makeInstance();
		instance.queueRequest(unlinkRequest);

		result = instance.applyChanges();

		// Assert models were unlinked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		children = this.testSystem.testLinkSystem.aEntity.get(children);

		assertTrue(a.getaChildKeys().isEmpty());

		for (TestLinkModelA child : children) {
			assertTrue(child.getPrimaryKey() == null);
		}

		// Link From Opposite Side
		// Use SET for one to many.
		MutableLinkChange linkAChangeToAChildren = MutableLinkChangeImpl.set(ListUtility.wrap(a.getModelKey()));

		instance = this.testSystem.linkModificationSystem.makeInstance();

		for (TestLinkModelA child : children) {
			ModelKey childModelKey = child.getModelKey();
			LinkModificationSystemRequest linkParentRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, childModelKey, TestLinkModelALinkSystemBuilderEntry.PRIMARY_LINK_NAME, linkAChangeToAChildren);
			instance.queueRequest(linkParentRequest);
		}

		result = instance.applyChanges();

		// Assert models were linked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		children = this.testSystem.testLinkSystem.aEntity.get(children);

		assertFalse(a.getaChildKeys().isEmpty());
		assertTrue(a.getaChildKeys().containsAll(childrenKeys));

		for (TestLinkModelA child : children) {
			assertTrue(child.getPrimaryKey().equals(a.getModelKey()));
		}

		// Unlink Models using clear
		MutableLinkChange clearAChildrenFromAChange = MutableLinkChangeImpl.clear();

		LinkModificationSystemRequest clearRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.A_CHILDREN_LINK_NAME, clearAChildrenFromAChange);

		instance = this.testSystem.linkModificationSystem.makeInstance();
		instance.queueRequest(clearRequest);

		result = instance.applyChanges();

		// Assert models were unlinked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		children = this.testSystem.testLinkSystem.aEntity.get(children);

		assertTrue(a.getbChildKeys().isEmpty());

		for (TestLinkModelA child : children) {
			assertTrue(child.getPrimaryKey() == null);
		}

	}

	/**
	 * Tests linking and unlinking a one to many relation.
	 */
	@Test
	public void testBidirectionalOneToOneLinkingAndUnlinking() throws UnavailableLinkException, UnavailableLinkModelException, ConflictingLinkModificationSystemRequestException, InvalidLinkModificationSystemRequestException, LinkModificationSystemInstanceAlreadyRunException, UnexpectedLinkModificationSystemChangeException {

		LinkSystemCreationInfo linkSystemInfo = this.testSystem.testLinkSystem;

		// Generate Models
		TestLinkModelA a = linkSystemInfo.aEntityGenerator.generate();
		TestLinkModelB b = linkSystemInfo.bEntityGenerator.generate();

		// Link Models
		ModelKey aModelKey = a.getModelKey();
		ModelKey bModelKey = b.getModelKey();

		MutableLinkChange linkAToBChange = MutableLinkChangeImpl.set(SetUtility.wrap(bModelKey));

		LinkModificationSystemRequest linkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.THIRD_LINK_NAME, linkAToBChange);

		LinkModificationSystemInstance instance = this.testSystem.linkModificationSystem.makeInstance();
		instance.queueRequest(linkRequest);

		@SuppressWarnings("unused")
		LinkModificationSystemChangesResult result = instance.applyChanges();

		// Assert models were linked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		b = this.testSystem.testLinkSystem.bEntity.get(b);

		assertTrue(a.getThirdKey().equals(b.getModelKey()));
		assertTrue(b.getMainKey().equals(a.getModelKey()));

		// Unlink Models
		MutableLinkChange unlinkAToBChange = MutableLinkChangeImpl.clear();

		LinkModificationSystemRequest unlinkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.THIRD_LINK_NAME, unlinkAToBChange);

		instance = this.testSystem.linkModificationSystem.makeInstance();
		instance.queueRequest(unlinkRequest);

		result = instance.applyChanges();

		// Assert models were unlinked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		b = this.testSystem.testLinkSystem.bEntity.get(b);

		assertTrue(a.getThirdKey() == null);
		assertTrue(b.getMainKey() == null);
	}

	/**
	 * Test unlinking from a model that has been deleted does not result in an UnavailableLinkModelException exception.
	 */
	@Test
	public void testOptionalUnlinking() throws UnavailableLinkException, UnavailableLinkModelException, ConflictingLinkModificationSystemRequestException, InvalidLinkModificationSystemRequestException, LinkModificationSystemInstanceAlreadyRunException, UnexpectedLinkModificationSystemChangeException {

		LinkSystemCreationInfo linkSystemInfo = this.testSystem.testLinkSystem;

		// Generate Models
		TestLinkModelA a = linkSystemInfo.aEntityGenerator.generate();
		TestLinkModelB b = linkSystemInfo.bEntityGenerator.generate();

		// Link Models
		ModelKey aModelKey = a.getModelKey();
		ModelKey bModelKey = b.getModelKey();

		MutableLinkChange linkAToBChange = MutableLinkChangeImpl.set(SetUtility.wrap(bModelKey));

		LinkModificationSystemRequest linkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.THIRD_LINK_NAME, linkAToBChange);

		LinkModificationSystemInstance instance = this.testSystem.linkModificationSystem.makeInstance();
		instance.queueRequest(linkRequest);

		@SuppressWarnings("unused")
		LinkModificationSystemChangesResult result = instance.applyChanges();

		// Assert models were linked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		b = this.testSystem.testLinkSystem.bEntity.get(b);

		assertTrue(a.getThirdKey().equals(b.getModelKey()));
		assertTrue(b.getMainKey().equals(a.getModelKey()));

		// Delete Model B directly.
		this.testSystem.testLinkSystem.bEntity.delete(b);

		// Assert/show model a is still linked
		a = this.testSystem.testLinkSystem.aEntity.get(a);

		assertTrue(a.getThirdKey().equals(b.getModelKey()));

		// Unlink Models
		MutableLinkChange unlinkAToBChange = MutableLinkChangeImpl.clear();

		LinkModificationSystemRequest unlinkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.THIRD_LINK_NAME, unlinkAToBChange);

		instance = this.testSystem.linkModificationSystem.makeInstance();
		instance.queueRequest(unlinkRequest);

		result = instance.applyChanges();

		// Assert models were unlinked
		a = this.testSystem.testLinkSystem.aEntity.get(a);

		assertTrue(a.getThirdKey() == null);
	}

	/**
	 * Test unlinking from a model that has been deleted does not result in an UnavailableLinkModelException exception.
	 */
	@Test
	public void testLinkingUnavailableModels() throws UnavailableLinkException, UnavailableLinkModelException, ConflictingLinkModificationSystemRequestException, InvalidLinkModificationSystemRequestException, LinkModificationSystemInstanceAlreadyRunException, UnexpectedLinkModificationSystemChangeException {

		LinkSystemCreationInfo linkSystemInfo = this.testSystem.testLinkSystem;

		// Link Models
		ModelKey aModelKey = linkSystemInfo.aEntityGenerator.generateKey();
		ModelKey bModelKey = linkSystemInfo.bEntityGenerator.generateKey();

		MutableLinkChange linkAToBChange = MutableLinkChangeImpl.set(SetUtility.wrap(bModelKey));

		LinkModificationSystemRequest linkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.THIRD_LINK_NAME, linkAToBChange);

		LinkModificationSystemInstance instance = this.testSystem.linkModificationSystem.makeInstance();
		instance.queueRequest(linkRequest);

		try {
			@SuppressWarnings("unused")
			LinkModificationSystemChangesResult result = instance.applyChanges();
			fail();
		} catch (LinkModificationFailedException e) {
			assertTrue(e.getFailedRequests().contains(linkRequest));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Test linking to unavailable model fails.
	 */
	@Test
	public void testLinkingToUnavailableModelFailsAndUndoIsProcessed() throws UnavailableLinkException, UnavailableLinkModelException, ConflictingLinkModificationSystemRequestException, InvalidLinkModificationSystemRequestException, LinkModificationSystemInstanceAlreadyRunException, UnexpectedLinkModificationSystemChangeException {

		LinkSystemCreationInfo linkSystemInfo = this.testSystem.testLinkSystem;

		// Generate Models
		TestLinkModelA a = linkSystemInfo.aEntityGenerator.generate();
		List<TestLinkModelA> children = linkSystemInfo.aEntityGenerator.generate(5);

		// Link Models
		ModelKey aModelKey = a.getModelKey();
		List<ModelKey> childrenKeys = ModelKey.readModelKeys(children);

		// Generate a non-existant model.
		ModelKey bModelKey = linkSystemInfo.bEntityGenerator.generateKey();

		MutableLinkChange linkAChildrenToAChange = MutableLinkChangeImpl.add(childrenKeys);
		LinkModificationSystemRequest childrenLinkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.A_CHILDREN_LINK_NAME, linkAChildrenToAChange);

		MutableLinkChange linkAToBChange = MutableLinkChangeImpl.set(SetUtility.wrap(bModelKey));
		LinkModificationSystemRequest unavailableLinkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.THIRD_LINK_NAME, linkAToBChange);

		LinkModificationSystemInstance instance = this.testSystem.linkModificationSystem.makeInstance();

		instance.queueRequest(childrenLinkRequest);
		instance.queueRequest(unavailableLinkRequest);

		try {
			@SuppressWarnings("unused")
			LinkModificationSystemChangesResult result = instance.applyChanges();
			fail();
		} catch (LinkModificationFailedException e) {
			assertFalse(e.getFailedRequests().contains(childrenLinkRequest));
			assertTrue(e.getFailedRequests().contains(unavailableLinkRequest));
		}

		// Assert models were not linked after failure.
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		children = this.testSystem.testLinkSystem.aEntity.get(children);

		assertTrue(a.getaChildKeys().isEmpty());

		for (TestLinkModelA child : children) {
			assertTrue(child.getPrimaryKey() == null);
		}
	}

	/**
	 * Test attempting to link many keys to a size one link.
	 */
	@Test
	public void testAttemptingToLinkManyToSizeOneLinkFails() throws UnavailableLinkException, UnavailableLinkModelException, ConflictingLinkModificationSystemRequestException, InvalidLinkModificationSystemRequestException, LinkModificationSystemInstanceAlreadyRunException, UnexpectedLinkModificationSystemChangeException {

		LinkSystemCreationInfo linkSystemInfo = this.testSystem.testLinkSystem;

		// Generate Models
		TestLinkModelA a = linkSystemInfo.aEntityGenerator.generate();

		// Link Models
		ModelKey aModelKey = a.getModelKey();
		List<ModelKey> bModelKeys = linkSystemInfo.bEntityGenerator.generateKeys(3);	//Invalid size

		MutableLinkChange linkAToBChange = MutableLinkChangeImpl.set(bModelKeys);

		LinkModificationSystemRequest linkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.THIRD_LINK_NAME, linkAToBChange);

		LinkModificationSystemInstance instance = this.testSystem.linkModificationSystem.makeInstance();

		try {
			instance.queueRequest(linkRequest);
			fail();
		} catch (TooManyChangeKeysException e) {

		}

	}

	/**
	 * Tests using an invalid model key.
	 */
	@Test
	public void testInvalidKeyConversions() throws UnavailableLinkException, UnavailableLinkModelException, ConflictingLinkModificationSystemRequestException, InvalidLinkModificationSystemRequestException, LinkModificationSystemInstanceAlreadyRunException, UnexpectedLinkModificationSystemChangeException {

		LinkSystemCreationInfo linkSystemInfo = this.testSystem.testLinkSystem;

		// Generate Models
		TestLinkModelB b = linkSystemInfo.bEntityGenerator.generate();

		// Link Models
		ModelKey bModelKey = b.getModelKey();
		List<ModelKey> invalidAModelKeys = linkSystemInfo.bEntityGenerator.generateKeys(3);	//Invalid size

		MutableLinkChange linkBToAChange = MutableLinkChangeImpl.set(invalidAModelKeys);

		LinkModificationSystemRequest linkRequest = new LinkModificationSystemRequestImpl(TestLinkModelB.MODEL_ENTITY_NAME, bModelKey, TestLinkModelBLinkSystemBuilderEntry.PARENTS_LINK_NAME, linkBToAChange);

		LinkModificationSystemInstance instance = this.testSystem.linkModificationSystem.makeInstance();

		instance.queueRequest(linkRequest);

		try {
			instance.applyChanges();
			fail();
		} catch (InvalidLinkModificationSystemRequestException e) {

		}

	}

	/**
	 * Test attempting an ADD or REMOVE on size.
	 */
	@Test
	public void testAttemptingToUseAddOrRemoveOnSizeOneLinkFails() throws UnavailableLinkException, UnavailableLinkModelException, ConflictingLinkModificationSystemRequestException, InvalidLinkModificationSystemRequestException, LinkModificationSystemInstanceAlreadyRunException, UnexpectedLinkModificationSystemChangeException {

		LinkSystemCreationInfo linkSystemInfo = this.testSystem.testLinkSystem;

		// Generate Models
		TestLinkModelA a = linkSystemInfo.aEntityGenerator.generate();
		TestLinkModelB b = linkSystemInfo.bEntityGenerator.generate();

		// Link Models
		ModelKey aModelKey = a.getModelKey();
		ModelKey bModelKey = b.getModelKey();

		// Try using Add
		MutableLinkChange linkAToBChange = MutableLinkChangeImpl.add(SetUtility.wrap(bModelKey));

		LinkModificationSystemRequest linkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.THIRD_LINK_NAME, linkAToBChange);

		LinkModificationSystemInstance instance = this.testSystem.linkModificationSystem.makeInstance();

		try {
			instance.queueRequest(linkRequest);
			fail();
		} catch (IllegalLinkChangeLinkModificationSystemRequestException e) {

		}

	}

	/**
	 * Test that only a single change to a model's link is requested.
	 *
	 * I.E. Two changes to the primary link on the same TestModelA cannot be performed.
	 */
	@Test
	public void testAttemptingToUsePerformMultipleConcurrentSameModelLinkModificationsFails() throws UnavailableLinkException, UnavailableLinkModelException, ConflictingLinkModificationSystemRequestException, InvalidLinkModificationSystemRequestException, LinkModificationSystemInstanceAlreadyRunException, UnexpectedLinkModificationSystemChangeException {

		LinkSystemCreationInfo linkSystemInfo = this.testSystem.testLinkSystem;

		// Generate Models
		TestLinkModelA a = linkSystemInfo.aEntityGenerator.generate();
		TestLinkModelB b = linkSystemInfo.bEntityGenerator.generate();

		// Link Models
		ModelKey aModelKey = a.getModelKey();
		ModelKey bModelKey = b.getModelKey();

		MutableLinkChange linkAToBChange = MutableLinkChangeImpl.set(SetUtility.wrap(bModelKey));

		LinkModificationSystemRequest linkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.THIRD_LINK_NAME, linkAToBChange);

		LinkModificationSystemInstance instance = this.testSystem.linkModificationSystem.makeInstance();

		// Attempt to queue twice
		instance.queueRequest(linkRequest);

		try {
			instance.queueRequest(linkRequest);
			fail("Queuing request should have failed.");
		} catch (ConflictingLinkModificationSystemRequestException e) {

		}
	}

	/**
	 * Performs an undo after performing the changes to a one to one link to see if the change properly reverses.
	 */
	@Test
	public void testUndoForOneToOneLink() throws UnavailableLinkException, UnavailableLinkModelException, ConflictingLinkModificationSystemRequestException, InvalidLinkModificationSystemRequestException, LinkModificationSystemInstanceAlreadyRunException, UnexpectedLinkModificationSystemChangeException {

		LinkSystemCreationInfo linkSystemInfo = this.testSystem.testLinkSystem;

		// Generate Models
		TestLinkModelA a = linkSystemInfo.aEntityGenerator.generate();
		TestLinkModelB b = linkSystemInfo.bEntityGenerator.generate();

		// Link Models
		ModelKey aModelKey = a.getModelKey();
		ModelKey bModelKey = b.getModelKey();

		MutableLinkChange linkAToBChange = MutableLinkChangeImpl.set(SetUtility.wrap(bModelKey));

		LinkModificationSystemRequest linkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.THIRD_LINK_NAME, linkAToBChange);

		LinkModificationSystemInstanceOptionsImpl options = new LinkModificationSystemInstanceOptionsImpl();
		options.setAutoCommit(false);	//Don't automatically commit.

		LinkModificationSystemInstance instance = this.testSystem.linkModificationSystem.makeInstance(options);
		instance.queueRequest(linkRequest);

		LinkModificationSystemChangesResult result = instance.applyChanges();

		// Assert models were linked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		b = this.testSystem.testLinkSystem.bEntity.get(b);

		assertTrue(a.getThirdKey().equals(b.getModelKey()));
		assertTrue(b.getMainKey().equals(a.getModelKey()));

		// Undo the changes
		result.undoChanges();

		// Assert models were unlinked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		b = this.testSystem.testLinkSystem.bEntity.get(b);

		assertTrue(a.getThirdKey() == null);
		assertTrue(b.getMainKey() == null);

	}

	/**
	 * Performs an undo after performing the changes to a one to many link to see if the change properly reverses.
	 */
	@Test
	public void testUndoForOneToManyLink() throws UnavailableLinkException, UnavailableLinkModelException, ConflictingLinkModificationSystemRequestException, InvalidLinkModificationSystemRequestException, LinkModificationSystemInstanceAlreadyRunException, UnexpectedLinkModificationSystemChangeException {

		LinkSystemCreationInfo linkSystemInfo = this.testSystem.testLinkSystem;

		// Generate Models
		TestLinkModelA a = linkSystemInfo.aEntityGenerator.generate();
		List<TestLinkModelA> children = linkSystemInfo.aEntityGenerator.generate(5);

		// Link Models
		ModelKey aModelKey = a.getModelKey();
		List<ModelKey> childrenKeys = ModelKey.readModelKeys(children);
		MutableLinkChange linkAChildrenToAChange = MutableLinkChangeImpl.add(childrenKeys);

		LinkModificationSystemRequest linkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.A_CHILDREN_LINK_NAME, linkAChildrenToAChange);

		LinkModificationSystemInstanceOptionsImpl options = new LinkModificationSystemInstanceOptionsImpl();
		options.setAutoCommit(false);	//Don't automatically commit.

		LinkModificationSystemInstance instance = this.testSystem.linkModificationSystem.makeInstance(options);
		instance.queueRequest(linkRequest);

		LinkModificationSystemChangesResult result = instance.applyChanges();	//Don't automatically commit.

		// Assert models were linked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		children = this.testSystem.testLinkSystem.aEntity.get(children);

		assertFalse(a.getaChildKeys().isEmpty());
		assertTrue(a.getaChildKeys().containsAll(childrenKeys));

		for (TestLinkModelA child : children) {
			assertTrue(child.getPrimaryKey().equals(a.getModelKey()));
		}

		// Unlink Models
		result.undoChanges();

		// Assert models were unlinked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		children = this.testSystem.testLinkSystem.aEntity.get(children);

		assertTrue(a.getaChildKeys().isEmpty());

		for (TestLinkModelA child : children) {
			assertTrue(child.getPrimaryKey() == null);
		}
	}

	/**
	 * Performs an undo after performing the changes to a many to many link to see if the change properly reverses.
	 */
	@Test
	public void testUndoForManyToManyLink() throws UnavailableLinkException, UnavailableLinkModelException, ConflictingLinkModificationSystemRequestException, InvalidLinkModificationSystemRequestException, LinkModificationSystemInstanceAlreadyRunException, UnexpectedLinkModificationSystemChangeException {

		LinkSystemCreationInfo linkSystemInfo = this.testSystem.testLinkSystem;

		// Generate Models
		TestLinkModelA a = linkSystemInfo.aEntityGenerator.generate();
		List<TestLinkModelB> children = linkSystemInfo.bEntityGenerator.generate(5);

		// Link Models
		ModelKey aModelKey = a.getModelKey();
		List<ModelKey> childrenKeys = ModelKey.readModelKeys(children);
		MutableLinkChange linkBChildrenToAChange = MutableLinkChangeImpl.add(childrenKeys);

		LinkModificationSystemRequest linkRequest = new LinkModificationSystemRequestImpl(TestLinkModelA.MODEL_ENTITY_NAME, aModelKey, TestLinkModelALinkSystemBuilderEntry.B_CHILDREN_LINK_NAME, linkBChildrenToAChange);

		LinkModificationSystemInstanceOptionsImpl options = new LinkModificationSystemInstanceOptionsImpl();
		options.setAutoCommit(false);	//Don't automatically commit.

		LinkModificationSystemInstance instance = this.testSystem.linkModificationSystem.makeInstance(options);
		instance.queueRequest(linkRequest);

		LinkModificationSystemChangesResult result = instance.applyChanges();

		// Assert models were linked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		children = this.testSystem.testLinkSystem.bEntity.get(children);

		assertFalse(a.getbChildKeys().isEmpty());
		assertTrue(a.getbChildKeys().containsAll(childrenKeys));

		for (TestLinkModelB child : children) {
			assertTrue(child.getParentKeys().contains(aModelKey));
		}

		// Unlink Models
		result.undoChanges();

		// Assert models were unlinked
		a = this.testSystem.testLinkSystem.aEntity.get(a);
		children = this.testSystem.testLinkSystem.bEntity.get(children);

		assertTrue(a.getbChildKeys().isEmpty());

		for (TestLinkModelB child : children) {
			assertTrue(child.getParentKeys().isEmpty());
		}

	}

	// MARK: Utilities
	@Test
	public void testBuildingRequestsWithReusableLinkModificationSystemRequestBuilder() {

		LinkSystemCreationInfo linkSystemInfo = this.testSystem.testLinkSystem;

		// Generate Models
		String modelType = TestLinkModelA.MODEL_ENTITY_NAME;
		String linkName = TestLinkModelALinkSystemBuilderEntry.B_CHILDREN_LINK_NAME;

		List<TestLinkModelA> parents = linkSystemInfo.aEntityGenerator.generate(5);
		List<TestLinkModelB> children = linkSystemInfo.bEntityGenerator.generate(5);

		List<String> primaryStringKeys = ModelKey.readStringKeys(parents);
		List<String> secondaryStringKeys = ModelKey.readStringKeys(children);

		RequestBuilderItem builder = ReusableLinkModificationSystemRequestBuilder.make(modelType);

		builder.setLinkChangeType(MutableLinkChangeType.ADD);
		builder.setKeys(secondaryStringKeys);
		builder.setLinkName(linkName);

		RequestBuilderGroup builderGroup = builder.makeForPrimaryKeys(primaryStringKeys);
		List<LinkModificationSystemRequest> requests = builderGroup.makeRequests();

		assertTrue(requests.size() == primaryStringKeys.size());

		LinkModificationSystemInstance instance = this.testSystem.linkModificationSystem.makeInstance();

		LinkModificationSystemUtility.queueRequests(requests, instance);

		@SuppressWarnings("unused")
		LinkModificationSystemChangesResult result = instance.applyChanges();

		// Assert models were linked
		parents = this.testSystem.testLinkSystem.aEntity.get(parents);
		children = this.testSystem.testLinkSystem.bEntity.get(children);

		for (TestLinkModelA parent : parents) {
			assertTrue(parent.getbChildKeys().size() == parents.size());
		}

		for (TestLinkModelB child : children) {
			assertTrue(child.getParentKeys().size() == parents.size());
		}

		// Unlink Models
		builder = ReusableLinkModificationSystemRequestBuilder.make(modelType);

		builder.setLinkChangeType(MutableLinkChangeType.CLEAR);

		List<String> linkNames = new ArrayList<String>();
		linkNames.add(TestLinkModelALinkSystemBuilderEntry.B_CHILDREN_LINK_NAME);
		linkNames.add(TestLinkModelALinkSystemBuilderEntry.A_CHILDREN_LINK_NAME);
		linkNames.add(TestLinkModelALinkSystemBuilderEntry.PRIMARY_LINK_NAME);

		builderGroup = builder.makeForPrimaryKeys(primaryStringKeys).makeForLinkNames(linkNames);
		List<LinkModificationSystemRequest> unlinkRequests = builderGroup.makeRequests();

		assertTrue(unlinkRequests.size() == (primaryStringKeys.size() * linkNames.size()));

		instance = this.testSystem.linkModificationSystem.makeInstance();

		LinkModificationSystemUtility.queueRequests(unlinkRequests, instance);

		result = instance.applyChanges();

		// Assert models were unlinked
		parents = this.testSystem.testLinkSystem.aEntity.get(parents);
		children = this.testSystem.testLinkSystem.bEntity.get(children);

		for (TestLinkModelA parent : parents) {
			assertTrue(parent.getbChildKeys().isEmpty());
		}

		for (TestLinkModelB child : children) {
			assertTrue(child.getParentKeys().isEmpty());
		}

	}

}
