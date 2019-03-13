package com.dereekb.gae.model.crud.services.response.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ReadResponse} that wraps another response to act as an existance response.
 * 
 * @author dereekb
 *
 */
public class ExistsReadResponse implements ReadResponse<ModelKey> {
	
	private ReadResponse<? extends UniqueModel> wrappedResponse;
	private Collection<ModelKey> existing = null;

	public ExistsReadResponse(ReadResponse<? extends UniqueModel> wrappedResponse) {
		this.setWrappedResponse(wrappedResponse);
	}

	public ReadResponse<? extends UniqueModel> getWrappedResponse() {
		return this.wrappedResponse;
	}
	
	public void setWrappedResponse(ReadResponse<? extends UniqueModel> wrappedResponse) {
		if (wrappedResponse == null) {
			throw new IllegalArgumentException("wrappedResponse cannot be null.");
		}
	
		this.wrappedResponse = wrappedResponse;
	}

	@Override
	public Collection<ModelKey> getFiltered() {
		return this.wrappedResponse.getFiltered();
	}

	@Override
	public Collection<ModelKey> getUnavailable() {
		return this.wrappedResponse.getUnavailable();
	}

	@Override
	public Collection<ModelKey> getFailed() {
		return this.wrappedResponse.getFailed();
	}

	@Override
	public Collection<ModelKey> getModels() {
		if (this.existing == null) {
			this.existing = ModelKey.readModelKeys(this.wrappedResponse.getModels());
		}
		
		return this.existing;
	}

}
