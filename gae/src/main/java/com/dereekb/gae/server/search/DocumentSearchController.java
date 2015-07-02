package com.dereekb.gae.server.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.server.search.document.DocumentIdentifierRequest;
import com.dereekb.gae.server.search.document.DocumentQueryBuilder;
import com.dereekb.gae.utilities.collections.batch.BatchGenerator;
import com.dereekb.gae.utilities.collections.pairs.ResultsPair;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.GetRequest;
import com.google.appengine.api.search.GetResponse;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.PutResponse;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchService;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;

/**
 * A static search controller for carrying out searches in the Google App Engine Search API.
 *
 * @author dereekb
 * @see <a href="https://developers.google.com/appengine/docs/java/search/#Java_Overview">Google App Engine Search API</a>
 */
public class DocumentSearchController {

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

	private static final Integer DEFAULT_RETRIEVE_LIMIT = 100;

	private static final Integer API_DOCUMENT_PUT_MAXIMUM = 200;
	private static final Integer API_DOCUMENT_DELETE_MAXIMUM = 200;
	private static final Integer API_RETRIEVE_LIMIT_MAXIMUM = 1000;

	private static final SearchService getSearchService() {
		return SearchServiceFactory.getSearchService();
	}

	private static final IndexSpec getIndexSpec(String indexName) {
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
		return indexSpec;
	}

	private static final Index getIndex(String name) {
		if (name == null) {
			throw new NullPointerException("Index name cannot be null.");
		}

		SearchService searchService = getSearchService();
		IndexSpec indexSpec = getIndexSpec(name);
		Index index = searchService.getIndex(indexSpec);
		return index;
	}

	/**
	 * Puts an arbitrary document into the search index.
	 *
	 * @param document
	 * @param indexName
	 * @param async
	 * @return Identifier of the document modified, if synchronous. Asynchronous returns null.
	 */
	public static String put(Document document,
	                         String indexName,
	                         boolean async) {
		Index index = getIndex(indexName);
		PutResponse response = putDocumentIntoIndex(document, index, async);

		String identifier = null;

		if (response != null) {
			List<String> responseIdentifiers = response.getIds();
			identifier = responseIdentifiers.get(0);
		}

		return identifier;
	}

	/**
	 * Puts a set of arbitrary documents into the search index.
	 *
	 * @param documents
	 * @param indexName
	 * @param async
	 * @return List of strings for the documents modified, if synchronous. Asynchronous returns an empty list.
	 */
	public static List<String> put(List<Document> documents,
	                               String indexName,
	                               boolean async) {

		Index index = getIndex(indexName);
		List<PutResultsPair> results = batchPutDocuments(documents, index, async);
		List<String> identifiers = null;

		if (results.isEmpty() == false) {
			identifiers = PutResultsPair.readIdentifiersFrom(results);
		} else {
			identifiers = new ArrayList<String>();
		}

		return identifiers;
	}

	/**
	 * Saves a list of ModelDocuments encased in a ModelDocumentSet.
	 *
	 * During synchronous calls, ModelDocuments have their savedDocumentWithId(identifier) function invoked after being
	 * update successfully.
	 *
	 * @param documentSet
	 *            ModelDocumentSet containing the documents to save.
	 * @param async
	 *            Whether to save in an asynchronous or synchronous manner.
	 *            Saving synchronously will cause the modelDocuments to be notified of the id they were saved with.
	 */
	public static void put(DocumentChangeSet documentSet,
	                       boolean async) {

		String indexName = documentSet.getIndexName();
		Index index = getIndex(indexName);

		List<DocumentChangeModel> documents = documentSet.getDocumentModels();
		List<List<DocumentChangeModel>> documentBatches = createDocumentListBatches(documents, API_DOCUMENT_PUT_MAXIMUM);

		boolean success = true;
		Integer batchCount = documentBatches.size();
		for (int i = 0; ((i < batchCount) && (success == true)); i += 1) {
			List<DocumentChangeModel> batch = documentBatches.get(i);
			success &= putModelDocumentsIntoIndex(batch, index, async);
		}

		documentSet.setSuccess(success);
	}

	/**
	 * Breaks up the list of documents and puts them into the datastore.
	 *
	 * @param documents
	 * @param indexName
	 * @param async
	 * @return List of PutResponses if synchronous.
	 */
	private static List<PutResultsPair> batchPutDocuments(List<Document> documents,
	                                                                                             Index index,
	                                                                                             boolean async) {
		List<List<Document>> documentBatches = createDocumentListBatches(documents, API_DOCUMENT_PUT_MAXIMUM);
		List<PutResultsPair> responses = new ArrayList<PutResultsPair>();

		Integer batchCount = documentBatches.size();
		for (int i = 0; i < batchCount; i += 1) {
			List<Document> batch = documentBatches.get(i);
			PutResponse response = putDocumentsIntoIndex(batch, index, async);

			if (response != null) {
				PutResultsPair pair = new PutResultsPair(batch);
				pair.setResult(response);

				if (response != null) {
					responses.add(pair);
				}
			}
		}

		return responses;
	}

	/**
	 * Differs from the other functions by actually processing the put request.
	 *
	 * @param documents
	 * @param index
	 * @param now
	 * @throws PutException
	 */
	private static boolean putModelDocumentsIntoIndex(List<DocumentChangeModel> documents,
	                                                                                                                        Index index,
	                                                  boolean async) throws PutException {
		boolean success = true;

		List<Document> builtDocuments = new ArrayList<Document>();
		Integer documentsCount = documents.size();

		for (int i = 0; i < documentsCount; i += 1) {
			DocumentChangeModel document = documents.get(i);
			Document builtDocument = document.getDocument();
			builtDocuments.add(builtDocument);
		}

		PutResponse response = putDocumentsIntoIndex(builtDocuments, index, async);

		if (response != null) {
			List<String> identifiers = response.getIds();

			for (int i = 0; i < documentsCount; i += 1) {
				DocumentChangeModel document = documents.get(i);
				String identifier = identifiers.get(i);
				document.savedWithId(identifier);
			}
		} else {
			// TODO: Create a notification of a put error to be rectified automatically later.
			success = false;
		}

		return success;
	}

	private static PutResponse putDocumentIntoIndex(Document document,
	                                                Index index,
	                                                boolean async) {
		List<Document> documents = new ArrayList<Document>(1);
		documents.add(document);
		return putDocumentsIntoIndex(documents, index, async);
	}

	/**
	 * Puts the documents into the target index.
	 *
	 * Do not call directly; instead use batchPutDocuments.
	 *
	 * @param documents
	 *            Documents to index.
	 * @param index
	 *            Index to put the documents into.
	 * @param async
	 *            Whether or not to perform the action asynchronously. Async put requests return null.
	 * @return A put response if synchronous, or null if asynchronous.
	 */
	private static PutResponse putDocumentsIntoIndex(List<Document> documents,
	                                                 Index index,
	                                                 boolean async) {
		PutResponse response = null;

		try {
			if (async) {
				index.putAsync(documents);
			} else {
				response = index.put(documents);
			}
		} catch (PutException e) {
			if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
				// TODO: Create a notification of a put error to be rectified automatically later.
			}
		}

		return response;
	}

	// Read Documents

	/**
	 * Retrieves a single document with the given id.
	 *
	 * @param documentIds
	 * @param indexName
	 * @return
	 */
	public static Document read(String documentId,
	                            String indexName) {
		Index index = getIndex(indexName);
		Document result = index.get(documentId);
		return result;
	}

	/**
	 * Retrieves a set of documents using the given list of documentIds.
	 *
	 * If a document returned is null, the null value will be added to the list in order for optional handling.
	 *
	 * @param documentIds
	 * @param indexName
	 * @return
	 */
	public static List<Document> read(List<String> documentIds,
	                                  String indexName) {
		Index index = getIndex(indexName);
		List<Document> results = readDocuments(documentIds, index);
		return results;
	}

	private static List<Document> readDocuments(List<String> documentIds,
	                                            Index index) {
		List<Document> results = new ArrayList<Document>();

		for (String documentId : documentIds) {
			Document document = index.get(documentId);
			results.add(document);
		}

		return results;
	}

	/**
	 * Read a document identifier.
	 *
	 * Always returns a list of documents, so it isn't effective for retrieving a single document at a time. Use other
	 * read() for single item retrieval.
	 *
	 * @param request
	 * @return
	 */
	public static List<Document> read(DocumentIdentifierRequest request) {
		String indexName = request.getIndex();
		Index index = getIndex(indexName);
		boolean rangeRequest = request.isRangeRequest();

		List<Document> results = null;

		if (rangeRequest) {
			results = readDocuments(request, index);
		} else {
			Document response = readDocument(request, index);
			results = new ArrayList<Document>();
			results.add(response);
		}

		return results;
	}

	private static Document readDocument(DocumentIdentifierRequest request,
	                                     Index index) {
		String identifier = request.getIdentifier();
		Document document = index.get(identifier);
		return document;
	}

	private static List<Document> readDocuments(DocumentIdentifierRequest request,
	                                            Index index) {
		String identifier = request.getIdentifier();
		Integer limit = request.getLimit();
		Boolean idsOnly = request.isIdentifiersOnlyRequest();

		if (limit == null) {
			limit = DEFAULT_RETRIEVE_LIMIT;
		}

		GetRequest.Builder requestBuilder = GetRequest.newBuilder().setReturningIdsOnly(idsOnly).setLimit(limit)
		        .setStartId(identifier);
		GetRequest getRequest = requestBuilder.build();

		GetResponse<Document> response = index.getRange(getRequest);
		List<Document> results = response.getResults();

		return results;
	}

	// Search Documents
	/**
	 * Differs from searchDocuments in that it compiles the result into an array of scored documents.
	 *
	 * @param query
	 * @return
	 */
	public static List<ScoredDocument> searchDocuments(DocumentQueryBuilder query) {

		List<ScoredDocument> resultsList = new ArrayList<ScoredDocument>();

		Results<ScoredDocument> results = search(query);
		Collection<ScoredDocument> resultsCollection = results.getResults();
		resultsList.addAll(resultsCollection);

		return resultsList;
	}

	public static Results<ScoredDocument> search(DocumentQueryBuilder query) {
		String indexName = query.getIndexName();
		Index index = getIndex(indexName);

		Query searchQuery = query.buildQuery();
		Results<ScoredDocument> results = index.search(searchQuery);
		return results;
	}

	// Delete Documents
	public static void delete(String documentId,
	                          String indexName,
	                          boolean async) {
		List<String> documentIds = new ArrayList<String>(1);
		documentIds.add(documentId);

		delete(documentIds, indexName, async);
	}

	/**
	 * Deletes the documents attached to the given searchables.
	 *
	 * Searchable objects are not reset with a null document identifier.
	 *
	 * @param models
	 * @param indexName
	 * @param async
	 */
	public static <T extends UniqueSearchModel> void deleteWithSearchables(Iterable<T> models,
	                                         String indexName,
	                                         boolean async) {

		List<String> documentIds = new ArrayList<String>();

		for (UniqueSearchModel searchable : models) {
			String documentId = searchable.getSearchIdentifier();

			if (documentId != null) {
				documentIds.add(documentId);
			}
		}

		delete(documentIds, indexName, async);
	}

	public static void delete(List<String> documentIds,
	                          String indexName,
	                          boolean async) {
		Index index = getIndex(indexName);

		List<List<String>> documentBatches = createDocumentListBatches(documentIds, API_DOCUMENT_DELETE_MAXIMUM);

		Integer batchCount = documentBatches.size();
		for (int i = 0; i < batchCount; i += 1) {
			List<String> batch = documentBatches.get(i);
			delete(batch, index, async);
		}
	}

	private static void delete(Iterable<String> documentIds,
	                           Index index,
	                           boolean async) {
		if (async) {
			index.deleteAsync(documentIds);
		} else {
			index.delete(documentIds);
		}
	}

	/**
	 * Utiility function for turning a list of items into multiple batch lists
	 * of size batchSize.
	 *
	 * @param items
	 * @param batchSize
	 * @return
	 */
	private static <T> List<List<T>> createDocumentListBatches(List<T> items,
	                                                           Integer batchSize) {
		List<List<T>> batches = BatchGenerator.createBatches(items, batchSize);
		return batches;
	}

	public static DocumentSearchIndexIterator makeIndexIterator(String indexName) {
		Index index = DocumentSearchController.getIndex(indexName);

		DocumentSearchIndexIterator iterator = new DocumentSearchIndexIterator(index);
		return iterator;
	}

	/**
	 * Not strictly an {@link Iterator}, but allows retrieval of documents from an index.
	 *
	 * @author dereekb
	 */
	public static class DocumentSearchIndexIterator {

		private String startId = null;

		private boolean reachedEnd = false;
		private Integer limit = DocumentSearchController.DEFAULT_RETRIEVE_LIMIT;
		private final Index index;

		private DocumentSearchIndexIterator(Index index) {
			this.index = index;
		}

		private GetRequest getNextRequest(boolean idsOnly) {
			GetRequest.Builder request = GetRequest.newBuilder().setReturningIdsOnly(idsOnly);

			if (this.startId == null) {
				request = request.setIncludeStart(true);
			} else {
				request = request.setIncludeStart(false).setStartId(this.startId);
			}

			if (this.limit != null) {
				request = request.setLimit(this.limit);
			}

			return request.build();
		}

		private GetResponse<Document> getNextResponse(boolean idsOnly) {
			GetRequest request = this.getNextRequest(idsOnly);
			GetResponse<Document> response = this.index.getRange(request);
			return response;
		}

		private List<Document> getNext(boolean idsOnly) {
			List<Document> results;

			if (this.reachedEnd == false) {
				GetResponse<Document> response = this.getNextResponse(idsOnly);
				results = response.getResults();

				if (results.isEmpty()) {
					this.reachedEnd = true;
					results = Collections.emptyList();
				} else {
					Integer size = results.size();
					Document last = results.get(size - 1);
					String lastId = last.getId();
					this.startId = lastId;
				}
			} else {
				results = Collections.emptyList();
			}

			return results;
		}

		public List<String> getNextDocumentsIdentifiers() {
			List<String> documentIds = new ArrayList<String>();
			List<Document> documents = this.getNext(true);

			for (Document document : documents) {
				String id = document.getId();
				documentIds.add(id);
			}

			return documentIds;
		}

		public List<Document> getNextDocuments() {
			List<Document> documents = this.getNext(false);
			return documents;
		}

		public boolean hasReachedEnd() {
			return this.reachedEnd;
		}

		public Integer getLimit() {
			return this.limit;
		}

		public void setLimit(Integer limit) throws IllegalArgumentException {
			if (limit <= 0 && limit <= DocumentSearchController.API_RETRIEVE_LIMIT_MAXIMUM) {
				throw new IllegalArgumentException("Limit must be greater than 0, and a max of "
				        + DocumentSearchController.API_RETRIEVE_LIMIT_MAXIMUM);
			}

			this.limit = limit;
		}

		public String getStartId() {
			return this.startId;
		}

		public void setStartId(String startId) throws RuntimeException {
			if (this.startId != null) {
				throw new RuntimeException("Can only set start id before iterating.");
			}

			this.startId = startId;
		}
	}
}
