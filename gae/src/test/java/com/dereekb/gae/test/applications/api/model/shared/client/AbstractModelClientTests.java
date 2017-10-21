package com.dereekb.gae.test.applications.api.model.shared.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;

import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.client.api.model.crud.builder.ClientCreateRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientDeleteRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientUpdateRequestSender;
import com.dereekb.gae.client.api.model.crud.request.ClientReadRequest;
import com.dereekb.gae.client.api.model.crud.request.impl.ClientReadRequestImpl;
import com.dereekb.gae.client.api.model.crud.response.ClientDeleteResponse;
import com.dereekb.gae.client.api.model.crud.response.SerializedClientCreateApiResponse;
import com.dereekb.gae.client.api.model.crud.response.SerializedClientReadApiResponse;
import com.dereekb.gae.client.api.model.crud.response.SerializedClientUpdateApiResponse;
import com.dereekb.gae.client.api.model.extension.search.query.builder.impl.ClientQueryRequestSenderImpl;
import com.dereekb.gae.client.api.model.extension.search.query.response.ClientModelQueryResponse;
import com.dereekb.gae.client.api.service.response.SerializedClientApiResponse;
import com.dereekb.gae.client.api.service.response.exception.ClientResponseSerializationException;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.UpdateRequest;
import com.dereekb.gae.model.crud.services.request.impl.CreateRequestImpl;
import com.dereekb.gae.model.crud.services.request.impl.DeleteRequestImpl;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.UpdateRequestImpl;
import com.dereekb.gae.model.crud.services.request.options.UpdateRequestOptions;
import com.dereekb.gae.model.crud.services.request.options.impl.UpdateRequestOptionsImpl;
import com.dereekb.gae.model.crud.services.response.CreateResponse;
import com.dereekb.gae.model.crud.services.response.SimpleReadResponse;
import com.dereekb.gae.model.crud.services.response.SimpleUpdateResponse;
import com.dereekb.gae.model.extension.search.query.service.impl.ModelQueryRequestImpl;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyModelKeyUtil;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;
import com.dereekb.gae.test.server.auth.TestLoginTokenPair;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.model.search.request.MutableSearchRequest;
import com.dereekb.gae.utilities.model.search.request.SearchRequest;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;
import com.googlecode.objectify.Key;

public abstract class AbstractModelClientTests extends ApiApplicationTestContext {

	protected class BasicTestUserSetup
	        implements TestingInstanceObject {

		public final Login login;
		public final TestLoginTokenPair pair;
		public final ClientRequestSecurity security;

		public BasicTestUserSetup() {
			this("USER");
		}

		public BasicTestUserSetup(String user) {
			this(user, false);
		}

		public BasicTestUserSetup(String user, boolean admin) {
			this((admin) ? AbstractModelClientTests.this.testLoginTokenContext.generateSystemAdmin(user)
			        : AbstractModelClientTests.this.testLoginTokenContext.generateLogin(user));
		}

		public BasicTestUserSetup(TestLoginTokenPair pair) {
			this.pair = pair;
			this.login = this.pair.getLogin();

			waitUntilTaskQueueCompletes();

			this.security = new ClientRequestSecurityImpl(this.pair);
		}

		@Override
		public ClientRequestSecurity getSecurity() {
			return this.security;
		}

	}

	protected interface TestingInstanceObject {

		public ClientRequestSecurity getSecurity();

	}

	protected abstract class AbstractTestingInstance<U extends TestingInstanceObject> {

		public final U testUser;

		public AbstractTestingInstance(U testUser) {
			if (testUser == null) {
				throw new IllegalArgumentException();
			}

			this.testUser = testUser;
		}

		protected ClientRequestSecurity getSecurity() {
			return this.testUser.getSecurity();
		}

		public abstract class AbstractModelTestingInstance<T extends UniqueModel> extends AbstractQueryableModelTestingInstance<T> {

			protected final ClientCreateRequestSender<T> createRequestSender;

			protected final ClientUpdateRequestSender<T> updateRequestSender;

			protected final ClientDeleteRequestSender<T> deleteRequestSender;

			public AbstractModelTestingInstance(ClientReadRequestSender<T> readRequestSender,
			        ClientQueryRequestSenderImpl<T, ?> queryRequestSender,
			        ClientUpdateRequestSender<T> updateRequestSender) {
				this(readRequestSender, queryRequestSender, null, updateRequestSender, null);
			}

			public AbstractModelTestingInstance(ClientReadRequestSender<T> readRequestSender,
			        ClientQueryRequestSenderImpl<T, ?> queryRequestSender,
			        ClientCreateRequestSender<T> createRequestSender,
			        ClientUpdateRequestSender<T> updateRequestSender,
			        ClientDeleteRequestSender<T> deleteRequestSender) {
				super(readRequestSender, queryRequestSender);
				this.createRequestSender = createRequestSender;
				this.updateRequestSender = updateRequestSender;
				this.deleteRequestSender = deleteRequestSender;
			}

			public ClientCreateRequestSender<T> getCreateRequestSender() {
				return this.createRequestSender;
			}

			public ClientUpdateRequestSender<T> getUpdateRequestSender() {
				return this.updateRequestSender;
			}

			public ClientDeleteRequestSender<T> getDeleteRequestSender() {
				return this.deleteRequestSender;
			}

			// MARK: Create
			public T create() {
				T template = this.makeTemplate();
				return this.create(template);
			}

			public List<T> create(int count) {
				T template = this.makeTemplate();
				return this.create(template, count);
			}

			public T makeTemplate() {
				throw new UnsupportedOperationException("No template function for this type.");
			}

			public List<T> create(T template,
			                      int count) {
				List<T> templates = ListUtility.cloneReferences(template, count);
				return this.create(templates);
			}

			public T create(T template) {
				CreateRequest<T> createRequest = new CreateRequestImpl<T>(template);
				List<T> created = this.create(createRequest);
				return created.get(0);
			}

			public List<T> create(List<T> templates) {
				CreateRequest<T> createRequest = new CreateRequestImpl<T>(templates);
				return this.create(createRequest);
			}

			public List<T> create(CreateRequest<T> createRequest) {
				List<T> created = null;

				try {
					CreateResponse<T> createResponse = this.sendCreate(createRequest).getSerializedResponse();
					Collection<T> results = createResponse.getModels();
					created = new ArrayList<T>(results);
				} catch (ClientRequestFailureException e) {
					e.printStackTrace();
					Assert.fail("Failed creating.");
				}

				return created;
			}
			
			public SerializedClientCreateApiResponse<T> sendCreate(CreateRequest<T> createRequest) {
				try {
					return this.createRequestSender.sendRequest(createRequest, AbstractTestingInstance.this.getSecurity());
				} catch (ClientRequestFailureException e) {
					e.printStackTrace();
					Assert.fail("Request failure.");
					return null;
				}
			}

			// MARK: Update
			public T update(T model) {
				return this.update(ListUtility.wrap(model)).get(0);
			}

			public List<T> update(Collection<T> models) {
				UpdateRequest<T> request = AbstractTestingInstance.this.makeAtomicUpdateRequest(models);
				SerializedClientApiResponse<SimpleUpdateResponse<T>> updateResponse = this.update(request);

				SimpleUpdateResponse<T> simpleResponse;

				try {
					simpleResponse = updateResponse.getSerializedResponse();
				} catch (ClientResponseSerializationException | ClientRequestFailureException e) {
					throw new RuntimeException(e);
				}

				return ListUtility.copy(simpleResponse.getModels());
			}

			public SerializedClientUpdateApiResponse<T> update(UpdateRequest<T> updateRequest) {
				try {
					return this.updateRequestSender.sendRequest(updateRequest,
					        AbstractTestingInstance.this.getSecurity());
				} catch (ClientRequestFailureException e) {
					Assert.fail("Failed updating.");
					throw new RuntimeException(e);
				}
			}

			// MARK: Delete
			public void delete(T model) {
				DeleteRequest deleteRequest = new DeleteRequestImpl(model);
				this.delete(deleteRequest);
			}

			public void delete(List<T> models) {
				DeleteRequest deleteRequest = new DeleteRequestImpl(models);
				this.delete(deleteRequest);
			}

			public void delete(DeleteRequest deleteRequest) {
				try {
					ClientDeleteResponse<T> deleteResponse = this.deleteRequestSender.delete(deleteRequest,
					        AbstractTestingInstance.this.getSecurity());
					Assert.assertFalse(deleteResponse.getModelKeys().isEmpty());
				} catch (ClientRequestFailureException e) {
					Assert.fail("Failed deleting.");
				}
			}

		}

		public abstract class AbstractQueryableModelTestingInstance<T extends UniqueModel> extends AbstractReadOnlyModelTestingInstance<T> {

			protected final ClientQueryRequestSenderImpl<T, ?> queryRequestSender;

			public AbstractQueryableModelTestingInstance(ClientReadRequestSender<T> readRequestSender,
			        ClientQueryRequestSenderImpl<T, ?> queryRequestSender) {
				super(readRequestSender);
				this.queryRequestSender = queryRequestSender;
			}

			public ClientQueryRequestSenderImpl<T, ?> getQueryRequestSender() {
				return this.queryRequestSender;
			}

			// MARK: Query
			public ClientModelQueryResponse<T> query(ConfigurableEncodedQueryParameters query) {
				MutableSearchRequest queryRequest = new ModelQueryRequestImpl();
				queryRequest.setSearchParameters(query.getParameters());
				queryRequest.setKeysOnly(false);
				return this.query(queryRequest);
			}

			public ClientModelQueryResponse<T> query(SearchRequest queryRequest) {
				try {
					return this.queryRequestSender.query(queryRequest, AbstractTestingInstance.this.getSecurity());
				} catch (ClientRequestFailureException e) {
					e.printStackTrace();
					Assert.fail("Failed querying.");
					throw new RuntimeException();
				}
			}

		}

		public abstract class AbstractReadOnlyModelTestingInstance<T extends UniqueModel> {

			protected final ClientReadRequestSender<T> readRequestSender;

			public AbstractReadOnlyModelTestingInstance(ClientReadRequestSender<T> readRequestSender) {
				super();
				this.readRequestSender = readRequestSender;
			}

			// MARK: Assertions
			public void assertDoesNotExist(T model) {
				this.assertDoesNotExist(ListUtility.wrap(model));
			}

			public void assertDoesNotExist(Iterable<T> models) {
				ClientReadRequestImpl readRequest = this.makeReadRequest(ModelKey.readModelKeys(models));
				SimpleReadResponse<T> readResponse = this.simpleRead(readRequest);
				Assert.assertTrue(readResponse.getModels().isEmpty());
			}

			// MARK: Read
			public T tryRead(T model) {
				try {
					return this.read(model);
				} catch (AssertionError e) {
					return null;
				}
			}

			public T read(T model) {
				return this.read(ListUtility.wrap(model)).get(0);
			}

			public T readByKey(Key<T> key) {
				ModelKey modelKey = ObjectifyModelKeyUtil.readModelKey(key);
				return this.readByKey(ListUtility.wrap(modelKey)).get(0);
			}

			public List<T> read(Iterable<T> types) {
				return this.readByKey(ModelKey.readModelKeys(types));
			}

			public List<T> readByKey(Collection<ModelKey> keys) {
				ClientReadRequestImpl clientReadRequest = this.makeAtomicReadRequest(keys);
				return this.readList(clientReadRequest);
			}

			public List<T> readList(ClientReadRequest readRequest) throws AtomicOperationException {
				SimpleReadResponse<T> readResponse = this.simpleRead(readRequest);
				Collection<T> types = readResponse.getModels();
				return ListUtility.copy(types);
			}

			public SimpleReadResponse<T> simpleRead(ClientReadRequest readRequest) {
				SerializedClientReadApiResponse<T> response = this.read(readRequest);

				try {
					return response.getSerializedResponse();
				} catch (ClientResponseSerializationException | ClientRequestFailureException e) {
					Assert.fail("Failed serializing response.");
					throw new RuntimeException(e);
				}
			}

			public SerializedClientReadApiResponse<T> read(ClientReadRequest readRequest) {
				try {
					return this.readRequestSender.sendRequest(readRequest, AbstractTestingInstance.this.getSecurity());
				} catch (ClientRequestFailureException e) {
					Assert.fail("Failed reading.");
					throw new RuntimeException(e);
				}
			}

			// MARK: Utility
			public ClientReadRequestImpl makeReadRequest(Iterable<ModelKey> keys) {
				return this.makeReadRequest(keys, false);
			}

			public ClientReadRequestImpl makeAtomicReadRequest(Iterable<ModelKey> keys) {
				return this.makeReadRequest(keys, true);
			}

			public ClientReadRequestImpl makeReadRequest(Iterable<ModelKey> keys,
			                                             boolean atomic) {
				ReadRequest readRequest = new KeyReadRequest(keys, atomic);
				ClientReadRequestImpl clientReadRequest = new ClientReadRequestImpl(readRequest);
				return clientReadRequest;
			}

		}

		public <T extends UniqueModel> UpdateRequest<T> makeAtomicUpdateRequest(T template) {
			return this.makeAtomicUpdateRequest(ListUtility.wrap(template));
		}
		
		public <T extends UniqueModel> UpdateRequest<T> makeAtomicUpdateRequest(Collection<T> templates) {
			UpdateRequestOptions options = new UpdateRequestOptionsImpl();
			options.setAtomic(true);

			UpdateRequest<T> updateRequest = new UpdateRequestImpl<T>(templates, options);
			return updateRequest;
		}

	}

}
