package com.dereekb.gae.test.applications.api.api.geoplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.test.applications.api.api.tests.ApiSearchTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;


public class GeoPlaceApiSearchTest extends ApiSearchTest<GeoPlace> {

	@Override
	@Autowired
	@Qualifier("geoPlaceSearchType")
	public void setSearchType(String type) {
		super.setSearchType(type);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	public void setGenerator(TestModelGenerator<GeoPlace> generator) {
		super.setGenerator(generator);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceDocumentIndexService")
	public void setIndexService(DocumentIndexService<GeoPlace> service) {
		super.setIndexService(service);
	}

}
