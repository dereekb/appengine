package com.dereekb.gae.test.applications.api.model.stored.image;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.model.extension.links.components.impl.RelationImpl;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
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
	@Qualifier("storedImageGeoPlaceLinkName")
	private String storedImageGeoPlaceLinkName;

	@Autowired
	@Qualifier("storedImageStoredBlobLinkName")
	private String storedImageStoredBlobLinkName;

	@Autowired
	@Qualifier("storedImageStoredImageSetLinkName")
	private String storedImageStoredImageSetLinkName;

	// MARK: Components
	@Autowired
	@Qualifier("geoPlaceType")
	private String geoPlaceLinkType;

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
	@Qualifier("geoPlaceRegistry")
	private ObjectifyRegistry<GeoPlace> geoPlaceRegistry;

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
	@Qualifier("geoPlaceTestModelGenerator")
	private TestModelGenerator<GeoPlace> geoPlaceGenerator;

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

	@Test
	public void testLinkingToGeoPlace() {
		GeoPlace geoPlace = this.geoPlaceGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();

		// Clear any generated links
		geoPlace.setDescriptor(null);
		this.geoPlaceRegistry.save(geoPlace, false);

		storedImage.setGeoPlace(null);
		this.storedImageRegistry.save(storedImage, false);

		// Start Test Linking
		this.linkModels(this.storedImageLinkType, storedImage.getModelKey(), this.storedImageGeoPlaceLinkName,
		        geoPlace.getModelKey());

		storedImage = this.storedImageRegistry.get(storedImage);
		geoPlace = this.geoPlaceRegistry.get(geoPlace);

		Assert.assertTrue(geoPlace.getObjectifyKey().equals(storedImage.getGeoPlace()));
		Assert.assertTrue(storedImage.equals(geoPlace.getDescriptor()));

		// Test Unlinking
		this.unlinkModels(this.storedImageLinkType, storedImage.getModelKey(), this.storedImageGeoPlaceLinkName,
		        geoPlace.getModelKey());

		storedImage = this.storedImageRegistry.get(storedImage);
		geoPlace = this.geoPlaceRegistry.get(geoPlace);

		Assert.assertFalse(geoPlace.getObjectifyKey().equals(storedImage.getGeoPlace()));
		Assert.assertFalse(storedImage.equals(geoPlace.getDescriptor()));
	}

	@Test
	public void testLinkingToStoredImageSet() {
		StoredImageSet storedImageSet = this.storedImageSetGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();

		// Clear any generated links
		storedImageSet.setImages(null);
		this.storedImageSetRegistry.save(storedImageSet, false);

		storedImage.setImageSets(null);
		this.storedImageRegistry.save(storedImage, false);

		// Start Test Linking
		this.linkModels(this.storedImageLinkType, storedImage.getModelKey(), this.storedImageStoredImageSetLinkName,
		        storedImageSet.getModelKey());

		storedImage = this.storedImageRegistry.get(storedImage);
		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);

		Assert.assertTrue(storedImage.getImageSets().contains(storedImageSet.getObjectifyKey()));
		Assert.assertTrue(storedImageSet.getImages().contains(storedImage.getObjectifyKey()));

		// Test Unlinking
		this.unlinkModels(this.storedImageLinkType, storedImage.getModelKey(), this.storedImageStoredImageSetLinkName,
		        storedImageSet.getModelKey());

		storedImage = this.storedImageRegistry.get(storedImage);
		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);

		Assert.assertFalse(storedImage.getImageSets().contains(storedImageSet.getObjectifyKey()));
		Assert.assertFalse(storedImageSet.getImages().contains(storedImage.getObjectifyKey()));
	}

	@Test
	public void testLinkDeleter() {
		StoredImageSet storedImageSet = this.storedImageSetGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();
		StoredBlob storedBlob = this.storedBlobGenerator.generate();
		GeoPlace geoPlace = this.geoPlaceGenerator.generate();

		// Clear all generated links
		storedBlob.setDescriptor(null);
		this.storedBlobRegistry.save(storedBlob, false);

		geoPlace.setDescriptor(null);
		this.geoPlaceRegistry.save(geoPlace, false);

		storedImageSet.setImages(null);
		this.storedImageSetRegistry.save(storedImageSet, false);

		storedImage.setStoredBlob(null);
		storedImage.setGeoPlace(null);
		storedImage.setImageSets(null);
		this.storedImageRegistry.save(storedImage, false);

		// Link Together
		this.linkModels(this.storedImageLinkType, storedImage.getModelKey(), this.storedImageGeoPlaceLinkName,
		        geoPlace.getModelKey());
		this.linkModels(this.storedImageLinkType, storedImage.getModelKey(), this.storedImageStoredBlobLinkName,
		        storedBlob.getModelKey());
		this.linkModels(this.storedImageLinkType, storedImage.getModelKey(), this.storedImageStoredImageSetLinkName,
		        storedImageSet.getModelKey());

		storedImage = this.storedImageRegistry.get(storedImage);

		// Delete Links
		LinkDeleterServiceRequest request = new LinkDeleterServiceRequestImpl(this.storedImageLinkType,
		        storedImage.getModelKey());
		this.linkDeleterService.deleteLinks(request);

		geoPlace = this.geoPlaceRegistry.get(geoPlace);
		storedBlob = this.storedBlobRegistry.get(storedBlob);
		storedImage = this.storedImageRegistry.get(storedImage);
		storedImageSet = this.storedImageSetRegistry.get(storedImageSet);

		// Check Not Linked. Deletable Items will be deleted via taskqueue most
		// likely.
		Assert.assertFalse(storedBlob.getObjectifyKey().equals(storedImage.getStoredBlob()));
		Assert.assertFalse(storedImage.equals(storedBlob.getDescriptor()));

		Assert.assertFalse(geoPlace.getObjectifyKey().equals(storedImage.getGeoPlace()));
		Assert.assertFalse(storedImage.equals(geoPlace.getDescriptor()));

		Assert.assertFalse(storedImage.getImageSets().contains(storedImageSet.getObjectifyKey()));
		Assert.assertFalse(storedImageSet.getImages().contains(storedImage.getObjectifyKey()));
	}

	// MARK: Special Conditions
	/**
	 * The geo place cannot be linked to a stored image that already has a
	 * geoplace.
	 */
	@Test
	public void testLinkingToImageBeforeClearingImage() {

		// TODO: Update.

		GeoPlace geoPlace = this.geoPlaceGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();

		LinkModelSet geoPlaceSet = this.linkSystem.loadSet(this.geoPlaceLinkType);
		geoPlaceSet.loadModel(geoPlace.getModelKey());

		LinkModel geoPlaceLinkModel = geoPlaceSet.getModelForKey(geoPlace.getModelKey());
		Link storedImageLink = geoPlaceLinkModel.getLink(this.storedImageLinkType);

		if (storedImage.getGeoPlace() != null) {
			try {
				RelationImpl addImage = new RelationImpl(storedImage.getModelKey());
				storedImageLink.addRelation(addImage);

				// SHould not be allowed if storedImage is set.
				Assert.fail();
			} catch (RelationChangeException e) {

			}
		}
	}

	/**
	 *
	 */
	@Test
	public void testLinkingToImageAfterClearingImage() {

		GeoPlace geoPlace = this.geoPlaceGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();

		storedImage.setGeoPlace(null);
		this.storedImageRegistry.save(storedImage, false);

		LinkModelSet geoPlaceSet = this.linkSystem.loadSet(this.geoPlaceLinkType);
		geoPlaceSet.loadModel(geoPlace.getModelKey());

		LinkModel geoPlaceLinkModel = geoPlaceSet.getModelForKey(geoPlace.getModelKey());
		Link storedImageLink = geoPlaceLinkModel.getLink(this.storedImageLinkType);

		RelationImpl addImage = new RelationImpl(storedImage.getModelKey());
		storedImageLink.addRelation(addImage);

		geoPlaceSet.save(true);

		storedImage = this.storedImageRegistry.get(storedImage);

		Assert.assertTrue(geoPlace.getObjectifyKey().equals(storedImage.getGeoPlace()));
	}

}
