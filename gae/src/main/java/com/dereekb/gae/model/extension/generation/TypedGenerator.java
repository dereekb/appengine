package com.dereekb.gae.model.extension.generation;

import java.util.List;

/**
 * Generator that supports a 'type' input keyed by string.
 * 
 * @author dereekb
 * 
 * @param <T>
 */
public interface TypedGenerator<T>
        extends Generator<T> {

	/**
	 * Generates a single model.
	 * 
	 * @param count
	 *            Amount of objects to generate.
	 * @param type
	 *            Type parameter. Affect this has on results is up to the implementation.
	 * @return Single generated object.
	 */
	public T generate(String type);

	/**
	 * Generates several objects.
	 * 
	 * @param count
	 *            Amount of objects to generate.
	 * @param type
	 *            Type parameter. Affect this has on results is up to the implementation.
	 * @return List of generated objects.
	 */
	public List<T> generate(int count,
	                        String type);

}
