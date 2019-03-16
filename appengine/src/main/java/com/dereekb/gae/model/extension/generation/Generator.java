package com.dereekb.gae.model.extension.generation;

import java.util.List;

/**
 * Model generator.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface Generator<T> {

	/**
	 * Generates a new model instance.
	 *
	 * @return A new model instance. Never {@code null}.
	 */
	public T generate();

	/**
	 * Generates a new instance.
	 *
	 * @param arg
	 *            {@link GeneratorArg} to use. Cannot be {@code null}. Use
	 *            {@link #generate()} otherwise if no specific
	 *            {@link GeneratorArg} is available/necessary.
	 * @return A new model instance. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             if {@code arg} is {@code null}.
	 */
	public T generate(GeneratorArg arg) throws IllegalArgumentException;

	/**
	 * Generates new instances.
	 *
	 * @param count
	 *            Number of instances to create.
	 * @param arg
	 *            Optional {@link GeneratorArg} to use. Can be {@code null}.
	 * @return Number of instances as count specified. Never {@code null}.
	 */
	public List<T> generate(int count,
	                        GeneratorArg arg);

}
