package com.dereekb.gae.server.search.components.impl;

import com.dereekb.gae.server.search.components.SearchServiceIndex;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link SearchServiceIndex} implementation.
 *
 * @author dereekb
 *
 */
public class SearchServiceIndexImpl
        implements SearchServiceIndex {

	private String indexName;

	public SearchServiceIndexImpl(String indexName) throws IllegalArgumentException {
		this.indexName = indexName;
	}

	@Override
	public String getIndexName() {
		return this.indexName;
	}

	public void setIndexName(String indexName) throws IllegalArgumentException {
		if (StringUtility.isEmptyString(indexName)) {
			throw new IllegalArgumentException("Index name cannot be null or empty.");
		}

		this.indexName = indexName;
	}

}
