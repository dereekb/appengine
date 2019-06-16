package com.dereekb.gae.server.search.system.request.impl;

import com.dereekb.gae.server.deprecated.search.system.request.DocumentRangeReadRequest;
import com.dereekb.gae.server.deprecated.search.system.request.DocumentResultType;

/**
 * {@link DocuemntRangeReadRequest} implementation.
 *
 * @author dereekb
 *
 */
public class DocumentRangeReadRequestImpl extends SearchDocumentRequestImpl
        implements DocumentRangeReadRequest {

	private String startIdentifier;
	private Integer limit;
	private DocumentResultType resultType;

	public DocumentRangeReadRequestImpl(String indexName, String documentIdentifier) throws IllegalArgumentException {
		this(indexName, documentIdentifier, 1, DocumentResultType.DOCUMENTS);
	}

	public DocumentRangeReadRequestImpl(String indexName, String startIdentifier, DocumentResultType resultType) {
		this(indexName, startIdentifier, null, resultType);
	}

	public DocumentRangeReadRequestImpl(String indexName,
	        String startIdentifier,
	        Integer limit,
	        DocumentResultType resultType) {
		super(indexName);
		this.setStartIdentifier(startIdentifier);
		this.limit = limit;
		this.resultType = resultType;
	}

	@Override
	public String getStartIdentifier() {
		return this.startIdentifier;
	}

	public void setStartIdentifier(String startIdentifier) {
		this.startIdentifier = startIdentifier;
	}

	@Override
	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Override
	public DocumentResultType getResultType() {
		return this.resultType;
	}

	public void setResultType(DocumentResultType resultType) {
		this.resultType = resultType;
	}

}
