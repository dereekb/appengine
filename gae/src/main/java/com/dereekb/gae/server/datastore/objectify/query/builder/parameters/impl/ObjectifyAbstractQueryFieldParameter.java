package com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyConditionQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.impl.ObjectifyQueryOrderingImpl;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.impl.AbstractQueryFieldParameter;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;

/**
 * {@link AbstractQueryFieldParameter} extension that implements
 * {@link ObjectifyQueryRequestLimitedConfigurer}.
 * 
 * Also acts as a static configurer for {@link AbstractQueryFieldParameter}
 * types for configuring {@link ObjectifyQueryRequestLimitedBuilder} values.
 *
 * @author dereekb
 *
 */
public abstract class ObjectifyAbstractQueryFieldParameter<T> extends AbstractQueryFieldParameter<T>
        implements ObjectifyQueryRequestLimitedConfigurer {

	public ObjectifyAbstractQueryFieldParameter() {
		super();
	}

	public ObjectifyAbstractQueryFieldParameter(String field, ExpressionOperator operator, T value) {
		super(field, operator, value);
	}

	public ObjectifyAbstractQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		super(field, parameterString);
	}

	public ObjectifyAbstractQueryFieldParameter(String field, T value) {
		super(field, value);
	}

	// MARK: ObjectifyQueryRequestLimitedConfigurer
	@Override
	public void configure(ObjectifyQueryRequestLimitedBuilder request) {
		ObjectifyAbstractQueryFieldParameter.configure(request, this);
	}

	// MARK: Static
	/**
	 * Configures a request using the input query parameter.
	 * 
	 * @param request
	 *            {@link ObjectifyQueryRequestLimitedBuilder}. Never
	 *            {@code null}.
	 * @param parameter
	 *            {@link AbstractQueryFieldParameter}. Never {@code null}.
	 */
	public static <T> void configure(ObjectifyQueryRequestLimitedBuilder request,
	                                 AbstractQueryFieldParameter<T> parameter) {
		String field = parameter.getField();
		T value = parameter.getValue();
		ExpressionOperator operator = parameter.getOperator();
		QueryResultsOrdering resultsOrdering = parameter.getOrdering();

		if (field != null) {

			if (operator != null) {
				ObjectifyConditionQueryFilter filter = new ObjectifyConditionQueryFilter(field, operator, value);
				request.addQueryFilter(filter);
			}

			if (resultsOrdering != null) {
				ObjectifyQueryOrdering ordering = new ObjectifyQueryOrderingImpl(field, resultsOrdering);
				request.addResultsOrdering(ordering);
			}

		}
	}

}
