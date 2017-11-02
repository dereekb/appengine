package com.dereekb.gae.test.model.extension.generator.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.generation.GeneratorArg;
import com.dereekb.gae.model.extension.generation.GeneratorUtility;
import com.dereekb.gae.model.extension.generation.ModelGenerator;
import com.dereekb.gae.model.extension.generation.impl.GeneratorArgImpl;
import com.dereekb.gae.server.datastore.ForceSetter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.test.model.extension.generator.TestModelGenerator;
import com.dereekb.gae.test.model.extension.generator.TestModelGeneratorDelegate;

/**
 * {@link TestModelGenerator} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class TestModelGeneratorImpl<T extends UniqueModel>
        implements TestModelGenerator<T> {

	protected ForceSetter<T> setter;
	protected ModelGenerator<T> generator;

	public TestModelGeneratorImpl() {}

	public TestModelGeneratorImpl(ForceSetter<T> setter, ModelGenerator<T> generator) {
		this.setSetter(setter);
		this.setGenerator(generator);
	}

	@Override
	public ForceSetter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(ForceSetter<T> setter) {
		this.setter = setter;
	}

	public ModelGenerator<T> getGenerator() {
		return this.generator;
	}

	public void setGenerator(ModelGenerator<T> generator) {
		this.generator = generator;
	}

	// MARK: TestModelGenerator
	@Override
	public String getTypeName() {
		return this.generator.getTypeName();
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
	public T generate() {
		T model = this.generator.generate();
		this.setter.forceStore(model);
		return model;
	}

	@Override
	public T generate(GeneratorArg arg) {
		T model = this.generator.generate(arg);
		this.setter.forceStore(model);
		return model;
	}

	@Override
	public List<T> generate(int count) {
		return GeneratorUtility.generate(count, this);
	}

	@Override
	public List<T> generate(int count,
	                        GeneratorArg arg) {
		List<T> models = this.generator.generate(count, arg);
		this.setter.forceStore(models);
		return models;
	}

	@Override
	public T generate(TestModelGeneratorDelegate<T> delegate) {
		return this.generate(delegate, new GeneratorArgImpl(), 0);
	}

	@Override
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
		T model = this.generate();
		delegate.configureTestModel(model, arg, index);
		this.setter.forceStore(model);
		return model;
	}

	@Override
	public T generateModelWithoutKey() {
		return this.generator.generateModelWithoutKey();
	}

	@Override
	public T generateModel(ModelKey identifier) {
		T model = this.generator.generateModel(identifier);
		this.setter.forceStore(model);
		return model;
	}

	@Override
	public List<T> generateModels(Iterable<ModelKey> identifiers) {
		List<T> models = this.generator.generateModels(identifiers);
		this.setter.forceStore(models);
		return models;
	}

}
