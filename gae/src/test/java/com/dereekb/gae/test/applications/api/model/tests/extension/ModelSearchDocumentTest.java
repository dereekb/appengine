package com.dereekb.gae.test.applications.api.model.tests.extension;

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
		T storedImage = this.make();

		Document document = this.builder.buildSearchDocument(storedImage);
		Assert.assertNotNull(document);
	}

	@Test
	public void testIndexing() {
		T storedImage = this.make();
		storedImage.setSearchIdentifier(null);

		Iterable<T> set = SingleItem.withValue(storedImage);

		boolean success = this.indexService.indexChange(set, IndexAction.INDEX);
		Assert.assertTrue(success);
		Assert.assertNotNull(storedImage.getSearchIdentifier());

		success = this.indexService.indexChange(set, IndexAction.UNINDEX);
		Assert.assertTrue(success);

		// The implementation should not clear the change.
		Assert.assertNotNull(storedImage.getSearchIdentifier());
	}

	protected T make() {
		if (this.generator == null) {
			throw new RuntimeException("No generator has been set.");
		}

		return this.generator.generate();
	}

}
