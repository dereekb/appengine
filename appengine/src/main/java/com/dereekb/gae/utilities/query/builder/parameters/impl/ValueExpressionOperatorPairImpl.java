package com.dereekb.gae.utilities.query.builder.parameters.impl;

import com.dereekb.gae.utilities.query.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.ValueExpressionOperatorPair;

/**
 * {@link ValueExpressionOperatorPair} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ValueExpressionOperatorPairImpl<T>
        implements ValueExpressionOperatorPair<T> {

	protected T value = null;
	protected ExpressionOperator operator = ExpressionOperator.NO_OP;

	public ValueExpressionOperatorPairImpl() {};
	
	public ValueExpressionOperatorPairImpl(T value) {
		this(value, ExpressionOperator.EQUAL);
	}

	public ValueExpressionOperatorPairImpl(ExpressionOperator operator) {
		this(null, operator);
	}
	
	public ValueExpressionOperatorPairImpl(T value, ExpressionOperator operator) {
		super();
		this.setValue(value);
		this.setOperator(operator);
	}

	public static <T> ValueExpressionOperatorPairImpl<T> make(T value,
	                                                          ExpressionOperator operator) {
		return new ValueExpressionOperatorPairImpl<T>(value, operator);
	}

	@Override
	public T getValue() {
		return this.value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public ExpressionOperator getOperator() {
		return this.operator;
	}

	public void clearOperator() {
		this.setOperator(ExpressionOperator.NO_OP);
	}

	public void setOperator(ExpressionOperator operator) throws IllegalArgumentException {
		if (operator == null) {
			throw new IllegalArgumentException("Operator cannot be null.");
		}

		this.operator = operator;
	}
	
	public void copyFrom(ValueExpressionOperatorPair<T> pair) {
		this.setValue(pair.getValue());
		this.setOperator(pair.getOperator());
	}

	@Override
	public String toString() {
		return "ValueExpressionOperatorPairImpl [value=" + this.value + ", operator=" + this.operator + "]";
	}

}
