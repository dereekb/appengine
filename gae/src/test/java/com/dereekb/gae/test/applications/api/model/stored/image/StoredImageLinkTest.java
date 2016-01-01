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
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.test.applications.api.model.extension.links.AbstractLinkServiceTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class StoredImageLinkTest extends AbstractLinkServiceTest {

	@Autowired
	@Qualifier("geoPlaceType")
	private String geoPlaceLinkType;

	@Autowired
	@Qualifier("storedImageType")
	private String storedImageLinkType;

	@Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	private TestModelGenerator<GeoPlace> geoPlaceGenerator;

	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	private TestModelGenerator<StoredImage> storedImageGenerator;

	@Autowired
	@Qualifier("storedImageRegistry")
	private GetterSetter<StoredImage> storedImageGetterSetter;

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
