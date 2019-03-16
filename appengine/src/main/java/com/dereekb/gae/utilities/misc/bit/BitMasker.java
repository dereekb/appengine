package com.dereekb.gae.utilities.misc.bit;

/**
 * Interface for generating bit masks.
 *
 * @author dereekb
 *
 * @param <T>
 *            Mask container type.
 */
public interface BitMasker<T> {

	/**
	 * Creates a mask of bits between the start and end values.
	 *
	 * Example: start = 3, end = 5 -> 00011000
	 *
	 * @param start
	 *            Index to start at.
	 * @param end
	 *            Index to end at.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if the index is out of bounds.
	 * @return Value containing the mask.
	 */
	public T makeMask(int start,
	                  int end) throws IndexOutOfBoundsException;

	/**
	 * Creates a mask of all bits after the input index.
	 *
	 * Example: index = 5 -> 11100000
	 *
	 * @param index
	 *            Index to start at.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if the index is out of bounds.
	 * @return Value containing the mask.
	 */
	public T makeLeftMask(int index) throws IndexOutOfBoundsException;

	/**
	 * Creates a mask of all bits before the input index.
	 *
	 * Example: index = 5 -> 00011111
	 *
	 * @param index
	 *            Index to end at.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if the index is out of bounds.
	 * @return Value containing the mask.
	 */
	public T makeRightMask(int index) throws IndexOutOfBoundsException;

}
