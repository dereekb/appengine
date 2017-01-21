package com.dereekb.gae.test.applications.api.model.stored.image;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.links.deleter.LinkDeleterServiceRequest;
import com.dereekb.gae.model.extension.links.deleter.impl.LinkDeleterServiceRequestImpl;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.model.extension.links.AbstractLinkServiceTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class StoredImageLinkTest extends AbstractLinkServiceTest {

	@Autowired
	@Qualifier("storedImageStoredBlobLinkName")
	private String storedImageStoredBlobLinkName;

	// MARK: Components
	@Autowired
	@Qualifier("storedImageType")
	private String storedImageLinkType;

	@Autowired
	@Qualifier("storedImageSetType")
	private String storedImageSetLinkType;

	@Autowired
	@Qualifier("storedBlobType")
	private String storedBlobLinkType;

	@Autowired
	@Qualifier("storedBlobRegistry")
	private ObjectifyRegistry<StoredBlob> storedBlobRegistry;

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

	@Autowired
	@Qualifier("storedBlobTestModelGenerator")
	private TestModelGenerator<StoredBlob> storedBlobGenerator;

	@Test
	public void testLinkingToStoredBlob() {
		StoredBlob storedBlob = this.storedBlobGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();

		// Clear any generated links
		storedBlob.setDescriptor(null);
		this.storedBlobRegistry.save(storedBlob, false);

		storedImage.setStoredBlob(null);
		this.storedImageRegistry.save(storedImage, false);

		// Start Test Linking
		this.linkModels(this.storedImageLinkType, storedImage.getModelKey(), this.storedImageStoredBlobLinkName,
		        storedBlob.getModelKey());

		storedImage = this.storedImageRegistry.get(storedImage);
		storedBlob = this.storedBlobRegistry.get(storedBlob);

		Assert.assertTrue(storedBlob.getObjectifyKey().equals(storedImage.getStoredBlob()));
		Assert.assertTrue(storedImage.equals(storedBlob.getDescriptor()));

		// Test Unlinking
		this.unlinkModels(this.storedImageLinkType, storedImage.getModelKey(), this.storedImageStoredBlobLinkName,
		        storedBlob.getModelKey());

		storedImage = this.storedImageRegistry.get(storedImage);
		storedBlob = this.storedBlobRegistry.get(storedBlob);

		Assert.assertFalse(storedBlob.getObjectifyKey().equals(storedImage.getStoredBlob()));
		Assert.assertFalse(storedImage.equals(storedBlob.getDescriptor()));
	}

	// TODO: Move to StoredImageSet Unit Tests
	/*
	 * @Test
	 * public void testLinkingToStoredImageSet() {
	 * StoredImageSet storedImageSet = this.storedImageSetGenerator.generate();
	 * StoredImage storedImage = this.storedImageGenerator.generate();
	 * 
	 * // Clear any generated links
	 * storedImageSet.setImages(null);
	 * this.storedImageSetRegistry.save(storedImageSet, false);
	 * 
	 * this.storedImageRegistry.save(storedImage, false);
	 * 
	 * // Start Test Linking
	 * this.linkModels(this.storedImageLinkType, storedImage.getModelKey(),
	 * this.storedImageStoredImageSetLinkName,
	 * storedImageSet.getModelKey());
	 * 
	 * storedImage = this.storedImageRegistry.get(storedImage);
	 * storedImageSet = this.storedImageSetRegistry.get(storedImageSet);
	 * 
	 * Assert.assertTrue(storedImageSet.getImages().contains(storedImage.
	 * getObjectifyKey()));
	 * 
	 * // Test Unlinking
	 * this.unlinkModels(this.storedImageLinkType, storedImage.getModelKey(),
	 * this.storedImageStoredImageSetLinkName,
	 * storedImageSet.getModelKey());
	 * 
	 * storedImage = this.storedImageRegistry.get(storedImage);
	 * storedImageSet = this.storedImageSetRegistry.get(storedImageSet);
	 * 
	 * Assert.assertFalse(storedImageSet.getImages().contains(storedImage.
	 * getObjectifyKey()));
	 * }
	 */
	@Test
	public void testLinkDeleter() {
		StoredImage storedImage = this.storedImageGenerator.generate();
		StoredBlob storedBlob = this.storedBlobGenerator.generate();

		// Clear all generated links
		storedBlob.setDescriptor(null);
		this.storedBlobRegistry.save(storedBlob, false);

		storedImage.setStoredBlob(null);
		this.storedImageRegistry.save(storedImage, false);

		// Link Together
		this.linkModels(this.storedImageLinkType, storedImage.getModelKey(), this.storedImageStoredBlobLinkName,
		        storedBlob.getModelKey());

		storedImage = this.storedImageRegistry.get(storedImage);

		// Delete Links
		LinkDeleterServiceRequest request = new LinkDeleterServiceRequestImpl(this.storedImageLinkType,
		        storedImage.getModelKey());
		this.linkDeleterService.deleteLinks(request);

		storedBlob = this.storedBlobRegistry.get(storedBlob);
		storedImage = this.storedImageRegistry.get(storedImage);

		// Check Not Linked. Deletable Items should be deleted via taskqueue.
		Assert.assertFalse(storedBlob.getObjectifyKey().equals(storedImage.getStoredBlob()));
		Assert.assertFalse(storedImage.equals(storedBlob.getDescriptor()));
	}

}
