package com.dereekb.gae.model.crud.services.request;

import java.util.List;

import com.dereekb.gae.model.crud.services.request.options.CreateRequestOptions;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Contains parameters for a create request.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface CreateRequest<T extends UniqueModel> {

	/**
	 * Returns the templates for the models to create.
	 * 
	 * @return Non-empty {@link List}. Never {@code null}.
	 */
	public List<T> getTemplates();

	/**
	 * @return {@link CreateRequestOptions}. Never {@code null}.
	 */
	public CreateRequestOptions getOptions();

}
