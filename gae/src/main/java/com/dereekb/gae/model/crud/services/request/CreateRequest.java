package com.dereekb.gae.model.crud.services.request;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * A create request using templates.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface CreateRequest<T extends UniqueModel> {

	/**
	 * @return Returns the templates for the models to create. Never null.
	 */
	public Collection<T> getTemplates();

	/**
	 * @return Returns the options. Never null.
	 */
	public CreateRequestOptions getOptions();

}
