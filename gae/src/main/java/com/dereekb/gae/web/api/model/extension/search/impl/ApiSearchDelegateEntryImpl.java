package com.dereekb.gae.web.api.model.extension.search.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.search.document.SearchableUniqueModel;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchResponse;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchService;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchDelegateEntry;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchReadRequest;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchUpdateRequest;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseDataImpl;

/**
 * {@link ApiSearchDelegateEntry} implementation.
 *
 * @author dereekb
 *
 */
public class ApiSearchDelegateEntryImpl<T extends SearchableUniqueModel, R>
        implements ApiSearchDelegateEntry {

	private String type;

	private SingleDirectionalConverter<ApiSearchReadRequest, R> requestBuilder;
	private DirectionalConverter<T, ? extends Object> resultConverter;

	private ModelDocumentSearchService<T, R> service;

	public ApiSearchDelegateEntryImpl(String type,
	        SingleDirectionalConverter<ApiSearchReadRequest, R> requestBuilder,
	        DirectionalConverter<T, ? extends Object> resultConverter,
	        ModelDocumentSearchService<T, R> service) {
		this.setType(type);
		this.requestBuilder = requestBuilder;
		this.resultConverter = resultConverter;
		this.service = service;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public SingleDirectionalConverter<ApiSearchReadRequest, R> getRequestBuilder() {
		return this.requestBuilder;
	}

	public void setRequestBuilder(SingleDirectionalConverter<ApiSearchReadRequest, R> requestBuilder) {
		this.requestBuilder = requestBuilder;
	}

	public ModelDocumentSearchService<T, R> getService() {
		return this.service;
	}

	public void setService(ModelDocumentSearchService<T, R> service) {
		this.service = service;
	}

	public DirectionalConverter<T, ? extends Object> getResultConverter() {
		return this.resultConverter;
	}

	public void setResultConverter(DirectionalConverter<T, ? extends Object> resultConverter) {
		this.resultConverter = resultConverter;
	}

	// MARK: ApiSearchDelegateEntry
	@Override
	public ApiResponseData search(ApiSearchReadRequest request) {
		R searchRequest = this.requestBuilder.convertSingle(request);
		ModelDocumentSearchResponse<T> response = this.service.search(searchRequest);

		boolean getModels = request.getModels();
		ApiResponseData responseData = null;

		if (getModels) {
			Collection<T> models = response.getModelSearchResults();
			responseData = this.convertModelResponse(models);
		} else {
			Collection<ModelKey> keys = response.getKeySearchResults();
			responseData = this.convertKeyResponse(keys);
		}

		return responseData;
	}

	@Override
	public ApiResponseData query(ApiSearchReadRequest request) {
		// TODO Add query components.
		throw new UnsupportedOperationException("Incomplete function.");
	}

	@Override
	public void updateSearchIndex(ApiSearchUpdateRequest request) {
		// TODO Queue up indexing requests.
		throw new UnsupportedOperationException("Incomplete function.");
	}

	// MARK: Internal
	private ApiResponseData convertModelResponse(Collection<T> models) {
		List<? extends Object> converted = this.resultConverter.convert(models);
		ApiResponseDataImpl data = new ApiResponseDataImpl(this.type, converted);
		return data;
	}

	private ApiResponseData convertKeyResponse(Collection<ModelKey> keys) {
		List<String> keyStrings = ModelKey.keysAsStrings(keys);
		ApiResponseDataImpl data = new ApiResponseDataImpl(this.type, keyStrings);
		return data;
	}

}
