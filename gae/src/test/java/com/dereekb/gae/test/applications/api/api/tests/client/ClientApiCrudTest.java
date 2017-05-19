package com.dereekb.gae.test.applications.api.api.tests.client;

import org.junit.Before;
import org.junit.Test;

import com.dereekb.gae.client.api.model.crud.builder.ClientCreateRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientDeleteRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientUpdateRequestSender;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.test.applications.api.ClientApiApplicationTestContext;
import com.dereekb.gae.test.mock.client.crud.ModelClientCreateRequestSenderTestUtility;
import com.dereekb.gae.test.mock.client.crud.ModelClientDeleteRequestSenderTestUtility;
import com.dereekb.gae.test.mock.client.crud.ModelClientReadRequestSenderTestUtility;
import com.dereekb.gae.test.mock.client.crud.ModelClientUpdateRequestSenderTestUtility;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

/**
 * Abstract testing class used for testing CRUD requests on a model.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class ClientApiCrudTest<T extends MutableUniqueModel> extends ClientApiApplicationTestContext {

	private TestModelGenerator<T> testModelGenerator;

	private boolean canCreateModel = true;

	private ClientCreateRequestSender<T> createRequestSender;
	private ClientReadRequestSender<T> readRequestSender;
	private ClientUpdateRequestSender<T> updateRequestSender;
	private ClientDeleteRequestSender<T> deleteRequestSender;

	private ModelClientCreateRequestSenderTestUtility<T> createRequestUtility;
	private ModelClientReadRequestSenderTestUtility<T> readRequestUtility;
	private ModelClientUpdateRequestSenderTestUtility<T> updateRequestUtility;
	private ModelClientDeleteRequestSenderTestUtility<T> deleteRequestUtility;

	public TestModelGenerator<T> getTestModelGenerator() {
		return this.testModelGenerator;
	}

	public void setTestModelGenerator(TestModelGenerator<T> testModelGenerator) {
		this.testModelGenerator = testModelGenerator;
	}

	public boolean isCanCreateModel() {
		return this.canCreateModel;
	}

	public void setCanCreateModel(boolean canCreateModel) {
		this.canCreateModel = canCreateModel;
	}

	public ClientCreateRequestSender<T> getCreateRequestSender() {
		return this.createRequestSender;
	}

	public void setCreateRequestSender(ClientCreateRequestSender<T> createRequestSender) {
		this.createRequestSender = createRequestSender;
	}

	public ClientReadRequestSender<T> getReadRequestSender() {
		return this.readRequestSender;
	}

	public void setReadRequestSender(ClientReadRequestSender<T> readRequestSender) {
		this.readRequestSender = readRequestSender;
	}

	public ClientUpdateRequestSender<T> getUpdateRequestSender() {
		return this.updateRequestSender;
	}

	public void setUpdateRequestSender(ClientUpdateRequestSender<T> updateRequestSender) {
		this.updateRequestSender = updateRequestSender;
	}

	public ClientDeleteRequestSender<T> getDeleteRequestSender() {
		return this.deleteRequestSender;
	}

	public void setDeleteRequestSender(ClientDeleteRequestSender<T> deleteRequestSender) {
		this.deleteRequestSender = deleteRequestSender;
	}

	// MARK: Utilities
	@Before
	public void setUtilities() {
		if (this.createRequestSender != null) {
			this.createRequestUtility = new ModelClientCreateRequestSenderTestUtility<T>(this.createRequestSender,
			        this.testModelGenerator);
		}

		if (this.readRequestSender != null) {
			this.readRequestUtility = new ModelClientReadRequestSenderTestUtility<T>(this.readRequestSender,
			        this.testModelGenerator);
		}

		if (this.updateRequestSender != null) {
			this.updateRequestUtility = new ModelClientUpdateRequestSenderTestUtility<T>(this.updateRequestSender,
			        this.testModelGenerator);
		}

		if (this.deleteRequestSender != null) {
			this.deleteRequestUtility = new ModelClientDeleteRequestSenderTestUtility<T>(this.deleteRequestSender,
			        this.testModelGenerator);
		}
	}

	public ModelClientCreateRequestSenderTestUtility<T> getCreateRequestUtility() {
		return this.createRequestUtility;
	}

	public ModelClientReadRequestSenderTestUtility<T> getReadRequestUtility() {
		return this.readRequestUtility;
	}

	public ModelClientUpdateRequestSenderTestUtility<T> getUpdateRequestUtility() {
		return this.updateRequestUtility;
	}

	public ModelClientDeleteRequestSenderTestUtility<T> getDeleteRequestUtility() {
		return this.deleteRequestUtility;
	}

	// MARK: Create Tests
	@Test
	public void testSystemClientCreateIsUnavailable() throws Exception {
		if (this.canCreateModel == false && this.createRequestUtility != null) {
			this.createRequestUtility.testCreateIsUnavailableRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testSystemModelClientCreateRequest() throws Exception {
		if (this.canCreateModel == true && this.createRequestUtility != null) {
			this.createRequestUtility.testMockCreateRequest(this.getRequestSecurity());
		}
	}

	// TODO: Add test for invalid create templates.

	// MARK: Read Tests
	@Test
	public void testSystemModelClientReadRequest() throws Exception {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testMockReadRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testNonAtomicModelClientReadRequest() throws Exception {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testNonAtomicReadRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testAtomicModelClientReadRequest() throws Exception {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testAtomicReadRequestFailures(this.getRequestSecurity());
		}
	}

	// MARK: Update Tests
	@Test
	public void testAtomicModelClientUpdateRequest() throws Exception {
		if (this.updateRequestUtility != null) {
			this.updateRequestUtility.testMockUpdateRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testAtomicModelClientUnavailableRequest() throws Exception {
		if (this.updateRequestUtility != null) {
			this.updateRequestUtility.testMockAtomicUnavailableUpdateRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testNonAtomicModelClientUnavailableRequest() throws Exception {
		if (this.updateRequestUtility != null) {
			this.updateRequestUtility.testMockNonAtomicUnavailableUpdateRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testUpdateWithNoIdentifierClientUpdateRequest() throws Exception {
		if (this.updateRequestUtility != null) {
			this.updateRequestUtility.testMockUpdateRequest(this.getRequestSecurity());
		}
	}

	// TODO: Add test for invalid update templates.

	// MARK: Delete Tests
	@Test
	public void testMockDeleteRequestReturnModels() throws Exception {
		if (this.deleteRequestUtility != null) {
			this.deleteRequestUtility.testMockDeleteRequestReturnModels(this.getRequestSecurity());
		}
	}
}
