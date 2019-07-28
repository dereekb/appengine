package com.dereekb.gae.test.app.model.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.app.server.datastore.objectify.AbstractObjectifyModelCrudTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class LoginObjectifyModelCrudTest extends AbstractObjectifyModelCrudTest<Login> {

	@Override
	@Autowired
	@Qualifier("loginTestModelGenerator")
	public void setTestModelGenerator(TestModelGenerator<Login> testLoginGenerator) {
		super.setTestModelGenerator(testLoginGenerator);
	}

	@Override
	@Autowired
	@Qualifier("loginRegistry")
	public void setRegistry(ObjectifyRegistry<Login> registry) {
		super.setRegistry(registry);
	}

}
