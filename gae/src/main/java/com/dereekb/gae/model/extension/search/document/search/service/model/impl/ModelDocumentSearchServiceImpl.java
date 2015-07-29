package com.dereekb.gae.model.extension.search.document.search.service.model.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.ReadRequestOptionsImpl;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.model.crud.services.response.impl.ReadResponseImpl;
import com.dereekb.gae.model.extension.search.document.search.service.key.KeyDocumentSearchService;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchResponse;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchService;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Default implementation of {@link ModelDocumentSearchService} that uses a
 * {@link KeyDocumentSearchService} and a {@link ReadService}.
 *
 * @author dereekb
 */
public final class ModelDocumentSearchServiceImpl<T extends UniqueModel, Q>
        implements ModelDocumentSearchService<T, Q> {

	private KeyDocumentSearchService<Q> keySearchService;
	private ReadService<T> readService;

	public ModelDocumentSearchServiceImpl() {}

	public ModelDocumentSearchServiceImpl(KeyDocumentSearchService<Q> keySearchService, ReadService<T> readService) {
		this.keySearchService = keySearchService;
		this.readService = readService;
	}

	public KeyDocumentSearchService<Q> getKeySearchService() {
		return this.keySearchService;
	}

	public void setKeySearchService(KeyDocumentSearchService<Q> keySearchService) {
		this.keySearchService = keySearchService;
	}

	public ReadService<T> getReadService() {
		return this.readService;
	}

	public void setReadService(ReadService<T> readService) {
		this.readService = readService;
	}

	@Override
	public ModelDocumentSearchResponseImpl searchModels(Q query) {
		List<ModelKey> keys = this.keySearchService.searchKeys(query);
		ModelDocumentSearchResponseImpl searchResponse = new ModelDocumentSearchResponseImpl(keys);
		return searchResponse;
	}

	@Override
	public String toString() {
		return "ModelDocumentSearchServiceImpl [keySearchService=" + this.keySearchService + ", readService="
		        + this.readService + "]";
	}

	/**
	 * {@link ModelDocumentSearchResponse} for
	 * {@link ModelDocumentSearchServiceImpl}. Lazy loads response values.
	 *
	 * @author dereekb
	 *
	 */
	public class ModelDocumentSearchResponseImpl
	        implements ModelDocumentSearchResponse<T> {

		private final List<ModelKey> keys;

		private ReadResponse<T> response = null;

		private ModelDocumentSearchResponseImpl(List<ModelKey> keys) {
			this.keys = keys;
		}

		// MARK: ModelDocumentSearchResponse
		@Override
		public List<ModelKey> getKeySearchResults() {
			return this.keys;
		}

		@Override
		public Collection<T> getModelSearchResults() {
			return this.getResponse().getModels();
		}

		public ReadResponse<T> getResponse() {
			if (this.response == null) {
				this.response = this.loadResponse();
			}

			return this.response;
		}

		private ReadResponse<T> loadResponse() {
			ReadResponse<T> response;

			if (this.keys.isEmpty() == false) {
				ReadRequestOptions options = new ReadRequestOptionsImpl(false);
				ReadRequest request = new KeyReadRequest(this.keys, options);
				response = ModelDocumentSearchServiceImpl.this.readService.read(request);
			} else {
				response = ReadResponseImpl.unavailable(this.keys);
			}

			return response;
		}

	}

}
