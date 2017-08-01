package com.dereekb.gae.test.applications.api.api.stored.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.applications.api.api.tests.ApiSearchTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.googlecode.objectify.Key;

public class StoredImageApiSearchTest extends ApiSearchTest<StoredImage> {

	@Autowired
	@Qualifier("storedBlobTestModelGenerator")
	private TestModelGenerator<StoredBlob> storedBlobGenerator;

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

	@Override
	protected void createRelated(StoredImage model) {
		Key<StoredBlob> storedBlobKey = model.getStoredBlob();

		if (storedBlobKey != null) {
			ModelKey storedBlobModelKey = new ModelKey(storedBlobKey.getId());
			this.storedBlobGenerator.generateModel(storedBlobModelKey);
		}

	}

}
