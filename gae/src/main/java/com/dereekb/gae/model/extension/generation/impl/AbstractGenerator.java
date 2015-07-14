package com.dereekb.gae.model.extension.generation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

/**
 * Abstract {@link Generator} used for extending.
 *
 * @author dereekb
 *
 * @param <T>
 */
public abstract class AbstractGenerator<T>
        implements Generator<T>, Factory<T> {

	public Random getRandom() {
		return new Random();
	}

	public Random getRandom(Long seed) {
		return new Random(seed);
	}

	public AbstractGenerator() {}

	@Override
    public T make() throws FactoryMakeFailureException {
		return this.generate();
	}

	@Override
	public T generate() {
		return this.generate(new GeneratorArgImpl());
	}

	@Override
	public abstract T generate(GeneratorArg arg);

	/**
	 * By default, {@link AbstractGenerator} will call {@link #generate()} the
	 * number of times specified by the {@code count} parameter.
	 */
	@Override
	public List<T> generate(int count,
	                        GeneratorArg arg) {
		List<T> models = new ArrayList<T>();

		for (int i = 0; i < count; i += 1) {
			T model = this.generate(arg);
			models.add(model);
		}

		return models;
	}

}
