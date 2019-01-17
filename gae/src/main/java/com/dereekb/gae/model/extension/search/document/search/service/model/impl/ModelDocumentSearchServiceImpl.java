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
import com.dereekb.gae.model.extension.search.document.search.service.DocumentSearchRequest;
import com.dereekb.gae.model.extension.search.document.search.service.DocumentSearchService;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentRequestConverter;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchResponse;
import com.dereekb.gae.model.extension.search.document.search.service.model.ModelDocumentSearchService;
import com.dereekb.gae.model.extension.search.document.search.utility.ScoredDocumentKeyReader;
import com.dereekb.gae.model.extension.search.document.search.utility.SearchDocumentUtility;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.search.system.response.SearchDocumentQueryResponse;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.dereekb.gae.utilities.collections.iterator.cursor.impl.ResultsCursorImpl;
import com.dereekb.gae.utilities.model.search.exception.NoSearchCursorException;
import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.ScoredDocument;

/**
 * {@link ModelDocumentSearchService} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <R>
 *            request type
 */
public class ModelDocumentSearchServiceImpl<T extends UniqueModel, R>
        implements ModelDocumentSearchService<T, R> {

	private ReadService<T> readService;
	private DocumentSearchService searchService;
	private ModelDocumentRequestConverter<R> converter;
	private ScoredDocumentKeyReader keyReader;

	public ModelDocumentSearchServiceImpl() {}

	public ModelDocumentSearchServiceImpl(ReadService<T> readService,
	        DocumentSearchService searchService,
	        ModelDocumentRequestConverter<R> converter,
	        ScoredDocumentKeyReader keyReader) {
		this.readService = readService;
		this.searchService = searchService;
		this.converter = converter;
		this.keyReader = keyReader;
	}

	public ReadService<T> getReadService() {
		return this.readService;
	}

	public void setReadService(ReadService<T> readService) {
		this.readService = readService;
	}

	public DocumentSearchService getSearchService() {
		return this.searchService;
	}

	public void setSearchService(DocumentSearchService searchService) {
		this.searchService = searchService;
	}

	public ModelDocumentRequestConverter<R> getConverter() {
		return this.converter;
	}

	public void setConverter(ModelDocumentRequestConverter<R> converter) {
		this.converter = converter;
	}

	@Override
	public ModelDocumentSearchResponse<T> search(R request) {
		DocumentSearchRequest searchRequest = this.converter.buildSearchRequest(request);
		return this.search(searchRequest);
	}

	@Override
	public ModelDocumentSearchResponse<T> search(DocumentSearchRequest request) {
		SearchDocumentQueryResponse queryResponse = this.searchService.search(request);
		return new ModelDocumentSearchResponseImpl(queryResponse);
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

		private final SearchDocumentQueryResponse response;

		private List<ModelKey> keys;
		private ReadResponse<T> modelReadResponse;

		private ModelDocumentSearchResponseImpl(SearchDocumentQueryResponse response) {
			this.response = response;
		}

		public ReadResponse<T> getModelReadResponse() {
			return this.modelReadResponse;
		}

		public void setModelReadResponse(ReadResponse<T> modelReadResponse) {
			this.modelReadResponse = modelReadResponse;
		}

		public SearchDocumentQueryResponse getResponse() {
			return this.response;
		}

		public void setKeys(List<ModelKey> keys) {
			this.keys = keys;
		}

		// MARK: Internal
		private Collection<T> getModels() {
			ReadResponse<T> response = this.getReadResponse();
			return response.getModels();
		}

		private ReadResponse<T> getReadResponse() {
			if (this.modelReadResponse == null) {
				this.modelReadResponse = this.loadResponse();
			}

			return this.modelReadResponse;
		}

		private ReadResponse<T> loadResponse() {
			List<ModelKey> keys = this.getKeys();
			ReadResponse<T> response;

			if (keys.isEmpty() == false) {
				ReadRequestOptions options = new ReadRequestOptionsImpl(false);
				ReadRequest request = new KeyReadRequest(keys, options);
				response = ModelDocumentSearchServiceImpl.this.readService.read(request);
			} else {
				response = ReadResponseImpl.unavailable(keys);
			}

			return response;
		}

		private List<ModelKey> getKeys() {
			if (this.keys == null) {
				this.keys = this.buildKeys();
			}

			return this.keys;
		}

		private List<ModelKey> buildKeys() {
			Collection<ScoredDocument> documents = this.response.getDocumentResults();
			List<ModelKey> keys = SearchDocumentUtility.readModelKeys(ModelDocumentSearchServiceImpl.this.keyReader,
			        documents);
			return keys;
		}

		@Override
		public boolean isKeysOnlyResponse() {
			return false;
		}

		@Override
		public boolean hasResults() {
			return this.response.getReturnedResults() > 0;
		}

		@Override
		public ResultsCursor getSearchCursor() {
			try {
				return new ResultsCursorImpl(this.getResultsCursor().toWebSafeString());
			} catch (NoSearchCursorException e) {
				return null;
			}
		}

		// MARK: SearchDocumentQueryResponse
		@Override
		public Collection<ScoredDocument> getDocumentResults() {
			return this.response.getDocumentResults();
		}

		@Override
		public Long getFoundResults() {
			return this.response.getFoundResults();
		}

		@Override
		public Integer getReturnedResults() {
			return this.response.getReturnedResults();
		}

		@Override
		public Cursor getResultsCursor() {
			return this.response.getResultsCursor();
		}

		// MARK: ModelDocumentSearchResponse
		@Override
		public List<ModelKey> getKeyResults() {
			return this.getKeys();
		}

		@Override
		public Collection<T> getModelResults() {
			return this.getModels();
		}

		@Override
		public String toString() {
			return "ModelDocumentSearchResponseImpl [getKeys()=" + this.getKeys() + "]";
		}

	}

	@Override
	public String toString() {
		return "ModelDocumentSearchServiceImpl [readService=" + this.readService + ", searchService="
		        + this.searchService + ", converter=" + this.converter + ", keyReader=" + this.keyReader + "]";
	}

}
