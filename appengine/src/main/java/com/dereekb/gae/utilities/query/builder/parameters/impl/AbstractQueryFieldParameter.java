package com.dereekb.gae.utilities.query.builder.parameters.impl;

import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;
import com.dereekb.gae.utilities.query.ExpressionOperator;
import com.dereekb.gae.utilities.misc.parameters.ConfigurableEncodedParameter;
import com.dereekb.gae.utilities.query.builder.parameters.EncodedQueryParameter;
import com.dereekb.gae.utilities.query.builder.parameters.EncodedValueExpressionOperatorPair;
import com.dereekb.gae.utilities.query.builder.parameters.QueryFieldParameter;
import com.dereekb.gae.utilities.query.builder.parameters.ValueExpressionOperatorPair;
import com.dereekb.gae.utilities.query.builder.parameters.impl.QueryFieldParameterDencoder.EncodedQueryParameterImpl;
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
public abstract class AbstractQueryFieldParameter<T> extends ValueExpressionOperatorPairImpl<T>
        implements ConfigurableEncodedParameter, QueryFieldParameter<T> {

	/**
	 * Public, temporary field to use in some cases where the field is ignored.
	 */
	public static final String TEMP_FIELD = "TMP";

	/**
	 * Empty string by default.
	 */
	public static final String DEFAULT_NULL_VALUE = "";

	private String field;

	private ValueExpressionOperatorPair<T> secondFilter;

	private QueryResultsOrdering ordering = null;

	/**
	 * Avoid using, as it is trusts sub classes to properly initialize.
	 */
	protected AbstractQueryFieldParameter() {}

	protected AbstractQueryFieldParameter(AbstractQueryFieldParameter<T> parameter) throws IllegalArgumentException {
		this((parameter != null) ? parameter.getField() : null, parameter);
	}

	protected AbstractQueryFieldParameter(String field) {
		this.setField(field);
		this.clearOperator();
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

	protected AbstractQueryFieldParameter(String field,
	        ExpressionOperator operator,
	        T value,
	        QueryResultsOrdering ordering) {
		this(field, operator, value);
		this.setOrdering(ordering);
	}

	public void setAsComparison(ExpressionOperator operator,
	                            T value)
	        throws IllegalArgumentException {
		this.setOperator(operator);
		this.setValue(value);
	}

	@Override
	public ValueExpressionOperatorPair<T> getSecondFilter() {
		return this.secondFilter;
	}

	public void clearSecondFilter() {
		this.setSecondFilter(null);
	}

	public void setSecondFilter(ValueExpressionOperatorPair<T> secondFilter) {
		this.secondFilter = secondFilter;
	}

	public void setPrimaryFilter(T value,
	                             ExpressionOperator operator) {
		this.setPrimaryFilter(ValueExpressionOperatorPairImpl.make(value, operator));
	}

	public void setPrimaryFilter(ValueExpressionOperatorPair<T> pair) {
		this.copyFrom(pair);
	}

	public void setSecondFilter(T value,
	                            ExpressionOperator operator) {
		this.setSecondFilter(ValueExpressionOperatorPairImpl.make(value, operator));
	}

	// MARK: Ordering
	@Override
	public QueryResultsOrdering getOrdering() {
		return this.ordering;
	}

	public void clearOrdering() {
		this.ordering = null;
	}

	public AbstractQueryFieldParameter<T> setOrdering(QueryResultsOrdering ordering) {
		this.ordering = ordering;
		return this;
	}

	public AbstractQueryFieldParameter<T> sortDescending() {
		return this.setOrdering(QueryResultsOrdering.Descending);
	}

	public AbstractQueryFieldParameter<T> sortAscending() {
		return this.setOrdering(QueryResultsOrdering.Ascending);
	}

	/**
	 * Makes this field only a sorting field.
	 * <p>
	 * Equivalent to calling {@link #clearOperator()} and
	 * {@link #setOrdering(QueryResultsOrdering)}.
	 *
	 * @param ordering
	 *            {@link QueryResultsOrdering}. Never {@code null}.
	 */
	public AbstractQueryFieldParameter<T> onlySort(QueryResultsOrdering ordering) {
		this.clearOperator();
		return this.setOrdering(ordering);
	}

	@Override
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
		return this.setFilter(field, ExpressionOperator.EQUAL, value);
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

	/**
	 * {@inheritDoc}
	 * <p>
	 * If the operator is an inequality, sorting for this type will also be
	 * initalized.
	 */
	@Override
	public void setOperator(ExpressionOperator operator) throws IllegalArgumentException {
		super.setOperator(operator);

		if (operator.isInequality()) {
			this.initSortingForInequality();
		}
	}

	/**
	 * Initializes sorting for an inequality operator.
	 */
	protected void initSortingForInequality() {
		this.sortAscending();
	}

	// MARK: KeyedEncodedQueryParameter
	@Override
	public String getParameterKey() {
		return this.field;
	}

	@Override
	public String keyValue() {
		return this.getParameterKey();
	}

	@Override
	public String getParameterString() {
		EncodedQueryParameter parameter = this.getParameterRepresentation();
		return QueryFieldParameterDencoder.SINGLETON.encodeString(parameter);
	}

	// MARK: ConfigurableQueryParameter
	@Override
	public void setParameterString(String parameterString) throws IllegalArgumentException {
		EncodedQueryParameter parameter = QueryFieldParameterDencoder.SINGLETON.decodeString(parameterString);
		this.setParameterRepresentation(parameter);
	}

	public EncodedQueryParameter getParameterRepresentation() {
		String parameterValue = null;

		switch (this.operator) {
			// Don't attempt to encode parameter value for nulls and no-ops as
			// they'll be ignored later anyways.
			case IS_NULL:
			case NO_OP:
				parameterValue = "";
				break;
			default:
				parameterValue = this.getParameterValue();
				break;
		}

		EncodedValueExpressionOperatorPair encodedSecondFilter = null;

		if (this.operator.isInequality()) {
			encodedSecondFilter = this.getEncodedSecondFilter();

			if (encodedSecondFilter != null && encodedSecondFilter.getOperator().isInequality() == false) {
				encodedSecondFilter = null;
			}
		}

		return new EncodedQueryParameterImpl(parameterValue, this.operator, this.ordering, encodedSecondFilter);
	}

	public final void setParameterRepresentation(EncodedQueryParameter parameter) throws IllegalArgumentException {
		this.operator = parameter.getOperator();
		this.ordering = parameter.getOrdering();

		switch (this.operator) {
			// Don't attempt to decode/set parameter value for nulls and no-ops.
			case IS_NULL:
			case NO_OP:
				this.setNullParameterValue();
				break;
			default:
				this.setParameterValue(parameter.getValue());
				break;
		}

		// Second Filter
		EncodedValueExpressionOperatorPair secondFilter = parameter.getSecondFilter();

		if (this.operator.isInequality() == false) {
			secondFilter = null;	// Only set if input is inequality.
			                    	// Otherwise, clear.
		}

		this.setEncodedSecondFilter(secondFilter);
	}

	// MARK: Second Filter
	public EncodedValueExpressionOperatorPair getEncodedSecondFilter() {
		if (this.secondFilter != null) {
			return this.encodeValueExpressionOperatorPair(this.secondFilter);
		} else {
			return null;
		}
	}

	public void setEncodedSecondFilter(EncodedValueExpressionOperatorPair secondFilter) {
		if (secondFilter == null) {
			this.clearSecondFilter();
		} else {
			ValueExpressionOperatorPairImpl<T> pair = this.decodeValueExpressionOperatorPair(secondFilter);
			this.setSecondFilter(pair);
		}
	}

	/**
	 * Retrieves the current parameter value.
	 *
	 * @return {@link String} value for the current value. Never {@code null}.
	 */
	protected String getParameterValue() {
		return this.encodeParameterValue(this.value);
	}

	/**
	 * Sets the parameter value.
	 *
	 * @param value
	 *            {@link String}. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             if the input value is not acceptable.
	 */
	protected final void setParameterValue(String parameterValue) throws IllegalArgumentException {
		T value = this.decodeParameterValue(parameterValue);
		this.setValue(value);
	}

	protected EncodedValueExpressionOperatorPair encodeValueExpressionOperatorPair(ValueExpressionOperatorPair<T> pair) {
		T value = pair.getValue();
		ExpressionOperator operator = pair.getOperator();
		String encodedValue = null;

		if (value == null) {
			operator = ExpressionOperator.IS_NULL;
		} else {
			encodedValue = this.encodeParameterValue(value);
		}

		return new EncodedValueExpressionOperatorPairImpl(encodedValue, operator);
	}

	protected ValueExpressionOperatorPairImpl<T> decodeValueExpressionOperatorPair(EncodedValueExpressionOperatorPair pair) {
		T value = this.decodeParameterValue(pair.getValue());
		return new ValueExpressionOperatorPairImpl<T>(value, pair.getOperator());
	}

	/**
	 * Encodes the parameter value.
	 *
	 * @return {@link String} value for the current value. Never {@code null}.
	 */
	protected abstract String encodeParameterValue(T value);

	/**
	 * Decodes the parameter value.
	 *
	 * @param value
	 *            {@link String}. Never {@code null}.
	 * @return value. May be {@code null}.
	 * @throws IllegalArgumentException
	 *             if the input value is not acceptable.
	 */
	protected abstract T decodeParameterValue(String value) throws IllegalArgumentException;

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

	// MARK: Utility
	public static <T extends AbstractQueryFieldParameter<?>> boolean isNullOrHasNullValue(T parameter) {
		return (parameter == null || parameter.getValue() == null);
	}

	@Override
	public String toString() {
		return "AbstractQueryFieldParameter [field=" + this.field + ", secondFilter=" + this.secondFilter
		        + ", ordering=" + this.ordering + ", value=" + this.value + ", operator=" + this.operator + "]";
	}

}
