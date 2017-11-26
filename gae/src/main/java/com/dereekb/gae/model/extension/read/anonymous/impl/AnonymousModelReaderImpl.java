package com.dereekb.gae.model.extension.read.anonymous.impl;

import java.util.Collection;
import java.util.Map;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.ReadRequestOptionsImpl;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.read.anonymous.AnonymousModelReader;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

/**
 * {@link AnonymousModelReader} implementation. Uses a {@link Map} of
 * {@link ReadService} instances.
 *
 * @author dereekb
 *
 */
public class AnonymousModelReaderImpl
        implements AnonymousModelReader {

	private Map<String, ReadService<? extends UniqueModel>> entries;

	public Map<String, ReadService<? extends UniqueModel>> getEntries() {
		return this.entries;
	}

	public void setEntries(Map<String, ReadService<? extends UniqueModel>> entries) {
		this.entries = new CaseInsensitiveMap<ReadService<? extends UniqueModel>>(entries);
	}

	@Override
	public ReadResponse<? extends UniqueModel> read(String type,
	                                                Collection<ModelKey> keys) {
		ReadRequestOptions options = new ReadRequestOptionsImpl(false);
		ReadRequest request = new KeyReadRequest(keys, options);
		ReadService<? extends UniqueModel> service = this.entries.get(type);
		return service.read(request);
	}

	@Override
	public String toString() {
		return "AnonymousModelReaderImpl [entries=" + this.entries + "]";
	}

}
