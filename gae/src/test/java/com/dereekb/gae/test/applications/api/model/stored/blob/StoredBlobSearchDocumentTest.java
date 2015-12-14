package com.dereekb.gae.test.applications.api.model.stored.blob;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.StagedDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.google.appengine.api.search.Document;

public class StoredBlobSearchDocumentTest extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("storedBlobTestModelGenerator")
	private TestModelGenerator<StoredBlob> storedBlobGenerator;

	@Autowired
	@Qualifier("storedBlobDocumentIndexService")
	private DocumentIndexService<StoredBlob> service;

	@Autowired
	@Qualifier("storedBlobSearchDocumentBuilder")
	private StagedDocumentBuilder<StoredBlob> builder;

	// MARK: Indexing
	@Test
	public void testDocumentBuilding() {
		StoredBlob storedBlob = this.storedBlobGenerator.generate();
		Document document = this.builder.buildSearchDocument(storedBlob);

		Assert.assertNotNull(document);
	}

	public void testIndexing() {

	}

}
