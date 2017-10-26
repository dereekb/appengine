package com.dereekb.gae.test.applications.core.server.datastore.query;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.MutableObjectifyQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.exception.TooManyQueryInequalitiesException;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyConditionQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestBuilderImpl;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestOptionsImpl;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.impl.ObjectifyQueryOrderingImpl;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.test.applications.core.CoreApplicationTestContext;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.utilities.collections.chain.Chain;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;
import com.google.appengine.api.datastore.Cursor;

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

	@Test
	public void testCursor() {
		Integer limit = 5;
		Integer count = 10;

		this.modelGenerator.generate(count);

		ObjectifyQueryRequestBuilder<Login> queryBuilder = this.registry.makeQuery();
		MutableObjectifyQueryRequestOptions options = new ObjectifyQueryRequestOptionsImpl();

		options.setLimit(limit);

		queryBuilder.setOptions(options);

		ExecutableObjectifyQuery<Login> queryA = queryBuilder.buildExecutableQuery();

		List<ModelKey> resultsA = queryA.queryModelKeys();
		Assert.assertTrue(resultsA.size() == limit);

		Cursor cursorA = queryA.getCursor();

		Assert.assertNotNull(cursorA);

		String cursorAString = cursorA.toWebSafeString();
		options.setCursor(cursorAString);

		queryBuilder.setOptions(options);
		ExecutableObjectifyQuery<Login> queryB = queryBuilder.buildExecutableQuery();
		List<ModelKey> resultsB = queryB.queryModelKeys();

		Assert.assertTrue(resultsB.size() == limit);
		Assert.assertFalse(resultsA.contains(resultsB.get(0)));
	}

	@Test
	public void testQueryingConsistency() {
		Integer count = 10;

		this.modelGenerator.generate(count);

		ObjectifyQueryRequestBuilder<Login> queryBuilder = this.registry.makeQuery();
		ObjectifyQueryRequestOptions options = new ObjectifyQueryRequestOptionsImpl();
		queryBuilder.setOptions(options);

		ExecutableObjectifyQuery<Login> query = queryBuilder.buildExecutableQuery();
		List<ModelKey> keys = query.queryModelKeys();
		Assert.assertNotNull(keys);

		List<Login> models = query.queryModels();
		Assert.assertNotNull(models);

		List<ModelKey> modelKeys = ModelKey.readModelKeys(models);

		Assert.assertTrue(keys.size() == models.size());
		Assert.assertTrue(modelKeys.containsAll(keys));
		Assert.assertTrue(query.hasResults());
		Assert.assertTrue(query.getResultCount() == count);
	}

	// MARK: Request Builder
	@Test
	public void testRequestBuilderOrderReorderingWithoutInequality() {
		ObjectifyQueryRequestBuilderImpl<Login> impl = (ObjectifyQueryRequestBuilderImpl<Login>) this.registry
		        .makeQuery();

		String fieldA = "fieldA";
		String fieldB = "fieldB";

		ObjectifyQueryFilter a = new ObjectifyConditionQueryFilter(fieldA, ExpressionOperator.EQUAL, "VALUE");
		// ObjectifyQueryFilter b = new ObjectifyConditionQueryFilter(fieldB,
		// ExpressionOperator.EQUAL, "VALUE");

		impl.addQueryFilter(a);
		// impl.addQueryFilter(b);

		ObjectifyQueryOrdering aOrder = new ObjectifyQueryOrderingImpl(fieldA, QueryResultsOrdering.Ascending);
		ObjectifyQueryOrdering bOrder = new ObjectifyQueryOrderingImpl(fieldB, QueryResultsOrdering.Ascending);

		impl.addResultsOrdering(bOrder);	// Add b first.
		impl.addResultsOrdering(aOrder);

		ExecutableObjectifyQuery<Login> executable = impl.buildExecutableQuery();

		// Check that
		Chain<ObjectifyQueryOrdering> chain = executable.getResultsOrdering();
		Assert.assertTrue(chain.getValue().getField().equals(fieldA));
	}

	@Test
	public void testRequestBuilderOrderReorderingWithInequality() {
		ObjectifyQueryRequestBuilderImpl<Login> impl = (ObjectifyQueryRequestBuilderImpl<Login>) this.registry
		        .makeQuery();

		String fieldA = "fieldA";
		String fieldB = "fieldB";

		ObjectifyQueryFilter a = new ObjectifyConditionQueryFilter(fieldA, ExpressionOperator.EQUAL, "VALUE");
		ObjectifyQueryFilter b = new ObjectifyConditionQueryFilter(fieldB, ExpressionOperator.GREATER_THAN, "VALUE");

		Assert.assertTrue(b.isInequality());

		impl.addQueryFilter(a);
		impl.addQueryFilter(b);

		Assert.assertTrue(impl.getInequalityFilter() != null);

		ObjectifyQueryOrdering aOrder = new ObjectifyQueryOrderingImpl(fieldA, QueryResultsOrdering.Ascending);
		ObjectifyQueryOrdering bOrder = new ObjectifyQueryOrderingImpl(fieldB, QueryResultsOrdering.Ascending);

		impl.addResultsOrdering(aOrder);
		impl.addResultsOrdering(bOrder);

		ExecutableObjectifyQuery<Login> executable = impl.buildExecutableQuery();

		Assert.assertTrue(executable.getQueryFilters().get(0).getField().equals(fieldB));

		// Check that b's field is first.
		Chain<ObjectifyQueryOrdering> chain = executable.getResultsOrdering();
		Assert.assertTrue(chain.getValue().getField().equals(fieldB));
	}

	@Test
	public void testRequestBuilderOrderReorderingWithDoubleInequality() {
		ObjectifyQueryRequestBuilderImpl<Login> impl = (ObjectifyQueryRequestBuilderImpl<Login>) this.registry
		        .makeQuery();

		String fieldA = "fieldA";
		String fieldB = "fieldB";

		ObjectifyQueryFilter a = new ObjectifyConditionQueryFilter(fieldA, ExpressionOperator.EQUAL, "VALUE");
		ObjectifyQueryFilter b = new ObjectifyConditionQueryFilter(fieldB, ExpressionOperator.GREATER_THAN, "VALUE");
		ObjectifyQueryFilter b2 = new ObjectifyConditionQueryFilter(fieldB, ExpressionOperator.LESS_THAN, "VALUE");

		Assert.assertTrue(b.isInequality());

		impl.addQueryFilter(a);
		impl.addQueryFilter(b);
		impl.addQueryFilter(b2);

		Assert.assertTrue(impl.getInequalityFilter() != null);
		Assert.assertTrue(impl.getInequalitySecondFilter() != null);

		ObjectifyQueryOrdering aOrder = new ObjectifyQueryOrderingImpl(fieldA, QueryResultsOrdering.Ascending);
		ObjectifyQueryOrdering bOrder = new ObjectifyQueryOrderingImpl(fieldB, QueryResultsOrdering.Ascending);

		impl.addResultsOrdering(aOrder);
		impl.addResultsOrdering(bOrder);

		ExecutableObjectifyQuery<Login> executable = impl.buildExecutableQuery();
		executable.queryModelKeys();

		Assert.assertTrue(executable.getQueryFilters().get(0).getField().equals(fieldB));

		// Check that b's field is first.
		Chain<ObjectifyQueryOrdering> chain = executable.getResultsOrdering();
		Assert.assertTrue(chain.getValue().getField().equals(fieldB));
	}
	
	@Test
	public void testRequestBuilderMultipleInequalitysFail() {
		ObjectifyQueryRequestBuilderImpl<Login> impl = (ObjectifyQueryRequestBuilderImpl<Login>) this.registry
		        .makeQuery();

		String fieldA = "fieldA";
		String fieldB = "fieldB";

		ObjectifyQueryFilter a = new ObjectifyConditionQueryFilter(fieldA, ExpressionOperator.GREATER_THAN, "VALUE");
		ObjectifyQueryFilter b = new ObjectifyConditionQueryFilter(fieldB, ExpressionOperator.GREATER_THAN, "VALUE");

		Assert.assertTrue(a.isInequality());
		Assert.assertTrue(b.isInequality());

		impl.addQueryFilter(a);

		try {
			impl.addQueryFilter(b);
			Assert.fail("Should have failed.");
		} catch (TooManyQueryInequalitiesException e) {

		}
	}

}
