package com.dereekb.gae.model.crud.services.response.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Default implementation of {@link CreateResponse}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public final class CreateResponseImpl<T extends UniqueModel>
        implements CreateResponse<T> {

	private final Collection<T> models;
	private final Collection<T> failed;

	public CreateResponseImpl(Collection<T> models, Collection<T> failed) {
		this.models = models;
		this.failed = failed;
	}

	@Override
	public Collection<T> getCreatedModels() {
		return this.models;
	}

	@Override
	public Collection<ModelKey> getCreatedModelKeys() {
		return ModelKey.readModelKeys(this.models);
	}

	@Override
	public Collection<T> getFailedTemplates() {
		return this.failed;
	}

}
