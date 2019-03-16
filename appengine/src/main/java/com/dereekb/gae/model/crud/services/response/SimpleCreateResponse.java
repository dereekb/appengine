package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.components.CreateService;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;

/**
 * Simple create response for a {@link CreateService}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @see CreateRequest
 */
public interface SimpleCreateResponse<T extends UniqueModel> {

	/**
	 * Returns all successfully created models.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<T> getModels();

	/**
	 * Returns a collection of keyed failures for templates, keyed by the index
	 * in the input.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<? extends KeyedInvalidAttribute> getInvalidTemplates();

}
