package com.dereekb.gae.test.server.datastore.objectify.tests;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.QueryParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;
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
		ExpressionOperator operator = ExpressionOperator.EQUAL;

		QueryParameter parameters = new ParameterImpl(value, operator, ordering);
		String encoding = dencoder.encodeString(parameters);

		QueryParameter decoded = dencoder.decodeString(encoding);
		Assert.assertTrue(decoded.getValue().equals(value));
		Assert.assertTrue(decoded.equals(parameters));

	}

	@Test
	public void testDecodingShort() {
		QueryParameter parameter = dencoder.decodeString("=,2");

		Assert.assertTrue(parameter.getValue().equals("2"));
		Assert.assertTrue(parameter.getOperator() == ExpressionOperator.EQUAL);
	}

	@Test
	public void testEncodingEqualsNull() {
		ParameterImpl parameter = new ParameterImpl("null", ExpressionOperator.IS_NULL);
		String encoded = dencoder.encodeString(parameter);
		Assert.assertTrue(encoded.equals("=n,null"));
	}

	@Test
	public void testDecodingShortEqualsNull() {
		QueryParameter parameter = dencoder.decodeString("=n,null");

		Assert.assertTrue(parameter.getOperator() == ExpressionOperator.IS_NULL);
		Assert.assertTrue(parameter.getValue().equals("null"));
	}

	@Test
	public void testDecodingShortEqualsNullWithoutValueDecodesEquivalency() {
		QueryParameter parameter = dencoder.decodeString("=n,");

		Assert.assertTrue(parameter.getOperator() == ExpressionOperator.EQUAL);
		Assert.assertTrue(parameter.getValue().equals("=n,"));
	}

	@Test
	public void testDecodingCommaSeparatedValuesNull() {
		String value = "1,2,3,4,5,6,7,8,9";

		QueryParameter parameter = dencoder.decodeString("in," + value);

		Assert.assertTrue(parameter.getOperator() == ExpressionOperator.IN);
		Assert.assertTrue(parameter.getValue().equals(value));
	}

	@Test
	public void testModelKeyParameterEncodingAndDecodingEqualsNull() {
		ModelKeyQueryFieldParameterBuilder builder = ModelKeyQueryFieldParameterBuilder.NUMBER_SINGLETON;
		
		ModelKeyQueryFieldParameter parameter = builder.makeNullModelKeyParameter("field");
		
		Assert.assertTrue(parameter.getOperator().equals(ExpressionOperator.IS_NULL));
		
		String encoded = parameter.getParameterString();
		
		ModelKeyQueryFieldParameter decoded = builder.makeModelKeyParameter("field", encoded);
		
		Assert.assertTrue(decoded.getOperator().equals(ExpressionOperator.IS_NULL));
	}

}
