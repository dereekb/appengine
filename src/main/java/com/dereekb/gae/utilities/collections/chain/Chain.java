package com.dereekb.gae.utilities.collections.chain;



/**
 * Special linked list implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface Chain<T>
        extends Iterable<T> {

	/**
	 * Gets the current chain link's value.
	 *
	 * @return value
	 */
	public T getValue();

	/**
	 * @return Next {@link Chain} value, or {@code null} if the end.
	 */
	public Chain<T> next();

	/**
	 * Whether or not the chain has a next value.
	 *
	 * @return {@code true} if not the end of a chain.
	 */
    public boolean hasNext();

	/**
	 * Inserts an element into the chain.
	 *
	 * @param element
	 *            Element to insert.
	 * @return {@link Chain} value created. Will be equal to {@link #next()}.
	 * @throws NullPointerException
	 *             thrown if the chain does not allow {@code null} values.
	 */
	public Chain<T> insert(T element) throws NullPointerException;

	/**
	 * Adds the element to the <i>end</i> of the chain.
	 *
	 * @param element
	 *            Element to add.
	 * @return {@link Chain} value created.
	 *
	 * @throws NullPointerException
	 *             thrown if the chain does not allow {@code null} values.
	 */
	public Chain<T> chain(T element) throws NullPointerException;

	/**
	 * Removes the next chained value, and bridges the chain together.
	 *
	 * @return value removed, or {@code null} if no value.
	 */
	public T removeNext();

	/**
	 * Removes all values after the chained value.
	 */
	public void breakNext();

	/**
	 * @return {@link Iterable} for {@link Chain} values. Never {@code null}.
	 */
	public Iterable<? extends Chain<T>> chainIterable();

}
