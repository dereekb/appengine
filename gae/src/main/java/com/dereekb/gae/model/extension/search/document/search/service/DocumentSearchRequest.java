package com.dereekb.gae.model.extension.search.document.search.service;

/**
 * Used by {@link DocumentSearchService}.
 *
 * @author dereekb
 *
 */
public interface DocumentSearchRequest {

	/**
	 * @return Index to query. Never {@code null}.
	 */
	public String getIndex();

	/**
	 * @return Query string. Never {@code null}.
	 */
	public String getQueryExpression();

	/**
	 * Returns any request options.
	 *
	 * @return Options. Can be {@code null}.
	 */
	public DocumentSearchRequestOptions getOptions();

}
