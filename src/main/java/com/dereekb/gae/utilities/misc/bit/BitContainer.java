package com.dereekb.gae.utilities.misc.bit;

/**
 * An editable container of bits.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface BitContainer<T>
        extends BitArithmetic<T> {

	/**
	 * Returns the number of bits.
	 *
	 * @return {@link Integer} equal to the number of bits. Never null.
	 */
	public Integer getBitLength();

	/**
	 * Reads the bit at the specified index, checking whether or not it is on or
	 * off.
	 *
	 * @param index
	 *            Index of the bit to set.
	 * @return <code>true</code> if the bit is toggled on.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if the index is out of bounds.
	 */
	public boolean getBit(int index) throws IndexOutOfBoundsException;

	/**
	 * Sets the bit at the specified index to on or off.
	 *
	 * @param on
	 *            Position to set the bit to.
	 * @param index
	 *            Index of the bit to set.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if the index is out of bounds.
	 */
	public void setBit(boolean on,
	                   int index) throws IndexOutOfBoundsException;

	/**
	 * Returns the raw value of this container.
	 *
	 * @return Current value. Never null.
	 */
	public T getValue();

	/**
	 * Sets the raw value of this container.
	 *
	 * @param value
	 *            New value. Setting null is acceptable, and will generally set
	 *            the value to the default.
	 * @throws IllegalArgumentException
	 *             if <code>value</code> is not acceptable.
	 */
	public void setValue(T value) throws IllegalArgumentException;

}
