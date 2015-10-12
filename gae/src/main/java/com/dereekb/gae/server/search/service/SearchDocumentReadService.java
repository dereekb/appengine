package com.dereekb.gae.server.search.service;

import com.dereekb.gae.server.search.service.exception.MissingDocumentException;
import com.dereekb.gae.server.search.service.request.DocumentIdentifierRequest;
import com.dereekb.gae.server.search.service.request.DocumentRangeReadRequest;
import com.dereekb.gae.server.search.service.response.SearchDocumentReadResponse;
import com.google.appengine.api.search.Document;


public interface SearchDocumentReadService {

	/**
	 * Reads the document at the start index in the
	 * {@link DocumentRangeReadRequest} provided.
	 *
	 * @param request
	 *            {@link DocumentRangeReadRequest}. Never {@code null}.
	 * @return {@link Document} corresponding to the first index.
	 */
	public Document readDocument(DocumentRangeReadRequest request) throws MissingDocumentException;

	/**
	 * Reads the document at the start index in the
	 * {@link DocumentRangeReadRequest} provided, ending at the limit specified
	 * by the request.
	 *
	 * @param request
	 *            {@link DocumentRangeReadRequest}. Never {@code null}.
	 * @return {@link SearchDocumentReadResponse}. Never {@code null}.
	 */
	public SearchDocumentReadResponse readDocuments(DocumentRangeReadRequest request);

	/**
	 * Reads the documents corresponding to the identifiers in the
	 * {@link DocumentIdentifierRequest}.
	 *
	 * @param request
	 *            {@link DocumentRangeReadRequest}. Never {@code null}.
	 * @return {@link SearchDocumentReadResponse}. Never {@code null}.
	 */
	public SearchDocumentReadResponse readDocuments(DocumentIdentifierRequest request);

}
