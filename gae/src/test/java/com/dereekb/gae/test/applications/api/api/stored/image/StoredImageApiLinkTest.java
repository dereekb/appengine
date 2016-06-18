package com.dereekb.gae.test.applications.api.api.stored.image;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.links.service.LinkChangeAction;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.test.applications.api.api.tests.ApiLinkTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.extension.link.ApiLinkChange;
import com.dereekb.gae.web.api.shared.request.ApiRequest;


public class StoredImageApiLinkTest extends ApiLinkTest<StoredImage> {

	@Override
	@Autowired
	@Qualifier("storedImageType")
	public void setModelType(String modelType) {
		super.setModelType(modelType);
	}

	@Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	private TestModelGenerator<GeoPlace> geoPlaceGenerator;

	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	private TestModelGenerator<StoredImage> storedImageGenerator;

	@Autowired
	@Qualifier("storedImageRegistry")
	private GetterSetter<StoredImage> storedImageGetterSetter;

	@Autowired
	@Qualifier("geoPlaceRegistry")
	private GetterSetter<GeoPlace> geoPlaceGetterSetter;

	@Test
	public void testLinkingGeoPlace() {
		StoredImage image = this.storedImageGenerator.generate();

		image.setGeoPlace(null);
		this.storedImageGetterSetter.save(image, false);

		GeoPlace place = this.geoPlaceGenerator.generate();
		place.setDescriptor(null);

		this.geoPlaceGetterSetter.save(place, false);

		ApiRequest<ApiLinkChange> request = this.buildRequest(LinkChangeAction.LINK, "GeoPlace", "GeoPlace",
		        image, place);
		this.performRequest(request);

		image = this.storedImageGetterSetter.get(image);
		place = this.geoPlaceGetterSetter.get(place);

		Assert.assertNotNull(image.getGeoPlace());
		Assert.assertTrue(image.getGeoPlace().equivalent(place.getObjectifyKey()));
		Assert.assertNotNull(place.getDescriptor());
		Assert.assertTrue(place.getDescriptorType().equals(this.getModelType()));
		Assert.assertTrue(place.getDescriptorId().equals(image.getModelKey().keyAsString()));
	}

}
