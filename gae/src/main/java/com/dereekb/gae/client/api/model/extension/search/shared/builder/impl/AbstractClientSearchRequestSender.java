package com.dereekb.gae.client.api.model.extension.search.shared.builder.impl;

import java.util.Collection;

import com.dereekb.gae.client.api.model.shared.builder.impl.AbstractConfiguredClientModelRequestSender;
import com.dereekb.gae.client.api.service.response.ClientApiResponse;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.SecuredClientApiRequestSender;
import com.dereekb.gae.model.extension.data.conversion.BidirectionalConverter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.TypeModelKeyConverter;
import com.dereekb.gae.server.datastore.objectify.keys.ObjectifyKeyConverter;
import com.dereekb.gae.utilities.model.search.exception.KeysOnlySearchException;
import com.dereekb.gae.utilities.model.search.request.SearchRequest;
import com.dereekb.gae.utilities.model.search.response.ModelSearchResponse;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Abstract client model request sender for search responses.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <O>
 *            model dto type
 * @param <R>
 *            request type
 * @param <S>
 *            serialized response type
 */
public abstract class AbstractClientSearchRequestSender<T extends UniqueModel, O, R extends SearchRequest, S> extends AbstractConfiguredClientModelRequestSender<T, O, R, S> {

	private ObjectifyKeyConverter<T, ModelKey> keyConverter;

	public AbstractClientSearchRequestSender(String type,
	        Class<O> dtoType,
	        BidirectionalConverter<T, O> dtoConverter,
	        TypeModelKeyConverter keyTypeConverter,
	        SecuredClientApiRequestSender requestSender,
	        ObjectifyKeyConverter<T, ModelKey> keyConverter) throws IllegalArgumentException {
		super(type, dtoType, dtoConverter, keyTypeConverter, requestSender);
		this.setKeyConverter(keyConverter);
	}

	public ObjectifyKeyConverter<T, ModelKey> getKeyConverter() {
		return this.keyConverter;
	}

	public void setKeyConverter(ObjectifyKeyConverter<T, ModelKey> keyConverter) {
		if (keyConverter == null) {
			throw new IllegalArgumentException("keyConverter cannot be null.");
		}

		this.keyConverter = keyConverter;
	}

	protected class AbstractClientSearchResponse extends AbstractSerializedResponse
	        implements ModelSearchResponse<T> {

		private final SearchRequest request;
		private SearchResponseDataSerializer dataSerializer;

		private Collection<T> modelResults;
		private Collection<ModelKey> modelKeyResults;

		public AbstractClientSearchResponse(SearchRequest request, ClientApiResponse response) {
			this(request, response, null);
		}

		public AbstractClientSearchResponse(SearchRequest request,
		        ClientApiResponse response,
		        ClientRequestSecurity security) {
			super(response, security);
			this.request = request;
		}

		public SearchRequest getRequest() {
			return this.request;
		}

		// MARK: ModelQueryResponse
		@Override
		public boolean isKeysOnlyResponse() {
			return this.request.isKeysOnly();
		}

		@Override
		public boolean hasResults() {
			return this.getDataSerializer().hasResults();
		}

		@Override
		public Collection<T> getModelResults() throws KeysOnlySearchException {
			if (this.modelResults == null) {
				if (this.isKeysOnlyResponse()) {
					throw new KeysOnlySearchException();
				} else {
					this.modelResults = this.getDataSerializer().getModelResults();
				}
			}

			return this.modelResults;
		}

		@Override
		public String getSearchCursor() {
			return this.getDataSerializer().getSearchCursor();
		}

		@Override
		public Collection<ModelKey> getKeyResults() {
			if (this.modelKeyResults == null) {
				if (this.isKeysOnlyResponse()) {
					this.modelKeyResults = this.getDataSerializer().getKeyResults();
				} else {
					Collection<T> modelResults = this.getModelResults();
					this.modelKeyResults = ModelKey.readModelKeys(modelResults);
				}
			}

			return this.modelKeyResults;
		}

		protected SearchResponseDataSerializer getDataSerializer() {
			if (this.dataSerializer == null) {
				JsonNode responseData = this.response.getPrimaryData().getJsonNode();
				this.dataSerializer = new SearchResponseDataSerializer(responseData);
			}

			return this.dataSerializer;
		}

	}

	protected class SearchResponseDataSerializer {

		private static final String MODEL_TYPE_KEY = "modelType";
		private static final String DATA_KEY = "data";
		private static final String CURSOR_KEY = "cursor";

		private final JsonNode data;

		public SearchResponseDataSerializer(JsonNode data) {
			this.data = data;
		}

		public boolean hasResults() {
			JsonNode dataNode = this.data.get(DATA_KEY);
			return dataNode.has(0);	// Has atleast one value.
		}

		public String getModelType() {
			return this.data.get(MODEL_TYPE_KEY).asText();
		}

		public Collection<T> getModelResults() throws KeysOnlySearchException {
			JsonNode dataNode = this.data.get(DATA_KEY);
			return AbstractClientSearchRequestSender.this.serializeModels(dataNode);
		}

		public String getSearchCursor() {
			if (this.data.has(CURSOR_KEY)) {
				return this.data.get(CURSOR_KEY).asText();
			} else {
				return null;
			}
		}

		public Collection<ModelKey> getKeyResults() {
			JsonNode dataNode = this.data.get(DATA_KEY);
			return AbstractClientSearchRequestSender.this.serializeKeys(dataNode);
		}

	}

}
