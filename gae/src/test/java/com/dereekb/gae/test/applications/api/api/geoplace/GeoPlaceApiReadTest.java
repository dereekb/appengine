package com.dereekb.gae.test.applications.api.api.geoplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.test.applications.api.api.tests.ApiReadTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

@Deprecated
public class GeoPlaceApiReadTest extends ApiReadTest<GeoPlace> {

	@Override
	@Autowired
	@Qualifier("geoPlaceType")
	public void setModelType(String modelType) {
		super.setModelType(modelType);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<GeoPlace> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

}
