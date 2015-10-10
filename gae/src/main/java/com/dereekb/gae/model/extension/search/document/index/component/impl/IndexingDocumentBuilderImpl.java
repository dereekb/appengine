package com.dereekb.gae.model.extension.search.document.index.component.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocument;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocumentBuilder;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocumentSet;
import com.dereekb.gae.model.extension.search.document.index.component.builder.SearchDocumentBuilder;
import com.dereekb.gae.server.search.UniqueSearchModel;
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
        implements IndexingDocumentBuilder<T> {

	private final String index;
	private final SearchDocumentBuilder<T> builder;

	public IndexingDocumentBuilderImpl(String index, SearchDocumentBuilder<T> builder) {
		this.index = index;
		this.builder = builder;
	}

	public String getIndex() {
		return this.index;
	}

	public SearchDocumentBuilder<T> getBuilder() {
		return this.builder;
	}

	public IndexingDocumentSet<T> buildSearchDocuments(Iterable<T> models) {
		List<IndexingDocument<T>> documents = new ArrayList<IndexingDocument<T>>();

		for (T model : models) {
			IndexingDocument<T> document = this.buildSearchDocument(model);
			documents.add(document);
		}

		IndexingDocumentSet<T> set = new IndexingDocumentSetImpl<T>(this.index, documents);
		return set;
	}

	@Override
	public IndexingDocument<T> buildSearchDocument(T model) {
		Document document = this.builder.buildSearchDocument(model);
		IndexingDocument<T> indexingDocument = new IndexingDocumentImpl<T>(document, model);
		return indexingDocument;
	}

}
