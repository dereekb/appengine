package com.dereekb.gae.model.extension.search.document.index.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.extension.search.document.index.IndexAction;
import com.dereekb.gae.model.extension.search.document.index.IndexPair;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocumentSet;
import com.dereekb.gae.model.extension.search.document.index.component.IndexingDocumentSetBuilder;
import com.dereekb.gae.model.extension.search.document.index.service.DocumentIndexService;
import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.server.search.service.SearchDocumentService;
import com.dereekb.gae.server.search.service.exception.DocumentPutException;
import com.dereekb.gae.server.search.service.request.impl.DocumentModelIdentifierRequestImpl;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * Implementation of {@link DocumentIndexService} and {@link IterableTask} that
 * processing {@link IndexPair} instances.
 *
 * @author dereekb
 *
 */
public class DocumentIndexServiceImpl<T extends UniqueSearchModel>
        implements DocumentIndexService<T>, IterableTask<IndexPair<T>> {

	private SearchDocumentService indexService;
	private IndexingDocumentSetBuilder<T> builder;

	public DocumentIndexServiceImpl() {}

	public DocumentIndexServiceImpl(SearchDocumentService indexService,
	                              IndexingDocumentSetBuilder<T> builder) {
		this.indexService = indexService;
		this.builder = builder;
	}

	public SearchDocumentService getIndexService() {
		return this.indexService;
	}

	public void setIndexService(SearchDocumentService indexService) {
		this.indexService = indexService;
	}

	public IndexingDocumentSetBuilder<T> getBuilder() {
		return this.builder;
	}

	public void setBuilder(IndexingDocumentSetBuilder<T> builder) {
		this.builder = builder;
	}

	// MARK: DocumentIndexService
	@Override
	public boolean indexChange(Iterable<T> models,
	                           IndexAction action) throws AtomicOperationException {

		List<IndexPair<T>> pairs = IndexPair.makePairs(models, action);
		boolean success;

		try {
			this.doTask(pairs);
			success = true;
		} catch (FailedTaskException e) {
			success = false;
		}

		return success;
	}

	// MARK: Task
	@Override
	public void doTask(Iterable<IndexPair<T>> input) throws FailedTaskException {
		try {
			InputSet inputSet = this.makeSet(input);
			inputSet.run();
		} catch (Exception e) {
			throw new FailedTaskException(e);
		}
	}

	public InputSet makeSet(Iterable<IndexPair<T>> pairs) {
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

		return new InputSet(index, update, unindex);
	}

	/**
	 * Internal class used for sorting operations on {@link IndexPair} values.
	 *
	 * @author dereekb
	 *
	 */
	private class InputSet {

		private final List<IndexPair<T>> index;
		private final List<IndexPair<T>> update;
		private final List<IndexPair<T>> unindex;

		public InputSet(List<IndexPair<T>> index,
		        List<IndexPair<T>> update,
		        List<IndexPair<T>> unindex) {
			this.index = index;
			this.update = update;
			this.unindex = unindex;
		}

		protected void run() {
			if (this.index.isEmpty() == false) {
				this.indexElements(this.index, false);
			}

			if (this.update.isEmpty() == false) {
				this.indexElements(this.index, true);
			}

			if (this.unindex.isEmpty() == false) {
				this.unindexElements(this.unindex);
			}
		}

		private void indexElements(Iterable<IndexPair<T>> pairs,
		                           boolean update) {
			List<T> models = IndexPair.getKeys(pairs);
			IndexingDocumentSet<T> set = DocumentIndexServiceImpl.this.builder.buildSearchDocuments(models, update);

			boolean success = true;

			try {
				DocumentIndexServiceImpl.this.indexService.put(set);
			} catch (DocumentPutException e) {
				success = false;
			}

			this.setResultsForPairs(success, pairs);
		}

		private void unindexElements(Collection<IndexPair<T>> pairs) {
			String index = DocumentIndexServiceImpl.this.builder.getIndex();

			Collection<? extends UniqueSearchModel> searchModels = new ArrayList<>(pairs);
			DocumentModelIdentifierRequestImpl request = new DocumentModelIdentifierRequestImpl(index, searchModels);

			boolean success = true;

			try {
				DocumentIndexServiceImpl.this.indexService.deleteDocuments(request);
			} catch (Exception e) {
				success = false;
			}

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
