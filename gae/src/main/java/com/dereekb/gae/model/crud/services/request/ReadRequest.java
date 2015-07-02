package com.dereekb.gae.model.crud.services.request;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Wrapper for read request parameters.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface ReadRequest<T extends UniqueModel> {

	/**
	 * @return Returns the keys of the models to read. Never null.
	 */
	public Iterable<ModelKey> getModelKeys();

	/**
	 * @return Returns the options. Never null.
	 */
	public ReadRequestOptions getOptions();

}
