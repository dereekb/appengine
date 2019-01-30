package com.dereekb.gae.client.api.model.crud.request.impl;

import java.util.Collection;

import com.dereekb.gae.client.api.model.crud.request.ClientDeleteRequest;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.options.DeleteRequestOptions;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ClientDeleteRequest} implementation.
 * 
 * @author dereekb
 *
 */
public class ClientDeleteRequestImpl
        implements ClientDeleteRequest {

	private DeleteRequest request;
	private boolean returnModels;

	public ClientDeleteRequestImpl(DeleteRequest request, boolean returnModels) {
		this.setRequest(request);
		this.setReturnModels(returnModels);
	}

	public DeleteRequest getRequest() {
		return this.request;
	}

	public void setRequest(DeleteRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("Request cannot be null.");
		}

		this.request = request;
	}

	public void setReturnModels(boolean returnModels) {
		this.returnModels = returnModels;
	}

	// MARK: ClientDeleteRequest
	@Override
	public Collection<ModelKey> getTargetKeys() {
		return this.request.getTargetKeys();
	}

	@Override
	public DeleteRequestOptions getOptions() {
		return this.request.getOptions();
	}

	@Override
	public boolean shouldReturnModels() {
		return this.returnModels;
	}

	@Override
	public String toString() {
		return "ClientDeleteRequestImpl [request=" + this.request + ", returnModels=" + this.returnModels + "]";
	}

}
