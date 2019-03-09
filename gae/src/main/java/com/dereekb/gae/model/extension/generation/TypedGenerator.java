package com.dereekb.gae.model.extension.generation;

import java.util.List;

/**
 * Generator that supports a 'type' input keyed by string.
 * 
 * @author dereekb
 * 
 * @param <T>
 *            model type
 */
public interface TypedGenerator<T>
        extends Generator<T> {

	/**
	 * Generates a single model.
	 * 
	 * @param count
	 *            Amount of objects to generate.
	 * @param type
	 *            Type parameter. Effect this has on results is up to the
	 *            implementation.
	 * @return New object. Never {@code null}.
	 */
	public T generate(String type);

	/**
	 * Generates several objects.
	 * 
	 * @param count
	 *            Amount of objects to generate.
	 * @param type
	 *            Type parameter. Effect this has on results is up to the
	 *            implementation.
	 * @return {@link List} of new objects. Never {@code null}.
	 */
	public List<T> generate(int count,
	                        String type);

}
