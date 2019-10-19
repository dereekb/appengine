package com.dereekb.gae.server.search.request.impl;

import java.util.Collection;

import com.dereekb.gae.server.search.request.SearchServiceQueryOptions;
import com.dereekb.gae.utilities.model.search.request.SearchOptions;
import com.dereekb.gae.utilities.model.search.request.impl.SearchOptionsImpl;

/**
 * {@link SearchServiceQueryOptions} implementation.
 *
 * @author dereekb
 *
 */
public class SearchServiceQueryOptionsImpl extends SearchOptionsImpl
        implements SearchServiceQueryOptions {

	private Collection<String> fieldsToReturn;

	public SearchServiceQueryOptionsImpl() {
		super();
	}

	public SearchServiceQueryOptionsImpl(SearchOptions options) {
		super(options);
	}

	public SearchServiceQueryOptionsImpl(SearchServiceQueryOptions options) {
		super(options);
		this.setFieldsToReturn(options.getFieldsToReturn());
	}

	// MARK: SearchServiceQueryOptions
	@Override
	public Collection<String> getFieldsToReturn() {
		return this.fieldsToReturn;
	}

	public void setFieldsToReturn(Collection<String> fieldsToReturn) {
		this.fieldsToReturn = fieldsToReturn;
	}

}
