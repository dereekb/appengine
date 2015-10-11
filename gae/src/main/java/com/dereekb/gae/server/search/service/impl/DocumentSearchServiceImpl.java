package com.dereekb.gae.server.search.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.search.service.SearchDocumentService;
import com.dereekb.gae.server.search.service.exception.MissingDocumentException;
import com.dereekb.gae.server.search.service.request.DocumentMultiReadRequest;
import com.dereekb.gae.server.search.service.request.DocumentRangeReadRequest;
import com.dereekb.gae.server.search.service.request.SearchDocumentRequest;
import com.dereekb.gae.server.search.service.response.SearchDocumentReadResponse;
import com.dereekb.gae.server.search.service.response.impl.SearchDocumentReadResponseImpl;
import com.dereekb.gae.utilities.collections.pairs.ResultsPair;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutResponse;
import com.google.appengine.api.search.SearchService;
import com.google.appengine.api.search.SearchServiceFactory;

public class SearchDocumentServiceImpl
        implements SearchDocumentService {

	private static final Integer DEFAULT_RETRIEVE_LIMIT = 100;

	private static final Integer API_DOCUMENT_PUT_MAXIMUM = 200;
	private static final Integer API_DOCUMENT_DELETE_MAXIMUM = 200;
	private static final Integer API_RETRIEVE_LIMIT_MAXIMUM = 1000;

	private SearchService searchService;

	public SearchDocumentServiceImpl() {
		this(SearchServiceFactory.getSearchService());
	}

	public SearchDocumentServiceImpl(SearchService searchService) {
		this.searchService = searchService;
	}

	public SearchService getSearchService() {
		return this.searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	// MARK: SearchDocumentReadService
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
	public SearchDocumentReadResponse readDocuments(DocumentMultiReadRequest request) {
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

	private static class PutResultsPair extends ResultsPair<List<Document>, PutResponse> {

		public PutResultsPair(List<Document> batch) {
			super(batch);
		}

		public List<String> getIdentifiers() {
			PutResponse response = this.getResult();
			List<String> identifiers = response.getIds();
			return identifiers;
		}

		public static List<String> readIdentifiersFrom(Iterable<PutResultsPair> results) {
			List<String> identifiers = new ArrayList<String>();

			for (PutResultsPair result : results) {
				List<String> resultIds = result.getIdentifiers();
				identifiers.addAll(resultIds);
			}

			return identifiers;
		}
	}

}
