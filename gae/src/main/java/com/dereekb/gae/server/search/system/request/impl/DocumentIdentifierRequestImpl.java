package com.dereekb.gae.server.search.system.request.impl;

import java.util.Collection;

import com.dereekb.gae.server.search.system.request.DocumentIdentifierRequest;

/**
 * {@link DocumentIdentifierRequest} implementation.
 *
 * @author dereekb
 *
 */
public class DocumentIdentifierRequestImpl extends SearchDocumentRequestImpl
        implements DocumentIdentifierRequest {

	private Collection<String> documentIdentifiers;

	public DocumentIdentifierRequestImpl(String indexName, Collection<String> documentIdentifiers)
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
