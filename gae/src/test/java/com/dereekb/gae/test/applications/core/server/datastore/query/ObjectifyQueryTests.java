package com.dereekb.gae.test.applications.core.server.datastore.query;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestOptionsImpl;
import com.dereekb.gae.test.applications.core.CoreApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;

/**
 * Collection of tests related to querying the datastore.
 * 
 * Tests are conducted using the {@link Login} type.
 * 
 * @author dereekb
 *
 */
public class ObjectifyQueryTests extends CoreApplicationTestContext {

	@Autowired
	@Qualifier("loginRegistry")
	private ObjectifyRegistry<Login> registry;

	@Autowired
	@Qualifier("loginTestModelGenerator")
	private TestModelGenerator<Login> modelGenerator;

	@Test
	public void testQuerying() {
		Integer count = 10;

		this.modelGenerator.generate(count);

		ObjectifyQueryRequestBuilder<Login> queryBuilder = this.registry.makeQuery();
		ExecutableObjectifyQuery<Login> query = queryBuilder.buildExecutableQuery();
		Integer resultsCount = query.getResultCount();

		Assert.assertTrue(resultsCount == count);
	}

	@Test
	public void testLimitQuerying() {
		Integer limit = 10;

		this.modelGenerator.generate(limit * 2);

		ObjectifyQueryRequestBuilder<Login> queryBuilder = this.registry.makeQuery();
		ObjectifyQueryRequestOptions options = new ObjectifyQueryRequestOptionsImpl(limit);
		queryBuilder.setOptions(options);

		ExecutableObjectifyQuery<Login> query = queryBuilder.buildExecutableQuery();
		Integer resultsCount = query.getResultCount();

		Assert.assertTrue(resultsCount == limit);
	}

	@Test
	public void testLimitOffsetQuerying() {
		Integer limit = 10;
		Integer total = limit * 2;
		Integer offset = 15;

		Integer expected = total - offset;

		this.modelGenerator.generate(total);

		ObjectifyQueryRequestBuilder<Login> queryBuilder = this.registry.makeQuery();
		ObjectifyQueryRequestOptions options = new ObjectifyQueryRequestOptionsImpl(offset, limit);
		queryBuilder.setOptions(options);

		ExecutableObjectifyQuery<Login> query = queryBuilder.buildExecutableQuery();
		Integer resultsCount = query.getResultCount();

		Assert.assertTrue(String.format("Expected %s but got %s", expected, resultsCount), resultsCount == expected);
	}

}
