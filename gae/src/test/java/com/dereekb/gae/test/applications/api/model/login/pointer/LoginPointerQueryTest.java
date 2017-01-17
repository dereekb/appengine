package com.dereekb.gae.test.applications.api.model.login.pointer;

import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.search.query.LoginQueryInitializer.ObjectifyLoginQuery;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestOptionsImpl;
import com.dereekb.gae.test.applications.api.model.tests.extension.ModelQueryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class LoginPointerQueryTest extends ModelQueryTest<LoginPointer> {

	@Autowired
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

	public void queryLoginTest() {

		List<Login> logins = this.testLoginGenerator.generate(5);
		Login a = logins.get(0);
		Login b = logins.get(1);

		ObjectifyLoginQuery keyQueryConfig = new ObjectifyLoginQuery();
		keyQueryConfig.orderByDatesDescending();

		ObjectifyQueryRequestBuilder<LoginPointer> query = this.getRegistry().makeQuery();
		keyQueryConfig.configure(query);

		ObjectifyQueryRequestOptionsImpl options = new ObjectifyQueryRequestOptionsImpl();
		options.setLimit(10);
		query.setOptions(options);

		List<Login> results = query.buildExecutableQuery().queryModels();
		Assert.assertFalse(results.isEmpty());
		Assert.assertTrue(results.size() == logins.size());

	}

}
