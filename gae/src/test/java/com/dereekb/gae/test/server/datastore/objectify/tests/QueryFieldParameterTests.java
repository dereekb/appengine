package com.dereekb.gae.test.server.datastore.objectify.tests;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.impl.QueryFieldParameterDencoder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.QueryFieldParameterDencoder.Parameter;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;

/**
 * {@link QueryFieldParameterDencoder} tests.
 *
 * @author dereekb
 *
 */
public class QueryFieldParameterTests {

	@Test
	public void testQueryFieldParameterDencoder() {
		QueryFieldParameterDencoder dencoder = new QueryFieldParameterDencoder();

		String value = "value";
		QueryResultsOrdering ordering = QueryResultsOrdering.Ascending;
		ExpressionOperator operator = ExpressionOperator.Equal;

		Parameter parameters = new Parameter(value, operator, ordering);
		String encoding = dencoder.encodeString(parameters);

		Parameter decoded = dencoder.decodeString(encoding);
		Assert.assertTrue(decoded.getValue().equals(value));
		Assert.assertTrue(decoded.equals(parameters));

	}

	@Test
	public void testDecodingShort() {
		QueryFieldParameterDencoder dencoder = new QueryFieldParameterDencoder();

		Parameter parameter = dencoder.decodeString("=,2");

		Assert.assertTrue(parameter.getValue().equals("2"));
		Assert.assertTrue(parameter.getOperator() == ExpressionOperator.Equal);

	}

}
