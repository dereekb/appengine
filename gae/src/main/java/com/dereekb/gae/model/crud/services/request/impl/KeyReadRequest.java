package com.dereekb.gae.model.crud.services.request.impl;

import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.ReadRequestOptionsImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * Implementation of {@link ReadRequest} that uses {@link ModelKey} objects.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public final class KeyReadRequest extends AbstractReadRequestImpl {

	public final Iterable<ModelKey> keys;

	public KeyReadRequest(ModelKey key) {
		this(SingleItem.withValue(key), null);
	}

	public KeyReadRequest(ModelKey key, boolean atomic) {
		this(SingleItem.withValue(key), new ReadRequestOptionsImpl(atomic));
	}

	public KeyReadRequest(ModelKey key, ReadRequestOptions options) {
		this(SingleItem.withValue(key), options);
	}

	public KeyReadRequest(Iterable<ModelKey> keys) {
		this(keys, null);
	}

	public KeyReadRequest(Iterable<ModelKey> keys, boolean atomic) {
		this(keys, new ReadRequestOptionsImpl(atomic));
	}

	public KeyReadRequest(Iterable<ModelKey> keys, ReadRequestOptions options) throws IllegalArgumentException {
		super(options);

		if (keys == null) {
			throw new IllegalArgumentException("Keys cannot be null.");
		}

		this.keys = keys;
	}

	@Override
	public Iterable<ModelKey> getModelKeys() {
		return this.keys;
	}

}
