package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

public interface CreateResponse<T extends UniqueModel> {

	/**
	 * @return Returns a collection of models that were created.
	 */
	public Collection<T> getCreatedModels();

	/**
	 * @return Returns a collection of keys for the models that were created.
	 */
	public Collection<ModelKey> getCreatedModelKeys();

	/**
	 * @return Returns a collection of templates that failed to be created.
	 */
	public Collection<T> getFailedTemplates();

}
