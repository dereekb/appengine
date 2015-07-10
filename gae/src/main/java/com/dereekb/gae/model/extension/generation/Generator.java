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
	 * Generates a new model instance.
	 *
	 * @return A new model instance. Never null.
	 */
	public T generate();

	/**
	 * Generates a new instance.
	 *
	 * @param seed
	 *            Optional randomization seed to use. Can be null.
	 * @return A new model instance. Never null.
	 */
	public T generate(Long seed);

	/**
	 * Generates new instances.
	 *
	 * @param count
	 *            Number of instances to create.
	 * @param seed
	 *            Optional randomization seed to use. Can be null.
	 * @return Number of instances as count specified. Never null.
	 */
	public List<T> generate(int count,
	                        Long seed);

}
