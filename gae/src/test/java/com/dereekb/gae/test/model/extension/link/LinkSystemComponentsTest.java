package com.dereekb.gae.test.model.extension.link;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.RelationResult;
import com.dereekb.gae.model.extension.links.components.impl.RelationImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.LinkCollectionImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringModelKeyConverterImpl;

public class LinkSystemComponentsTest {

	private BidirectionalConverter<String, ModelKey> stringConverter = StringModelKeyConverterImpl.CONVERTER;

	// MARK: LinkCollectionImpl
	@Test
	public void testLinkCollectionImplAddingAndRemoval() {

		LinkCollectionImpl<String> link = this.makeTestLink();

		// Test Adding Single
		String singleValue = "A";
		RelationImpl singleRelation = new RelationImpl(new ModelKey("A"));
		RelationResult result = link.addRelation(singleRelation);

		Assert.assertTrue(link.getCollection().contains(singleValue));
		Assert.assertTrue(result.getRedundant().isEmpty());
		Assert.assertFalse(result.getHits().isEmpty());

		// Test Removing Single
		result = link.removeRelation(singleRelation);

		Assert.assertFalse(link.getCollection().contains(singleValue));
		Assert.assertTrue(result.getRedundant().isEmpty());
		Assert.assertFalse(result.getHits().isEmpty());

	}

	private LinkCollectionImpl<String> makeTestLink() {
		LinkInfo info = null;
		Set<String> collection = new HashSet<String>();
		LinkCollectionImpl<String> link = new LinkCollectionImpl<String>(info, collection, this.stringConverter);
		return link;
	}

}
