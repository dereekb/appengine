package com.dereekb.gae.utilities.misc.numbers;

/**
 * Calculator for a {@link Number}.
 * 
 * @author dereekb
 *
 * @param <N>
 *            number type
 */
public interface Calculator<N extends Number> {

	public N zero();

	public N add(N a,
	             N b);

	public N subtract(N a,
	                  N b);

}
