package com.dereekb.gae.test.applications.api.model.stored.image;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.StagedDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchResponse;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchService;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.search.document.query.StoredImageSearchRequest;
import com.dereekb.gae.test.applications.api.model.tests.extension.ModelSearchDocumentTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.test.model.extension.generator.impl.WrappedTestModelGeneratorImpl;
import com.google.appengine.api.search.Document;

@Ignore
@Deprecated
public class StoredImageSearchDocumentTest extends ModelSearchDocumentTest<StoredImage> {

	@Autowired
	@Qualifier("storedBlobTestModelGenerator")
	private TestModelGenerator<StoredBlob> storedBlobGenerator;

	@Override
	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	public void setGenerator(TestModelGenerator<StoredImage> generator) {
		super.setGenerator(new StoredImageTestModelGenerator(generator));
	}

	@Override
	@Autowired
	@Qualifier("storedImageDocumentIndexService")
	public void setIndexService(DocumentIndexService<StoredImage> service) {
		super.setIndexService(service);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSearchDocumentBuilder")
	public void setBuilder(StagedDocumentBuilder<StoredImage> builder) {
		super.setBuilder(builder);
	}

	@Autowired
	@Qualifier("storedImageSearchService")
	private ModelDocumentSearchService<StoredImage, StoredImageSearchRequest> searchService;

	@Test
	public void testSearchService() {
		List<StoredImage> models = this.generator.generate(10);

		this.indexService.indexChange(models, IndexAction.INDEX);

		StoredImageSearchRequest request = new StoredImageSearchRequest();
		ModelDocumentSearchResponse<StoredImage> response = this.searchService.search(request);

		Collection<StoredImage> results = response.getModelResults();
		Assert.assertTrue(results.containsAll(models));
	}

	// MARK: Indexing
	@Override
	@Test
	public void testDocumentBuilding() {
		StoredImage storedImage = this.generator.generate();
		super.testDocumentBuilding();

		Document document = this.builder.buildSearchDocument(storedImage);
		Assert.assertNotNull(document);
	}

	@Test
	public void testSearchIndexFields() {
		StoredImage model = this.generator.generate();
		Document document = this.builder.buildSearchDocument(model);

		Set<String> names = document.getFieldNames();
		Assert.assertTrue(names.contains("SB_id")); // StoredBlob ID Field
	}

	// MARK: Make Override
	private class StoredImageTestModelGenerator extends WrappedTestModelGeneratorImpl<StoredImage> {

		protected StoredImageTestModelGenerator(TestModelGenerator<StoredImage> generator)
		        throws IllegalArgumentException {
			super(generator);
		}

		@Override
		public StoredImage generate(GeneratorArg arg) throws IllegalArgumentException {
			StoredImage storedImage = super.generate(arg);

			StoredBlob storedBlob = StoredImageSearchDocumentTest.this.storedBlobGenerator.generate();
			storedImage.setStoredBlob(storedBlob.getObjectifyKey());

			return storedImage;
		}

	}

}
