package com.dereekb.gae.model.extension.generation.impl;

import java.util.List;

import com.dereekb.gae.model.extension.data.conversion.DirectionalConverter;
import com.dereekb.gae.model.extension.generation.Generator;
import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.utilities.collections.SingleItem;

/**
 * {@link Generator} implementation that uses a {@link DirectionalConverter} to
 * convert models from a different generator.
 *
 * @author dereekb
 *
 * @param <I>
 *            Input type.
 * @param <O>
 *            Generated type.
 */
public class DerivedGeneratorImpl<I, O>
        implements Generator<O> {

	private Generator<I> generator;
	private DirectionalConverter<I, O> converter;

	public DerivedGeneratorImpl() {}

	public DerivedGeneratorImpl(Generator<I> generator, DirectionalConverter<I, O> converter) {
		this.generator = generator;
		this.converter = converter;
	}

	public Generator<I> getGenerator() {
		return this.generator;
	}

	public void setGenerator(Generator<I> generator) {
		this.generator = generator;
	}

	public DirectionalConverter<I, O> getConverter() {
		return this.converter;
	}

	public void setConverter(DirectionalConverter<I, O> converter) {
		this.converter = converter;
	}

	// Generator
	@Override
	public O generate() {
		return this.generate(null);
	}

	@Override
	public O generate(GeneratorArg arg) {
		I model = this.generator.generate(arg);
		return this.converter.convert(SingleItem.withValue(model)).get(0);
	}

	@Override
	public List<O> generate(int count,
	                        GeneratorArg arg) {
		List<I> models = this.generator.generate(count, arg);
		return this.converter.convert(models);
	}

}
