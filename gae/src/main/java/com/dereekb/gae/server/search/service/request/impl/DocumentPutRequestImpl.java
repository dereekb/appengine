package com.dereekb.gae.server.search.service.request.impl;

import java.util.Collection;

import com.dereekb.gae.server.search.service.request.DocumentPutRequest;
import com.dereekb.gae.server.search.service.request.DocumentPutRequestModel;

/**
 * {@link DocumentPutRequest} implementation.
 *
 * @author dereekb
 *
 */
public class DocumentPutRequestImpl extends SearchDocumentRequestImpl
        implements DocumentPutRequest {

	private Collection<DocumentPutRequestModel> putRequestModels;

	public DocumentPutRequestImpl(String indexName, Collection<DocumentPutRequestModel> putRequestModels)
	        throws IllegalArgumentException {
		super(indexName);
		this.putRequestModels = putRequestModels;
	}

	@Override
	public Collection<DocumentPutRequestModel> getPutRequestModels() {
		return this.putRequestModels;
	}

	public void setPutRequestModels(Collection<DocumentPutRequestModel> putRequestModels)
	        throws IllegalArgumentException {
		if (putRequestModels == null) {
			throw new IllegalArgumentException("PutRequestModels cannot be null.");
		}

		this.putRequestModels = putRequestModels;
	}

}
