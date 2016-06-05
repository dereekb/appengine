package com.dereekb.gae.web.api.model.extension.search.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchResponse;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchService;
import com.dereekb.gae.model.extension.search.query.search.service.ModelQueryResponse;
import com.dereekb.gae.model.extension.search.query.search.service.ModelQueryService;
import com.dereekb.gae.model.extension.search.query.search.service.impl.ModelQueryRequestImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;
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
public class ApiSearchDelegateEntryImpl<T extends UniqueModel, R>
        implements ApiSearchDelegateEntry {

	private String type;

	private ModelQueryService<T> queryService;
	private ModelDocumentSearchService<T, R> searchService;

	private SingleDirectionalConverter<ApiSearchReadRequest, R> requestBuilder;
	private DirectionalConverter<T, ? extends Object> resultConverter;

	public ApiSearchDelegateEntryImpl(String type,
	        ModelQueryService<T> queryService,
	        SingleDirectionalConverter<ApiSearchReadRequest, R> requestBuilder,
	        DirectionalConverter<T, ? extends Object> resultConverter) {
		this(type, queryService, null, requestBuilder, resultConverter);
	}

	public ApiSearchDelegateEntryImpl(String type,
	        ModelDocumentSearchService<T, R> searchService,
	        SingleDirectionalConverter<ApiSearchReadRequest, R> requestBuilder,
	        DirectionalConverter<T, ? extends Object> resultConverter) {
		this(type, null, searchService, requestBuilder, resultConverter);
	}

	public ApiSearchDelegateEntryImpl(String type,
	        ModelQueryService<T> queryService,
	        ModelDocumentSearchService<T, R> searchService,
	        SingleDirectionalConverter<ApiSearchReadRequest, R> requestBuilder,
	        DirectionalConverter<T, ? extends Object> resultConverter) {
		this.setType(type);
		this.setQueryService(queryService);
		this.setSearchService(searchService);
		this.setRequestBuilder(requestBuilder);
		this.setResultConverter(resultConverter);
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ModelQueryService<T> getQueryService() {
		return this.queryService;
	}

	public void setQueryService(ModelQueryService<T> queryService) {
		this.queryService = queryService;
	}

	public ModelDocumentSearchService<T, R> getSearchService() {
		return this.searchService;
	}

	public void setSearchService(ModelDocumentSearchService<T, R> searchService) {
		this.searchService = searchService;
	}

	public SingleDirectionalConverter<ApiSearchReadRequest, R> getRequestBuilder() {
		return this.requestBuilder;
	}

	public void setRequestBuilder(SingleDirectionalConverter<ApiSearchReadRequest, R> requestBuilder) {
		this.requestBuilder = requestBuilder;
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
		if (this.searchService == null) {
			throw new UnsupportedOperationException("Searching is unsupported for this type.");
		}

		R searchRequest = this.requestBuilder.convertSingle(request);
		ModelDocumentSearchResponse<T> response = this.searchService.search(searchRequest);

		boolean keysOnly = request.getKeysOnly();
		ApiResponseData responseData = null;

		if (keysOnly) {
			Collection<ModelKey> keys = response.getKeySearchResults();
			responseData = this.convertKeyResponse(keys);
		} else {
			Collection<T> models = response.getModelSearchResults();
			responseData = this.convertModelResponse(models);
		}

		return responseData;
	}

	@Override
	public ApiResponseData query(ApiSearchReadRequest request) {
		if (this.queryService == null) {
			throw new UnsupportedOperationException("Querying is unsupported for this type.");
		}

		boolean keysOnly = request.getKeysOnly();
		Map<String, String> parameters = request.getParameters();
		ModelQueryRequestImpl queryRequest = new ModelQueryRequestImpl(keysOnly, request, parameters);
		ModelQueryResponse<T> response = this.queryService.queryModels(queryRequest);

		ApiResponseData responseData = null;

		if (keysOnly) {
			Collection<ModelKey> keys = response.getResponseKeys();
			responseData = this.convertKeyResponse(keys);
		} else {
			Collection<T> models = response.getModels();
			responseData = this.convertModelResponse(models);
		}

		return responseData;
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