package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Response returned by a {@link ReadService}.
 *
 * @author dereekb
 * @param <T>
 */
public interface ReadResponse<T> {

	/**
	 * @return Returns a collection of models that were read.
	 */
	public Collection<T> getModels();

	/**
	 * @return Returns a collection of {@link ModelKey} instances for elements
	 *         that were filtered out.
	 */
	public Collection<ModelKey> getFiltered();

	/**
	 * @return Returns a collection of {@link ModelKey} instances for elements
	 *         that were not available. Should not include models returned from
	 *         getFiltered().
	 */
	public Collection<ModelKey> getUnavailable();

}
