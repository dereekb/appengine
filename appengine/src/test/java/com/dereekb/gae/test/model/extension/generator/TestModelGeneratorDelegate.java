package com.dereekb.gae.test.model.extension.generator;

import com.dereekb.gae.model.extension.generation.GeneratorArg;

/**
 * Delegate for a model generator.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface TestModelGeneratorDelegate<T> {

	/**
	 * Configures the test model.
	 * <p>
	 * The input model will at-most have only a model key affixed to it.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 * @param arg
	 *            {@link GeneratorArg}. Never {@code null}.
	 */
	public void configureTestModel(T model,
	                               GeneratorArg arg,
	                               int index);

}
