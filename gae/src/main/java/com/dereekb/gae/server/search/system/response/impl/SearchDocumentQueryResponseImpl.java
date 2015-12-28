package com.dereekb.gae.server.search.system.response.impl;

import java.util.Collection;

import com.dereekb.gae.server.search.system.response.SearchDocumentQueryResponse;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;

/**
 * {@link SearchDocumentQueryResponse} implementation.
 * <p>
 * Will lazy-load/perform the search.
 *
 * @author dereekb
 *
 */
public class SearchDocumentQueryResponseImpl
        implements SearchDocumentQueryResponse {

	private Results<ScoredDocument> results = null;

	private final Index index;
	private final Query query;

	public SearchDocumentQueryResponseImpl(Index index, Query query) {
		this.index = index;
		this.query = query;
	}

	@Override
	public Collection<ScoredDocument> getDocumentResults() {
		Results<ScoredDocument> results = this.getResults();
		return results.getResults();
	}

	@Override
	public Long getFoundResults() {
		Results<ScoredDocument> results = this.getResults();
		return results.getNumberFound();
	}

	@Override
	public Integer getReturnedResults() {
		Results<ScoredDocument> results = this.getResults();
		return results.getNumberReturned();
	}

	// MARK: Internal
	private Results<ScoredDocument> getResults() {
		if (this.results == null) {
			this.results = this.performSearch();
		}

		return this.results;
	}

	private Results<ScoredDocument> performSearch() {
		Results<ScoredDocument> results = this.index.search(this.query);
		return results;
	}

}
