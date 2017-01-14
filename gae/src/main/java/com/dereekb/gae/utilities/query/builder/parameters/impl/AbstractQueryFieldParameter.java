package com.dereekb.gae.utilities.query.builder.parameters.impl;

import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameter;
import com.dereekb.gae.utilities.query.builder.parameters.Parameter;
import com.dereekb.gae.utilities.query.builder.parameters.impl.QueryFieldParameterDencoder.ParameterImpl;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;

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
        implements ConfigurableEncodedQueryParameter {

	/**
	 * Empty string by default.
	 */
	public static final String DEFAULT_NULL_VALUE = "";

	private String field;
	private ExpressionOperator operator;
	private T value;

	private QueryResultsOrdering ordering;

	protected AbstractQueryFieldParameter() {}

	protected AbstractQueryFieldParameter(AbstractQueryFieldParameter<T> parameter) throws IllegalArgumentException {
		this((parameter != null) ? parameter.getField() : null, parameter);
	}

	protected AbstractQueryFieldParameter(String field, AbstractQueryFieldParameter<T> parameter)
	        throws IllegalArgumentException {
		if (parameter == null) {
			throw new IllegalArgumentException("Parameter cannot be null.");
		}

		this.setField(field);

		this.setOperator(parameter.getOperator());
		this.setValue(parameter.getValue());

		this.setOrdering(parameter.getOrdering());
	}

	protected AbstractQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		this.setField(field);
		this.setParameterString(parameterString);
	}

	protected AbstractQueryFieldParameter(String field, T value) {
		this.setEqualityFilter(field, value);
	}

	protected AbstractQueryFieldParameter(String field, ExpressionOperator operator, T value) {
		this.setFilter(field, operator, value);
	}

	public void setComparison(ExpressionOperator operator,
	                          T value)
	        throws IllegalArgumentException {
		this.setOperator(operator);
		this.setValue(value);
	}

	public ExpressionOperator getOperator() {
		return this.operator;
	}

	/**
	 * Sets the operator.
	 *
	 * @param operator
	 *            {@link ExpressionOperator}. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             if the operator is null.
	 */
	public void setOperator(ExpressionOperator operator) throws IllegalArgumentException {
		if (operator == null) {
			throw new IllegalArgumentException("Operator cannot be null.");
		}

		this.operator = operator;
	}

	public T getValue() {
		return this.value;
	}

	public AbstractQueryFieldParameter<T> setValue(T value) {
		this.value = value;
		return this;
	}

	public QueryResultsOrdering getOrdering() {
		return this.ordering;
	}

	public AbstractQueryFieldParameter<T> setOrdering(QueryResultsOrdering ordering) {
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
	                                                        T value)
	        throws IllegalArgumentException {
		return this.setFilter(field, ExpressionOperator.Equal, value);
	}

	public AbstractQueryFieldParameter<T> setFilter(String field,
	                                                ExpressionOperator operator,
	                                                T value)
	        throws IllegalArgumentException {
		this.setField(field);
		this.setOperator(operator);
		this.setValue(value);
		return this;
	}

	// MARK: QueryParameter
	@Override
	public String getParameterString() {
		Parameter parameter = this.getParameterRepresentation();
		return QueryFieldParameterDencoder.SINGLETON.encodeString(parameter);
	}

	// MARK: ConfigurableQueryParameter
	@Override
	public void setParameterString(String parameterString) throws IllegalArgumentException {
		Parameter parameter = QueryFieldParameterDencoder.SINGLETON.decodeString(parameterString);
		this.setParameterRepresentation(parameter);
	}

	public Parameter getParameterRepresentation() {
		String value = null;

		if (this.value == null) {

		} else {
			value = this.getParameterValue();
		}

		return new ParameterImpl(value, this.operator, this.ordering);
	}

	public void setParameterRepresentation(Parameter parameter) throws IllegalArgumentException {
		this.operator = parameter.getOperator();
		this.ordering = parameter.getOrdering();

		if (this.operator == ExpressionOperator.IsNull) {
			this.setNullParameterValue();
		} else {
			this.setParameterValue(parameter.getValue());
		}
	}

	/**
	 * Retrieves the current parameter value.
	 * 
	 * @return {@link String} value for the current value. Never {@code null}.
	 */
	protected abstract String getParameterValue();

	/**
	 * Sets the parameter value.
	 * 
	 * @param value
	 *            {@link String}. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             if the input value is not acceptable.
	 */
	protected abstract void setParameterValue(String value) throws IllegalArgumentException;

	/**
	 * Retrieves the string representation for a null value.
	 * 
	 * @return {@link String} value for the current value. Never {@code null}.
	 */
	protected String getNullParameterValue() throws IllegalArgumentException {
		return DEFAULT_NULL_VALUE;
	}

	/**
	 * Called when the value needs to be set null.
	 * 
	 * @throws IllegalArgumentException
	 *             if the input value is not acceptable.
	 */
	protected void setNullParameterValue() throws IllegalArgumentException {
		this.value = null;
	}

	@Override
	public String toString() {
		return "AbstractQueryFieldParameter [field=" + this.field + ", operator=" + this.operator + ", value="
		        + this.value + ", ordering=" + this.ordering + "]";
	}

}
