package com.dereekb.gae.server.search.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.search.components.SearchServiceIndex;
import com.dereekb.gae.server.search.exception.SearchIndexUpdateException;
import com.dereekb.gae.server.search.request.SearchServiceDeleteRequest;
import com.dereekb.gae.server.search.request.SearchServiceIndexRequest;
import com.dereekb.gae.server.search.request.SearchServiceIndexRequestPair;
import com.dereekb.gae.server.search.request.SearchServiceQueryOptions;
import com.dereekb.gae.server.search.request.SearchServiceQueryRequest;
import com.dereekb.gae.server.search.request.SearchServiceReadRequest;
import com.dereekb.gae.server.search.response.SearchServiceQueryResponse;
import com.dereekb.gae.server.search.response.SearchServiceReadResponse;
import com.dereekb.gae.server.search.response.impl.SearchServiceReadResponseImpl;
import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.dereekb.gae.utilities.collections.iterator.cursor.impl.ResultsCursorImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.model.search.exception.NoSearchCursorException;
import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.PutResponse;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchService;
import com.google.appengine.api.search.SearchServiceFactory;

/**
 * {@link SearchDocumentSystem} implementation.
 *
 * @author dereekb
 *
 */
public class GcsSearchServiceImpl
        implements com.dereekb.gae.server.search.service.SearchService {

	private static final Integer API_DOCUMENT_PUT_MAXIMUM = 200;
	private static final Integer API_DOCUMENT_DELETE_MAXIMUM = 200;
	private static final Integer API_RETRIEVE_LIMIT_MAXIMUM = 1000;

	private SearchService searchService;

	private Integer documentIndexBatchSize = API_RETRIEVE_LIMIT_MAXIMUM;
	private Integer documentDeleteMax = API_DOCUMENT_DELETE_MAXIMUM;
	private Integer documentPutMax = API_DOCUMENT_PUT_MAXIMUM;

	public GcsSearchServiceImpl() {
		this(null);
	}

	public GcsSearchServiceImpl(SearchService searchService) {
		this.searchService = searchService;
	}

	public SearchService getSearchService() {
		if (this.searchService == null) {
			this.searchService = SearchServiceFactory.getSearchService();
		}

		return this.searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public Integer getDocumentIndexBatchSize() {
		return this.documentIndexBatchSize;
	}

	public void setDocumentIndexBatchSize(Integer documentIndexBatchSize) {
		if (documentIndexBatchSize == null) {
			throw new IllegalArgumentException("documentIndexBatchSize cannot be null.");
		}

		this.documentIndexBatchSize = documentIndexBatchSize;
	}

	public Integer getDocumentDeleteMax() {
		return this.documentDeleteMax;
	}

	public void setDocumentDeleteMax(Integer documentDeleteMax) {
		if (documentDeleteMax == null) {
			throw new IllegalArgumentException("documentDeleteMax cannot be null.");
		}

		this.documentDeleteMax = documentDeleteMax;
	}

	public Integer getDocumentPutMax() {
		return this.documentPutMax;
	}

	public void setDocumentPutMax(Integer documentPutMax) {
		if (documentPutMax == null) {
			throw new IllegalArgumentException("documentPutMax cannot be null.");
		}

		this.documentPutMax = documentPutMax;
	}

	// MARK: SearchDocumentReadSystem
	/*
	 * @Override
	 * public Document readDocument(SearchServiceRangeReadRequest request)
	 * throws MissingDocumentException {
	 * Index index = this.getIndex(request);
	 * String identifier = request.getStartIdentifier();
	 * Document result = index.get(identifier);
	 *
	 * if (result == null) {
	 * throw new MissingDocumentException();
	 * }
	 *
	 * return result;
	 * }
	 */

	/*
	 * @Override
	 * public SearchDocumentReadResponse readDocuments(DocumentRangeReadRequest
	 * request) {
	 * Index index = this.getIndex(request);
	 * String identifier = request.getStartIdentifier();
	 * Document document = index.get(identifier);
	 *
	 * SearchDocumentReadResponse response =
	 * SearchDocumentReadResponseImpl.responseForDocument(identifier, document);
	 * return response;
	 * }
	 */

	@Override
	public SearchServiceReadResponse readDocuments(SearchServiceReadRequest request) {
		Iterable<String> identifiers = request.getDocumentKeys();
		Index index = this.getIndex(request);

		List<Document> results = new ArrayList<Document>();
		List<String> missing = new ArrayList<String>();

		for (String identifier : identifiers) {
			Document document = index.get(identifier);

			if (document != null) {
				results.add(document);
			} else {
				missing.add(identifier);
			}
		}

		SearchServiceReadResponseImpl response = new SearchServiceReadResponseImpl(results, missing);
		return response;
	}

	// MARK: SearchServiceIndexer
	@Override
	public void updateIndex(SearchServiceIndexRequest request) throws SearchIndexUpdateException {
		Index index = this.getIndex(request);
		Iterable<SearchServiceIndexRequestPair> pairs = request.getRequestPairs();

		try {
			this.batchAndPut(index, pairs);
		} catch (PutException e) {
			throw new SearchIndexUpdateException(e);
		}
	}

	private void batchAndPut(Index index,
	                         Iterable<SearchServiceIndexRequestPair> pairs)
	        throws PutException {
		PartitionerImpl partition = new PartitionerImpl(this.documentPutMax);
		List<List<SearchServiceIndexRequestPair>> batches = partition.makePartitions(pairs);

		for (List<SearchServiceIndexRequestPair> batch : batches) {
			this.putBatch(index, batch);
		}
	}

	private void putBatch(Index index,
	                      List<SearchServiceIndexRequestPair> pairs)
	        throws PutException {
		List<Document> documents = new ArrayList<Document>();

		for (SearchServiceIndexRequestPair pair : pairs) {
			Document document = pair.getDocument();
			documents.add(document);
		}

		PutResponse response = index.put(documents);
		List<String> resultIds = response.getIds();

		for (int i = 0; i < pairs.size(); i += 1) {
			SearchServiceIndexRequestPair model = pairs.get(i);
			String resultId = resultIds.get(i);
			model.setResult(resultId);
		}
	}

	@Override
	public void deleteFromIndex(SearchServiceDeleteRequest request) {
		Index index = this.getIndex(request);
		Iterable<String> keys = request.getDocumentKeys();

		try {
			this.batchAndDelete(index, keys);
		} catch (PutException e) {

		}
	}

	private void batchAndDelete(Index index,
	                            Iterable<String> identifiers)
	        throws PutException {
		PartitionerImpl partition = new PartitionerImpl(this.documentDeleteMax);
		List<List<String>> batches = partition.makePartitions(identifiers);

		for (List<String> batch : batches) {
			this.deleteBatch(index, batch);
		}
	}

	private void deleteBatch(Index index,
	                         List<String> identifiers) {
		index.delete(identifiers);
	}

	// MARK: SearchDocumentQuerySystem
	@Override
	public SearchServiceQueryResponse queryIndex(SearchServiceQueryRequest request) {
		return new SearchServiceQueryResponseImpl(request);
	}

	/*
	 * // MARK: SearchDocumentIteratorService
	 *
	 * @Override
	 * public SearchServiceBatchIterator
	 * makeIndexIterator(DocumentIteratorRequest request) {
	 * Index index = this.getIndex(request);
	 * Integer batchSize = request.getBatchSize();
	 *
	 * if (batchSize == null) {
	 * batchSize = this.documentIndexBatchSize;
	 * }
	 *
	 * DocumentResultType resultType = request.getResultType();
	 * boolean idsOnly = resultType == DocumentResultType.IDENTIFIERS;
	 * return new DocumentIteratorImpl(index, batchSize, idsOnly);
	 * }
	 */

	// MARK: Internal
	private Index getIndex(SearchServiceIndex index) {
		String indexName = index.getIndexName();
		return this.getIndex(indexName);
	}

	private Index getIndex(String name) {
		if (StringUtility.isEmptyString(name)) {
			throw new NullPointerException("Index name cannot be null or empty.");
		}

		SearchService searchService = this.getSearchService();
		IndexSpec indexSpec = this.getIndexSpec(name);
		Index index = searchService.getIndex(indexSpec);
		return index;
	}

	private IndexSpec getIndexSpec(String indexName) {
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
		return indexSpec;
	}

	/**
	 * Internal {@link SearchServiceQueryResponse} implementation.
	 *
	 * @author dereekb
	 *
	 */
	private class SearchServiceQueryResponseImpl
	        implements SearchServiceQueryResponse {

		private Results<ScoredDocument> results = null;

		private final SearchServiceQueryRequest request;

		public SearchServiceQueryResponseImpl(SearchServiceQueryRequest request) {
			this.request = request;
		}

		// MARK: SearchServiceQueryResponse
		@Override
		public Collection<ScoredDocument> getDocumentResults() {
			return this.getResults().getResults();
		}

		@Override
		public Long getFoundResults() {
			return this.getResults().getNumberFound();
		}

		@Override
		public Integer getReturnedResults() {
			return this.getResults().getNumberReturned();
		}

		@Override
		public ResultsCursor getResultsCursor() throws NoSearchCursorException {
			Cursor cursor = this.getResults().getCursor();

			if (cursor == null) {
				throw new NoSearchCursorException();
			}

			return ResultsCursorImpl.make(cursor.toWebSafeString());
		}

		// MARK: Internal
		private Results<ScoredDocument> getResults() {
			if (this.results == null) {
				this.results = this.performSearch();
			}

			return this.results;
		}

		private Results<ScoredDocument> performSearch() {
			Index index = this.getIndex();
			Query query = this.buildQuery();

			Results<ScoredDocument> results = index.search(query);
			return results;
		}

		private Index getIndex() {
			return GcsSearchServiceImpl.this.getIndex(this.request.getIndexName());
		}

		private Query buildQuery() {
			SearchServiceQueryOptions searchOptions = this.request.getSearchOptions();

			// TODO: Add sort options

			Cursor cursor = null;

			if (searchOptions.getCursor() != null) {
				cursor = Cursor.newBuilder().build(searchOptions.getCursor().getCursorString());
			}

			QueryOptions.Builder queryOptions = QueryOptions.newBuilder();
			Collection<String> fieldsToReturn = searchOptions.getFieldsToReturn();

			if (fieldsToReturn != null) {
				queryOptions = queryOptions.setFieldsToReturn(ListUtility.toArray(String.class, fieldsToReturn));
			}

			if (searchOptions.getLimit() != null) {
				queryOptions = queryOptions.setLimit(searchOptions.getLimit());
			}

			if (searchOptions.getOffset() != null) {
				queryOptions = queryOptions.setOffset(searchOptions.getOffset());
			}

			if (cursor != null) {
				queryOptions = queryOptions.setCursor(cursor);
			}

			String queryString = "";

			if (this.request.getExpression() != null) {
				queryString = this.request.getExpression().getQueryExpression();
			}

			Query query = Query.newBuilder().setOptions(queryOptions).build(queryString);
			return query;
		}

	}

}
