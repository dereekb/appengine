package com.dereekb.gae.server.search.request.impl;

import com.dereekb.gae.server.search.query.SearchServiceQueryExpression;
import com.dereekb.gae.server.search.request.SearchServiceQueryOptions;
import com.dereekb.gae.server.search.request.SearchServiceQueryRequest;

/**
 * {@link SearchServiceQueryRequest} implementation.
 *
 * @author dereekb
 *
 */
public class SearchServiceQueryRequestImpl extends AbstractSearchServiceRequest
        implements SearchServiceQueryRequest {

	private SearchServiceQueryOptions searchOptions = new SearchServiceQueryOptionsImpl();
	private SearchServiceQueryExpression expression;

	public SearchServiceQueryRequestImpl(String indexName) throws IllegalArgumentException {
		super(indexName);
	}

	public SearchServiceQueryRequestImpl(String indexName, SearchServiceQueryOptions searchOptions)
	        throws IllegalArgumentException {
		this(indexName, searchOptions, null);
	}

	public SearchServiceQueryRequestImpl(String indexName,
	        SearchServiceQueryOptions searchOptions,
	        SearchServiceQueryExpression expression) throws IllegalArgumentException {
		super(indexName);
		this.setIndexName(indexName);
		this.setSearchServiceQueryOptions(searchOptions);
		this.setExpression(expression);
	}

	@Override
	public SearchServiceQueryOptions getSearchOptions() {
		return this.searchOptions;
	}

	public void setSearchServiceQueryOptions(SearchServiceQueryOptions searchOptions) {
		if (searchOptions == null) {
			throw new IllegalArgumentException("searchOptions cannot be null.");
		}

		this.searchOptions = searchOptions;
	}

	@Override
	public SearchServiceQueryExpression getExpression() {
		return this.expression;
	}

	public void setExpression(SearchServiceQueryExpression expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "SearchServiceQueryRequestImpl [searchOptions=" + this.searchOptions + ", expression=" + this.expression
		        + ", getIndexName()=" + this.getIndexName() + "]";
	}

}
