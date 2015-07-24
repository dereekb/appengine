package com.dereekb.gae.model.extension.read;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used for reading {@link UniqueModel} values using a {@link ModelKey}.
 *
 * @author dereekb
 *
 */
public interface AnonymousModelReader {

	public ReadResponse<? extends UniqueModel> read(String type,
	                                                Collection<ModelKey> keys);

}
