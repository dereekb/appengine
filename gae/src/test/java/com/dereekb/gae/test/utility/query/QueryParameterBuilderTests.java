package com.dereekb.gae.test.utility.query;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.misc.numbers.impl.LongBigDecimalUtilityImpl;
import com.dereekb.gae.utilities.query.builder.parameters.EncodedQueryParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.IntegerQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.LongBigDecimalQueryFieldParameterBuilder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.LongBigDecimalQueryFieldParameterBuilder.LongBigDecimalQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeySetQueryFieldParameterBuilder;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeySetQueryFieldParameterBuilder.ModelKeySetQueryFieldParameter;

public class QueryParameterBuilderTests {

	@Test
	public void testIntegerQueryFieldParameterDencoding() {

		IntegerQueryFieldParameter parameter = new IntegerQueryFieldParameter();

		parameter.setParameterString("=,3");

		Assert.assertTrue(parameter.getValue() == 3);
		Assert.assertTrue(parameter.getOperator() == ExpressionOperator.EQUAL);
	}

	@Test
	public void testModelKeySetQueryFieldParameterBuilderDencoding() {

		ModelKeySetQueryFieldParameterBuilder builder = ModelKeySetQueryFieldParameterBuilder.number();

		String parameterString = "in,1,2,3,4,5,6";

		ModelKeySetQueryFieldParameter parameter = builder.makeModelKeyParameter("field", parameterString);
		Set<ModelKey> keys = parameter.getValue();

		Assert.assertTrue(keys.size() == 6);
		Assert.assertTrue(parameter.getOperator() == ExpressionOperator.IN);
	}

	@Test
	public void testLongBigDecimalQueryFieldParameter() {

		LongBigDecimalQueryFieldParameterBuilder builder = LongBigDecimalQueryFieldParameterBuilder.THREE_PRECISION_BUILDER;

		String decimalValue = "1234.123";
		BigDecimal decimal = new BigDecimal(decimalValue);
		Long convertedLong = LongBigDecimalUtilityImpl.THREE_PRECISION_UTILITY.fromDecimal(decimal);
		
		LongBigDecimalQueryFieldParameter parameter = builder.make("test", decimal);
		
		Long value = parameter.getValue();
		Assert.assertTrue(value.equals(convertedLong));
		
		EncodedQueryParameter queryParameter = parameter.getParameterRepresentation();
		String qValue = queryParameter.getValue();
		
		Assert.assertTrue(qValue.equals(decimalValue));
	}

}
