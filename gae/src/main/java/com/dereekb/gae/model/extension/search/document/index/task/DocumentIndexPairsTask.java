package com.dereekb.gae.model.extension.search.document.index.task;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.search.document.index.IndexPair;
import com.dereekb.gae.model.extension.search.document.index.component.DocumentIndexer;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocument;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocumentBuilder;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link Task} for indexing documents.
 *
 * @author dereekb
 *
 * @param <T>
 *            input model
 */
public class DocumentIndexPairsTask<T extends UniqueSearchModel>
        implements IterableTask<IndexPair<T>> {

	private IndexingDocumentBuilder<T> builder;
	private DocumentIndexer<T> indexer;

	public DocumentIndexPairsTask() {}

	public DocumentIndexPairsTask(IndexingDocumentBuilder<T> builder, DocumentIndexer<T> indexer) {
		this.builder = builder;
		this.indexer = indexer;
	}

	public IndexingDocumentBuilder<T> getBuilder() {
		return this.builder;
	}

	public void setBuilder(IndexingDocumentBuilder<T> builder) {
		this.builder = builder;
	}

	public DocumentIndexer<T> getIndexer() {
		return this.indexer;
	}

	public void setIndexer(DocumentIndexer<T> indexer) {
		this.indexer = indexer;
	}

	// MARK: Task
	@Override
	public void doTask(Iterable<IndexPair<T>> input) throws FailedTaskException {
		try {
			DocumentIndexFunctionInputSet inputSet = this.makeSet(input);
			inputSet.run();
		} catch (Exception e) {
			throw new FailedTaskException(e);
		}
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

	/**
	 * Internal class used for sorting operations on {@link IndexPair} values.
	 *
	 * @author dereekb
	 *
	 */
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
				IndexingDocument<T> document = DocumentIndexPairsTask.this.builder.buildSearchDocument(model);
				documents.add(document);
			}

			boolean success = DocumentIndexPairsTask.this.indexer.indexDocuments(documents);
			this.setResultsForPairs(success, pairs);
		}

		private void unindexElements(Iterable<IndexPair<T>> pairs) {
			List<T> models = IndexPair.getKeys(pairs);
			boolean success = DocumentIndexPairsTask.this.indexer.deleteDocuments(models);
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
