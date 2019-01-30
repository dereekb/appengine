package com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.impl.AbstractQueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.StringQueryFieldParameter;

/**
 * {@link AbstractQueryFieldParameter} for a {@link String} value.
 *
 * @author dereekb
 *
 */
public class ObjectifyStringQueryFieldParameter extends StringQueryFieldParameter
        implements ObjectifyQueryRequestLimitedConfigurer {

	public ObjectifyStringQueryFieldParameter() {
		super();
	}

	public ObjectifyStringQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		super(field, parameterString);
	}

	public ObjectifyStringQueryFieldParameter(String field, ExpressionOperator operator, String value) {
		super(field, operator, value);
	}

	// MARK: ObjectifyQueryRequestLimitedConfigurer
	@Override
	public void configure(ObjectifyQueryRequestLimitedBuilder request) {
		ObjectifyAbstractQueryFieldParameter.configure(request, this);
	}

}
