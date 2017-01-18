package com.dereekb.gae.test.utility.query;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.impl.IntegerQueryFieldParameter;

public class QueryParameterBuilderTests {

	@Test
	public void testIntegerQueryFieldParameterDencoding() {

		IntegerQueryFieldParameter parameter = new IntegerQueryFieldParameter();

		parameter.setParameterString("=,3");

		Assert.assertTrue(parameter.getValue() == 3);
		Assert.assertTrue(parameter.getOperator() == ExpressionOperator.EQUAL);
	}

}
