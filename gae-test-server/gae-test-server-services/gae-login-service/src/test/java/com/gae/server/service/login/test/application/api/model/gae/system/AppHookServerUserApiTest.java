package com.gae.server.service.login.test.application.api.model.gae.system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.client.api.model.crud.builder.ClientCreateRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientDeleteRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientUpdateRequestSender;
import com.dereekb.gae.client.api.model.extension.search.query.builder.ClientQueryRequestSender;
import com.dereekb.gae.model.crud.services.request.CreateRequest;
import com.dereekb.gae.model.crud.services.request.impl.CreateRequestImpl;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.hook.AppHook;
import com.dereekb.gae.test.applications.api.model.tests.AbstractServerModelRequestSenderTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class AppHookServerUserApiTest extends AbstractServerModelRequestSenderTest<AppHook> {

	@Override
	@Autowired
	@Qualifier("appHookTestModelGenerator")
	public void setTestModelGenerator(TestModelGenerator<AppHook> testAppHookGenerator) {
		super.setTestModelGenerator(testAppHookGenerator);
	}

	@Override
	@Autowired
	@Qualifier("appHookClientCreateRequestSender")
	public void setCreateRequestSender(ClientCreateRequestSender<AppHook> requestSender) {
		super.setCreateRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("appHookClientReadRequestSender")
	public void setReadRequestSender(ClientReadRequestSender<AppHook> requestSender) {
		super.setReadRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("appHookClientUpdateRequestSender")
	public void setUpdateRequestSender(ClientUpdateRequestSender<AppHook> requestSender) {
		super.setUpdateRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("appHookClientDeleteRequestSender")
	public void setDeleteRequestSender(ClientDeleteRequestSender<AppHook> requestSender) {
		super.setDeleteRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("appHookClientQueryRequestSender")
	public void setQueryRequestSender(ClientQueryRequestSender<AppHook> requestSender) {
		super.setQueryRequestSender(requestSender);
	}

	// APP
	@Autowired
	@Qualifier("appClientCreateRequestSender")
	private ClientCreateRequestSender<App> appRequestSender;

	public AppHookServerUserApiTest() {
		this.setCanCreateModel(true);
	}


	/**
	 * Override to create an app first.
	 */
	@Override
	@Test
	public void testSystemModelClientCreateRequest() throws Exception {

		CreateRequest<App> appCreateRequest = new CreateRequestImpl<App>(new App());
		App app = this.appRequestSender.create(appCreateRequest, this.getRequestSecurity()).getModels().iterator().next();

		AppHook template = this.testModelGenerator.generate();
		template.setModelKey(null);
		template.setApp(app.getObjectifyKey());

		CreateRequest<AppHook> createRequest = new CreateRequestImpl<AppHook>(template);
		this.getCreateRequestUtility().testMockCreateRequest(createRequest, this.getRequestSecurity());
	}

}
