package com.dereekb.gae.test.applications.api.model.tests.extension;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.model.extension.search.document.SearchableUniqueModel;
import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.search.document.index.component.builder.staged.StagedDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
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
		T model = this.make();

		Document document = this.builder.buildSearchDocument(model);
		Assert.assertNotNull(document);
	}

	@Test
	public void testIndexing() {
		T model = this.make();
		model.setSearchIdentifier(null);

		Iterable<T> set = SingleItem.withValue(model);

		this.indexService.indexChange(set, IndexAction.INDEX);
		Assert.assertNotNull(model.getSearchIdentifier());

		this.indexService.indexChange(set, IndexAction.UNINDEX);

		// The implementation should not clear the change.
		Assert.assertNotNull(model.getSearchIdentifier());
	}

	@Test
	public void testIndexingMultiple() {
		List<T> models = this.make(10);

		this.indexService.indexChange(models, IndexAction.INDEX);

		for (T model : models) {
			Assert.assertNotNull(model.getSearchIdentifier());
		}
	}

	protected T make() {
		if (this.generator == null) {
			throw new RuntimeException("No generator has been set.");
		}

		return this.generator.generate();
	}

	protected List<T> make(int limit) {
		List<T> list = new ArrayList<T>();

		for (int i = 0; i < limit; i += 1) {
			list.add(this.make());
		}

		return list;
	}

}
