package com.dereekb.gae.test.server.datastore.objectify.tests;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.Parameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.QueryFieldParameterDencoder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.QueryFieldParameterDencoder.ParameterImpl;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;

/**
 * {@link QueryFieldParameterDencoder} tests.
 *
 * @author dereekb
 *
 */
public class QueryFieldParameterTests {

	private static final QueryFieldParameterDencoder dencoder = new QueryFieldParameterDencoder();

	@Test
	public void testQueryFieldParameterDencoder() {
		String value = "value";
		QueryResultsOrdering ordering = QueryResultsOrdering.Ascending;
		ExpressionOperator operator = ExpressionOperator.Equal;

		Parameter parameters = new ParameterImpl(value, operator, ordering);
		String encoding = dencoder.encodeString(parameters);

		Parameter decoded = dencoder.decodeString(encoding);
		Assert.assertTrue(decoded.getValue().equals(value));
		Assert.assertTrue(decoded.equals(parameters));

	}

	@Test
	public void testDecodingShort() {
		Parameter parameter = dencoder.decodeString("=,2");

		Assert.assertTrue(parameter.getValue().equals("2"));
		Assert.assertTrue(parameter.getOperator() == ExpressionOperator.Equal);
	}

	@Test
	public void testEncodingEqualsNull() {
		ParameterImpl parameter = new ParameterImpl("null", ExpressionOperator.IsNull);
		String encoded = dencoder.encodeString(parameter);
		Assert.assertTrue(encoded.equals("=n,null"));
	}

	@Test
	public void testDecodingShortEqualsNull() {
		Parameter parameter = dencoder.decodeString("=n,null");

		Assert.assertTrue(parameter.getOperator() == ExpressionOperator.IsNull);
		Assert.assertTrue(parameter.getValue().equals("null"));
	}

}
