package com.dereekb.gae.test.applications.api.model.login.login;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.search.query.LoginQueryInitializer.ObjectifyLoginQuery;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestOptionsImpl;
import com.dereekb.gae.test.applications.api.model.tests.extension.ModelQueryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

public class LoginQueryTest extends ModelQueryTest<Login> {

	@Override
	@Autowired
	@Qualifier("loginRegistry")
	public void setRegistry(ObjectifyRegistry<Login> registry) {
		super.setRegistry(registry);
	}

	@Override
	@Autowired
	@Qualifier("loginTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<Login> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Test
	public void testLatestLogins() {

		List<Login> logins = this.getModelGenerator().generate(10);

		ObjectifyLoginQuery keyQueryConfig = new ObjectifyLoginQuery();
		keyQueryConfig.orderByDatesDescending();

		ObjectifyQueryRequestBuilder<Login> query = this.getRegistry().makeQuery();
		keyQueryConfig.configure(query);

		ObjectifyQueryRequestOptionsImpl options = new ObjectifyQueryRequestOptionsImpl();
		options.setLimit(10);
		query.setOptions(options);

		List<Login> results = query.buildExecutableQuery().queryModels();
		Assert.assertFalse(results.isEmpty());
		Assert.assertTrue(results.size() == logins.size());
	}

}
