package com.dereekb.gae.server.search.system.request.impl;

import java.util.Collection;

import com.dereekb.gae.server.search.system.request.DocumentPutRequest;
import com.dereekb.gae.server.search.system.request.DocumentPutRequestModel;

/**
 * {@link DocumentPutRequest} implementation.
 *
 * @author dereekb
 *
 */
public class DocumentPutRequestImpl extends SearchDocumentRequestImpl
        implements DocumentPutRequest {

	private boolean update;
	private Collection<DocumentPutRequestModel> putRequestModels;

	public DocumentPutRequestImpl(String indexName, Collection<DocumentPutRequestModel> putRequestModels)
	        throws IllegalArgumentException {
		super(indexName);
		this.putRequestModels = putRequestModels;
	}

	@Override
	public boolean isUpdate() {
		return this.update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
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
