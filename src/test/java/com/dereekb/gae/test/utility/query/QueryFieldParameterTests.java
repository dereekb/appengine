package com.dereekb.gae.test.utility.query;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.EncodedQueryParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.DateQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeyQueryFieldParameterBuilder.ModelKeyQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.QueryFieldParameterDencoder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.QueryFieldParameterDencoder.EncodedQueryParameterImpl;
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

		EncodedQueryParameter parameters = new EncodedQueryParameterImpl(value, operator, ordering);
		String encoding = dencoder.encodeString(parameters);

		EncodedQueryParameter decoded = dencoder.decodeString(encoding);
		Assert.assertTrue(decoded.getValue().equals(value));
		Assert.assertTrue(decoded.equals(parameters));

	}

	@Test
	public void testDecodingShort() {
		EncodedQueryParameter parameter = dencoder.decodeString("=,2");
		Assert.assertTrue(parameter.getValue().equals("2"));
		Assert.assertTrue(parameter.getOperator() == ExpressionOperator.EQUAL);
	}

	@Test
	public void testEncodingEqualsNull() {
		EncodedQueryParameterImpl parameter = new EncodedQueryParameterImpl("null", ExpressionOperator.IS_NULL);
		String encoded = dencoder.encodeString(parameter);
		Assert.assertTrue(encoded.equals("=n,null"));
	}

	@Test
	public void testDecodingShortEqualsNull() {
		EncodedQueryParameter parameter = dencoder.decodeString("=n,null");

		Assert.assertTrue(parameter.getOperator() == ExpressionOperator.IS_NULL);
		Assert.assertTrue(parameter.getValue().equals("null"));
	}

	@Test
	public void testDecodingShortEqualsNullWithoutValueDecodesEquivalency() {
		EncodedQueryParameter parameter = dencoder.decodeString("=n,");

		Assert.assertTrue(parameter.getOperator() == ExpressionOperator.EQUAL);
		Assert.assertTrue(parameter.getValue().equals("=n,"));
	}

	@Test
	public void testDecodingCommaSeparatedValuesNull() {
		String value = "1,2,3,4,5,6,7,8,9";

		EncodedQueryParameter parameter = dencoder.decodeString("in," + value);

		Assert.assertTrue(parameter.getOperator() == ExpressionOperator.IN);
		Assert.assertTrue(parameter.getValue().equals(value));
	}

	@Test
	public void testEncodingSecondParameter() {
		String value = "value";
		ExpressionOperator operator = ExpressionOperator.GREATER_OR_EQUAL_TO;
		QueryResultsOrdering ordering = QueryResultsOrdering.Ascending;

		String secondValue = "valueB";
		ExpressionOperator secondOperator = ExpressionOperator.LESS_OR_EQUAL_TO;

		EncodedQueryParameter parameters = new EncodedQueryParameterImpl(value, operator, ordering, secondValue,
		        secondOperator);
		String encoding = dencoder.encodeString(parameters);

		EncodedQueryParameter decoded = dencoder.decodeString(encoding);
		Assert.assertTrue(decoded.getValue().equals(value));
		Assert.assertTrue(decoded.getOperator().equals(operator));
		Assert.assertTrue(decoded.getSecondFilter() != null);
		Assert.assertTrue(decoded.getSecondFilter().getValue().equals(secondValue));
		Assert.assertTrue(decoded.getSecondFilter().getOperator().equals(secondOperator));
		Assert.assertTrue(decoded.equals(parameters));
	}

	@Test
	public void testDecodingSecondParameter() {
		String value = "value";
		QueryResultsOrdering ordering = QueryResultsOrdering.Ascending;
		ExpressionOperator operator = ExpressionOperator.EQUAL;

		EncodedQueryParameter parameters = new EncodedQueryParameterImpl(value, operator, ordering);
		String encoding = dencoder.encodeString(parameters);

		EncodedQueryParameter decoded = dencoder.decodeString(encoding);
		Assert.assertTrue(decoded.getValue().equals(value));
		Assert.assertTrue(decoded.equals(parameters));

	}
	
	@Test
	public void testDecodingDateParameters() {
		
		String field = "field";
		
		Date start = new Date(0);
		Date end = new Date(1000);
		
		DateQueryFieldParameter parameter = new DateQueryFieldParameter(field);
		parameter.searchRange(start, end);
		
		String parameterString = parameter.getParameterString();
		DateQueryFieldParameter decoded = new DateQueryFieldParameter(field, parameterString);
		
		Assert.assertTrue(decoded.getValue().equals(start));
		Assert.assertTrue(decoded.getSecondFilter() != null);
		Assert.assertTrue(decoded.getSecondFilter().getValue().equals(end));
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
