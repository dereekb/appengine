package com.dereekb.gae.model.extension.search.document.search.service.model;

import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.search.document.search.service.key.KeyDocumentSearchService;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Default implementation of {@link ModelDocumentSearchService} that uses a
 * {@link KeyDocumentSearchService} and a {@link ReadService}.
 *
 * @author dereekb
 */
public final class DefaultModelDocumentSearchService<T extends UniqueModel, Q>
        implements ModelDocumentSearchService<T, Q> {

	private final KeyDocumentSearchService<Q> keySearchService;
	private final ReadService<T> readService;

	public DefaultModelDocumentSearchService(KeyDocumentSearchService<Q> keySearchService, ReadService<T> readService) {
		this.keySearchService = keySearchService;
		this.readService = readService;
	}

	@Override
	public ModelDocumentSearchResponse<T> searchModels(Q query) {
		List<ModelKey> keys = this.keySearchService.searchKeys(query);
		DefaultModelDocumentSearchResponse<T> searchResponse = new DefaultModelDocumentSearchResponse<T>(keys);

		if (keys.isEmpty() == false) {
			ReadRequestOptions options = new ReadRequestOptions(false);
			ReadRequest<T> request = new KeyReadRequest<T>(keys, options);
			ReadResponse<T> readResponse = this.readService.read(request);
			searchResponse.setResponse(readResponse);
		}

		return searchResponse;
	}

}
