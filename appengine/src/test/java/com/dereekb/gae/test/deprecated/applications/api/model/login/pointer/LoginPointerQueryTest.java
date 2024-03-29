package com.dereekb.gae.test.applications.api.model.login.pointer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.model.pointer.search.query.LoginPointerQueryInitializer.ObjectifyLoginPointerQuery;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.test.deprecated.applications.api.model.login.LoginModelTestUtilities;
import com.dereekb.gae.test.deprecated.applications.api.model.tests.extension.ModelQueryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

@Disabled
@Deprecated
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

	@Test
	public void queryLoginTest() throws Exception {
		Integer count = 5;

		List<Login> logins = this.testLoginGenerator.generate(5);
		Login a = logins.get(0);
		Login b = logins.get(1);

		List<LoginPointer> aPointers = this.getModelGenerator().generate(count);
		LoginModelTestUtilities.setPointerLogins(a, aPointers);
		this.getRegistry().update(aPointers);

		List<LoginPointer> bPointers = this.getModelGenerator().generate(count);
		LoginModelTestUtilities.setPointerLogins(b, bPointers);
		this.getRegistry().update(bPointers);

		ObjectifyLoginPointerQuery queryConfig = new ObjectifyLoginPointerQuery();
		queryConfig.setLogin(a);

		// No limit, should only return the 5.
		ModelQueryUnitTest<ObjectifyLoginPointerQuery> test = new ModelQueryUnitTest<ObjectifyLoginPointerQuery>(
		        queryConfig);
		ModelQueryUnitTest<ObjectifyLoginPointerQuery>.Results results = test.performQuery();

		results.assertResultsMatch();
		List<LoginPointer> resultModels = results.getQuery().queryModels();
		assertTrue(aPointers.containsAll(resultModels));
		assertTrue(aPointers.size() == resultModels.size());
	}

	@Test
	public void queryTypeTest() throws Exception {
		Integer count = 5;

		LoginPointerType testType = LoginPointerType.OAUTH_GOOGLE;

		List<LoginPointer> pointers = this.getModelGenerator().generate(count);
		LoginModelTestUtilities.setPointerTypes(testType, pointers);
		this.getRegistry().update(pointers);

		List<LoginPointer> extraPointers = this.getModelGenerator().generate(count);
		LoginModelTestUtilities.setPointerTypes(LoginPointerType.PASSWORD, extraPointers);
		this.getRegistry().update(extraPointers);

		ObjectifyLoginPointerQuery queryConfig = new ObjectifyLoginPointerQuery();
		queryConfig.setType(testType);

		ModelQueryUnitTest<ObjectifyLoginPointerQuery> test = new ModelQueryUnitTest<ObjectifyLoginPointerQuery>(
		        queryConfig);
		ModelQueryUnitTest<ObjectifyLoginPointerQuery>.Results results = test.performQuery();

		results.assertResultsMatch();
		List<LoginPointer> resultModels = results.getQuery().queryModels();
		assertTrue(pointers.containsAll(resultModels));
		assertTrue(pointers.size() == resultModels.size());
	}

}
