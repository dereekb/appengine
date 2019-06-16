package com.dereekb.gae.web.api.model.extension.search.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.data.conversion.SingleDirectionalConverter;
import com.dereekb.gae.model.extension.search.query.service.ModelQueryResponse;
import com.dereekb.gae.model.extension.search.query.service.ModelQueryService;
import com.dereekb.gae.model.extension.search.query.service.impl.ModelQueryRequestImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.dereekb.gae.utilities.model.search.response.ModelSearchResponse;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchDelegateEntry;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchReadRequest;
import com.dereekb.gae.web.api.model.extension.search.ApiSearchUpdateRequest;

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

	// TODO: Abstract the requestBuilder/requestConverter away into a new
	// interface that extends or contains ModelDocumentSearchService

	private SingleDirectionalConverter<ApiSearchReadRequest, R> requestBuilder;
	private DirectionalConverter<T, ? extends Object> resultConverter;

	public ApiSearchDelegateEntryImpl(String type,
	        ModelQueryService<T> queryService,
	        DirectionalConverter<T, ? extends Object> resultConverter) {
		this(type, queryService, null, resultConverter);
	}

	public ApiSearchDelegateEntryImpl(String type,
	        ModelQueryService<T> queryService,
	        SingleDirectionalConverter<ApiSearchReadRequest, R> requestBuilder,
	        DirectionalConverter<T, ? extends Object> resultConverter) {
		this.setType(type);
		this.setQueryService(queryService);
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
	public ApiSearchResponseData search(ApiSearchReadRequest request) {
		throw new UnsupportedOperationException("Document search is unavailable for this type.");
	}

	@Override
	public ApiSearchResponseData query(ApiSearchReadRequest request) {
		if (this.queryService == null) {
			throw new UnsupportedOperationException("Querying is unsupported for this type.");
		}

		ModelQueryRequestImpl queryRequest = new ModelQueryRequestImpl(request);
		ModelQueryResponse<T> response = this.queryService.queryModels(queryRequest);
		return this.buildResponseForResult(response);
	}

	@Override
	public void updateSearchIndex(ApiSearchUpdateRequest request) {
		// TODO Queue up indexing requests.
		throw new UnsupportedOperationException("Unsupported function.");
	}

	// MARK: Internal
	private ApiSearchResponseData buildResponseForResult(ModelQueryResponse<T> response) {
		return this.buildModelDataResponse(response);
	}

	private ApiSearchResponseData buildModelDataResponse(ModelSearchResponse<T> response) {
		ApiSearchResponseData searchResponseData = null;

		if (response.isKeysOnlyResponse()) {
			Collection<ModelKey> keys = response.getKeyResults();
			searchResponseData = this.convertKeyResponse(keys);
		} else {
			Collection<T> models = response.getModelResults();
			searchResponseData = this.convertModelResponse(models);
		}

		ResultsCursor cursor = response.getSearchCursor();
		searchResponseData.setResultsCursor(cursor);

		return searchResponseData;
	}

	private ApiSearchResponseData convertModelResponse(Collection<T> models) {
		List<? extends Object> converted = this.resultConverter.convert(models);
		ApiSearchResponseData data = new ApiSearchResponseData(this.type, converted);
		return data;
	}

	private ApiSearchResponseData convertKeyResponse(Collection<ModelKey> keys) {
		List<String> keyStrings = ModelKey.keysAsStrings(keys);
		ApiSearchResponseData data = new ApiSearchResponseData(this.type, keyStrings);
		return data;
	}

}
