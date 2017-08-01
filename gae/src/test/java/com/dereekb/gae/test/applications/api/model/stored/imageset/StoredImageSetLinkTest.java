package com.dereekb.gae.test.applications.api.model.stored.imageset;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.impl.DeleteRequestImpl;
import com.dereekb.gae.model.extension.links.deleter.LinkDeleterServiceRequest;
import com.dereekb.gae.model.extension.links.deleter.impl.LinkDeleterServiceRequestImpl;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.dereekb.gae.test.applications.api.model.extension.links.AbstractLinkServiceTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.googlecode.objectify.Key;

/**
 * 
 * @author dereekb
 * 
 * @deprecated Replace with client tests.
 */
@Ignore
@Deprecated
public class StoredImageSetLinkTest extends AbstractLinkServiceTest {

	@Autowired
	@Qualifier("storedImageSetIconLinkName")
	private String storedImageSetIconLinkName;

	@Autowired
	@Qualifier("storedImageSetImagesLinkName")
	private String storedImageSetImagesLinkName;

	private ExtendedObjectifyModelKeyUtil<StoredImage> storedImageKeysUtil = ExtendedObjectifyModelKeyUtil
	        .number(StoredImage.class);

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

	@Autowired
	@Qualifier("storedImageCrudService")
	private CrudService<StoredImage> storedImageCrudService;

	@Test
	public void testLinkingIcon() {
		StoredImageSet storedImageSet = this.storedImageSetGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();
		List<StoredImage> imagesInSet = this.storedImageGenerator.generate(4);

		// Clear any generated links
		storedImageSet.setIcon(null);
		storedImageSet.setImages(null);
		this.storedImageSetRegistry.update(storedImageSet);
		this.storedImageRegistry.update(imagesInSet);

		// Start Test Linking
		this.linkModels(this.storedImageSetLinkType, storedImageSet, this.storedImageSetIconLinkName, storedImage);
		this.linkModels(this.storedImageSetLinkType, storedImageSet, this.storedImageSetImagesLinkName, imagesInSet);

		storedImage = this.storedImageRegistry.get(storedImage);
		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);
		imagesInSet = this.storedImageRegistry.get(imagesInSet);

		Assert.assertTrue(storedImage.getObjectifyKey().equals(storedImageSet.getIcon()));

		// Test Unlinking
		this.unlinkModels(this.storedImageSetLinkType, storedImageSet, this.storedImageSetIconLinkName, storedImage);
		this.unlinkModels(this.storedImageSetLinkType, storedImageSet, this.storedImageSetImagesLinkName, imagesInSet);

		storedImage = this.storedImageRegistry.get(storedImage);
		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);
		imagesInSet = this.storedImageRegistry.get(imagesInSet);

		Assert.assertFalse(storedImage.getObjectifyKey().equals(storedImageSet.getIcon()));
	}

	@Test
	public void testLinkingImage() {
		StoredImageSet storedImageSet = this.storedImageSetGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();

		// Clear any generated links
		storedImageSet.setImages(null);
		this.storedImageSetRegistry.update(storedImageSet);
		this.storedImageRegistry.update(storedImage);

		// Start Test Linking
		this.linkModels(this.storedImageSetLinkType, storedImageSet.getModelKey(), this.storedImageSetImagesLinkName,
		        storedImage.getModelKey());

		storedImage = this.storedImageRegistry.get(storedImage);
		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);

		Assert.assertTrue(storedImageSet.getImages().contains(storedImage.getObjectifyKey()));

		// Test Unlinking
		this.unlinkModels(this.storedImageSetLinkType, storedImageSet.getModelKey(), this.storedImageSetImagesLinkName,
		        storedImage.getModelKey());

		storedImage = this.storedImageRegistry.get(storedImage);
		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);

		Assert.assertFalse(storedImageSet.getImages().contains(storedImage.getObjectifyKey()));
	}

	@Test
	public void testLinkingMultipleImages() {
		StoredImageSet storedImageSet = this.storedImageSetGenerator.generate();
		List<StoredImage> storedImages = this.storedImageGenerator.generate(10);

		// Clear any generated links
		storedImageSet.setImages(null);
		this.storedImageSetRegistry.update(storedImageSet);

		List<ModelKey> keys = ModelKey.readModelKeys(storedImages);

		// Start Test Linking
		this.linkModels(this.storedImageSetLinkType, storedImageSet.getModelKey(), this.storedImageSetImagesLinkName,
		        keys);

		storedImages = this.storedImageRegistry.get(storedImages);
		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);

		Set<Key<StoredImage>> setKeys = storedImageSet.getImages();
		List<Key<StoredImage>> objectifyKeys = this.storedImageKeysUtil.convertFrom(keys);
		Assert.assertTrue(setKeys.containsAll(objectifyKeys));

		// Test Unlinking
		this.unlinkModels(this.storedImageSetLinkType, storedImageSet.getModelKey(), this.storedImageSetImagesLinkName,
		        keys);

		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);

		Assert.assertFalse(storedImageSet.getImages().containsAll(objectifyKeys));
	}

	@Test
	public void testLinkDeleter() {
		StoredImageSet storedImageSet = this.storedImageSetGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();
		List<StoredImage> imagesInSet = this.storedImageGenerator.generate(4);

		// Clear any generated links
		storedImageSet.setIcon(null);
		storedImageSet.setImages(null);
		this.storedImageSetRegistry.update(storedImageSet);
		this.storedImageRegistry.update(imagesInSet);

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

		Assert.assertFalse(storedImage.getObjectifyKey().equals(storedImageSet.getIcon()));
	}

	/**
	 * Tests that when a stored image is deleted, the taskqueue does it's magic
	 * to remove it from all StoredImageSet values that reference it.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testUnlinkFromDeletedStoredImage() throws InterruptedException {
		StoredImageSet storedImageSet = this.storedImageSetGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();

		// Clear any generated links
		storedImageSet.setImages(null);
		this.storedImageSetRegistry.update(storedImageSet);

		// Link Together
		this.linkModels(this.storedImageSetLinkType, storedImageSet, this.storedImageSetImagesLinkName, storedImage);

		// Delete the model via CRUD service.
		DeleteRequest deleteRequest = new DeleteRequestImpl(storedImage);
		this.storedImageCrudService.delete(deleteRequest);

		// Let the TaskQueue Complete
		this.waitForTaskQueueToComplete();

		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);
		Assert.assertFalse(storedImageSet.getImages().contains(storedImage.getObjectifyKey()));
	}

}
