package com.dereekb.gae.web.api.model.extension.search.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.search.document.service.TypedModelSearchService;
import com.dereekb.gae.model.extension.search.document.service.TypedModelSearchServiceResponse;
import com.dereekb.gae.model.extension.search.document.service.impl.TypedModelSearchServiceRequestImpl;
import com.dereekb.gae.model.extension.search.query.service.ModelQueryResponse;
import com.dereekb.gae.model.extension.search.query.service.ModelQueryService;
import com.dereekb.gae.model.extension.search.query.service.impl.ModelQueryRequestImpl;
import com.dereekb.gae.server.datastore.models.TypedModel;
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
        implements ApiSearchDelegateEntry, TypedModel {

	private String type;

	private ModelQueryService<T> queryService;
	private TypedModelSearchService<T> searchService;

	private DirectionalConverter<T, ? extends Object> resultConverter;

	public ApiSearchDelegateEntryImpl(String type,
	        ModelQueryService<T> queryService,
	        DirectionalConverter<T, ? extends Object> resultConverter) {
		this(type, queryService, null, resultConverter);
	}

	public ApiSearchDelegateEntryImpl(String type,
	        ModelQueryService<T> queryService,
	        TypedModelSearchService<T> searchService,
	        DirectionalConverter<T, ? extends Object> resultConverter) {
		this.setModelType(type);
		this.setQueryService(queryService);
		this.setSearchService(searchService);
		this.setResultConverter(resultConverter);
	}

	@Override
	public String getModelType() {
		return this.type;
	}

	public void setModelType(String type) {
		this.type = type;
	}

	public ModelQueryService<T> getQueryService() {
		return this.queryService;
	}

	public void setQueryService(ModelQueryService<T> queryService) {
		this.queryService = queryService;
	}

	public DirectionalConverter<T, ? extends Object> getResultConverter() {
		return this.resultConverter;
	}

	public void setResultConverter(DirectionalConverter<T, ? extends Object> resultConverter) {
		this.resultConverter = resultConverter;
	}

	public TypedModelSearchService<T> getSearchService() {
		return this.searchService;
	}

	public void setSearchService(TypedModelSearchService<T> searchService) {
		this.searchService = searchService;
	}

	// MARK: ApiSearchDelegateEntry
	@Override
	public ApiSearchResponseData search(ApiSearchReadRequest request) {
		if (this.searchService == null) {
			throw new UnsupportedOperationException("Searching is unsupported for this type.");
		}

		TypedModelSearchServiceRequestImpl searchRequest = new TypedModelSearchServiceRequestImpl(request);
		searchRequest.setIndex(request.getIndex());

		TypedModelSearchServiceResponse<T> response = this.searchService.searchModels(searchRequest);
		return this.buildResponseForResult(response);
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
		throw new UnsupportedOperationException("Unsupported function.");
	}

	// MARK: Internal
	private ApiSearchResponseData buildResponseForResult(TypedModelSearchServiceResponse<T> response) {
		return this.buildModelDataResponse(response);
	}

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
