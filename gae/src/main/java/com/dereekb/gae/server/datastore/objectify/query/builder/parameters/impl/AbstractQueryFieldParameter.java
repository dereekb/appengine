package com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.ConfigurableQueryParameter;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.QueryFieldParameterDencoder.Parameter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyConditionQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryResultsOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.impl.ObjectifyQueryOrderingImpl;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;

/**
 * {@link ObjectifyQueryRequestLimitedConfigurer} implementation that targets a
 * specific field.
 * <p>
 * Can specify an operator, and an ordering.
 *
 * @author dereekb
 *
 */
public abstract class AbstractQueryFieldParameter<T>
        implements ConfigurableQueryParameter {

	private String field;
	private ExpressionOperator operator;
	protected T value;

	private ObjectifyQueryResultsOrdering ordering;

	public AbstractQueryFieldParameter() {}

	public AbstractQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		this.setField(field);
		this.setParameterString(parameterString);
	}

	public AbstractQueryFieldParameter(String field, T value) {
		this.setEqualityFilter(field, value);
	}

	public AbstractQueryFieldParameter(String field, ExpressionOperator operator, T value) {
		this.setFilter(field, operator, value);
	}

	public void setComparison(ExpressionOperator operator,
	                          T value) {
		this.setOperator(operator);
		this.setValue(value);
	}

	public ExpressionOperator getOperator() {
		return this.operator;
	}

	public void setOperator(ExpressionOperator operator) {
		this.operator = operator;
	}

	public T getValue() {
		return this.value;
	}

	public AbstractQueryFieldParameter<T> setValue(T value) {
		this.value = value;
		return this;
	}

	public AbstractQueryFieldParameter<T> setOrdering(ObjectifyQueryResultsOrdering ordering) {
		this.ordering = ordering;
		return this;
	}

	public String getField() {
		return this.field;
	}

	public AbstractQueryFieldParameter<T> setField(String field) {
		if (field == null || field.isEmpty()) {
			throw new IllegalArgumentException("Field cannot be a null or empty string.");
		}

		this.field = field;
		return this;
	}

	// Filter
	public AbstractQueryFieldParameter<T> setEqualityFilter(String field,
	                                                T value) {
		return this.setFilter(field, ExpressionOperator.Equal, value);
	}

	public AbstractQueryFieldParameter<T> setFilter(String field,
	                                        ExpressionOperator operator,
	                                        T value) {
		this.setField(field);
		this.setOperator(operator);
		this.setValue(value);
		return this;
	}

	// MARK: ObjectifyQueryRequestConfigurer
	@Override
	public void configure(ObjectifyQueryRequestLimitedBuilder request) {
		if (this.field != null) {

			if (this.operator != null) {
				ObjectifyConditionQueryFilter filter = new ObjectifyConditionQueryFilter(this.field, this.operator,
				        this.value);
				request.addQueryFilter(filter);
			}

			if (this.ordering != null) {
				ObjectifyQueryOrdering ordering = new ObjectifyQueryOrderingImpl(this.field, this.ordering);
				request.addResultsOrdering(ordering);
			}

		}
	}

	// MARK: QueryParameter
	@Override
	public String getParameterString() {
		Parameter parameter = this.getParameterRepresentation();
		return QueryFieldParameterDencoder.SINGLETON.encodeString(parameter);
	}

	@Override
	public void setParameterString(String parameterString) throws IllegalArgumentException {
		Parameter parameter = QueryFieldParameterDencoder.SINGLETON.decodeString(parameterString);
		this.setParameterRepresentation(parameter);
	}

	public Parameter getParameterRepresentation() {
		String value = this.getParameterValue();
		return new Parameter(value, this.operator, this.ordering);
	}

	public void setParameterRepresentation(Parameter parameter) throws IllegalArgumentException {
		this.operator = parameter.getOperator();
		this.ordering = parameter.getOrdering();
		this.setParameterValue(parameter.getValue());
	}

	public abstract String getParameterValue();

	public abstract void setParameterValue(String value) throws IllegalArgumentException;

	@Override
	public String toString() {
		return "AbstractQueryFieldParameter [field=" + this.field + ", operator=" + this.operator + ", value=" + this.value
		        + ", ordering=" + this.ordering + "]";
	}

}
