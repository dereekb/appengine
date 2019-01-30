package com.dereekb.gae.model.crud.services.response.impl;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.data.conversion.impl.AbstractDirectionalConverter;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Abstract class used for wrapping results to another type.
 * 
 * @author dereekb
 *
 * @param <W>
 *            Output response type
 * @param <T>
 *            Input response type
 */
public abstract class AbstractReadResponseWrapper<W, T> extends AbstractDirectionalConverter<T, W>
        implements ReadResponse<W> {

	private final ReadResponse<? extends T> response;
	private Collection<W> wrappedResponse;

	public AbstractReadResponseWrapper(ReadResponse<? extends T> response) {
		this.response = response;
	}

	// MARK: ReadResponse
	@Override
	public Collection<ModelKey> getFiltered() {
		return this.response.getFiltered();
	}

	@Override
	public Collection<ModelKey> getUnavailable() {
		return this.response.getUnavailable();
	}

	@Override
	public Collection<ModelKey> getFailed() {
		return this.response.getFailed();
	}

	@Override
	public Collection<W> getModels() {
		Collection<? extends T> models = this.response.getModels();

		if (this.wrappedResponse == null) {
			this.wrappedResponse = this.convertIterable(models);
		}

		return this.wrappedResponse;
	}

}
