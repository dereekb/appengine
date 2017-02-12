package com.dereekb.gae.test.applications.api.api.login.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.test.applications.api.api.tests.ClientApiCrudTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class LoginClientCrudTests extends ClientApiCrudTest<Login> {

	@Override
	@Autowired
	@Qualifier("loginTestModelGenerator")
	public void setTestModelGenerator(TestModelGenerator<Login> testLoginGenerator) {
		super.setTestModelGenerator(testLoginGenerator);
	}

	@Override
	@Autowired
	@Qualifier("loginClientReadRequestSender")
	public void setReadRequestSender(ClientReadRequestSender<Login> readRequestSender) {
		super.setReadRequestSender(readRequestSender);
	}

}
