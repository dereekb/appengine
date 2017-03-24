package com.dereekb.gae.test.applications.api.api.geoplace;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.dto.GeoPlaceData;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.api.tests.ApiEditTest;
import com.dereekb.gae.test.applications.api.api.tests.client.ClientApiCrudTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.crud.controller.EditModelController;
import com.dereekb.gae.web.api.model.crud.request.ApiDeleteRequest;

/**
 * 
 * @author dereekb
 * 
 * @deprecated Replaced with {@link ClientApiCrudTest}.
 */
@Deprecated
public class GeoPlaceApiEditTest extends ApiEditTest<GeoPlace, GeoPlaceData> {

	@Autowired
	@Qualifier("geoPlaceRegistry")
	public ObjectifyRegistry<GeoPlace> geoPlaceRegistry;

	@Override
	@Autowired
	@Qualifier("geoPlaceRegistry")
	public void setGetter(Getter<GeoPlace> getter) {
		super.setGetter(getter);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceEditController")
	public void setController(EditModelController<GeoPlace, GeoPlaceData> controller) {
		super.setController(controller);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceDataBuilder")
	public void setConverter(DirectionalConverter<GeoPlace, GeoPlaceData> converter) {
		super.setConverter(converter);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceTestDataGenerator")
	public void setModelDataGenerator(Generator<GeoPlaceData> modelDataGenerator) {
		super.setModelDataGenerator(modelDataGenerator);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<GeoPlace> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Test
	public void testDeleteRules() {
		GeoPlace geoPlace = this.modelGenerator.generate();
		String stringIdentifier = ModelKey.readStringKey(geoPlace.getModelKey());

		List<String> stringIdentifiers = new ArrayList<String>();
		stringIdentifiers.add(stringIdentifier);

		geoPlace.setDescriptorId("id");
		geoPlace.setDescriptorType("type");

		this.geoPlaceRegistry.update(geoPlace);

		Assert.assertNotNull(geoPlace.getDescriptor());

		ApiDeleteRequest request = new ApiDeleteRequest(stringIdentifiers);

		try {
			this.controller.delete(request);
			Assert.fail();
		} catch (AtomicOperationException e) {
			Assert.assertFalse(e.getUnavailableStringKeys().isEmpty());
			Assert.assertTrue(e.getUnavailableStringKeys().containsAll(stringIdentifiers));
		}

		geoPlace.setDescriptor(null);
		this.geoPlaceRegistry.update(geoPlace);

		try {
			this.controller.delete(request);
		} catch (AtomicOperationException e) {
			Assert.fail();
		}
	}

}
