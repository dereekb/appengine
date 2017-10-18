package com.dereekb.gae.test.model.extension.generator.utility;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.impl.GeneratorArgImpl;
import com.dereekb.gae.test.model.extension.generator.TestModelGeneratorDelegate;

/**
 * Non-static utility for a generator.
 * 
 * @author dereekb
 *
 * @param <T>
 */
public class TestGeneratorDelegateUtility<T> {

	private Generator<T> generator;

	public Generator<T> getGenerator() {
		return this.generator;
	}

	public void setGenerator(Generator<T> generator) {
		if (generator == null) {
			throw new IllegalArgumentException("generator cannot be null.");
		}

		this.generator = generator;
	}

	// MARK: Utilities
	public T generate(TestModelGeneratorDelegate<T> delegate) {
		return this.generate(delegate, new GeneratorArgImpl(), 0);
	}

	public List<T> generate(int count,
	                        TestModelGeneratorDelegate<T> delegate) {
		List<T> models = new ArrayList<T>();
		GeneratorArg arg = new GeneratorArgImpl();

		for (int i = 0; i < count; i += 1) {
			T model = this.generate(delegate, arg, i);
			models.add(model);
		}

		return models;
	}

	public T generate(TestModelGeneratorDelegate<T> delegate,
	                  GeneratorArg arg,
	                  int index) {
		T model = this.generator.generate();
		delegate.configureTestModel(model, arg, index);
		return model;
	}

}
