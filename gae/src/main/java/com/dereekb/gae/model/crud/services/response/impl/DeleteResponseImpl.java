package com.dereekb.gae.model.crud.services.response.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.response.DeleteResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;


/**
 * Default implementation of {@link DeleteResponse}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public final class DeleteResponseImpl<T extends UniqueModel> extends ServiceResponse<T>
        implements DeleteResponse<T> {

	public DeleteResponseImpl(Collection<T> models) {
		super(models);
	}

	public DeleteResponseImpl(Collection<T> models, Collection<ModelKey> unavailable) {
		super(models, unavailable);
	}

	public DeleteResponseImpl(Collection<T> models, Collection<ModelKey> filtered, Collection<ModelKey> unavailable) {
		super(models, filtered, unavailable);
	}

	public DeleteResponseImpl(ServiceResponse<T> response) {
		super(response);
	}

	@Override
	public Collection<T> getDeletedModels() {
		return this.models;
	}

	@Override
	public Collection<ModelKey> getFiltered() {
		return this.filtered;
	}

	@Override
	public Collection<ModelKey> getUnavailable() {
		return this.unavailable;
	}

}
