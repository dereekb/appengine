package com.dereekb.gae.test.app.mock.client.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.impl.EncodedLoginTokenImpl;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.test.app.mock.client.crud.ModelClientCreateRequestSenderTestUtility;
import com.dereekb.gae.test.app.mock.client.crud.ModelClientDeleteRequestSenderTestUtility;
import com.dereekb.gae.test.app.mock.client.crud.ModelClientReadRequestSenderTestUtility;
import com.dereekb.gae.test.app.mock.client.crud.ModelClientUpdateRequestSenderTestUtility;
import com.dereekb.gae.test.app.mock.client.extension.ModelClientLinkRequestSenderTestUtility;
import com.dereekb.gae.test.app.mock.client.extension.ModelClientQueryRequestSenderTestUtility;
import com.dereekb.gae.test.app.mock.client.extension.ModelClientRolesContextServiceRequestSenderTestUtility;

/**
 * Abstract server tests.
 * <p>
 * Passing these tests with a system account generally means the API is at least
 * usable for CRUD calls, but does not indicate the business logic behind them
 * is valid.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractServerModelRequestSenderTests<T extends MutableUniqueModel> extends AbstractModelRequestSenderTests<T> {

	private boolean canCreateModel = true;

	private ModelClientCreateRequestSenderTestUtility<T> createRequestUtility;
	private ModelClientReadRequestSenderTestUtility<T> readRequestUtility;
	private ModelClientUpdateRequestSenderTestUtility<T> updateRequestUtility;
	private ModelClientDeleteRequestSenderTestUtility<T> deleteRequestUtility;

	// Search
	private ModelClientQueryRequestSenderTestUtility<T> queryRequestUtility;

	// Link
	private String testLinkName;
	private ModelClientLinkRequestSenderTestUtility<T> linkRequestUtility;

	// Model Roles
	private ModelClientRolesContextServiceRequestSenderTestUtility<T> modelRolesRequestUtility;

	// Events
	public AbstractServerModelRequestSenderTests() {
		super();
	}

	public AbstractServerModelRequestSenderTests(String testLinkName) {
		super();
		this.setTestLinkName(testLinkName);
	}

	public boolean isCanCreateModel() {
		return this.canCreateModel;
	}

	public void setCanCreateModel(boolean canCreateModel) {
		this.canCreateModel = canCreateModel;
	}

	public String getTestLinkName() {
		return this.testLinkName;
	}

	public void setTestLinkName(String testLinkName) {
		this.testLinkName = testLinkName;
	}

	// MARK: Utilities
	@BeforeEach
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

		if (this.queryRequestSender != null) {
			this.queryRequestUtility = new ModelClientQueryRequestSenderTestUtility<T>(this.queryRequestSender,
			        this.testModelGenerator);
		}

		if (this.linkRequestSender != null && this.testLinkName != null) {
			this.linkRequestUtility = new ModelClientLinkRequestSenderTestUtility<T>(this.testLinkName,
			        this.testModelGenerator, this.linkRequestSender);
		}

		if (this.modelRolesRequestSender != null) {
			this.modelRolesRequestUtility = new ModelClientRolesContextServiceRequestSenderTestUtility<T>(
			        this.modelRolesRequestSender, this.testModelGenerator);
		}
	}

	@BeforeEach
	public void generateSystemUser() {
		// Use system account for tests
		this.testLoginTokenContext.generateSystemAdmin();
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

	public ModelClientQueryRequestSenderTestUtility<T> getQueryRequestUtility() {
		return this.queryRequestUtility;
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

	@Test
	public void testSystemModelClientCreateNothingRequest() throws Exception {
		if (this.canCreateModel == true && this.createRequestUtility != null) {
			this.createRequestUtility.testMockCreateNothingRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testMockCreateTooManyRequestThrowsClientError() throws Exception {
		if (this.canCreateModel == true && this.createRequestUtility != null) {
			this.createRequestUtility.testMockCreateTooManyRequestThrowsClientError(this.getRequestSecurity());
		}
	}
	@Test
	public void testSendMockCreateTooManyRequestThrowsApiResponse() throws Exception {
		if (this.canCreateModel == true && this.createRequestUtility != null) {
			this.createRequestUtility.testSendMockCreateTooManyRequestThrowsApiResponse(this.getRequestSecurity());
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

	@Test
	public void testMockReadRequestWithRelated() throws Exception {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testMockReadRequestWithRelated(this.getRequestSecurity());
		}
	}

	@Test
	public void testReadNothing() throws Exception {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testReadNothing(this.getRequestSecurity());
		}
	}

	@Test
	public void testReadMoreThanMaxThrowsClientError() throws Exception {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testReadMoreThanMaxThrowsClientError(this.getRequestSecurity());
		}
	}

	@Test
	public void testSendReadMoreThanMaxThrowsApiResponse() throws Exception {
		if (this.readRequestUtility != null) {
			this.readRequestUtility.testSendReadMoreThanMaxThrowsApiResponse(this.getRequestSecurity());
		}
	}

	// MARK: Update Tests
	@Test
	public void testAtomicModelClientUpdateSingleRequest() throws Exception {
		if (this.updateRequestUtility != null) {
			this.updateRequestUtility.testMockUpdateSingleRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testAtomicModelClientUpdateMultipleRequest() throws Exception {
		if (this.updateRequestUtility != null) {
			this.updateRequestUtility.testMockUpdateManyRequest(this.getRequestSecurity());
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
			this.updateRequestUtility.testMockUpdateWithoutIdentifier(this.getRequestSecurity());
		}
	}

	@Test
	public void testEmptyMockUpdateClientUpdateRequest() throws Exception {
		if (this.updateRequestUtility != null) {
			this.updateRequestUtility.testEmptyMockUpdate(this.getRequestSecurity());
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

	@Test
	public void testMockDeleteTooManyRequestThrowsClientError() throws Exception {
		if (this.deleteRequestUtility != null) {
			this.deleteRequestUtility.testMockDeleteTooManyRequestThrowsClientError(this.getRequestSecurity());
		}
	}

	@Test
	public void testSendMockDeleteTooManyRequestThrowsApiResponse() throws Exception {
		if (this.deleteRequestUtility != null) {
			this.deleteRequestUtility.testSendMockDeleteTooManyRequestThrowsApiResponse(this.getRequestSecurity());
		}
	}

	// MARK: Query Tests
	@Test
	public void testSystemClientQuery() throws Exception {
		if (this.queryRequestUtility != null) {
			this.queryRequestUtility.testMockQueryRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testSystemClientKeysOnlyQuery() throws Exception {
		if (this.queryRequestUtility != null) {
			this.queryRequestUtility.testMockQueryRequestKeysOnly(this.getRequestSecurity());
		}
	}

	// MARK: Link Tests
	@Test
	public void testSystemClientLinkToNonExistantLink() throws Exception {
		if (this.linkRequestUtility != null) {
			this.linkRequestUtility.testSystemClientLinkToNonExistantLink(this.getRequestSecurity());
		}
	}

	@Test
	public void testSystemClientLinkAtomicRequest() throws Exception {
		if (this.linkRequestUtility != null) {
			this.linkRequestUtility.testSystemClientLinkAtomicRequest(this.getRequestSecurity());
		}
	}

	@Test
	public void testSystemClientLinkNonAtomicRequest() throws Exception {
		if (this.linkRequestUtility != null) {
			this.linkRequestUtility.testSystemClientLinkNonAtomicRequest(this.getRequestSecurity());
		}
	}

	// MARK: Model Roles
	@Test
	public void testSystemClientReadModelRolesForSingleModelAtomic() throws Exception {
		if (this.modelRolesRequestUtility != null) {
			this.modelRolesRequestUtility.testSystemClientReadModelRolesForSingleModelAtomic(this.getRequestSecurity());
		}
	}

	@Test
	public void testSystemClientReadModelRolesForSingleModelAtomicFailsOnMissing() throws Exception {
		if (this.modelRolesRequestUtility != null) {
			this.modelRolesRequestUtility
			        .testSystemClientReadModelRolesForSingleModelAtomicFailsOnMissing(this.getRequestSecurity());
		}
	}

	@Test
	public void testSystemClientReadModelRolesForMultipleModelsAtomic() throws Exception {
		if (this.modelRolesRequestUtility != null) {
			this.modelRolesRequestUtility
			        .testSystemClientReadModelRolesForMultipleModelsAtomic(this.getRequestSecurity());
		}
	}

	@Test
	public void testSystemClientReadModelRolesForMultipleModelsAtomicFailsOnMissing() throws Exception {
		if (this.modelRolesRequestUtility != null) {
			this.modelRolesRequestUtility
			        .testSystemClientReadModelRolesForMultipleModelsAtomicFailsOnMissing(this.getRequestSecurity());
		}
	}

	@Test
	public void testSystemClientReadModelRolesForMultipleModelsNonAtomicWithMissing() throws Exception {
		if (this.modelRolesRequestUtility != null) {
			this.modelRolesRequestUtility
			        .testSystemClientReadModelRolesForMultipleModelsNonAtomicWithMissing(this.getRequestSecurity());
		}
	}

	// MARK: Utility
	@Override
	public ClientRequestSecurity getRequestSecurity() {
		EncodedLoginToken overrideToken = new EncodedLoginTokenImpl(this.testLoginTokenContext.getToken());
		return new ClientRequestSecurityImpl(overrideToken);
	}

}
