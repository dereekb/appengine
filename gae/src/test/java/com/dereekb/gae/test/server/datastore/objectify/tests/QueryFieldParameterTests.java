package com.dereekb.gae.test.server.datastore.objectify.tests;

import org.junit.Assert;
import org.junit.Test;

import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.QueryFieldParameterDencoder;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.QueryFieldParameterDencoder.Parameter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryConditionOperator;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryResultsOrdering;

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
		ObjectifyQueryResultsOrdering ordering = ObjectifyQueryResultsOrdering.Ascending;
		ObjectifyQueryConditionOperator operator = ObjectifyQueryConditionOperator.Equal;

		Parameter parameters = new Parameter(value, operator, ordering);
		String encoding = dencoder.encodeString(parameters);

		Parameter decoded = dencoder.decodeString(encoding);
		Assert.assertTrue(decoded.getValue().equals(value));
		Assert.assertTrue(decoded.equals(parameters));

	}

}
