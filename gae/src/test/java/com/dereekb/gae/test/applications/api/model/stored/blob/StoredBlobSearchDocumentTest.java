package com.dereekb.gae.test.applications.api.model.stored.blob;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.StagedDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchResponse;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchService;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.search.document.query.StoredBlobSearchRequest;
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

	@Autowired
	@Qualifier("storedBlobSearchService")
	private ModelDocumentSearchService<StoredBlob, StoredBlobSearchRequest> searchService;

	@Test
	public void testSearchService() {
		List<StoredBlob> models = this.getGenerator().generate(10);
		StoredBlob model = models.get(0);
		Date date = model.getDate();

		Assert.assertNotNull(date);

		this.indexService.indexChange(models, IndexAction.INDEX);

		StoredBlobSearchRequest request = new StoredBlobSearchRequest();
		ModelDocumentSearchResponse<StoredBlob> response = this.searchService.search(request);

		Collection<StoredBlob> results = response.getModelSearchResults();
		Assert.assertTrue(results.containsAll(models));

	}

}
