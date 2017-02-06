package com.dereekb.gae.test.utility.query;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.impl.IntegerQueryFieldParameter;
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

}
