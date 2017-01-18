package com.dereekb.gae.test.applications.api.model.login.pointer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.applications.api.model.tests.extension.ModelQueryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class LoginPointerQueryTest extends ModelQueryTest<LoginPointer> {

	@Autowired
	@Qualifier("loginTestModelGenerator")
	private TestModelGenerator<Login> testLoginGenerator;

	@Override
	@Autowired
	@Qualifier("loginPointerRegistry")
	public void setRegistry(ObjectifyRegistry<LoginPointer> registry) {
		super.setRegistry(registry);
	}

	@Override
	@Autowired
	@Qualifier("loginPointerTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<LoginPointer> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Override
	@Autowired
	@Qualifier("loginPointerType")
	public void setQueryType(String queryType) {
		super.setQueryType(queryType);
	}

}
