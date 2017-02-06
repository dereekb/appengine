package com.dereekb.gae.server.search.system.response.impl;

import java.util.Collection;

import com.dereekb.gae.server.search.system.response.SearchDocumentQueryResponse;
import com.google.appengine.api.search.Cursor;
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
	public Cursor getResultsCursor() {
		return this.getResults().getCursor();
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
