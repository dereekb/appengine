package com.dereekb.gae.test.applications.api.model.stored.imageset;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.links.deleter.LinkDeleterServiceRequest;
import com.dereekb.gae.model.extension.links.deleter.impl.LinkDeleterServiceRequestImpl;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.model.extension.links.AbstractLinkServiceTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class StoredImageSetLinkTest extends AbstractLinkServiceTest {

	@Autowired
	@Qualifier("storedImageSetIconLinkName")
	private String storedImageSetIconLinkName;

	@Autowired
	@Qualifier("storedImageSetImagesLinkName")
	private String storedImageSetImagesLinkName;

	// MARK: Components
	@Autowired
	@Qualifier("storedImageType")
	private String storedImageLinkType;

	@Autowired
	@Qualifier("storedImageSetType")
	private String storedImageSetLinkType;

	@Autowired
	@Qualifier("storedImageRegistry")
	private ObjectifyRegistry<StoredImage> storedImageRegistry;

	@Autowired
	@Qualifier("storedImageSetRegistry")
	private ObjectifyRegistry<StoredImageSet> storedImageSetRegistry;

	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	private TestModelGenerator<StoredImage> storedImageGenerator;

	@Autowired
	@Qualifier("storedImageSetTestModelGenerator")
	private TestModelGenerator<StoredImageSet> storedImageSetGenerator;

	@Test
	public void testLinkingIcon() {
		StoredImageSet storedImageSet = this.storedImageSetGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();

		// Clear any generated links
		storedImageSet.setIcon(null);
		this.storedImageSetRegistry.save(storedImageSet, false);

		// Start Test Linking
		this.linkModels(this.storedImageSetLinkType, storedImageSet.getModelKey(), this.storedImageSetIconLinkName,
		        storedImage.getModelKey());

		storedImage = this.storedImageRegistry.get(storedImage);
		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);

		Assert.assertTrue(storedImage.getObjectifyKey().equals(storedImageSet.getIcon()));

		// Test Unlinking
		this.unlinkModels(this.storedImageSetLinkType, storedImageSet.getModelKey(), this.storedImageSetIconLinkName,
		        storedImage.getModelKey());

		storedImage = this.storedImageRegistry.get(storedImage);
		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);

		Assert.assertFalse(storedImage.getObjectifyKey().equals(storedImageSet.getIcon()));
	}

	@Test
	public void testLinkDeleter() {
		StoredImageSet storedImageSet = this.storedImageSetGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();

		// Clear all generated links
		storedImageSet.setIcon(null);
		this.storedImageSetRegistry.save(storedImageSet, false);

		// Link Together
		this.linkModels(this.storedImageSetLinkType, storedImageSet.getModelKey(), this.storedImageSetIconLinkName,
		        storedImage.getModelKey());

		// Delete Links
		LinkDeleterServiceRequest request = new LinkDeleterServiceRequestImpl(this.storedImageSetLinkType,
		        storedImageSet.getModelKey());
		this.linkDeleterService.deleteLinks(request);

		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);

		// Check Not Linked. Deletable Items will be deleted via taskqueue most
		// likely.
		Assert.assertFalse(storedImage.getObjectifyKey().equals(storedImageSet.getIcon()));
	}

}
