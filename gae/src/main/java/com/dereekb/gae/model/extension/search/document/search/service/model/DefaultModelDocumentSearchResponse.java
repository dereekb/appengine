package com.dereekb.gae.model.extension.search.document.search.service.model;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.crud.services.response.impl.ReadResponseImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Default implementation of {@link ModelDocumentSearchResponse} that wraps a
 * {@link ReadResponse} and list of {@link ModelKey} objects.
 *
 * @author dereekb
 *
 * @param <T>
 */
public final class DefaultModelDocumentSearchResponse<T extends UniqueModel>
        implements ModelDocumentSearchResponse<T> {

	private final List<ModelKey> keys;
	private ReadResponse<T> response;

	public DefaultModelDocumentSearchResponse(List<ModelKey> keys) {
		this.keys = keys;
		this.response = ReadResponseImpl.unavailable(keys);
	}

	@Override
	public List<ModelKey> getKeySearchResults() {
		return this.keys;
	}

	public List<ModelKey> getKeys() {
		return this.keys;
	}

	public ReadResponse<T> getResponse() {
		return this.response;
	}

	public void setResponse(ReadResponse<T> response) {
		this.response = response;
	}

	@Override
	public Collection<T> getModels() {
		return this.response.getModels();
	}

	@Override
	public Collection<ModelKey> getFiltered() {
		return this.response.getFiltered();
	}

	@Override
	public Collection<ModelKey> getUnavailable() {
		return this.response.getUnavailable();
	}

}
