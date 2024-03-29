package com.dereekb.gae.server.search.system.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.deprecated.search.system.SearchDocumentSystem;
import com.dereekb.gae.server.deprecated.search.system.exception.DocumentPutException;
import com.dereekb.gae.server.deprecated.search.system.exception.MissingDocumentException;
import com.dereekb.gae.server.deprecated.search.system.iterator.DocumentIterator;
import com.dereekb.gae.server.deprecated.search.system.iterator.DocumentIteratorRequest;
import com.dereekb.gae.server.deprecated.search.system.iterator.SearchDocumentIteratorService;
import com.dereekb.gae.server.deprecated.search.system.iterator.impl.DocumentIteratorImpl;
import com.dereekb.gae.server.deprecated.search.system.request.DocumentIdentifierRequest;
import com.dereekb.gae.server.deprecated.search.system.request.DocumentPutRequest;
import com.dereekb.gae.server.deprecated.search.system.request.DocumentPutRequestModel;
import com.dereekb.gae.server.deprecated.search.system.request.DocumentQueryRequest;
import com.dereekb.gae.server.deprecated.search.system.request.DocumentRangeReadRequest;
import com.dereekb.gae.server.deprecated.search.system.request.DocumentResultType;
import com.dereekb.gae.server.deprecated.search.system.request.SearchDocumentRequest;
import com.dereekb.gae.server.deprecated.search.system.response.SearchDocumentQueryResponse;
import com.dereekb.gae.server.deprecated.search.system.response.SearchDocumentReadResponse;
import com.dereekb.gae.server.deprecated.search.system.response.impl.SearchDocumentQueryResponseImpl;
import com.dereekb.gae.server.deprecated.search.system.response.impl.SearchDocumentReadResponseImpl;
import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.PutResponse;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.SearchService;
import com.google.appengine.api.search.SearchServiceFactory;

/**
 * {@link SearchDocumentSystem} implementation.
 *
 * @author dereekb
 *
 */
public class GcsSearchSystemImpl
        implements SearchDocumentSystem, SearchDocumentIteratorService {

	private static final Integer API_DOCUMENT_PUT_MAXIMUM = 200;
	private static final Integer API_DOCUMENT_DELETE_MAXIMUM = 200;
	private static final Integer API_RETRIEVE_LIMIT_MAXIMUM = 1000;

	private SearchService searchService;

	private boolean asyncDelete = true;

	private Integer documentIndexBatchSize = API_RETRIEVE_LIMIT_MAXIMUM;
	private Integer documentDeleteMax = API_DOCUMENT_DELETE_MAXIMUM;
	private Integer documentPutMax = API_DOCUMENT_PUT_MAXIMUM;

	public GcsSearchSystemImpl() {
		this.searchService = null;
	}

	public GcsSearchSystemImpl(SearchService searchService) {
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

	public boolean isAsyncDelete() {
		return this.asyncDelete;
	}

	public void setAsyncDelete(boolean asyncDelete) {
		this.asyncDelete = asyncDelete;
	}

	public Integer getDocumentDeleteMax() {
		return this.documentDeleteMax;
	}

	public void setDocumentDeleteMax(Integer documentDeleteMax) {
		this.documentDeleteMax = documentDeleteMax;
	}

	public Integer getDocumentPutMax() {
		return this.documentPutMax;
	}

	public void setDocumentPutMax(Integer documentPutMax) {
		this.documentPutMax = documentPutMax;
	}

	// MARK: SearchDocumentReadSystem
	@Override
	public Document readDocument(DocumentRangeReadRequest request) throws MissingDocumentException {
		Index index = this.getIndex(request);
		String identifier = request.getStartIdentifier();
		Document result = index.get(identifier);

		if (result == null) {
			throw new MissingDocumentException();
		}

		return result;
	}

	@Override
	public SearchDocumentReadResponse readDocuments(DocumentRangeReadRequest request) {
		Index index = this.getIndex(request);
		String identifier = request.getStartIdentifier();
		Document document = index.get(identifier);

		SearchDocumentReadResponse response = SearchDocumentReadResponseImpl.responseForDocument(identifier, document);
		return response;
	}

	@Override
	public SearchDocumentReadResponse readDocuments(DocumentIdentifierRequest request) {
		Collection<String> identifiers = request.getDocumentIdentifiers();
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

		SearchDocumentReadResponse response = new SearchDocumentReadResponseImpl(results, missing);
		return response;
	}

	// MARK: SearchDocumentIndexSystem
	@Override
	public void put(DocumentPutRequest request) throws DocumentPutException {
		Index index = this.getIndex(request);
		Collection<DocumentPutRequestModel> models = request.getPutRequestModels();

		try {
			this.batchAndPut(index, models);
		} catch (PutException e) {
			throw new DocumentPutException(e);
		}
	}

	private void batchAndPut(Index index,
	                         Collection<DocumentPutRequestModel> models) throws PutException {
		PartitionerImpl partition = new PartitionerImpl(this.documentPutMax);
		List<List<DocumentPutRequestModel>> batches = partition.makePartitionsWithCollection(models);

		for (List<DocumentPutRequestModel> batch : batches) {
			this.putBatch(index, batch);
		}
	}

	private void putBatch(Index index,
	                      List<DocumentPutRequestModel> models) throws PutException {
		List<Document> documents = new ArrayList<Document>();

		for (DocumentPutRequestModel model : models) {
			Document document = model.getDocument();
			documents.add(document);
		}

		PutResponse response = index.put(documents);
		List<String> resultIds = response.getIds();

		for (int i = 0; i < models.size(); i += 1) {
			DocumentPutRequestModel model = models.get(i);
			String resultId = resultIds.get(i);
			model.setSearchIdentifier(resultId);
		}
	}

	// MARK: SearchDocumentDeleteSystem
	@Override
	public void deleteDocuments(DocumentIdentifierRequest request) {
		Index index = this.getIndex(request);
		Collection<String> identifiers = request.getDocumentIdentifiers();

		try {
			this.batchAndDelete(index, identifiers);
		} catch (PutException e) {

		}
	}

	private void batchAndDelete(Index index,
	                            Collection<String> identifiers) throws PutException {
		PartitionerImpl partition = new PartitionerImpl(this.documentDeleteMax);
		List<List<String>> batches = partition.makePartitionsWithCollection(identifiers);

		for (List<String> batch : batches) {
			this.deleteBatch(index, batch);
		}
	}

	private void deleteBatch(Index index,
	                         List<String> identifiers) {
		if (this.asyncDelete) {
			index.deleteAsync(identifiers);
		} else {
			index.delete(identifiers);
		}
	}

	// MARK: SearchDocumentQuerySystem
	@Override
	public SearchDocumentQueryResponse queryDocuments(DocumentQueryRequest request) {
		Index index = this.getIndex(request);
		Query query = request.getDocumentQuery();
		return new SearchDocumentQueryResponseImpl(index, query);
	}

	// MARK: SearchDocumentIteratorService
	@Override
	public DocumentIterator makeIndexIterator(DocumentIteratorRequest request) {
		Index index = this.getIndex(request);
		Integer batchSize = request.getBatchSize();

		if (batchSize == null) {
			batchSize = this.documentIndexBatchSize;
		}

		DocumentResultType resultType = request.getResultType();
		boolean idsOnly = resultType == DocumentResultType.IDENTIFIERS;
		return new DocumentIteratorImpl(index, batchSize, idsOnly);
	}

	// MARK: Internal
	private Index getIndex(SearchDocumentRequest request) {
		String indexName = request.getIndexName();
		return this.getIndex(indexName);
	}

	private Index getIndex(String name) {
		if (name == null) {
			throw new NullPointerException("Index name cannot be null.");
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

}
