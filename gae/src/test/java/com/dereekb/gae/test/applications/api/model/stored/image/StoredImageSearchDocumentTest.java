package com.dereekb.gae.test.applications.api.model.stored.image;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.StagedDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.google.appengine.api.search.Document;

public class StoredImageSearchDocumentTest extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("geoPlaceTestModelGenerator")
	private TestModelGenerator<GeoPlace> geoPlaceGenerator;

	@Autowired
	@Qualifier("storedBlobTestModelGenerator")
	private TestModelGenerator<StoredBlob> storedBlobGenerator;

	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	private TestModelGenerator<StoredImage> storedImageGenerator;

	@Autowired
	@Qualifier("storedImageDocumentIndexService")
	private DocumentIndexService<StoredImage> service;

	@Autowired
	@Qualifier("storedImageSearchDocumentBuilder")
	private StagedDocumentBuilder<StoredImage> builder;

	// MARK: Indexing
	@Test
	public void testDocumentBuilding() {
		StoredImage storedImage = this.storedImageGenerator.generate();

		GeoPlace geoPlace = this.geoPlaceGenerator.generate();
		storedImage.setGeoPlace(geoPlace.getObjectifyKey());

		StoredBlob storedBlob = this.storedBlobGenerator.generate();
		storedImage.setBlob(storedBlob.getObjectifyKey());

		Document document = this.builder.buildSearchDocument(storedImage);
		Assert.assertNotNull(document);

		// Test with null GeoPlace
		storedImage.setGeoPlace(null);
		document = this.builder.buildSearchDocument(storedImage);
		Assert.assertNotNull(document);

	}

	public void testIndexing() {

	}

}
