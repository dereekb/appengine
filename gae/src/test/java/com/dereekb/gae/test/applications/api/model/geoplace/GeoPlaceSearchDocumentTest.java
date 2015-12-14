package com.dereekb.gae.test.applications.api.model.geoplace;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.StagedDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.google.appengine.api.search.Document;

public class GeoPlaceSearchDocumentTest extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	private TestModelGenerator<GeoPlace> geoPlaceGenerator;

	@Autowired
	@Qualifier("geoPlaceDocumentIndexService")
	private DocumentIndexService<GeoPlace> service;

	@Autowired
	@Qualifier("geoPlaceSearchDocumentBuilder")
	private StagedDocumentBuilder<GeoPlace> builder;

	// MARK: Indexing
	@Test
	public void testDocumentBuilding() {
		GeoPlace geoPlace = this.geoPlaceGenerator.generate();
		Document document = this.builder.buildSearchDocument(geoPlace);

		Assert.assertNotNull(document);
	}

	public void testIndexing() {

	}

}
