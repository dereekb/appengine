package com.dereekb.gae.test.applications.api.model.stored.imageset;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.StagedDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchResponse;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchService;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.model.stored.image.set.search.document.index.StoredImageSetDocumentBuilderStep;
import com.dereekb.gae.model.stored.image.set.search.document.query.StoredImageSetSearchRequest;
import com.dereekb.gae.test.applications.api.model.tests.extension.ModelSearchDocumentTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.test.model.extension.generator.impl.WrappedTestModelGeneratorImpl;
import com.google.appengine.api.search.Document;

public class StoredImageSetSearchDocumentTest extends ModelSearchDocumentTest<StoredImageSet> {

	@Autowired
	@Qualifier("storedImageTestModelGenerator")
	private TestModelGenerator<StoredImage> storedImageGenerator;

	@Override
	@Autowired
	@Qualifier("storedImageSetTestModelGenerator")
	public void setGenerator(TestModelGenerator<StoredImageSet> generator) {
		super.setGenerator(new StoredImageSetTestModelGenerator(generator));
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetDocumentIndexService")
	public void setIndexService(DocumentIndexService<StoredImageSet> service) {
		super.setIndexService(service);
	}

	@Override
	@Autowired
	@Qualifier("storedImageSetSearchDocumentBuilder")
	public void setBuilder(StagedDocumentBuilder<StoredImageSet> builder) {
		super.setBuilder(builder);
	}

	@Autowired
	@Qualifier("storedImageSetSearchService")
	private ModelDocumentSearchService<StoredImageSet, StoredImageSetSearchRequest> searchService;

	@Test
	public void testSearchService() {
		List<StoredImageSet> models = this.generator.generate(10);

		this.indexService.indexChange(models, IndexAction.INDEX);

		StoredImageSetSearchRequest request = new StoredImageSetSearchRequest();
		ModelDocumentSearchResponse<StoredImageSet> response = this.searchService.search(request);

		Collection<StoredImageSet> results = response.getModelResults();
		Assert.assertTrue(results.containsAll(models));
	}

	// MARK: Indexing
	@Override
	@Test
	public void testDocumentBuilding() {
		StoredImageSet storedImageSet = this.generator.generate();
		super.testDocumentBuilding();

		// Test with null Icon
		storedImageSet.setIcon(null);

		Document document = this.builder.buildSearchDocument(storedImageSet);
		Assert.assertNotNull(document);
	}

	@Test
	public void testSearchIndexFields() {
		StoredImageSet model = this.generator.generate();
		Document document = this.builder.buildSearchDocument(model);

		Set<String> names = document.getFieldNames();
		Assert.assertTrue(names.contains(StoredImageSetDocumentBuilderStep.LABEL_FIELD));
		Assert.assertTrue(names.contains(StoredImageSetDocumentBuilderStep.DETAIL_FIELD));
		Assert.assertTrue(names.contains(StoredImageSetDocumentBuilderStep.TAGS_FIELD));
	}

	// MARK: Make Override
	private class StoredImageSetTestModelGenerator extends WrappedTestModelGeneratorImpl<StoredImageSet> {

		protected StoredImageSetTestModelGenerator(TestModelGenerator<StoredImageSet> generator)
		        throws IllegalArgumentException {
			super(generator);
		}

		@Override
		public StoredImageSet generate(GeneratorArg arg) throws IllegalArgumentException {
			StoredImageSet storedImageSet = super.generate(arg);

			StoredImage icon = StoredImageSetSearchDocumentTest.this.storedImageGenerator.generate();
			storedImageSet.setIcon(icon.getObjectifyKey());

			return storedImageSet;
		}

	}

}
