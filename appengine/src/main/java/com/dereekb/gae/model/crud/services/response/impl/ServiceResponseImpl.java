package com.dereekb.gae.model.crud.services.response.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.services.response.ServiceResponse;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ServiceResponse} implementation.
 * 
 * @author dereekb
 */
public class ServiceResponseImpl
        implements ServiceResponse {

	protected Collection<ModelKey> filtered;
	protected Collection<ModelKey> unavailable;

	public ServiceResponseImpl() {
		this(new ArrayList<ModelKey>(0), new ArrayList<ModelKey>(0));
	}

	public ServiceResponseImpl(Collection<ModelKey> unavailable) {
		this(new ArrayList<ModelKey>(0), unavailable);
	}

	public ServiceResponseImpl(Collection<ModelKey> filtered, Collection<ModelKey> unavailable) {
		this.setFiltered(filtered);
		this.setUnavailable(unavailable);
	}

	public ServiceResponseImpl(ServiceResponse response) {
		this(response.getFiltered(), response.getUnavailable());
	}

	// MARK: ServiceResponse
	@Override
	public Collection<ModelKey> getFiltered() {
		return this.filtered;
	}

	public void setFiltered(Collection<ModelKey> filtered) throws IllegalArgumentException {
		if (filtered == null) {
			throw new IllegalArgumentException("filtered cannot be null.");
		}

		this.filtered = filtered;
	}

	@Override
	public Collection<ModelKey> getUnavailable() {
		return this.unavailable;
	}

	public void setUnavailable(Collection<ModelKey> unavailable) throws IllegalArgumentException {
		if (unavailable == null) {
			throw new IllegalArgumentException("unavailable cannot be null.");
		}

		this.unavailable = unavailable;
	}

	// MARK: SimpleServiceResponse
	@Override
	public List<ModelKey> getFailed() {
		List<ModelKey> errors = new ArrayList<ModelKey>();

		errors.addAll(this.filtered);
		errors.addAll(this.unavailable);

		return errors;
	}

	// MARK: Other
	public boolean isComplete() {
		return this.unavailable.isEmpty() && this.filtered.isEmpty();
	}

}
