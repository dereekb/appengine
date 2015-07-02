package com.dereekb.gae.test.model.extension.link;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkData;
import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.model.extension.links.components.impl.RelationImpl;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.system.impl.SimpleLinkSystem;
import com.dereekb.gae.model.extension.links.components.system.impl.bidirectional.BidirectionalLinkSystem;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.link.model.DifferentLinkModel;
import com.dereekb.gae.test.model.extension.link.model.DifferentLinkModelDelegate;
import com.dereekb.gae.test.model.extension.link.model.SomeLinkModel;
import com.dereekb.gae.test.model.extension.link.model.SomeLinkModelDelegate;

public class BidirectionalLinkSystemTest {

	private BidirectionalLinkSystem buildSystem(boolean useSingleSet) {
		SimpleLinkSystem system = new SimpleLinkSystem();

		DifferentLinkModelDelegate differentLinkDelegate = new DifferentLinkModelDelegate();
		differentLinkDelegate.setUseSingleSet(useSingleSet);
		system.addEntry(differentLinkDelegate);

		SomeLinkModelDelegate someLinkDelegate = new SomeLinkModelDelegate();
		system.addEntry(someLinkDelegate);
		someLinkDelegate.setUseSingleSet(useSingleSet);

		BidirectionalLinkSystem bidirectionalSystem = new BidirectionalLinkSystem(system);

		bidirectionalSystem.addEntry(differentLinkDelegate);
		bidirectionalSystem.addEntry(someLinkDelegate);

		return bidirectionalSystem;
	}

	@Test
	public void testLinkSystemComponents() {
		// Create a new system. This will create the model types as a singleton.
		BidirectionalLinkSystem system = this.buildSystem(true);

		// SomeLinkModel uses Long
		String entryType = SomeLinkModel.MODEL_TYPE;

		LinkModelSet set = system.loadSet(entryType);
		List<ModelKey> keys = new ArrayList<ModelKey>();

		ModelKey keyA = new ModelKey(1L);

		keys.add(keyA);
		keys.add(new ModelKey(2L));
		keys.add(new ModelKey(3L));

		// Load Multiple Models
		set.loadModels(keys);

		Collection<LinkModel> loadedModels = set.getLinkModels();
		Assert.assertTrue(loadedModels.size() == keys.size());

		// Load Single Models
		LinkModel model = set.getModelForKey(keyA);
		Assert.assertTrue(model != null);
		Assert.assertTrue(model.getModelKey() != null);

		ModelKey loadedModelKey = model.getModelKey();
		Assert.assertTrue(keyA.equals(loadedModelKey));

		Collection<Link> links = model.getLinks();
		Assert.assertTrue(links != null);

		Link differentModelLink = model.getLink(SomeLinkModel.DIFFERENT_MODEL_LINKS_NAME);

		// Added a new relation to all keys.
		String stringKey = "ThisIsATestStringKey";
		ModelKey stringModelKey = new ModelKey(stringKey);

		Relation relation = new RelationImpl(stringModelKey);
		differentModelLink.addRelation(relation);

		// Check the relations
		Link linkingsLink = model.getLink(SomeLinkModel.DIFFERENT_MODEL_LINKS_NAME);
		LinkData linkData = linkingsLink.getLinkData();
		List<ModelKey> relationKeys = linkData.getRelationKeys();

		Assert.assertTrue(relationKeys.size() == 1);
		Assert.assertTrue(relationKeys.contains(stringModelKey));

		// Save the changes
		set.save();

		// Changes are complete, check to see if the other is in the set.
		// Remember that LinkModelSet are singletons for these tests.
		String secondaryEntryType = DifferentLinkModel.MODEL_TYPE;
		LinkModelSet linkBSet = system.loadSet(secondaryEntryType);
		LinkModel secondaryModel = linkBSet.getModelForKey(stringModelKey);

		String secondaryLinkName = DifferentLinkModel.SOME_MODEL_LINKS_NAME;
		Link secondaryPrimaryLink = secondaryModel.getLink(secondaryLinkName);
		LinkData secondaryLinkData = secondaryPrimaryLink.getLinkData();
		List<ModelKey> secondaryPrimaryKeys = secondaryLinkData.getRelationKeys();

		Assert.assertTrue(secondaryPrimaryKeys.size() == 1);
		Assert.assertTrue(secondaryPrimaryKeys.contains(keyA)); // Is connected
																// to the
																// primary key
	}

}