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

	protected Random random = new Random();
	protected Integer generationAmount = DEFAULT_GENERATION_AMOUNT;

	protected Long randomPositiveLong() {
		return Math.abs(this.random.nextLong());
	}

	protected Integer randomPositiveInt() {
		return Math.abs(this.random.nextInt());
	}

	public Random getRandom() {
		return this.random;
	}

	public void setRandom(Random random) {
		this.random = random;
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
	public abstract T generate();

	@Override
    public List<T> generate(int count) {
		List<T> models = new ArrayList<T>();

		for (int i = 0; i < count; i += 1) {
			T model = this.generate();
			models.add(model);
		}

		return models;
	}

}
