package com.dereekb.gae.test.applications.api.model.geoplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;

public class GeoPlaceSearchDocumentTest extends ApiApplicationTestContext {


	@Autowired
	@Qualifier("geoPlaceDocumentIndexService")
	private DocumentIndexService<GeoPlace> service;

	// MARK: Indexing
	public void testDocumentBuilding() {

	}

	public void testIndexing() {

	}

}
