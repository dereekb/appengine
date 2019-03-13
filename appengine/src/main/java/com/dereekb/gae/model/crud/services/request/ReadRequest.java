package com.dereekb.gae.model.crud.services.request;

import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Contains paramters for a read request.
 *
 * @author dereekb
 */
public interface ReadRequest {

	/**
	 * @return Returns the keys of the models to read. Never null.
	 */
	public Iterable<ModelKey> getModelKeys();

	/**
	 * @return Returns the options. Never null.
	 */
	public ReadRequestOptions getOptions();

}
