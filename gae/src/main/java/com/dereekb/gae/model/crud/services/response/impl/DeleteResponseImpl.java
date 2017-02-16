package com.dereekb.gae.model.crud.services.response.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.response.DeleteResponse;
import com.dereekb.gae.model.crud.services.response.ModelServiceResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link DeleteResponse} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class DeleteResponseImpl<T extends UniqueModel> extends ModelServiceResponseImpl<T>
        implements DeleteResponse<T> {

	public DeleteResponseImpl() {
		super();
	}

	public DeleteResponseImpl(Collection<T> models, Collection<ModelKey> filtered, Collection<ModelKey> unavailable) {
		super(models, filtered, unavailable);
	}

	public DeleteResponseImpl(Collection<T> models, Collection<ModelKey> unavailable) {
		super(models, unavailable);
	}

	public DeleteResponseImpl(Collection<T> models) {
		super(models);
	}

	public DeleteResponseImpl(ModelServiceResponse<T> response) {
		super(response);
	}

}
