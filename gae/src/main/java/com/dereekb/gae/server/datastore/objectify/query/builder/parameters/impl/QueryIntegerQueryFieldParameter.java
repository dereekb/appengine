package com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.impl.AbstractQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.IntegerQueryFieldParameter;

/**
 * {@link AbstractQueryFieldParameter} used in Objectify queries.
 * 
 * @author dereekb
 *
 */
public class QueryIntegerQueryFieldParameter extends IntegerQueryFieldParameter
        implements ObjectifyQueryRequestLimitedConfigurer {

	public QueryIntegerQueryFieldParameter() {
		super();
	}

	public QueryIntegerQueryFieldParameter(String field, ExpressionOperator operator, Integer value) {
		super(field, operator, value);
	}

	public QueryIntegerQueryFieldParameter(String field, Integer value) {
		super(field, value);
	}

	public QueryIntegerQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		super(field, parameterString);
	}

	// MARK: ObjectifyQueryRequestLimitedConfigurer
	@Override
	public void configure(ObjectifyQueryRequestLimitedBuilder request) {
		ObjectifyAbstractQueryFieldParameter.configure(request, this);
	}

}
