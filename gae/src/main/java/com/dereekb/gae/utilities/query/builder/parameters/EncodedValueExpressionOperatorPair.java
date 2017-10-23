package com.dereekb.gae.utilities.query.builder.parameters;

/**
 * Encoded {@link ValueExpressionOperatorPair}. Has an encoded string value.
 * 
 * @author dereekb
 *
 */
public interface EncodedValueExpressionOperatorPair
        extends ExpressionOperatorParameter {

	/**
	 * Returns the string representation of the value.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getValue();

}
