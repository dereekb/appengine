package com.dereekb.gae.server.search.service.request.impl;

import java.util.Collection;

import com.dereekb.gae.server.search.service.request.DocumentMultiReadRequest;

/**
 * {@link DocuemntMultiReadRequest} implementation.
 *
 * @author dereekb
 *
 */
public class DocumentMultiReadRequestImpl extends SearchDocumentRequestImpl
        implements DocumentMultiReadRequest {

	private Collection<String> documentIdentifiers;

	public DocumentMultiReadRequestImpl(String indexName, Collection<String> documentIdentifiers)
	        throws IllegalArgumentException {
		super(indexName);
		this.setDocumentIdentifiers(documentIdentifiers);
	}

	@Override
    public Collection<String> getDocumentIdentifiers() {
		return this.documentIdentifiers;
	}

	public void setDocumentIdentifiers(Collection<String> documentIdentifiers) throws IllegalArgumentException {
		if (documentIdentifiers == null) {
			throw new IllegalArgumentException("Document identifiers cannot be null.");
		}

		this.documentIdentifiers = documentIdentifiers;
	}

}
