package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * System response that contains info about which models were filtered,
 * unavailable and failed in general to be changed by a system request.
 * <p>
 * Generally extended by responses for requests that require loading models.
 * 
 * @author dereekb
 *
 */
public interface ServiceResponse
        extends SimpleServiceResponse {

	/**
	 * Returns a collection of {@link ModelKey} instances for elements
	 * that were filtered out.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<ModelKey> getFiltered();

	/**
	 * Returns a collection of {@link ModelKey} instances for elements
	 * that were not available.
	 * <p>
	 * Should not include models returned from {{@link #getFiltered()}.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<ModelKey> getUnavailable();

	/**
	 * {@inheritDoc}
	 * 
	 * This is the union between {@link #getFiltered()} and
	 * {@link #getUnavailable()}.
	 * 
	 */
	@Override
	public Collection<ModelKey> getFailed();

}
