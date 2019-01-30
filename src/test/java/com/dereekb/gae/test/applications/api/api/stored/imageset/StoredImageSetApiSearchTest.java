package com.dereekb.gae.test.applications.api.api.stored.imageset;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.test.applications.api.api.tests.ApiSearchTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;


@Ignore
@Deprecated
public class StoredImageSetApiSearchTest extends ApiSearchTest<StoredImageSet> {

	@Override
	@Autowired
	@Qualifier("storedImageSetSearchType")
	public void setSearchType(String type) {
		super.setSearchType(type);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetTestModelGenerator")
	public void setGenerator(TestModelGenerator<StoredImageSet> generator) {
		super.setGenerator(generator);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetDocumentIndexService")
	public void setIndexService(DocumentIndexService<StoredImageSet> service) {
		super.setIndexService(service);
	}

}
