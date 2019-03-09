package com.dereekb.gae.model.crud.services.request.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.options.DeleteRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.DeleteRequestOptionsImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * {@link DeleteRequest} implementation.
 *
 * @author dereekb
 *
 */
public class DeleteRequestImpl
        implements DeleteRequest {

	private Collection<ModelKey> targetKeys;
	private DeleteRequestOptions options;

	public DeleteRequestImpl(UniqueModel target) {
		this(SingleItem.withValue(target.getModelKey()));
	}

	public DeleteRequestImpl(UniqueModel target, DeleteRequestOptions options) {
		this(SingleItem.withValue(target.getModelKey()), options);
	}

	public DeleteRequestImpl(ModelKey targetKey) {
		this(SingleItem.withValue(targetKey));
	}

	public DeleteRequestImpl(ModelKey targetKey, DeleteRequestOptions options) {
		this(SingleItem.withValue(targetKey), options);
	}

	public DeleteRequestImpl(Collection<? extends UniqueModel> uniqueModels) {
		this(uniqueModels, null);
	}

	public DeleteRequestImpl(Collection<? extends UniqueModel> uniqueModels, DeleteRequestOptions options) {
		if (uniqueModels == null) {
			throw new IllegalArgumentException("Keys cannot be null.");
		}

		this.targetKeys = ModelKey.readModelKeys(uniqueModels);
		this.setOptions(options);
	}

	@Override
	public Collection<ModelKey> getTargetKeys() {
		return this.targetKeys;
	}

	public void setTargetKeys(Collection<ModelKey> targetKeys) {
		this.targetKeys = targetKeys;
	}

	@Override
	public DeleteRequestOptions getOptions() {
		return this.options;
	}

	public void setOptions(DeleteRequestOptions options) {
		if (options == null) {
			options = new DeleteRequestOptionsImpl();
		}

		this.options = options;
	}

}
