package com.dereekb.gae.test.app.server.datastore.query;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.MutableObjectifyQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestOptionsImpl;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;

/**
 * Tests the Objectify Options
 *
 * @author dereekb
 *
 */
public class ObjectifyQueryOptionsTest extends AbstractObjectifyQueryTests {

	@Test
	public void testQuerying() {
		Integer count = 10;

		this.modelGenerator.generate(count);

		ObjectifyQueryRequestBuilder<Foo> queryBuilder = this.registry.makeQuery();
		ExecutableObjectifyQuery<Foo> query = queryBuilder.buildExecutableQuery();
		Integer resultsCount = query.getResultCount();


		assertTrue(resultsCount == count);
	}

	@Test
	public void testLimitQuerying() {
		Integer limit = 10;

		this.modelGenerator.generate(limit * 2);

		ObjectifyQueryRequestBuilder<Foo> queryBuilder = this.registry.makeQuery();
		ObjectifyQueryRequestOptions options = new ObjectifyQueryRequestOptionsImpl(limit);
		queryBuilder.setOptions(options);

		ExecutableObjectifyQuery<Foo> query = queryBuilder.buildExecutableQuery();
		Integer resultsCount = query.getResultCount();

		assertTrue(resultsCount == limit);
	}

	@Test
	public void testLimitOffsetQuerying() {
		Integer limit = 10;
		Integer total = limit * 2;
		Integer offset = 15;

		Integer expected = total - offset;

		this.modelGenerator.generate(total);

		ObjectifyQueryRequestBuilder<Foo> queryBuilder = this.registry.makeQuery();

		ObjectifyQueryRequestOptions options = new ObjectifyQueryRequestOptionsImpl(offset, limit);
		queryBuilder.setOptions(options);

		ExecutableObjectifyQuery<Foo> query = queryBuilder.buildExecutableQuery();
		Integer resultsCount = query.getResultCount();

		assertTrue(resultsCount == expected, String.format("Expected %s but got %s", expected, resultsCount));
	}

	@Test
	public void testCursor() {
		Integer limit = 5;
		Integer count = 10;

		this.modelGenerator.generate(count);

		ObjectifyQueryRequestBuilder<Foo> queryBuilder = this.registry.makeQuery();
		MutableObjectifyQueryRequestOptions options = new ObjectifyQueryRequestOptionsImpl();

		options.setLimit(limit);

		queryBuilder.setOptions(options);

		ExecutableObjectifyQuery<Foo> queryA = queryBuilder.buildExecutableQuery();

		List<ModelKey> resultsA = queryA.queryModelKeys();
		assertTrue(resultsA.size() == limit);

		ResultsCursor cursorA = queryA.getCursor();
		assertNotNull(cursorA);
		options.setCursor(cursorA);

		queryBuilder.setOptions(options);
		ExecutableObjectifyQuery<Foo> queryB = queryBuilder.buildExecutableQuery();
		List<ModelKey> resultsB = queryB.queryModelKeys();

		assertTrue(resultsB.size() == limit);
		assertFalse(resultsA.contains(resultsB.get(0)));
	}

	@Test
	public void testQueryingConsistency() {
		Integer count = 10;

		this.modelGenerator.generate(count);

		ObjectifyQueryRequestBuilder<Foo> queryBuilder = this.registry.makeQuery();
		ObjectifyQueryRequestOptions options = new ObjectifyQueryRequestOptionsImpl();
		queryBuilder.setOptions(options);

		ExecutableObjectifyQuery<Foo> query = queryBuilder.buildExecutableQuery();
		List<ModelKey> keys = query.queryModelKeys();
		assertNotNull(keys);

		List<Foo> models = query.queryModels();
		assertNotNull(models);

		List<ModelKey> modelKeys = ModelKey.readModelKeys(models);

		assertTrue(keys.size() == models.size());
		assertTrue(modelKeys.containsAll(keys));
		assertTrue(query.hasResults());
		assertTrue(query.getResultCount() == count);
	}

}
