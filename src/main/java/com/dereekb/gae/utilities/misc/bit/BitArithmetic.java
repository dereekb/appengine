package com.dereekb.gae.utilities.misc.bit;

/**
 * Interface for performing Bit-wise logic.
 *
 * @author dereekb
 *
 * @param <T>
 *            Value type.
 * @see BitContainer
 */
public interface BitArithmetic<T> {

	// MARK: Shift
	/**
	 * Shifts bits to the left by the specified amount.
	 *
	 * Example:
	 *
	 * A 2 bit shift left: 001100 -> 110000
	 *
	 * @param bits
	 *            Amount of bits to shift by.
	 */
	public void bitShiftLeft(int bits);

	/**
	 * Shifts bits to the right by the specified amount.
	 *
	 * Example:
	 *
	 * A 2 bit shift right: 001100 -> 000011
	 *
	 * @param bits
	 *            Amount of bits to shift by.
	 */
	public void bitShiftRight(int bits);

	// MARK: Logic
	/**
	 * Performs a logical AND with the value and returns the result.
	 *
	 * @param value
	 *            Input value.
	 * @return Result of logical AND.
	 */
	public T and(T value);

	/**
	 * Performs a logical OR with the value and returns the result.
	 *
	 * @param value
	 *            Input value.
	 * @return Result of logical OR.
	 */
	public T or(T value);

	/**
	 * Performs a logical XOR with the value and returns the result.
	 *
	 * @param value
	 *            Input value.
	 * @return Result of logical XOR.
	 */
	public T xor(T value);

	/**
	 * Performs a logical NOT with the value and returns the result.
	 *
	 * @param value
	 *            Input value.
	 * @return Result of logical NOT.
	 */
	public T not(T value);

	/**
	 * Performs a logical AND with the value.
	 *
	 * @param value
	 *            Input value.
	 */
	public void applyAnd(T value);

	/**
	 * Performs a logical OR with the value.
	 *
	 * @param value
	 *            Input value.
	 */
	public void applyOr(T value);

	/**
	 * Performs a logical XOR with the value.
	 *
	 * @param value
	 *            Input value.
	 */
	public void applyXor(T value);

	/**
	 * Performs a logical NOT with the value.
	 *
	 * @param value
	 *            Input value.
	 */
	public void applyNot(T value);

}
