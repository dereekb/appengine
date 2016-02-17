package com.dereekb.gae.model.crud.services.request;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.request.options.UpdateRequestOptions;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Contains parameters for an update request.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface UpdateRequest<T extends UniqueModel> {

	/**
	 * Corresponds to the models that should be updated. The templates carry the
	 * same key as their target, and the values that should be updated.
	 *
	 * @return Returns the instances representing the models to update. Never
	 *         {@code null}.
	 */
	public Collection<T> getTemplates();

	/**
	 * Returns update options.
	 *
	 * @return Returns the options. Never {@code null}.
	 */
	public UpdateRequestOptions getOptions();

}
