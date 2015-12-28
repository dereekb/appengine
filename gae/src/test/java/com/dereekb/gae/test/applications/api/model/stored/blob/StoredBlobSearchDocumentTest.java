package com.dereekb.gae.test.applications.api.model.stored.blob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.StagedDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.test.applications.api.model.tests.extension.ModelSearchDocumentTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class StoredBlobSearchDocumentTest extends ModelSearchDocumentTest<StoredBlob> {

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

	@Override
	@Autowired
	@Qualifier("storedBlobSearchDocumentBuilder")
	public void setBuilder(StagedDocumentBuilder<StoredBlob> builder) {
		super.setBuilder(builder);
	}

}
