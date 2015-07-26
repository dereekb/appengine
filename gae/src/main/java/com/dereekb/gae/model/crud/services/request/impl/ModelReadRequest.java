package com.dereekb.gae.model.crud.services.request.impl;

import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.ReadRequestOptions;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.SingleItem;


/**
 * Implementation of {@link ReadRequest} that uses {@link UniqueModel} objects.
 *
 * @author dereekb
 *
 */
public final class ModelReadRequest extends AbstractReadRequestImpl {

	public final Iterable<? extends UniqueModel> models;

	public ModelReadRequest(UniqueModel model) throws IllegalArgumentException {
		this(SingleItem.withValue(model), null);
	}

	public ModelReadRequest(UniqueModel model, ReadRequestOptions options) throws IllegalArgumentException {
		this(SingleItem.withValue(model), options);
	}

	public ModelReadRequest(Iterable<? extends UniqueModel> models) throws IllegalArgumentException {
		this(models, null);
	}

	public ModelReadRequest(Iterable<? extends UniqueModel> models, ReadRequestOptions options)
	        throws IllegalArgumentException {
		super(options);

		if (models == null) {
			throw new IllegalArgumentException("Models cannot be null.");
		}

		this.models = models;
	}

	@Override
	public Iterable<ModelKey> getModelKeys() {
		return ModelKey.readModelKeys(this.models);
	}

}
