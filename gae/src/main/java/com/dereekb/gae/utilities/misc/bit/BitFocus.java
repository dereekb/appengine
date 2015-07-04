package com.dereekb.gae.utilities.misc.bit;

/**
 * Interface used for focusing on a specific set of bits.
 *
 * @author dereekb
 *
 * @param <T>
 *            Value type
 */
public interface BitFocus<T> {

	/**
	 * Focuses on the bits between the start and end values.
	 *
	 * For example:
	 *
	 * focusBits(3, 6) = 0011011000 -> 1011
	 *
	 * @param start
	 *            Start index.
	 * @param end
	 *            End index.
	 * @return New value containing the focused bits.
	 */
	public T focusBits(int start,
	                   int end);

	/**
	 * Focuses on the bits by applying a mask and focusing on the start index
	 * specified.
	 *
	 * For example:
	 *
	 * focusBits(0xFF0000, 2) = ABCDEF -> AB00
	 *
	 * @param mask
	 *            Mask to apply to the current value.
	 * @param start
	 *            Start index.
	 * @return New value containing the focused bits.
	 */
	public T focusBits(T mask,
	                   int start);

}
