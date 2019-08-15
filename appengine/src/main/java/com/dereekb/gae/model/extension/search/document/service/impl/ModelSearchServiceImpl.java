package com.dereekb.gae.model.extension.search.document.service.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.model.extension.search.document.service.ModelSearchService;
import com.dereekb.gae.model.extension.search.document.service.ModelSearchServiceRequest;
import com.dereekb.gae.model.extension.search.document.service.ModelSearchServiceResponse;
import com.dereekb.gae.model.extension.search.document.utility.ModelDocumentBuilderUtility;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.server.search.query.SearchServiceQueryExpression;
import com.dereekb.gae.server.search.request.SearchServiceQueryRequest;
import com.dereekb.gae.server.search.request.impl.SearchServiceQueryOptionsImpl;
import com.dereekb.gae.server.search.request.impl.SearchServiceQueryRequestImpl;
import com.dereekb.gae.server.search.response.SearchServiceQueryResponse;
import com.dereekb.gae.server.search.service.SearchService;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.model.search.exception.NoSearchCursorException;
import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;
import com.google.appengine.api.search.ScoredDocument;

/**
 * {@link ModelSearchService} implementation.
 *
 * @author dereekb
 *
 */
public class ModelSearchServiceImpl
        implements ModelSearchService {

	private SearchService searchService;
	private TypeModelKeyConverter keyTypeConverter;

	public ModelSearchServiceImpl(SearchService searchService, TypeModelKeyConverter keyTypeConverter) {
		super();
		this.setSearchService(searchService);
		this.setKeyTypeConverter(keyTypeConverter);
	}

	public SearchService getSearchService() {
		return this.searchService;
	}

	public void setSearchService(SearchService searchService) {
		if (searchService == null) {
			throw new IllegalArgumentException("searchService cannot be null.");
		}

		this.searchService = searchService;
	}

	public TypeModelKeyConverter getKeyTypeConverter() {
		return this.keyTypeConverter;
	}

	public void setKeyTypeConverter(TypeModelKeyConverter keyTypeConverter) {
		if (keyTypeConverter == null) {
			throw new IllegalArgumentException("keyTypeConverter cannot be null.");
		}

		this.keyTypeConverter = keyTypeConverter;
	}

	// MARK: ModelSearchService
	@Override
	public ModelSearchServiceResponse searchModels(ModelSearchServiceRequest request)
	        throws IllegalQueryArgumentException {
		return new ModelSearchServiceResponseImpl(request);
	}

	private class ModelSearchServiceResponseImpl
	        implements ModelSearchServiceResponse {

		private final ModelSearchServiceRequest request;

		private transient SearchServiceQueryResponse response;
		private transient List<ModelKey> keyResults;

		public ModelSearchServiceResponseImpl(ModelSearchServiceRequest request) {
			this.request = request;
		}

		@Override
		public boolean hasResults() {
			return this.getResponse().getReturnedResults() > 0;
		}

		@Override
		public Collection<ModelKey> getKeyResults() {
			if (this.keyResults == null) {
				Collection<ScoredDocument> documents = this.getResponse().getDocumentResults();
				List<String> stringKeys = ModelDocumentBuilderUtility.readStringKeysFromDocuments(documents);
				this.keyResults = ModelSearchServiceImpl.this.keyTypeConverter.convertKeys(this.request.getModelType(),
				        stringKeys);
			}

			return this.keyResults;
		}

		@Override
		public ResultsCursor getSearchCursor() {
			try {
				return this.getResponse().getResultsCursor();
			} catch (NoSearchCursorException e) {
				return null;
			}
		}

		// MARK: Internal
		private SearchServiceQueryResponse getResponse() {
			if (this.response == null) {
				String index = this.request.getIndexName();

				SearchServiceQueryExpression expression = this.request.getExpression();
				SearchServiceQueryOptionsImpl searchOptions;

				if (this.request.getSearchOptions() != null) {
					searchOptions = new SearchServiceQueryOptionsImpl(this.request.getSearchOptions());
				} else {
					searchOptions = new SearchServiceQueryOptionsImpl();
				}

				// Set only return the id field.
				searchOptions.setFieldsToReturn(ListUtility.toList(ModelDocumentBuilderUtility.ID_FIELD));

				// Build New Request
				SearchServiceQueryRequest request = new SearchServiceQueryRequestImpl(index, searchOptions, expression);
				this.response = ModelSearchServiceImpl.this.searchService.queryIndex(request);
			}

			return this.response;
		}

	}

}
