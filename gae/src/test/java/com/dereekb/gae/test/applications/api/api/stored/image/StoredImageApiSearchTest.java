package com.dereekb.gae.test.applications.api.api.stored.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.test.applications.api.api.tests.ApiSearchTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;


public class StoredImageApiSearchTest extends ApiSearchTest<StoredImage> {

	@Override
	@Autowired
	@Qualifier("storedImageSearchType")
	public void setSearchType(String type) {
		super.setSearchType(type);
	}

	@Override
	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	public void setGenerator(TestModelGenerator<StoredImage> generator) {
		super.setGenerator(generator);
	}

	@Override
	@Autowired
	@Qualifier("storedImageDocumentIndexService")
	public void setIndexService(DocumentIndexService<StoredImage> service) {
		super.setIndexService(service);
	}

}
