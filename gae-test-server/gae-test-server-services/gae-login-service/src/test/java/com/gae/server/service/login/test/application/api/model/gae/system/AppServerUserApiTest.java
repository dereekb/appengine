package com.gae.server.service.login.test.application.api.model.gae.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.client.api.model.crud.builder.ClientCreateRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientDeleteRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientUpdateRequestSender;
import com.dereekb.gae.client.api.model.extension.search.query.builder.ClientQueryRequestSender;
import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.test.applications.api.model.tests.AbstractServerModelRequestSenderTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class AppServerUserApiTest extends AbstractServerModelRequestSenderTest<App> {

	@Override
	@Autowired
	@Qualifier("appTestModelGenerator")
	public void setTestModelGenerator(TestModelGenerator<App> testAppGenerator) {
		super.setTestModelGenerator(testAppGenerator);
	}

	@Override
	@Autowired
	@Qualifier("appClientCreateRequestSender")
	public void setCreateRequestSender(ClientCreateRequestSender<App> requestSender) {
		super.setCreateRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("appClientReadRequestSender")
	public void setReadRequestSender(ClientReadRequestSender<App> requestSender) {
		super.setReadRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("appClientUpdateRequestSender")
	public void setUpdateRequestSender(ClientUpdateRequestSender<App> requestSender) {
		super.setUpdateRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("appClientDeleteRequestSender")
	public void setDeleteRequestSender(ClientDeleteRequestSender<App> requestSender) {
		super.setDeleteRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("appClientQueryRequestSender")
	public void setQueryRequestSender(ClientQueryRequestSender<App> requestSender) {
		super.setQueryRequestSender(requestSender);
	}

	public AppServerUserApiTest() {}

}
