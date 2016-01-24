package com.dereekb.gae.test.applications.api.model.stored.image;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.RelationResult;
import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.model.extension.links.components.impl.RelationImpl;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.server.datastore.GetterSetter;
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
	@Qualifier("geoPlaceTestModelGenerator")
	private TestModelGenerator<GeoPlace> geoPlaceGenerator;

	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	private TestModelGenerator<StoredImage> storedImageGenerator;

	@Autowired
	@Qualifier("storedBlobTestModelGenerator")
	private TestModelGenerator<StoredBlob> storedBlobGenerator;

	@Autowired
	@Qualifier("storedImageRegistry")
	private GetterSetter<StoredImage> storedImageGetterSetter;

	@Test
	public void testLinkingToStoredBlob() {

		StoredBlob storedBlob = this.storedBlobGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();

		// Clear any generated links
		storedBlob.setDescriptor(null);
		this.storedBlobRegistry.save(storedBlob, false);

		storedImage.setBlob(null);
		this.storedImageRegistry.save(storedImage, false);

		// Start Test Linking
		LinkModelSet storedImageLinkSet = this.linkSystem.loadSet(this.storedImageLinkType);
		storedImageLinkSet.loadModel(storedImage.getModelKey());
		LinkModel model = storedImageLinkSet.getModelForKey(storedImage.getModelKey());

		Link storedBlobLink = model.getLink(this.storedImageStoredBlobLinkName);

		Assert.assertNotNull(storedBlobLink);

		RelationImpl setBlob = new RelationImpl(storedBlob.getModelKey());
		RelationResult result = storedBlobLink.setRelation(setBlob);

		storedImageLinkSet.save(true);

		storedImage = this.storedImageRegistry.get(storedImage);
		storedBlob = this.storedBlobRegistry.get(storedBlob);

		Assert.assertTrue(storedImage.getBlob().equals(storedBlob.getObjectifyKey()));
		Assert.assertTrue(storedBlob.getDescriptor().equals(storedImage));

	}

	@Test
	public void testLinkingToGeoPlace() {

		GeoPlace geoPlace = this.geoPlaceGenerator.generate();
		StoredBlob storedBlob = this.storedBlobGenerator.generate();
		StoredImage storedImage = this.storedImageGenerator.generate();


		LinkModelSet geoPlaceSet = this.linkSystem.loadSet(this.geoPlaceLinkType);
		geoPlaceSet.loadModel(geoPlace.getModelKey());

		LinkModel geoPlaceLinkModel = geoPlaceSet.getModelForKey(geoPlace.getModelKey());
		Link storedImageLink = geoPlaceLinkModel.getLink(this.storedImageLinkType);

		storedImage.setGeoPlace(geoPlace.getObjectifyKey());
		geoPlace.setDescriptor(storedImage);

		this.geoPlaceRegistry.save(geoPlace, false);
		this.storedImageRegistry.save(storedImage, false);

	}

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
		this.storedImageGetterSetter.save(storedImage, false);

		LinkModelSet geoPlaceSet = this.linkSystem.loadSet(this.geoPlaceLinkType);
		geoPlaceSet.loadModel(geoPlace.getModelKey());

		LinkModel geoPlaceLinkModel = geoPlaceSet.getModelForKey(geoPlace.getModelKey());
		Link storedImageLink = geoPlaceLinkModel.getLink(this.storedImageLinkType);

		RelationImpl addImage = new RelationImpl(storedImage.getModelKey());
		storedImageLink.addRelation(addImage);

		geoPlaceSet.save(true);

		storedImage = this.storedImageGetterSetter.get(storedImage);

		Assert.assertTrue(geoPlace.getObjectifyKey().equals(storedImage.getGeoPlace()));
	}

}
