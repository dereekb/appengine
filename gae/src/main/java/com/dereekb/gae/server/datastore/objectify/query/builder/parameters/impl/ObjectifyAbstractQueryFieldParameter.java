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
	 * Configures only if parameter is not null.
	 */
	public static <T> void tryConfigure(ObjectifyQueryRequestLimitedBuilder request,
	                                    AbstractQueryFieldParameter<T> parameter) {
		if (parameter != null) {
			configure(request, parameter);
		}
	}

	/**
	 * Configures only if parameter is not null.
	 */
	public static <T> void tryConfigure(ObjectifyQueryRequestLimitedBuilder request,
	                                    AbstractQueryFieldParameter<?> parameter,
	                                    T value) {
		if (parameter != null) {
			configure(request, parameter, value);
		}
	}

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
		T value = parameter.getValue();
		ObjectifyAbstractQueryFieldParameter.configure(request, parameter, value);
	}

	/**
	 * Configures the request using the input query parameter, and a custom
	 * value.
	 * 
	 * Use when the value needs to be overridden, but otherwise the rest of the
	 * config comes from the parameter.
	 * 
	 * @param request
	 *            {@link ObjectifyQueryRequestLimitedBuilder}. Never
	 *            {@code null}.
	 * @param parameter
	 *            {@link AbstractQueryFieldParameter}. Never {@code null}.
	 * @param value
	 *            value to override.
	 */
	public static <T> void configure(ObjectifyQueryRequestLimitedBuilder request,
	                                 AbstractQueryFieldParameter<?> parameter,
	                                 T value) {
		String field = parameter.getField();
		ExpressionOperator operator = parameter.getOperator();
		QueryResultsOrdering resultsOrdering = parameter.getOrdering();

		if (field != null) {

			if (operator != ExpressionOperator.NO_OP) {
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
