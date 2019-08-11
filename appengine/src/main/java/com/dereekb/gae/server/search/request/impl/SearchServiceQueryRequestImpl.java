package com.dereekb.gae.server.search.request.impl;

import com.dereekb.gae.server.search.query.SearchServiceQueryExpression;
import com.dereekb.gae.server.search.request.SearchServiceQueryRequest;
import com.dereekb.gae.utilities.model.search.request.SearchOptions;
import com.dereekb.gae.utilities.model.search.request.impl.SearchOptionsImpl;

/**
 * {@link SearchServiceQueryRequest} implementation.
 *
 * @author dereekb
 *
 */
public class SearchServiceQueryRequestImpl extends AbstractSearchServiceRequest
        implements SearchServiceQueryRequest {

	private SearchOptions searchOptions = new SearchOptionsImpl();
	private SearchServiceQueryExpression expression;

	public SearchServiceQueryRequestImpl(String indexName) throws IllegalArgumentException {
		super(indexName);
	}

	public SearchServiceQueryRequestImpl(String indexName, SearchOptions searchOptions)
	        throws IllegalArgumentException {
		this(indexName, searchOptions, null);
	}

	public SearchServiceQueryRequestImpl(String indexName,
	        SearchOptions searchOptions,
	        SearchServiceQueryExpression expression) throws IllegalArgumentException {
		super(indexName);
		this.setIndexName(indexName);
		this.setSearchOptions(searchOptions);
		this.setExpression(expression);
	}

	@Override
	public SearchOptions getSearchOptions() {
		return this.searchOptions;
	}

	public void setSearchOptions(SearchOptions searchOptions) {
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
