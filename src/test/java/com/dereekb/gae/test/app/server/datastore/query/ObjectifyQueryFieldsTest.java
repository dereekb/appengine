package com.dereekb.gae.test.app.server.datastore.query;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.extras.gen.test.model.foo.Foo;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.exception.TooManyQueryInequalitiesException;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyConditionQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestBuilderImpl;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.impl.ObjectifyQueryOrderingImpl;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.collections.chain.Chain;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;

/**
 * Tests the Objectify Fields
 *
 * @author dereekb
 *
 */
public class ObjectifyQueryFieldsTest extends AbstractObjectifyQueryTests {

	protected static final Integer NUMBER_A = 1;
	protected static final Integer NUMBER_B = 2;
	protected static final String STRING_A = "a";


	// MARK: Query Fields
	@Test
	public void testQueryingArrayFieldForSingleValue() {

		Foo foo = this.generateTestFoo();

		ObjectifyQueryRequestBuilderImpl<Foo> builder = (ObjectifyQueryRequestBuilderImpl<Foo>) this.registry
		        .makeQuery();

		ObjectifyQueryFilter filter = new ObjectifyConditionQueryFilter("numberList", ExpressionOperator.EQUAL, NUMBER_A);
		builder.addQueryFilter(filter);

		ExecutableObjectifyQuery<Foo> executable = builder.buildExecutableQuery();
		List<ModelKey> keys = executable.queryModelKeys();

		assertTrue(keys.contains(foo.getModelKey()));
	}

	@Test
	public void testQueryingSetFieldForSingleValue() {

		Foo foo = this.generateTestFoo();

		ObjectifyQueryRequestBuilderImpl<Foo> builder = (ObjectifyQueryRequestBuilderImpl<Foo>) this.registry
		        .makeQuery();

		ObjectifyQueryFilter filter = new ObjectifyConditionQueryFilter("stringSet", ExpressionOperator.EQUAL, STRING_A);
		builder.addQueryFilter(filter);

		ExecutableObjectifyQuery<Foo> executable = builder.buildExecutableQuery();
		List<ModelKey> keys = executable.queryModelKeys();

		assertTrue(keys.contains(foo.getModelKey()));
	}

	@Test
	public void testQueryingArrayFieldForMultipleValuesUsingInFails() {

		Foo foo = this.generateTestFoo();

		try {
			ObjectifyQueryRequestBuilderImpl<Foo> builder = (ObjectifyQueryRequestBuilderImpl<Foo>) this.registry
			        .makeQuery();

			@SuppressWarnings("deprecation")
			ObjectifyQueryFilter filter = new ObjectifyConditionQueryFilter("numberList", ExpressionOperator.IN, ListUtility.toList(NUMBER_A, NUMBER_B));
			builder.addQueryFilter(filter);

			ExecutableObjectifyQuery<Foo> executable = builder.buildExecutableQuery();
			List<ModelKey> keys = executable.queryModelKeys();

			assertTrue(keys.contains(foo.getModelKey()));
		} catch (Exception e) {
			assertTrue(UnsupportedOperationException.class.isAssignableFrom(e.getClass()));
		}
	}


	private Foo generateTestFoo() {
		Foo foo = this.modelGenerator.generate();

		foo.setNumber(NUMBER_A);

		List<Integer> numberList = ListUtility.toList(NUMBER_A, NUMBER_B, 3);
		foo.setNumberList(numberList);

		Set<String> stringSet = SetUtility.makeSet(STRING_A, "b", "c");
		foo.setStringSet(stringSet);

		this.registry.update(foo);

		return foo;
	}

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
		assertTrue(chain.getValue().getField().equals(fieldA));
	}

	@Test
	public void testRequestBuilderOrderReorderingWithInequality() {
		ObjectifyQueryRequestBuilderImpl<Foo> impl = (ObjectifyQueryRequestBuilderImpl<Foo>) this.registry
		        .makeQuery();

		String fieldA = "fieldA";
		String fieldB = "fieldB";

		ObjectifyQueryFilter a = new ObjectifyConditionQueryFilter(fieldA, ExpressionOperator.EQUAL, "VALUE");
		ObjectifyQueryFilter b = new ObjectifyConditionQueryFilter(fieldB, ExpressionOperator.GREATER_THAN, "VALUE");

		assertTrue(b.isInequality());

		impl.addQueryFilter(a);
		impl.addQueryFilter(b);

		assertTrue(impl.getInequalityFilter() != null);

		ObjectifyQueryOrdering aOrder = new ObjectifyQueryOrderingImpl(fieldA, QueryResultsOrdering.Ascending);
		ObjectifyQueryOrdering bOrder = new ObjectifyQueryOrderingImpl(fieldB, QueryResultsOrdering.Ascending);

		impl.addResultsOrdering(aOrder);
		impl.addResultsOrdering(bOrder);

		ExecutableObjectifyQuery<Foo> executable = impl.buildExecutableQuery();

		assertTrue(executable.getQueryFilters().get(0).getField().equals(fieldB));

		// Check that b's field is first.
		Chain<ObjectifyQueryOrdering> chain = executable.getResultsOrdering();
		assertTrue(chain.getValue().getField().equals(fieldB));
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

		assertTrue(b.isInequality());

		impl.addQueryFilter(a);
		impl.addQueryFilter(b);
		impl.addQueryFilter(b2);

		assertTrue(impl.getInequalityFilter() != null);
		assertTrue(impl.getInequalitySecondFilter() != null);

		ObjectifyQueryOrdering aOrder = new ObjectifyQueryOrderingImpl(fieldA, QueryResultsOrdering.Ascending);
		ObjectifyQueryOrdering bOrder = new ObjectifyQueryOrderingImpl(fieldB, QueryResultsOrdering.Ascending);

		impl.addResultsOrdering(aOrder);
		impl.addResultsOrdering(bOrder);

		ExecutableObjectifyQuery<Foo> executable = impl.buildExecutableQuery();
		executable.queryModelKeys();

		assertTrue(executable.getQueryFilters().get(0).getField().equals(fieldB));

		// Check that b's field is first.
		Chain<ObjectifyQueryOrdering> chain = executable.getResultsOrdering();
		assertTrue(chain.getValue().getField().equals(fieldB));
	}

	@Test
	public void testRequestBuilderMultipleInequalitysFail() {
		ObjectifyQueryRequestBuilderImpl<Foo> impl = (ObjectifyQueryRequestBuilderImpl<Foo>) this.registry
		        .makeQuery();

		String fieldA = "fieldA";
		String fieldB = "fieldB";

		ObjectifyQueryFilter a = new ObjectifyConditionQueryFilter(fieldA, ExpressionOperator.GREATER_THAN, "VALUE");
		ObjectifyQueryFilter b = new ObjectifyConditionQueryFilter(fieldB, ExpressionOperator.GREATER_THAN, "VALUE");

		assertTrue(a.isInequality());
		assertTrue(b.isInequality());

		impl.addQueryFilter(a);

		try {
			impl.addQueryFilter(b);
			fail("Should have failed.");
		} catch (TooManyQueryInequalitiesException e) {

		}
	}

}
