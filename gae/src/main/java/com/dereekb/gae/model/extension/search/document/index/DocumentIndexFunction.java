package com.dereekb.gae.model.extension.search.document.index;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.search.document.index.component.DocumentIndexer;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocument;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocumentBuilder;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.utilities.function.staged.filter.FilteredStagedFunction;

/**
 * Staged function used for indexing elements using {@link IndexPair} instances.
 *
 * @author dereekb
 *
 * @param <T>
 */
public final class DocumentIndexFunction<T extends UniqueSearchModel> extends FilteredStagedFunction<T, IndexPair<T>> {

	private final IndexingDocumentBuilder<T> builder;
	private final DocumentIndexer<T> indexer;

	public DocumentIndexFunction(IndexingDocumentBuilder<T> builder, DocumentIndexer<T> indexer) {
		this.builder = builder;
		this.indexer = indexer;
	}

	@Override
	protected void doFunction() {
		Iterable<IndexPair<T>> pairs = this.getWorkingObjects();
		DocumentIndexFunctionInputSet inputSet = this.makeSet(pairs);
		inputSet.run();
	}

	public DocumentIndexer<T> getIndexer() {
		return this.indexer;
	}

	public DocumentIndexFunctionInputSet makeSet(Iterable<IndexPair<T>> pairs) {
		List<IndexPair<T>> index = new ArrayList<IndexPair<T>>();
		List<IndexPair<T>> update = new ArrayList<IndexPair<T>>();
		List<IndexPair<T>> unindex = new ArrayList<IndexPair<T>>();

		for (IndexPair<T> pair : pairs) {
			switch (pair.getAction()) {
				case INDEX:
					if (pair.wasInitiallyIndexed()) {
						update.add(pair);
					} else {
						index.add(pair);
                    }
					break;
				case UNINDEX:
					if (pair.wasInitiallyIndexed()) {
						unindex.add(pair);
					} else {
						pair.setSuccessful(true); // No change possible.
					}
					break;
			}
		}

		return new DocumentIndexFunctionInputSet(index, update, unindex);
	}

	public IndexingDocumentBuilder<T> getBuilder() {
		return this.builder;
	}

	private class DocumentIndexFunctionInputSet {

		private final List<IndexPair<T>> index;
		private final List<IndexPair<T>> update;
		private final List<IndexPair<T>> unindex;

		public DocumentIndexFunctionInputSet(List<IndexPair<T>> index,
		        List<IndexPair<T>> update,
		        List<IndexPair<T>> unindex) {
			this.index = index;
			this.update = update;
			this.unindex = unindex;
		}

		protected void run() {
			if (this.index.isEmpty() == false) {
				this.indexElements(this.index);
			}

			if (this.update.isEmpty() == false) {
				this.indexElements(this.index);
			}

			if (this.unindex.isEmpty() == false) {
				this.unindexElements(this.unindex);
			}
		}

		private void indexElements(Iterable<IndexPair<T>> pairs) {
			List<T> models = IndexPair.getKeys(pairs);
			List<IndexingDocument<T>> documents = new ArrayList<IndexingDocument<T>>();

			for (T model : models) {
				IndexingDocument<T> document = DocumentIndexFunction.this.builder.buildSearchDocument(model);
				documents.add(document);
			}

			boolean success = DocumentIndexFunction.this.indexer.indexDocuments(documents);
			this.setResultsForPairs(success, pairs);
		}

		private void unindexElements(Iterable<IndexPair<T>> pairs) {
			List<T> models = IndexPair.getKeys(pairs);
			boolean success = DocumentIndexFunction.this.indexer.deleteDocuments(models);
			this.setResultsForPairs(success, pairs);
		}

		private void setResultsForPairs(boolean success,
		                                Iterable<IndexPair<T>> pairs) {
			for (IndexPair<T> pair : pairs) {
				pair.setSuccessful(success);
			}
		}

	}

}
