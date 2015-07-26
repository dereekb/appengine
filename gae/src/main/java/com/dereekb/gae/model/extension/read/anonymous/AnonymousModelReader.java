package com.dereekb.gae.model.extension.read.anonymous;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.read.exception.UnavailableTypeException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used for reading {@link UniqueModel} values using a {@link ModelKey}.
 *
 * @author dereekb
 *
 */
public interface AnonymousModelReader {

	/**
	 * Reads models of the specified type.
	 *
	 * @param type
	 *            type to read. Never {@code null}.
	 * @param keys
	 *            keys to read. Never {@code null}.
	 * @return {@link ReadResponse} containing model results.
	 * @throws UnavailableTypeException
	 *             If the requested type is unavailable.
	 */
	public ReadResponse<? extends UniqueModel> read(String type,
	                                                Collection<ModelKey> keys) throws UnavailableTypeException;

}
