package com.dereekb.gae.test.applications.api.model.login.key;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletResponse;

import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.key.search.query.LoginKeyQueryInitializer.ObjectifyLoginKeyQuery;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.test.deprecated.applications.api.model.tests.extension.ModelQueryTest;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.googlecode.objectify.Key;

/**
 *
 * @author dereekb
 *
 */
@Disabled
@Deprecated
public class LoginKeyQueryTest extends ModelQueryTest<LoginKey> {

	@Override
	@Autowired
	@Qualifier("loginKeyRegistry")
	public void setRegistry(ObjectifyRegistry<LoginKey> registry) {
		super.setRegistry(registry);
	}

	@Override
	@Autowired
	@Qualifier("loginKeyTestModelGenerator")
	public void setModelGenerator(TestModelGenerator<LoginKey> modelGenerator) {
		super.setModelGenerator(modelGenerator);
	}

	@Override
	@Autowired
	@Qualifier("loginKeyType")
	public void setQueryType(String queryType) {
		super.setQueryType(queryType);
	}

	@Test
	public void testQueryingLoginPointer() {
		String pointerId = "POINTER_KEY";
		Key<LoginPointer> pointerKey = Key.create(LoginPointer.class, pointerId);
		List<LoginKey> keys = this.getModelGenerator().generate(6);
		List<LoginKey> pointers = new ArrayList<LoginKey>();

		// 3 of the LoginKeys should be associated with the pointer now.
		for (int i = 0; i < keys.size(); i += 2) {
			LoginKey key = keys.get(i);
			key.setLoginPointer(pointerKey);
			pointers.add(key);
		}

		this.getRegistry().update(keys);

		ObjectifyLoginKeyQuery keyQueryConfig = new ObjectifyLoginKeyQuery();
		keyQueryConfig.setLoginPointer(pointerKey);

		ObjectifyQueryRequestBuilder<LoginKey> query = this.getRegistry().makeQuery();
		keyQueryConfig.configure(query);

		List<LoginKey> results = query.buildExecutableQuery().queryModels();
		assertFalse(results.isEmpty());
		assertTrue(results.size() == pointers.size());
		assertTrue(pointers.containsAll(results));
	}

	@Test
	public void testRejectQueryWithAnonymousUser() throws Exception {

		this.getModelGenerator().generate(20);

		String token = this.testLoginTokenContext.generateAnonymousToken();

		ObjectifyLoginKeyQuery query = new ObjectifyLoginKeyQuery();

		ModelQueryUnitTest<ObjectifyLoginKeyQuery> test = new ModelQueryUnitTest<ObjectifyLoginKeyQuery>(query);
		MockHttpServletResponse response = test.performSecureHttpRequest(token, null);

		assertTrue(response.getStatus() == HttpStatus.SC_FORBIDDEN);
	}

}
