package com.dereekb.gae.client.api.model.crud.request.impl;

import com.dereekb.gae.client.api.model.crud.request.ClientReadRequest;
import com.dereekb.gae.client.api.model.shared.request.impl.RelatedTypesRequestImpl;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ClientReadRequest} implementation.
 * 
 * @author dereekb
 *
 */
public class ClientReadRequestImpl extends RelatedTypesRequestImpl
        implements ClientReadRequest {

	private ReadRequest readRequest;

	public ClientReadRequestImpl(ReadRequest readRequest) throws IllegalArgumentException {
		this(false, readRequest);
	}

	public ClientReadRequestImpl(boolean loadRelatedTypes, ReadRequest readRequest) throws IllegalArgumentException {
		this.setLoadRelatedTypes(loadRelatedTypes);
		this.setReadRequest(readRequest);
	}

	public ReadRequest getReadRequest() {
		return this.readRequest;
	}

	public void setReadRequest(ReadRequest readRequest) throws IllegalArgumentException {
		if (readRequest == null) {
			throw new IllegalArgumentException("ReadRequest cannot be null.");
		}

		this.readRequest = readRequest;
	}

	// MARK: ReadRequest
	@Override
	public Iterable<ModelKey> getModelKeys() {
		return this.readRequest.getModelKeys();
	}

	@Override
	public ReadRequestOptions getOptions() {
		return this.readRequest.getOptions();
	}

	@Override
	public String toString() {
		return "ClientReadRequestImpl [readRequest=" + this.readRequest + ", loadRelatedTypes=" + this.loadRelatedTypes
		        + ", relatedTypesFilter=" + this.relatedTypesFilter + "]";
	}

}
