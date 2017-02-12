package com.dereekb.gae.model.crud.services.response.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Default implementation of {@link ReadResponse}.
 *
 * @author dereekb
 *
 * @param <T>
 */
public final class ReadResponseImpl<T> extends AbstractServiceResponse<T>
        implements ReadResponse<T> {

	public ReadResponseImpl(Collection<T> models) {
		super(models);
	}

	public ReadResponseImpl(Collection<T> models, Collection<ModelKey> unavailable) {
		super(models, unavailable);
	}

	public ReadResponseImpl(Collection<T> models, Collection<ModelKey> filtered, Collection<ModelKey> unavailable) {
		super(models, filtered, unavailable);
	}

	public ReadResponseImpl(AbstractServiceResponse<T> response) {
		super(response);
	}

	/**
	 * Convenience constructor for building unavailable models.
	 *
	 * @param unavailable
	 * @return
	 */
	public static <T> ReadResponseImpl<T> unavailable(Collection<ModelKey> unavailable) {
		return new ReadResponseImpl<T>(null, unavailable);
	}

	@Override
	public Collection<T> getModels() {
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
