package com.dereekb.gae.test.model.extension.link;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkData;
import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.model.extension.links.components.impl.RelationImpl;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.model.change.LinkChange;
import com.dereekb.gae.model.extension.links.components.model.change.LinkModelChange;
import com.dereekb.gae.model.extension.links.components.model.change.LinkModelSetChange;
import com.dereekb.gae.model.extension.links.components.system.impl.SimpleLinkSystem;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.link.model.DifferentLinkModelDelegate;
import com.dereekb.gae.test.model.extension.link.model.SomeLinkModel;
import com.dereekb.gae.test.model.extension.link.model.SomeLinkModelDelegate;

/**
 *
 * @author dereekb
 *
 */
public class SimpleLinkSystemTest {

	private SimpleLinkSystem buildSystem() {
		SimpleLinkSystem system = new SimpleLinkSystem();

		DifferentLinkModelDelegate differentLinkDelegate = new DifferentLinkModelDelegate();
		system.addEntry(differentLinkDelegate);

		SomeLinkModelDelegate someLinkDelegate = new SomeLinkModelDelegate();
		system.addEntry(someLinkDelegate);

		return system;
	}

	@Test
	public void testLinkSystemComponents() {
		SimpleLinkSystem system = this.buildSystem();

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
		set.save(false);

		// Changes are complete.
		LinkModelSetChange setChanges = set.getChanges();
		Map<ModelKey, LinkModelChange> modelChanges = setChanges.getModelChanges();

		Assert.assertFalse(modelChanges.isEmpty());

		LinkModelChange modelAChanges = modelChanges.get(keyA);
		Assert.assertTrue(modelAChanges != null);

		LinkChange linkChange = modelAChanges.getChangesForLink(SomeLinkModel.DIFFERENT_MODEL_LINKS_NAME);
		Set<ModelKey> addedKey = linkChange.getAddedKeys();
		Assert.assertFalse(addedKey.isEmpty());
		Assert.assertTrue(addedKey.contains(stringModelKey));

	}

}
