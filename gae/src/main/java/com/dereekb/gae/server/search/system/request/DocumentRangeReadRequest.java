package com.dereekb.gae.server.search.system.request;

/**
 * Request used for reading an arbitrary range of documents.
 *
 * @author dereekb
 *
 */
public interface DocumentRangeReadRequest
        extends SearchDocumentRequest {

	/**
	 * Identifier to start the range read at.
	 *
	 * @return {@link String} representing the identifier. Never {@code null}.
	 */
	public String getStartIdentifier();

	/**
	 * Optional number of results to return.
	 *
	 * @return Number of results to return. Returns {@code null} if no
	 *         preference.
	 */
	public Integer getLimit();

	/**
	 * Type of results to return.
	 *
	 * @return {@link DocumentResultType}. Never {@code null}.
	 */
	public DocumentResultType getResultType();

}
