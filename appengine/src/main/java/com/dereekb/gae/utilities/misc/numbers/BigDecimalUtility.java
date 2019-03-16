package com.dereekb.gae.utilities.misc.numbers;

import java.math.BigDecimal;

/**
 * Utility for converting to/from BigDecimal
 * 
 * @author dereekb
 *
 * @param <N>
 *            number type
 */
public interface BigDecimalUtility<N extends Number> {

	/**
	 * Same as {@link #toDecimal(Number)} but will suppress any
	 * {@link DecimalPrecisionLostException} exception.
	 * 
	 * @param input {@link Number}. Can be {@code null}.
	 * @return {@link BigDecimal}, or {@code null} if input is {@code null}.
	 */
	public BigDecimal quickToDecimal(N input);

	/**
	 * Converts from a BigDecimal to a Number. If null, will return null.
	 * 
	 * @param input {@link BigDecimal}. Can be {@code null}.
	 * @return {@link Number}, or {@code null} if input is {@code null}.
	 */
	public N fromDecimal(BigDecimal input);

	/**
	 * Converts from a Number to BigDecimal. If null, will return null.
	 * 
	 * @param input {@link Number}. Can be {@code null}.
	 * @return {@link BigDecimal}, or {@code null} if input is {@code null}.
	 */
	public BigDecimal toDecimal(N input) throws DecimalPrecisionLostException;

}
