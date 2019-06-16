package com.dereekb.gae.client.api.model.extension.search.query.builder.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.extension.search.query.builder.ClientQueryRequestSender;
import com.dereekb.gae.client.api.model.extension.search.query.response.ClientModelQueryResponse;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryModelResultIterator;
import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryRequestBuilder;
import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryRequestBuilderFactory;
import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryRequestOptions;
import com.dereekb.gae.server.datastore.models.query.iterator.ExecutableIndexedModelQuery;
import com.dereekb.gae.server.datastore.objectify.query.exception.InvalidQuerySortingException;
import com.dereekb.gae.utilities.model.search.request.impl.SearchRequestImpl;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.dereekb.gae.utilities.collections.iterator.index.exception.UnavailableIteratorIndexException;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.model.search.exception.NoSearchCursorException;
import com.dereekb.gae.utilities.model.search.request.SearchRequest;
import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;

/**
 * {@link IndexedModelQueryRequestBuilderFactory} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ClientIndexedModelQueryRequestBuilderFactory<T extends UniqueModel>
        implements IndexedModelQueryRequestBuilderFactory<T> {

	private static final ClientRequestSecurity DEFAULT_SECURITY = ClientRequestSecurityImpl.systemSecurity();

	/**
	 * Whether or not to query models by default when using boolean requests.
	 */
	private boolean preferQueryModels = true;

	private ClientQueryRequestSender<T> clientQuery;
	private ClientRequestSecurity clientSecurity;

	public ClientIndexedModelQueryRequestBuilderFactory(ClientQueryRequestSender<T> clientQuery) {
		this(clientQuery, DEFAULT_SECURITY);
	}

	public ClientIndexedModelQueryRequestBuilderFactory(ClientQueryRequestSender<T> clientQuery,
	        ClientRequestSecurity clientSecurity) {
		super();
		this.setClientQuery(clientQuery);
		this.setClientSecurity(clientSecurity);
	}

	public boolean isPreferQueryModels() {
		return this.preferQueryModels;
	}

	public void setPreferQueryModels(boolean preferQueryModels) {
		this.preferQueryModels = preferQueryModels;
	}

	public ClientQueryRequestSender<T> getClientQuery() {
		return this.clientQuery;
	}

	public void setClientQuery(ClientQueryRequestSender<T> clientQuery) {
		if (clientQuery == null) {
			throw new IllegalArgumentException("clientQuery cannot be null.");
		}

		this.clientQuery = clientQuery;
	}

	public ClientRequestSecurity getClientSecurity() {
		return this.clientSecurity;
	}

	public void setClientSecurity(ClientRequestSecurity clientSecurity) {
		if (clientSecurity == null) {
			throw new IllegalArgumentException("clientSecurity cannot be null.");
		}

		this.clientSecurity = clientSecurity;
	}

	// MARK: IndexedModelQueryRequestBuilderFactory
	@Override
	public IndexedModelQueryRequestBuilder<T> makeQuery() {
		return this.makeQuery(null);
	}

	@Override
	public IndexedModelQueryRequestBuilder<T> makeQuery(Map<String, String> parameters)
	        throws IllegalQueryArgumentException {
		return new IndexedModelQueryRequestBuilderImpl(parameters);
	}

	// MARK: IndexedModelQueryRequestBuilder
	public class IndexedModelQueryRequestBuilderImpl
	        implements IndexedModelQueryRequestBuilder<T> {

		private final Map<String, String> parameters;
		private IndexedModelQueryRequestOptions options;

		public IndexedModelQueryRequestBuilderImpl(Map<String, String> parameters) {
			super();
			this.parameters = parameters;
		}

		// MARK: IndexedModelQueryRequestBuilder
		@Override
		public IndexedModelQueryRequestOptions getOptions() {
			return this.options;
		}

		@Override
		public void setOptions(IndexedModelQueryRequestOptions options) {
			this.options = options;
		}

		@Override
		public ExecutableIndexedModelQuery<T> buildExecutableQuery() throws InvalidQuerySortingException {
			return new ExecutableIndexedModelQueryImpl(this.options);
		}

		private class ExecutableIndexedModelQueryImpl
		        implements ExecutableIndexedModelQuery<T> {

			private final IndexedModelQueryRequestOptions options;
			private transient ClientModelQueryResponse<T> modelsResponse;
			private transient ClientModelQueryResponse<T> keysResponse;

			public ExecutableIndexedModelQueryImpl(IndexedModelQueryRequestOptions options) {
				super();
				this.options = options;
			}

			// MARK: ExecutableIndexedModelQuery
			@Override
			public List<T> queryModels() {
				return ListUtility.copy(this.getModelsResponse().getModelResults());
			}

			@Override
			public IndexedModelQueryModelResultIterator<T> queryModelResultsIterator() {
				return new IndexedModelQueryModelResultIteratorImpl();
			}

			@Override
			public List<ModelKey> queryModelKeys() {
				return ListUtility.copy(this.getGenericResponse().getKeyResults());
			}

			@Override
			public boolean hasResults() {
				return this.getGenericResponse().getKeyResults().isEmpty() == false;
			}

			@Override
			public Integer getResultCount() {
				return this.getGenericResponse().getKeyResults().size();
			}

			@Override
			public ResultsCursor getCursor() throws NoSearchCursorException {
				return this.getGenericResponse().getSearchCursor();
			}

			@Override
			public IndexedModelQueryRequestOptions getOptions() {
				return this.options;
			}

			// MARK: Internal
			protected ClientModelQueryResponse<T> getGenericResponse() {
				if (this.modelsResponse != null) {
					return this.modelsResponse;
				} else if (this.keysResponse != null) {
					return this.keysResponse;
				} else {
					if (ClientIndexedModelQueryRequestBuilderFactory.this.preferQueryModels) {
						return this.getModelsResponse();
					} else {
						return this.getKeysResponse();
					}
				}
			}

			protected ClientModelQueryResponse<T> getModelsResponse() {
				if (this.modelsResponse == null) {
					try {
						SearchRequest request = this.makeSearchRequest(false);
						this.modelsResponse = ClientIndexedModelQueryRequestBuilderFactory.this.clientQuery
						        .query(request, ClientIndexedModelQueryRequestBuilderFactory.this.clientSecurity);
					} catch (ClientRequestFailureException e) {
						throw new RuntimeException(e);
					}

				}

				return this.modelsResponse;
			}

			protected ClientModelQueryResponse<T> getKeysResponse() {
				if (this.keysResponse == null) {
					// Use the models response as the keys response.
					if (this.modelsResponse != null) {
						this.keysResponse = this.modelsResponse;
					} else {
						try {
							SearchRequest request = this.makeSearchRequest(true);
							this.keysResponse = ClientIndexedModelQueryRequestBuilderFactory.this.clientQuery
							        .query(request, ClientIndexedModelQueryRequestBuilderFactory.this.clientSecurity);
						} catch (ClientRequestFailureException e) {
							throw new RuntimeException(e);
						}
					}
				}

				return this.keysResponse;
			}

			protected SearchRequest makeSearchRequest(boolean keysOnly) {
				SearchRequestImpl request = new SearchRequestImpl();

				request.setOptions(this.options);
				request.setParameters(IndexedModelQueryRequestBuilderImpl.this.parameters);
				request.setKeysOnly(keysOnly);

				return request;
			}

			// MARK: IndexedModelQueryModelResultIterator
			private class IndexedModelQueryModelResultIteratorImpl
			        implements IndexedModelQueryModelResultIterator<T> {

				private final Iterator<T> iterator = getModelsResponse().getModelResults().iterator();

				// MARK: IndexedModelQueryModelResultIterator
				@Override
				public ResultsCursor getStartCursor() throws UnavailableIteratorIndexException {
					return ExecutableIndexedModelQueryImpl.this.options.getCursor();
				}

				@Override
				public ResultsCursor getEndCursor() throws UnavailableIteratorIndexException {
					return getModelsResponse().getSearchCursor();
				}

				@Override
				public boolean hasNext() {
					return this.iterator.hasNext();
				}

				@Override
				public T next() {
					return this.iterator.next();
				}

			}

		}

	}

}
