package com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.ConfigurableQueryParameter;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.QueryFieldParameterDencoder.Parameter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyConditionQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryConditionOperator;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryResultsOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.impl.ObjectifyQueryOrderingImpl;

/**
 * {@link ObjectifyQueryRequestLimitedConfigurer} implementation that targets a
 * specific field.
 * <p>
 * Can specify an operator, and an ordering.
 *
 * @author dereekb
 *
 */
public abstract class QueryFieldParameter<T>
        implements ObjectifyQueryRequestLimitedConfigurer, ConfigurableQueryParameter {

	private String field;
	private ObjectifyQueryConditionOperator operator;
	private T value;

	private ObjectifyQueryResultsOrdering ordering;

	public QueryFieldParameter() {}

	public QueryFieldParameter(String parameterString) throws IllegalArgumentException {
		this.setParameterString(parameterString);
	}

	public QueryFieldParameter(String field, T value) {
		this.setEqualityFilter(field, value);
	}

	public QueryFieldParameter(String field, ObjectifyQueryConditionOperator operator, T value) {
		this.setFilter(field, operator, value);
	}

	public void setComparison(ObjectifyQueryConditionOperator operator,
	                          T value) {
		this.setOperator(operator);
		this.setValue(value);
	}

	public ObjectifyQueryConditionOperator getOperator() {
		return this.operator;
	}

	public void setOperator(ObjectifyQueryConditionOperator operator) {
		this.operator = operator;
	}

	public T getValue() {
		return this.value;
	}

	public QueryFieldParameter<T> setValue(T value) {
		this.value = value;
		return this;
	}

	public QueryFieldParameter<T> setOrdering(ObjectifyQueryResultsOrdering ordering) {
		this.ordering = ordering;
		return this;
	}

	public String getField() {
		return this.field;
	}

	public QueryFieldParameter<T> setField(String field) {
		if (field == null || field.isEmpty()) {
			throw new IllegalArgumentException("Field cannot be a null or empty string.");
		}

		this.field = field;
		return this;
	}

	// Filter
	public QueryFieldParameter<T> setEqualityFilter(String field,
	                                                T value) {
		return this.setFilter(field, ObjectifyQueryConditionOperator.Equal, value);
	}

	public QueryFieldParameter<T> setFilter(String field,
	                                        ObjectifyQueryConditionOperator operator,
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
		return "QueryFieldParameter [field=" + this.field + ", operator=" + this.operator + ", value=" + this.value
		        + ", ordering=" + this.ordering + "]";
	}

}
