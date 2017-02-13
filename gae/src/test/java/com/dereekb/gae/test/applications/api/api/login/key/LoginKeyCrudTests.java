package com.dereekb.gae.test.applications.api.api.login.key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.client.api.model.crud.builder.ClientReadRequestSender;
import com.dereekb.gae.client.api.model.crud.builder.ClientUpdateRequestSender;
import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.test.applications.api.api.tests.ClientApiCrudTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class LoginKeyCrudTests extends ClientApiCrudTest<LoginKey> {

	@Override
	@Autowired
	@Qualifier("loginKeyTestModelGenerator")
	public void setTestModelGenerator(TestModelGenerator<LoginKey> testLoginKeyGenerator) {
		super.setTestModelGenerator(testLoginKeyGenerator);
	}

	@Override
	@Autowired
	@Qualifier("loginKeyClientReadRequestSender")
	public void setReadRequestSender(ClientReadRequestSender<LoginKey> requestSender) {
		super.setReadRequestSender(requestSender);
	}

	@Override
	@Autowired
	@Qualifier("loginKeyClientUpdateRequestSender")
	public void setUpdateRequestSender(ClientUpdateRequestSender<LoginKey> requestSender) {
		super.setUpdateRequestSender(requestSender);
	}

}
