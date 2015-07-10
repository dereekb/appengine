package com.dereekb.gae.model.extension.generation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

public abstract class AbstractGenerator<T>
        implements Generator<T>, Factory<T> {

	private static final Integer DEFAULT_GENERATION_AMOUNT = 10;

	protected Integer generationAmount = DEFAULT_GENERATION_AMOUNT;

	public Random getRandom() {
		return new Random();
	}

	public Random getRandom(Long seed) {
		return new Random(seed);
	}

	public Integer getGenerationAmount() {
		return this.generationAmount;
	}

	public void setGenerationAmount(Integer generationAmount) {
		this.generationAmount = generationAmount;
	}

	@Override
    public T make() throws FactoryMakeFailureException {
		return this.generate();
	}

	@Override
	public T generate() {
		return this.generate(null);
	}

	@Override
	public abstract T generate(Long seed);

	/**
	 * By default, {@link AbstractGenerator} will call {@link #generate()} the
	 * number of times specified by the {@code count} parameter.
	 */
	@Override
	public List<T> generate(int count,
	                        Long seed) {
		List<T> models = new ArrayList<T>();

		for (int i = 0; i < count; i += 1) {
			T model = this.generate();
			models.add(model);
		}

		return models;
	}

}
