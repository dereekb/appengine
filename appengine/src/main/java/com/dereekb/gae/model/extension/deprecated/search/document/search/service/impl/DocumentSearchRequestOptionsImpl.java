package com.dereekb.gae.model.extension.search.document.search.service.impl;

import com.dereekb.gae.utilities.model.search.request.SearchOptions;
import com.dereekb.gae.utilities.model.search.request.impl.SearchOptionsImpl;

/**
 * {@link SearchOptions} implementation.
 *
 * @author dereekb
 *
 */
public class DocumentSearchRequestOptionsImpl extends SearchOptionsImpl {

	public DocumentSearchRequestOptionsImpl() {
		super();
	}

	public DocumentSearchRequestOptionsImpl(SearchOptions options) {
		super(options);
	}

	public DocumentSearchRequestOptionsImpl(String cursor, Integer offset, Integer limit) {
		super(cursor, offset, limit);
	}

}
