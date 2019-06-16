package com.dereekb.gae.utilities.query.builder.parameters.impl;

import com.dereekb.gae.utilities.query.ExpressionOperator;
import com.dereekb.gae.utilities.query.builder.parameters.EncodedValueExpressionOperatorPair;

/**
 * {@link EncodedValueExpressionOperatorPair} implementation.
 * 
 * @author dereekb
 *
 */
public class EncodedValueExpressionOperatorPairImpl
        implements EncodedValueExpressionOperatorPair {

	protected String value;
	protected ExpressionOperator operator;

	public EncodedValueExpressionOperatorPairImpl(EncodedValueExpressionOperatorPair pair) {
		this(pair.getValue(), pair.getOperator());
	}

	public EncodedValueExpressionOperatorPairImpl(String value, ExpressionOperator operator) {
		this.setValue(value);
		this.setOperator(operator);
	}

	public static EncodedValueExpressionOperatorPairImpl tryCopy(EncodedValueExpressionOperatorPair filter) {
		if (filter != null) {
			return new EncodedValueExpressionOperatorPairImpl(filter);
		} else {
			return null;
		}
	}

	public static EncodedValueExpressionOperatorPairImpl tryMake(String value,
	                                                             ExpressionOperator operator) {
		if (value != null) {
			return new EncodedValueExpressionOperatorPairImpl(value, operator);
		} else {
			return null;
		}
	}

	@Override
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) throws IllegalArgumentException {
		if (value == null) {
			throw new IllegalArgumentException("Parameter value cannot be null.");
		}

		this.value = value;
	}

	@Override
	public ExpressionOperator getOperator() {
		return this.operator;
	}

	public void setOperator(ExpressionOperator operator) {
		this.operator = operator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.operator == null) ? 0 : this.operator.hashCode());
		result = prime * result + ((this.value == null) ? 0 : this.value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		
		if (EncodedValueExpressionOperatorPair.class.isAssignableFrom(obj.getClass()) == false) {
			return false;
		}
		
		EncodedValueExpressionOperatorPair other = (EncodedValueExpressionOperatorPair) obj;
		
		if (this.operator != other.getOperator()) {
			return false;
		}
		if (this.value == null) {
			if (other.getValue() != null) {
				return false;
			}
		} else if (!this.value.equals(other.getValue())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "EncodedValueExpressionOperatorPairImpl [value=" + this.value + ", operator=" + this.operator + "]";
	}

}
