package com.dereekb.gae.client.api.model.crud.response;

import java.util.Collection;

import com.dereekb.gae.client.api.model.crud.builder.ClientDeleteRequestSender;
import com.dereekb.gae.model.crud.services.response.SimpleDeleteResponse;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link SimpleDeleteResponse} extension for a
 * {@link ClientDeleteRequestSender}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ClientDeleteResponse<T>
        extends SimpleDeleteResponse<T> {

	/**
	 * {@inheritDoc}
	 * 
	 * @throws {@link
	 *             UnsupportedOperationException} if the request was
	 *             configured to not return models.
	 */
	@Override
	public Collection<T> getModels() throws UnsupportedOperationException;

	/**
	 * Returns the keys corresponding to those deleted.
	 * 
	 * @return {@link Collection} of model keys.
	 */
	public Collection<ModelKey> getModelKeys();

}
