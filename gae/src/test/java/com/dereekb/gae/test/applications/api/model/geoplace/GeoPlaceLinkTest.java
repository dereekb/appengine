package com.dereekb.gae.test.applications.api.model.geoplace;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.model.extension.links.AbstractLinkServiceTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class GeoPlaceLinkTest extends AbstractLinkServiceTest {

	@Autowired
	@Qualifier("geoPlaceType")
	private String geoPlaceLinkType;

	@Autowired
	@Qualifier("geoPlaceParentLinkName")
	private String geoPlaceParentLinkName;

	@Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	private TestModelGenerator<GeoPlace> geoPlaceGenerator;

	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	private TestModelGenerator<StoredImage> storedImageGenerator;

	@Autowired
	@Qualifier("geoPlaceRegistry")
	private ObjectifyRegistry<GeoPlace> geoPlaceRegistry;

	@Autowired
	@Qualifier("storedImageRegistry")
	private ObjectifyRegistry<StoredImage> storedImageRegistry;

	public String getGeoPlaceLinkType() {
		return this.geoPlaceLinkType;
	}

	public void setGeoPlaceLinkType(String geoPlaceLinkType) {
		this.geoPlaceLinkType = geoPlaceLinkType;
	}

	public TestModelGenerator<GeoPlace> getGeoPlaceGenerator() {
		return this.geoPlaceGenerator;
	}

	public void setGeoPlaceGenerator(TestModelGenerator<GeoPlace> geoPlaceGenerator) {
		this.geoPlaceGenerator = geoPlaceGenerator;
	}

	public TestModelGenerator<StoredImage> getStoredImageGenerator() {
		return this.storedImageGenerator;
	}

	public void setStoredImageGenerator(TestModelGenerator<StoredImage> storedImageGenerator) {
		this.storedImageGenerator = storedImageGenerator;
	}

	@Test
	public void testLinkingToParent() {
		GeoPlace child = this.geoPlaceGenerator.generate();
		GeoPlace parent = this.geoPlaceGenerator.generate();

		// Clear any generated links
		child.setParent(null);
		this.geoPlaceRegistry.save(child, false);

		// Start Test Linking
		this.linkModels(this.geoPlaceLinkType, child, this.geoPlaceParentLinkName, parent);

		child = this.geoPlaceRegistry.get(child);

		Assert.assertTrue(parent.getObjectifyKey().equals(child.getParent()));

		// Test Unlinking
		this.unlinkModels(this.geoPlaceLinkType, child, this.geoPlaceParentLinkName, parent);

		child = this.geoPlaceRegistry.get(child);

		Assert.assertFalse(parent.getObjectifyKey().equals(child.getParent()));
		Assert.assertNull(child.getParent());
	}

}
