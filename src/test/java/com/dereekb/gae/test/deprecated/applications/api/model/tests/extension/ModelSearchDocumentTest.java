package com.dereekb.gae.test.applications.api.model.tests.extension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.model.extension.search.document.SearchableUniqueModel;
import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.StagedDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.test.deprecated.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.utilities.collections.SingleItem;
import com.google.appengine.api.search.Document;

public abstract class ModelSearchDocumentTest<T extends SearchableUniqueModel> extends ApiApplicationTestContext {

	protected TestModelGenerator<T> generator;
	protected DocumentIndexService<T> indexService;
	protected StagedDocumentBuilder<T> builder;

	public ModelSearchDocumentTest() {}

	public DocumentIndexService<T> getIndexService() {
		return this.indexService;
	}

	public void setIndexService(DocumentIndexService<T> service) {
		this.indexService = service;
	}

	public StagedDocumentBuilder<T> getBuilder() {
		return this.builder;
	}

	public void setBuilder(StagedDocumentBuilder<T> builder) {
		this.builder = builder;
	}

	public TestModelGenerator<T> getGenerator() {
		return this.generator;
	}

	public void setGenerator(TestModelGenerator<T> generator) {
		this.generator = generator;
	}

	@Test
	public void testDocumentBuilding() {
		T model = this.generator.generate();

		Document document = this.builder.buildSearchDocument(model);
		assertNotNull(document);
	}

	@Test
	public void testIndexing() {
		T model = this.generator.generate();
		model.setSearchIdentifier(null);

		Iterable<T> set = SingleItem.withValue(model);

		this.indexService.indexChange(set, IndexAction.INDEX);
		assertNotNull(model.getSearchIdentifier());

		this.indexService.indexChange(set, IndexAction.UNINDEX);

		// The implementation should not clear the change.
		assertNotNull(model.getSearchIdentifier());
	}

	@Test
	public void testIndexingMultiple() {
		List<T> models = this.generator.generate(10);

		this.indexService.indexChange(models, IndexAction.INDEX);

		for (T model : models) {
			assertNotNull(model.getSearchIdentifier());
		}
	}

}
