package com.dereekb.gae.test.applications.api.model.login.login;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.search.query.LoginQueryInitializer.ObjectifyLoginQuery;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestOptionsImpl;
import com.dereekb.gae.test.applications.api.model.tests.extension.ModelQueryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

@Ignore
@Deprecated
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

	@Override
	@Autowired
	@Qualifier("loginType")
	public void setQueryType(String queryType) {
		super.setQueryType(queryType);
	}

	@Test
	public void testQueryLatestLogins() throws Exception {

		this.testLoginTokenContext.generateSystemAdmin("tqll");	// Only admins
		                                                       	// can
		// query all through
		// the API.

		List<Login> logins = this.getModelGenerator().generate(10);

		ObjectifyLoginQuery keyQueryConfig = new ObjectifyLoginQuery();
		keyQueryConfig.orderByDatesDescending();

		ObjectifyQueryRequestOptionsImpl options = new ObjectifyQueryRequestOptionsImpl();
		options.setLimit(10);

		ModelQueryTest<Login>.ModelQueryUnitTest<?>.Results results = this.testAndAssertQueryMatches(keyQueryConfig,
		        options);

		List<Login> modelResults = results.getQuery().queryModels();
		Assert.assertFalse(modelResults.isEmpty());
		Assert.assertTrue(modelResults.size() == logins.size());
	}

}
