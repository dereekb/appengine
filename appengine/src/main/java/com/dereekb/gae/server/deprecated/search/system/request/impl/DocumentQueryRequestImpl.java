package com.dereekb.gae.server.search.system.request.impl;

import com.dereekb.gae.server.deprecated.search.system.request.DocumentQueryRequest;
import com.google.appengine.api.search.Query;

/**
 * {@link DocumentQueryRequest} implementation.
 *
 * @author dereekb
 *
 */
public class DocumentQueryRequestImpl
        implements DocumentQueryRequest {

	private String indexName;
	private Query documentQuery;

	@Override
	public String getIndexName() {
		return this.indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	@Override
	public Query getDocumentQuery() {
		return this.documentQuery;
	}

	public void setDocumentQuery(Query.Builder builder) {
		this.documentQuery = builder.build();
	}

	public void setDocumentQuery(Query documentQuery) {
		this.documentQuery = documentQuery;
	}

	@Override
	public String toString() {
		return "DocumentQueryRequestImpl [indexName=" + this.indexName + ", documentQuery=" + this.documentQuery + "]";
	}

}
