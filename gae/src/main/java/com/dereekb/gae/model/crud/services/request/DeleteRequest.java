package com.dereekb.gae.model.crud.services.request;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;


public interface DeleteRequest<T extends UniqueModel> {

	/**
	 * @return Returns the keys of models to delete. Never null.
	 */
	public Collection<ModelKey> getTargetKeys();

	/**
	 * @return Returns the options. Never null.
	 */
	public DeleteRequestOptions getOptions();

}
