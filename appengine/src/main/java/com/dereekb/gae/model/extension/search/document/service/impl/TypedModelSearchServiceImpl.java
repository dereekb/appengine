package com.dereekb.gae.model.extension.search.document.service.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.search.document.components.ModelSearch;
import com.dereekb.gae.model.extension.search.document.components.ModelSearchFactory;
import com.dereekb.gae.model.extension.search.document.components.ModelSearchInitializer;
import com.dereekb.gae.model.extension.search.document.components.impl.ModelSearchInitializerImpl;
import com.dereekb.gae.model.extension.search.document.service.ModelSearchService;
import com.dereekb.gae.model.extension.search.document.service.ModelSearchServiceResponse;
import com.dereekb.gae.model.extension.search.document.service.TypedModelSearchService;
import com.dereekb.gae.model.extension.search.document.service.TypedModelSearchServiceRequest;
import com.dereekb.gae.model.extension.search.document.service.TypedModelSearchServiceResponse;
import com.dereekb.gae.model.extension.search.document.service.exception.SearchIndexRequiredException;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.search.request.SearchServiceQueryOptions;
import com.dereekb.gae.server.search.request.SearchServiceQueryRequest;
import com.dereekb.gae.server.search.request.impl.SearchServiceQueryOptionsImpl;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.dereekb.gae.utilities.model.search.exception.KeysOnlySearchException;
import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;

/**
 * {@link TypedModelSearchService} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class TypedModelSearchServiceImpl<T extends UniqueModel>
        implements TypedModelSearchService<T> {

	private String modelType;

	private Getter<T> getter;
	private ModelSearchService modelSearchService;
	private ModelSearchInitializer<ModelSearch> modelSearchInitializer;

	private String defaultIndex;

	public TypedModelSearchServiceImpl(String modelType,
	        Getter<T> getter,
	        ModelSearchService modelSearchService,
	        ModelSearchFactory<ModelSearch> modelSearchFactory) {
		this(modelType, getter, modelSearchService, new ModelSearchInitializerImpl(modelSearchFactory), null);
	}

	public TypedModelSearchServiceImpl(String modelType,
	        Getter<T> getter,
	        ModelSearchService modelSearchService,
	        ModelSearchInitializer<ModelSearch> modelSearchInitializer) {
		this(modelType, getter, modelSearchService, modelSearchInitializer, null);
	}

	public TypedModelSearchServiceImpl(String modelType,
	        Getter<T> getter,
	        ModelSearchService modelSearchService,
	        ModelSearchInitializer<ModelSearch> modelSearchInitializer,
	        String defaultIndex) {
		super();
		this.setModelType(modelType);
		this.setGetter(getter);
		this.setModelSearchService(modelSearchService);
		this.setModelSearchInitializer(modelSearchInitializer);
		this.setDefaultIndex(defaultIndex);
	}

	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		if (modelType == null) {
			throw new IllegalArgumentException("modelType cannot be null.");
		}

		this.modelType = modelType;
	}

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("getter cannot be null.");
		}

		this.getter = getter;
	}

	public ModelSearchService getModelSearchService() {
		return this.modelSearchService;
	}

	public void setModelSearchService(ModelSearchService modelSearchService) {
		if (modelSearchService == null) {
			throw new IllegalArgumentException("modelSearchService cannot be null.");
		}

		this.modelSearchService = modelSearchService;
	}

	public ModelSearchInitializer<ModelSearch> getModelSearchInitializer() {
		return this.modelSearchInitializer;
	}

	public void setModelSearchInitializer(ModelSearchInitializer<ModelSearch> modelSearchInitializer) {
		if (modelSearchInitializer == null) {
			throw new IllegalArgumentException("modelSearchInitializer cannot be null.");
		}

		this.modelSearchInitializer = modelSearchInitializer;
	}

	public String getDefaultIndex() {
		return this.defaultIndex;
	}

	public void setDefaultIndex(String defaultIndex) {
		this.defaultIndex = defaultIndex;
	}

	// MARK: ModelSearchService
	@Override
	public TypedModelSearchServiceResponse<T> searchModels(TypedModelSearchServiceRequest request)
	        throws IllegalQueryArgumentException {
		SearchServiceQueryRequest queryRequest = this.makeQueryRequest(request);
		return new TypedModelSearchServiceResponseImpl(request, queryRequest);
	}

	// MARK: Internal
	private SearchServiceQueryRequest makeQueryRequest(TypedModelSearchServiceRequest request)
	        throws IllegalQueryArgumentException,
	            SearchIndexRequiredException {

		String index = request.getIndex();

		if (index == null) {
			index = this.defaultIndex;
		}

		if (index == null) {
			throw new SearchIndexRequiredException();
		}

		SearchServiceQueryOptions searchOptions = new SearchServiceQueryOptionsImpl(request);
		return this.modelSearchInitializer.initalizeSearchRequest(index, searchOptions, request.getParameters());
	}

	/**
	 * {@link TypedModelSearchServiceResponse} implementation.
	 *
	 * @author dereekb
	 *
	 */
	private class TypedModelSearchServiceResponseImpl
	        implements TypedModelSearchServiceResponse<T> {

		private final TypedModelSearchServiceRequest request;
		private final SearchServiceQueryRequest queryRequest;

		private transient ModelSearchServiceResponse response;
		private transient List<T> models;

		public TypedModelSearchServiceResponseImpl(TypedModelSearchServiceRequest request,
		        SearchServiceQueryRequest queryRequest) {
			this.request = request;
			this.queryRequest = queryRequest;
		}

		// MARK: TypedModelSearchServiceResponse
		@Override
		public boolean isKeysOnlyResponse() {
			return this.request.isKeysOnly();
		}

		@Override
		public boolean hasResults() {
			return this.getResponse().hasResults();
		}

		@Override
		public Collection<T> getModelResults() throws KeysOnlySearchException {
			if (this.models == null) {
				Collection<ModelKey> keys = this.getKeyResults();
				this.models = TypedModelSearchServiceImpl.this.getter.getWithKeys(keys);
			}

			return this.models;
		}

		@Override
		public Collection<ModelKey> getKeyResults() {
			return this.getResponse().getKeyResults();
		}

		@Override
		public ResultsCursor getSearchCursor() {
			return this.getResponse().getSearchCursor();
		}

		// MARK: Internal
		private ModelSearchServiceResponse getResponse() {
			if (this.response == null) {
				String modelType = TypedModelSearchServiceImpl.this.modelType;
				ModelSearchServiceRequestImpl request = new ModelSearchServiceRequestImpl(modelType, this.queryRequest);
				this.response = TypedModelSearchServiceImpl.this.modelSearchService.searchModels(request);
			}

			return this.response;
		}

	}

}
