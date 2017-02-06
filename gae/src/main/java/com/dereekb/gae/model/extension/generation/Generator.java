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
	 * @param arg
	 *            {@link GeneratorArg} to use. Cannot be null. Use
	 *            {@link #generate()} otherwise if no specific
	 *            {@link GeneratorArg} is available/necessary.
	 * @return A new model instance. Never null.
	 * @throws IllegalArgumentException
	 *             if {@code arg} is null.
	 */
	public T generate(GeneratorArg arg) throws IllegalArgumentException;

	/**
	 * Generates new instances.
	 *
	 * @param count
	 *            Number of instances to create.
	 * @param seed
	 *            Optional {@link GeneratorArg} to use. Can be null.
	 * @return Number of instances as count specified. Never null.
	 */
	public List<T> generate(int count,
	                        GeneratorArg arg);

}
