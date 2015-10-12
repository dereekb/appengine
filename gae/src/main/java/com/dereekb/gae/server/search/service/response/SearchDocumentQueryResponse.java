package com.dereekb.gae.server.search.service.response;

import java.util.Collection;

import com.google.appengine.api.search.ScoredDocument;


public interface SearchDocumentQueryResponse {

	/**
	 * @return {@link Collection} of {@link ScoredDocument} values. Never
	 *         {@code null}.
	 */
	public Collection<ScoredDocument> getDocumentResults();

	/**
	 * Returns the total number of results that match the query.
	 *
	 * @return {@link Long} for the total number of results. Never {@code null}.
	 */
	public Long getFoundResults();

	/**
	 * Returns the number of returned results.
	 *
	 * @return {@link Integer} for the number of returned results. Never
	 *         {@code null}.
	 */
	public Integer getReturnedResults();

}
