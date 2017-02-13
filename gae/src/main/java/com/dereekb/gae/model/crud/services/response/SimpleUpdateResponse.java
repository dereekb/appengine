package com.dereekb.gae.model.crud.services.response;

import java.util.Collection;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;

/**
 * Simple update response.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface SimpleUpdateResponse<T extends UniqueModel>
        extends SimpleModelServiceResponse<T> {

	/**
	 * {@inheritDoc}
	 * 
	 * This is the collection of models successfully updated.
	 */
	@Override
	public Collection<T> getModels();

	/**
	 * {@inheritDoc}
	 * 
	 * This includes all models/keys from {@link #getUpdateFailures()}.
	 */
	@Override
	public Collection<ModelKey> getFailed();

	/**
	 * Returns the result of {@link SimpleServiceResponse#getFailed()}; all keys
	 * that were not included with
	 * {@link SimpleUpdateResponse#getUpdateFailures()}.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<ModelKey> getMissingKeys();

	/**
	 * Returns a collection of keyed failures.
	 * 
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<? extends KeyedInvalidAttribute> getUpdateFailures();

}
