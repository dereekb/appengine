package com.dereekb.gae.model.extension.search.document.search;

import java.util.List;

import com.google.appengine.api.search.ScoredDocument;

/**
 * Pair used by {@link DocumentSearchFunction}.
 *
 * @author dereekb
 *
 * @param <Q>
 *            Query type.
 */
public final class SearchPair<Q> {

	private final Q query;
	private List<ScoredDocument> results;

	public SearchPair(Q query) {
		this.query = query;
	}

	public Q getQuery() {
		return this.query;
	}

	public List<ScoredDocument> getResults() {
		return this.results;
	}

	public void setResults(List<ScoredDocument> results) {
		this.results = results;
	}

}
