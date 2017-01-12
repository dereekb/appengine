package com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl;

import java.util.Date;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.impl.AbstractQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.DateQueryFieldParameter;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;

/**
 * {@link AbstractQueryFieldParameter} for a date.
 *
 * @author dereekb
 *
 */
public class ObjectifyDateQueryFieldParameter extends DateQueryFieldParameter
        implements ObjectifyQueryRequestLimitedConfigurer {

	public ObjectifyDateQueryFieldParameter(String field, Date value) {
		super(field, value);
	}

	public ObjectifyDateQueryFieldParameter(String field, ExpressionOperator operator, Date value) {
		super(field, operator, value);
	}

	public ObjectifyDateQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		super(field, parameterString);
	}

	public ObjectifyDateQueryFieldParameter(String field) {
		super(field);
	}

	public static ObjectifyDateQueryFieldParameter recentDate(String field) {
		ObjectifyDateQueryFieldParameter parameter = new ObjectifyDateQueryFieldParameter(field);
		parameter.setOrdering(QueryResultsOrdering.Descending);
		parameter.setOperator(ExpressionOperator.LessOrEqualTo);
		return parameter;
	}

	// MARK: ObjectifyQueryRequestLimitedConfigurer
	@Override
	public void configure(ObjectifyQueryRequestLimitedBuilder request) {
		ObjectifyAbstractQueryFieldParameter.configure(request, this);
	}

}
