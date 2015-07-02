package com.dereekb.gae.model.crud.services.request;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.UniqueModel;

public interface UpdateRequest<T extends UniqueModel> {

	/**
	 * Corresponds to the models that should be updated. The templates carry the
	 * same key as their target, and the values that should be updated.
	 *
	 * @return Returns the instances representing the models to update. Never
	 *         null.
	 */
	public Collection<T> getTemplates();

	/**
	 * @return Returns the options. Never null.
	 */
	public UpdateRequestOptions getOptions();

}
