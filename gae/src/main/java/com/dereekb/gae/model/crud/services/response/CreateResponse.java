package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.components.CreateService;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Create response for a {@link CreateService}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * 
 * @see CreateRequest
 */
public interface CreateResponse<T extends UniqueModel> {

	/**
	 * Returns the collection of models that were created.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<T> getCreatedModels();

	/**
	 * Returns a collection of keys for the models that were created.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 * 
	 * @deprecated Use {@link ModelKey#readModelKeys(Iterable)} instead.
	 */
	@Deprecated
	public Collection<ModelKey> getCreatedModelKeys();

	/**
	 * Returns the collection of templates that failed to be created.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<T> getFailedTemplates();

}
