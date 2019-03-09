package com.dereekb.gae.test.applications.core.server.datastore.query;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.exception.TooManyQueryInequalitiesException;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyConditionQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestBuilderImpl;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.impl.ObjectifyQueryOrderingImpl;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.collections.chain.Chain;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;

/**
 * Tests the Objectify Fields
 *
 * @author dereekb
 *
 */
public class ObjectifyQueryFieldsTest extends AbstractObjectifyQueryTest {

	// MARK: Request Builder Ordering
	@Test
	public void testRequestBuilderOrderReorderingWithoutInequality() {
		ObjectifyQueryRequestBuilderImpl<Foo> impl = (ObjectifyQueryRequestBuilderImpl<Foo>) this.registry
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

		ExecutableObjectifyQuery<Foo> executable = impl.buildExecutableQuery();

		// Check that
		Chain<ObjectifyQueryOrdering> chain = executable.getResultsOrdering();
		Assert.assertTrue(chain.getValue().getField().equals(fieldA));
	}

	@Test
	public void testRequestBuilderOrderReorderingWithInequality() {
		ObjectifyQueryRequestBuilderImpl<Foo> impl = (ObjectifyQueryRequestBuilderImpl<Foo>) this.registry
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

		ExecutableObjectifyQuery<Foo> executable = impl.buildExecutableQuery();

		Assert.assertTrue(executable.getQueryFilters().get(0).getField().equals(fieldB));

		// Check that b's field is first.
		Chain<ObjectifyQueryOrdering> chain = executable.getResultsOrdering();
		Assert.assertTrue(chain.getValue().getField().equals(fieldB));
	}

	@Test
	public void testRequestBuilderOrderReorderingWithDoubleInequality() {
		ObjectifyQueryRequestBuilderImpl<Foo> impl = (ObjectifyQueryRequestBuilderImpl<Foo>) this.registry
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

		ExecutableObjectifyQuery<Foo> executable = impl.buildExecutableQuery();
		executable.queryModelKeys();

		Assert.assertTrue(executable.getQueryFilters().get(0).getField().equals(fieldB));

		// Check that b's field is first.
		Chain<ObjectifyQueryOrdering> chain = executable.getResultsOrdering();
		Assert.assertTrue(chain.getValue().getField().equals(fieldB));
	}

	@Test
	public void testRequestBuilderMultipleInequalitysFail() {
		ObjectifyQueryRequestBuilderImpl<Foo> impl = (ObjectifyQueryRequestBuilderImpl<Foo>) this.registry
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
