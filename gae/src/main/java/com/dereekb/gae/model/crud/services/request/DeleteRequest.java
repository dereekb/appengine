package com.dereekb.gae.model.crud.services.request;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.request.options.DeleteRequestOptions;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Contains parameters for a delete request.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface DeleteRequest {

	/**
	 * @return Returns the keys of models to delete. Never null.
	 */
	public Collection<ModelKey> getTargetKeys();

	/**
	 * @return Returns the options. Never null.
	 */
	public DeleteRequestOptions getOptions();

}
