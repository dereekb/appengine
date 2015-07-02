package com.dereekb.gae.model.extension.search.query.search.service.model;

import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.extension.search.query.search.service.key.ModelKeyQueryService;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;


/**
 * Default implementation of {@link DefaultModelQueryService} that uses a
 * {@link ModelKeyQueryService} and a {@link ReadService}.
 *
 * @author dereekb
 */
public final class DefaultModelQueryService<T extends UniqueModel, Q>
        implements ModelQueryService<T, Q> {

	private final ModelKeyQueryService<Q> queryService;
	private final ReadService<T> readService;

	public DefaultModelQueryService(ModelKeyQueryService<Q> queryService, ReadService<T> readService) {
		this.queryService = queryService;
		this.readService = readService;
	}

	public ModelKeyQueryService<Q> getQueryService() {
    	return this.queryService;
    }

	public ReadService<T> getReadService() {
    	return this.readService;
    }

	@Override
	public ModelQueryResponse<T> queryModels(Q query) {
		List<ModelKey> keys = this.queryService.queryKeys(query);
		DefaultModelQueryResponse<T> queryResponse = new DefaultModelQueryResponse<T>(keys);

		if (keys.isEmpty() == false) {
			ReadRequestOptions options = new ReadRequestOptions(false);
			ReadRequest<T> request = new KeyReadRequest<T>(keys, options);
			ReadResponse<T> readResponse = this.readService.read(request);
			queryResponse.setResponse(readResponse);
		}

		return queryResponse;
	}

}
