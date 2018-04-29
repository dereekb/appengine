package com.gae.server.service.login.test.application.api.model.gae.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.client.api.model.crud.builder.ClientCreateRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientDeleteRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientUpdateRequestSender;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.test.applications.api.model.tests.AbstractServerModelRequestSenderTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class LoginServerUserApiTest extends AbstractServerModelRequestSenderTest<Login> {

	@Override
	@Autowired
	@Qualifier("loginTestModelGenerator")
	public void setTestModelGenerator(TestModelGenerator<Login> testLoginGenerator) {
		super.setTestModelGenerator(testLoginGenerator);
	}

	@Override
	@Autowired
	@Qualifier("loginClientCreateRequestSender")
	public void setCreateRequestSender(ClientCreateRequestSender<Login> requestSender) {
		super.setCreateRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("loginClientReadRequestSender")
	public void setReadRequestSender(ClientReadRequestSender<Login> requestSender) {
		super.setReadRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("loginClientUpdateRequestSender")
	public void setUpdateRequestSender(ClientUpdateRequestSender<Login> requestSender) {
		super.setUpdateRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("loginClientDeleteRequestSender")
	public void setDeleteRequestSender(ClientDeleteRequestSender<Login> requestSender) {
		super.setDeleteRequestSender(requestSender);
	}

	public LoginServerUserApiTest() {
		this.setCanCreateModel(false);
	}

}
