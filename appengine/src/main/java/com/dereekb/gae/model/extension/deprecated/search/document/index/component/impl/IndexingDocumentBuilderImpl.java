package com.dereekb.gae.model.extension.search.document.index.component.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.deprecated.search.document.index.component.IndexingDocument;
import com.dereekb.gae.model.extension.deprecated.search.document.index.component.IndexingDocumentBuilder;
import com.dereekb.gae.model.extension.deprecated.search.document.index.component.IndexingDocumentSet;
import com.dereekb.gae.model.extension.deprecated.search.document.index.component.IndexingDocumentSetBuilder;
import com.dereekb.gae.model.extension.deprecated.search.document.index.component.builder.SearchDocumentBuilder;
import com.dereekb.gae.server.deprecated.search.UniqueSearchModel;
import com.google.appengine.api.search.Document;

/**
 * Default implementation of the {@link IndexingDocumentBuilder} interface.
 *
 * Uses a {@link IndexingDocumentBuilder} to build {@link IndexingDocumentSet}
 * instances.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class IndexingDocumentBuilderImpl<T extends UniqueSearchModel>
        implements IndexingDocumentBuilder<T>, IndexingDocumentSetBuilder<T> {

	private String index;
	private SearchDocumentBuilder<T> builder;

	public IndexingDocumentBuilderImpl(String index, SearchDocumentBuilder<T> builder) {
		this.index = index;
		this.builder = builder;
	}

	@Override
    public String getIndex() {
		return this.index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public SearchDocumentBuilder<T> getBuilder() {
		return this.builder;
	}

	public void setBuilder(SearchDocumentBuilder<T> builder) {
		this.builder = builder;
	}

	// MARK: IndexingDocumentSet
	@Override
	public IndexingDocument<T> buildSearchDocument(T model) {
		Document document = this.builder.buildSearchDocument(model);
		IndexingDocument<T> indexingDocument = new IndexingDocumentImpl<T>(document, model);
		return indexingDocument;
	}

	// MARK: IndexingDocumentSetBuilder
	@Override
	public IndexingDocumentSet<T> buildSearchDocuments(Iterable<T> models,
	                                                   boolean update) {
		return this.buildSearchDocuments(models, this.index, update);
	}

	@Override
	public IndexingDocumentSet<T> buildSearchDocuments(Iterable<T> models,
	                                                   String index,
	                                                   boolean update) {
		List<IndexingDocument<T>> documents = new ArrayList<IndexingDocument<T>>();

		for (T model : models) {
			IndexingDocument<T> document = this.buildSearchDocument(model);
			documents.add(document);
		}

		IndexingDocumentSetImpl<T> set = new IndexingDocumentSetImpl<T>(index, documents);
		set.setUpdate(update);
		return set;
	}

}
