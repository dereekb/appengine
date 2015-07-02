package com.dereekb.gae.model.extension.search.document.index;

import com.dereekb.gae.model.extension.search.document.index.component.DocumentIndexer;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocumentBuilder;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.utilities.function.staged.factory.AbstractFilteredStagedFunctionFactory;

/**
 * Factory used for building {@link DocumentIndexFunction} instances.
 * 
 * @author dereekb
 *
 * @param <T>
 */
public class DocumentIndexFunctionFactory<T extends UniqueSearchModel> extends AbstractFilteredStagedFunctionFactory<DocumentIndexFunction<T>, T, IndexPair<T>> {

	private final IndexingDocumentBuilder<T> builder;
	private final DocumentIndexer<T> indexer;

	public DocumentIndexFunctionFactory(IndexingDocumentBuilder<T> builder, DocumentIndexer<T> indexer) {
		this.builder = builder;
		this.indexer = indexer;
	}

	@Override
	protected DocumentIndexFunction<T> newStagedFunction() {
		return new DocumentIndexFunction<T>(this.builder, this.indexer);
	}

	public IndexingDocumentBuilder<T> getBuilder() {
		return this.builder;
	}

	public DocumentIndexer<T> getIndexer() {
		return this.indexer;
	}

}