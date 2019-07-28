package com.dereekb.gae.test.app.mock.client.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.client.api.auth.model.roles.ClientModelRolesServiceRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientCreateRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientDeleteRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientUpdateRequestSender;
import com.dereekb.gae.client.api.model.extension.link.ClientLinkServiceRequestSender;
import com.dereekb.gae.client.api.model.extension.search.query.builder.ClientQueryRequestSender;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.impl.ClientRequestSecurityImpl;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.impl.EncodedLoginTokenImpl;
import com.dereekb.gae.server.datastore.models.MutableUniqueModel;
import com.dereekb.gae.test.app.mock.context.AbstractAppTestingContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

/**
 * Abstract base tests class for tests using Client classes.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractModelRequestSenderTests<T extends MutableUniqueModel> extends AbstractAppTestingContext {

	protected TestModelGenerator<T> testModelGenerator;

	// CRUD
	protected ClientCreateRequestSender<T> createRequestSender;
	protected ClientReadRequestSender<T> readRequestSender;
	protected ClientUpdateRequestSender<T> updateRequestSender;
	protected ClientDeleteRequestSender<T> deleteRequestSender;

	// Search
	protected ClientQueryRequestSender<T> queryRequestSender;

	// Link
	@Autowired(required = false)
	@Qualifier("clientLinkRequestSender")
	protected ClientLinkServiceRequestSender linkRequestSender;

	// Model Roles
	@Autowired(required = true)
	@Qualifier("clientModelRolesContextServiceRequestSender")
	protected ClientModelRolesServiceRequestSender modelRolesRequestSender;

	public TestModelGenerator<T> getTestModelGenerator() {
		return this.testModelGenerator;
	}

	public void setTestModelGenerator(TestModelGenerator<T> testModelGenerator) {
		this.testModelGenerator = testModelGenerator;
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

	public ClientQueryRequestSender<T> getQueryRequestSender() {
		return this.queryRequestSender;
	}

	public void setQueryRequestSender(ClientQueryRequestSender<T> queryRequestSender) {
		this.queryRequestSender = queryRequestSender;
	}

	public ClientLinkServiceRequestSender getLinkRequestSender() {
		return this.linkRequestSender;
	}

	public void setLinkRequestSender(ClientLinkServiceRequestSender linkRequestSender) {
		this.linkRequestSender = linkRequestSender;
	}

	public ClientModelRolesServiceRequestSender getModelRolesRequestSender() {
		return this.modelRolesRequestSender;
	}

	public void setModelRolesRequestSender(ClientModelRolesServiceRequestSender modelRolesRequestSender) {
		this.modelRolesRequestSender = modelRolesRequestSender;
	}

	// MARK: Utility
	@Override
	public ClientRequestSecurity getRequestSecurity() {
		EncodedLoginToken overrideToken = new EncodedLoginTokenImpl(this.testLoginTokenContext.getToken());
		return new ClientRequestSecurityImpl(overrideToken);
	}

}
