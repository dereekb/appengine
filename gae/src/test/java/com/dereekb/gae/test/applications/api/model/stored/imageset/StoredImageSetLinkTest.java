package com.dereekb.gae.test.applications.api.model.stored.imageset;

import java.util.List;

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
		List<StoredImage> imagesInSet = this.storedImageGenerator.generate(4);

		// Clear any generated links
		storedImageSet.setIcon(null);
		storedImageSet.setImages(null);
		this.storedImageSetRegistry.save(storedImageSet, false);

		for (StoredImage imageInSet : imagesInSet) {
			imageInSet.setImageSets(null);
		}

		this.storedImageRegistry.save(imagesInSet, false);

		// Start Test Linking
		this.linkModels(this.storedImageSetLinkType, storedImageSet, this.storedImageSetIconLinkName, storedImage);
		this.linkModels(this.storedImageSetLinkType, storedImageSet, this.storedImageSetImagesLinkName, imagesInSet);

		storedImage = this.storedImageRegistry.get(storedImage);
		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);
		imagesInSet = this.storedImageRegistry.get(imagesInSet);

		Assert.assertTrue(storedImage.getObjectifyKey().equals(storedImageSet.getIcon()));

		for (StoredImage imageInSet : imagesInSet) {
			Assert.assertTrue(imageInSet.getImageSets().contains(storedImageSet.getObjectifyKey()));
		}

		// Test Unlinking
		this.unlinkModels(this.storedImageSetLinkType, storedImageSet, this.storedImageSetIconLinkName, storedImage);
		this.unlinkModels(this.storedImageSetLinkType, storedImageSet, this.storedImageSetImagesLinkName, imagesInSet);

		storedImage = this.storedImageRegistry.get(storedImage);
		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);
		imagesInSet = this.storedImageRegistry.get(imagesInSet);

		for (StoredImage imageInSet : imagesInSet) {
			Assert.assertFalse(imageInSet.getImageSets().contains(storedImageSet.getObjectifyKey()));
		}

		Assert.assertFalse(storedImage.getObjectifyKey().equals(storedImageSet.getIcon()));
	}

	@Test
	public void testLinkDeleter() {
		StoredImageSet storedImageSet = this.storedImageSetGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();
		List<StoredImage> imagesInSet = this.storedImageGenerator.generate(4);

		// Clear any generated links
		storedImageSet.setIcon(null);
		storedImageSet.setImages(null);
		this.storedImageSetRegistry.save(storedImageSet, false);

		for (StoredImage imageInSet : imagesInSet) {
			imageInSet.setImageSets(null);
		}

		this.storedImageRegistry.save(imagesInSet, false);

		// Link Together
		this.linkModels(this.storedImageSetLinkType, storedImageSet, this.storedImageSetIconLinkName, storedImage);
		this.linkModels(this.storedImageSetLinkType, storedImageSet, this.storedImageSetImagesLinkName, imagesInSet);

		// Delete Links
		LinkDeleterServiceRequest request = new LinkDeleterServiceRequestImpl(this.storedImageSetLinkType,
		        storedImageSet.getModelKey());
		this.linkDeleterService.deleteLinks(request);

		// Check Not Linked. Deletable Items will be deleted via taskqueue most
		// likely.
		storedImage = this.storedImageRegistry.get(storedImage);
		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);
		imagesInSet = this.storedImageRegistry.get(imagesInSet);

		for (StoredImage imageInSet : imagesInSet) {
			Assert.assertFalse(imageInSet.getImageSets().contains(storedImageSet.getObjectifyKey()));
		}

		Assert.assertFalse(storedImage.getObjectifyKey().equals(storedImageSet.getIcon()));
	}

}
