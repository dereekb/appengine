package com.dereekb.gae.model.extension.generation.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.utilities.factory.Factory;

/**
 * {@link Generator} implementation that wraps a {@link Factory}.
 *
 * @author dereekb
 */
public class FactoryGeneratorImpl<T>
        implements Generator<T> {

	private Factory<T> factory;

	public FactoryGeneratorImpl(Factory<T> factory) {
		this.factory = factory;
	}

	public Factory<T> getFactory() {
		return this.factory;
	}

	public void setFactory(Factory<T> factory) {
		this.factory = factory;
	}

	// Generator
	@Override
	public T generate() {
		return this.factory.make();
	}

	@Override
	public T generate(GeneratorArg arg) {
		return this.factory.make();
	}

	@Override
	public List<T> generate(int count,
	                        GeneratorArg arg) {
		List<T> models = new ArrayList<T>();

		for (int i = 0; i < count; i += 1) {
			T model = this.factory.make();
			models.add(model);
		}

		return models;
	}

}
