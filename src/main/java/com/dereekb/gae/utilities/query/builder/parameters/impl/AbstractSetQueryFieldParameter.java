package com.dereekb.gae.utilities.query.builder.parameters.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.query.builder.parameters.impl.ModelKeySetQueryFieldParameterBuilder.ModelKeySetQueryFieldParameter;

/**
 * Abstract Set query field parameter.
 *
 * @author dereekb
 *
 */
public abstract class AbstractSetQueryFieldParameter<T> extends AbstractQueryFieldParameter<Set<T>> {

	public static final Integer MAX_KEYS_ALLOWED = 30;

	private static final Set<ExpressionOperator> ALLOWED_SET_OPERATORS = SetUtility.makeSet(ExpressionOperator.HAS,
	        ExpressionOperator.EQUAL);

	protected AbstractSetQueryFieldParameter() {};

	protected AbstractSetQueryFieldParameter(AbstractQueryFieldParameter<Set<T>> parameter) throws IllegalArgumentException {
		super(parameter);
	}

	public AbstractSetQueryFieldParameter(String field, AbstractQueryFieldParameter<Set<T>> parameter)
	        throws IllegalArgumentException {
		super(field, parameter);
	}

	protected AbstractSetQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		this.setField(field);
		this.setParameterString(parameterString);
	}

	protected AbstractSetQueryFieldParameter(String field, Collection<T> value) throws IllegalArgumentException {
		this.setField(field);
		this.setValue(value);
		this.setOperator(ExpressionOperator.HAS);
	}

	// MARK: Override
	public void setValue(Collection<T> value) {
		if (value.size() > MAX_KEYS_ALLOWED) {
			throw new IllegalArgumentException("Only " + MAX_KEYS_ALLOWED + " keys are allowed for this query.");
		}

		Set<T> set = new HashSet<T>(value);
		this.setValue(set);
	}

	@Override
	public void setValue(Set<T> value) {
		if (value == null || value.isEmpty()) {
			throw new IllegalArgumentException("Set cannot be empty.");
		}

		Set<T> set = new HashSet<T>(value);
		super.setValue(set);
	}

	public void setSingleValue(T value) {
		this.assertSingleValueIsValid(value);

		Set<T> set = SetUtility.wrap(value);
		super.setValue(set);
	}

	protected void assertSingleValueIsValid(T value) {
		if (value == null) {
			throw new IllegalArgumentException("Single value cannot be null.");
		}
	}

	@Override
	public void setOperator(ExpressionOperator operator) throws IllegalArgumentException {
		if (ALLOWED_SET_OPERATORS.contains(operator) == false) {
			throw new IllegalArgumentException("Disallowed operator. Try using IN.");
		}

		super.setOperator(operator);
	}

	// MARK: AbstractQueryFieldParameter
	@Override
	protected String encodeParameterValue(Set<T> values) {
		return this.encodeValuesFromString(values);
	}

	@Override
	protected Set<T> decodeParameterValue(String value) throws IllegalArgumentException {
		return SetUtility.newHashSet(this.decodeValuesFromString(value));
	}

	protected abstract String encodeValuesFromString(Collection<T> values);

	protected abstract Collection<T> decodeValuesFromString(String value);

	// MARK: Utility
	public static <T extends ModelKeySetQueryFieldParameter> boolean isNullOrHasNoValue(T parameter) {
		return (parameter == null || parameter.getValue() == null || parameter.getValue().isEmpty());
	}

}
