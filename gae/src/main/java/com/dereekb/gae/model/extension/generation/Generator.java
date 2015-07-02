package com.dereekb.gae.model.extension.generation;

import java.util.List;

/**
 * Generator of type <T>.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface Generator<T> {

	/**
	 * Generates a new instance.
	 * 
	 * @return
	 */
	public T generate();

	/**
	 * Generates new instances.
	 *
	 * @param count
	 *            Number of instances to create.
	 * @return Number of instances as count specified.
	 */
	public List<T> generate(int count);

}
