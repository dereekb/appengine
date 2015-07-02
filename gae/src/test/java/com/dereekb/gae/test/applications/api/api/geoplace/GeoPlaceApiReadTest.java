package com.dereekb.gae.test.applications.api.api.geoplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.test.applications.api.api.ApiReadTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.web.api.model.controller.ReadModelController;

public class GeoPlaceApiReadTest extends ApiReadTest<GeoPlace> {

	@Override
	@Autowired
	@Qualifier("geoPlaceReadController")
	public void setController(ReadModelController<GeoPlace> controller) {
		super.setController(controller);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<GeoPlace> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
