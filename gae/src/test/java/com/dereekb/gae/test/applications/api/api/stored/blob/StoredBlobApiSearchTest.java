package com.dereekb.gae.test.applications.api.api.stored.blob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.test.applications.api.api.tests.ApiSearchTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;


public class StoredBlobApiSearchTest extends ApiSearchTest<StoredBlob> {

	@Override
	@Autowired
	@Qualifier("storedBlobSearchType")
	public void setSearchType(String type) {
		super.setSearchType(type);
	}

	@Override
	@Autowired
	@Qualifier("storedBlobTestModelGenerator")
	public void setGenerator(TestModelGenerator<StoredBlob> generator) {
		super.setGenerator(generator);
	}

	@Override
	@Autowired
	@Qualifier("storedBlobDocumentIndexService")
	public void setIndexService(DocumentIndexService<StoredBlob> service) {
		super.setIndexService(service);
	}

}
