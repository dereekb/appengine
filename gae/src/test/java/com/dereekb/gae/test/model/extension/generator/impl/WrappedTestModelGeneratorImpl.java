package com.dereekb.gae.test.model.extension.generator.impl;

import java.util.List;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.server.datastore.Setter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.test.model.extension.generator.TestModelGeneratorDelegate;

/**
 * Abstract {@link TestModelGenerator} implementation that wraps another
 * generator, allowing overriding selective components.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class WrappedTestModelGeneratorImpl<T extends UniqueModel>
        implements TestModelGenerator<T> {

	private TestModelGenerator<T> generator;

	protected WrappedTestModelGeneratorImpl(TestModelGenerator<T> generator) throws IllegalArgumentException {
		this.setGenerator(generator);
	}

	public TestModelGenerator<T> getGenerator() {
		return this.generator;
	}

	public void setGenerator(TestModelGenerator<T> generator) throws IllegalArgumentException {
		if (generator == null) {
			throw new IllegalArgumentException("Generator cannot be null.");
		}

		this.generator = generator;
	}

	@Override
	public String getModelType() {
		return this.generator.getModelType();
	}

	@Override
	public ModelKey generateKey() {
		return this.generator.generateKey();
	}

	@Override
	public List<ModelKey> generateKeys(int count) {
		return this.generator.generateKeys(count);
	}

	@Override
	public T generateModelWithoutKey() {
		return this.generator.generateModelWithoutKey();
	}

	@Override
	public T generate(GeneratorArg arg) throws IllegalArgumentException {
		return this.generator.generate(arg);
	}

	@Override
	public Setter<T> getSetter() {
		return this.generator.getSetter();
	}

	@Override
	public T generate() {
		return this.generator.generate();
	}

	@Override
	public List<T> generate(int count) {
		return this.generator.generate(count);
	}

	@Override
	public List<T> generate(int count,
	                        GeneratorArg arg) {
		return this.generator.generate(count, arg);
	}

	@Override
	public T generateModel(ModelKey identifier) {
		return this.generator.generateModel(identifier);
	}

	@Override
	public List<T> generateModels(Iterable<ModelKey> identifiers) {
		return this.generator.generateModels(identifiers);
	}

	@Override
	public T generate(TestModelGeneratorDelegate<T> delegate) {
		return this.generator.generate(delegate);
	}

	@Override
	public List<T> generate(int count,
	                        TestModelGeneratorDelegate<T> delegate) {
		return this.generator.generate(count, delegate);
	}

}
