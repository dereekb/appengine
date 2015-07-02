package com.dereekb.gae.utilities.collections.chain;

/**
 * Default implementation of the {@link Chain} class. Used for chaining arbitrary values together.
 * 
 * @author dereekb
 *
 * @param <T> Value type
 */
public class ValuesChain<T> extends Chain<ValuesChain<T>> {

	private T value;

	public ValuesChain(T value) {
		this.value = value;
	}

	@Override
	protected ValuesChain<T> self() {
		return this;
	}

	public ValuesChain<T> chain(T value) {
		ValuesChain<T> newChain = new ValuesChain<T>(value);
		return this.chain(newChain);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.separatedToString(", ");
	}

	public String toString(String separator) throws NullPointerException {
		if (separator == null) {
			throw new NullPointerException("Separator cannot be null.");
		}

		return this.separatedToString(separator);
	}

	protected String separatedToString(String separator) {
		return (this.value + ((this.next != null) ? separator + this.next.toString(separator) : ""));
	}

}
