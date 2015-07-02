package com.dereekb.gae.test.applications.api.api.geoplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.dto.GeoPlaceData;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.test.applications.api.api.ApiEditTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.controller.EditModelController;

public class GeoPlaceApiEditTest extends ApiEditTest<GeoPlace, GeoPlaceData> {

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

}
