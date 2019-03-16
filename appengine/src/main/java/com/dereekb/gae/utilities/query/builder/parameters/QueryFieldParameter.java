package com.dereekb.gae.utilities.query.builder.parameters;

/**
 * 
 * @author dereekb
 *
 * @param <T>
 *            field parameter
 */
public interface QueryFieldParameter<T>
        extends ValueExpressionOperatorPair<T>, OrderedQueryParameter {

	/**
	 * Returns the field.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getField();

	/**
	 * Returns the second filter expression, if applicable.
	 * 
	 * @return {@link ValueExpressionOperatorPair}. {@code null} if not
	 *         specified.
	 */
	public ValueExpressionOperatorPair<T> getSecondFilter();

}
