package com.dereekb.gae.test.applications.api.model.geoplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.StagedDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.test.applications.api.model.tests.extension.ModelSearchDocumentTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class GeoPlaceSearchDocumentTest extends ModelSearchDocumentTest<GeoPlace> {

	@Override
	@Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	public void setGenerator(TestModelGenerator<GeoPlace> generator) {
		super.setGenerator(generator);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceDocumentIndexService")
	public void setService(DocumentIndexService<GeoPlace> service) {
		super.setService(service);
	}

	@Override
	@Autowired
	@Qualifier("geoPlaceSearchDocumentBuilder")
	public void setBuilder(StagedDocumentBuilder<GeoPlace> builder) {
		super.setBuilder(builder);
	}

}
