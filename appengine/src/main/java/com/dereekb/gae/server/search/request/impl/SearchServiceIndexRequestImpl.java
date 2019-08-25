package com.dereekb.gae.server.search.request.impl;

import com.dereekb.gae.server.search.request.SearchServiceIndexRequest;
import com.dereekb.gae.server.search.request.SearchServiceIndexRequestPair;

/**
 * {@link SearchServiceIndexRequest} implementation.
 *
 * @author dereekb
 *
 */
public class SearchServiceIndexRequestImpl extends AbstractSearchServiceRequest
        implements SearchServiceIndexRequest {

	private Iterable<SearchServiceIndexRequestPair> requestPairs;

	private boolean allowAsync = false;

	public SearchServiceIndexRequestImpl(String indexName, Iterable<SearchServiceIndexRequestPair> requestPairs)
	        throws IllegalArgumentException {
		this(indexName, requestPairs, false);
	}

	public SearchServiceIndexRequestImpl(String indexName,
	        Iterable<SearchServiceIndexRequestPair> requestPairs,
	        boolean allowAsync) throws IllegalArgumentException {
		super(indexName);
		this.setAllowAsync(allowAsync);
		this.setRequestPairs(requestPairs);
	}

	@Override
	public boolean getAllowAsync() {
		return this.allowAsync;
	}

	public void setAllowAsync(boolean allowAsync) {
		this.allowAsync = allowAsync;
	}

	@Override
	public Iterable<SearchServiceIndexRequestPair> getRequestPairs() {
		return this.requestPairs;
	}

	public void setRequestPairs(Iterable<SearchServiceIndexRequestPair> requestPairs) {
		if (requestPairs == null) {
			throw new IllegalArgumentException("requestPairs cannot be null.");
		}

		this.requestPairs = requestPairs;
	}

	@Override
	public String toString() {
		return "SearchServiceIndexRequestImpl [requestPairs=" + this.requestPairs + ", getIndexName()="
		        + this.getIndexName() + "]";
	}

}
